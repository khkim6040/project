package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiBlockStatement;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiForStatement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReturnStatement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchStatement;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.PsiWhileStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class for detecting 'Message Chain' code smells.
 * This class extends BaseDetectAction, enabling it to analyze code and identify instances where a sequence of method
 * calls is chained together over a defined threshold length, indicating a code smell.
 *
 * @author Jinyoung Kim
 * @author Chanho Song
 */
public class MessageChain extends BaseDetectAction {

    /**
     * Returns the unique story ID for the Message Chain detection.
     * This identifier is used for differentiating between various detection stories.
     *
     * @return A String representing the unique story ID for 'Message Chain'.
     */
    @Override
    public String storyID() {
        return "MC";
    }

    /**
     * Provides the name of the detection story in a format suitable for display.
     * This method returns the name of the story focused on identifying message chains in methods.
     *
     * @return A String representing the name of the detection story.
     */
    @Override
    public String storyName() {
        return "Message Chain";
    }

    /**
     * Returns a description of the 'Message Chain' code smell.
     * The description is provided in HTML format and outlines the criteria for determining a message chain.
     *
     * @return A String in HTML format describing the 'Message Chain' code smell.
     */
    @Override
    public String description() {
        return "There are message chain whose length is longer than a set standard. When a sequence of method calls is chained together,detect it as code smell 'message chain'.";
    }

    /**
     * Scans through the given project's PSI tree to find and list 'message chain' code smells.
     * Code smell occurs when a sequence of method calls is chained together over a defined threshold length.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return list of PsiElements where each element represents a detected message chain code smell.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMessageChainLength = UserProperties.getParam(storyID());

        List<PsiElement> messageChains = new ArrayList<>();
        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass psiClass) {
                for (PsiMethod method : psiClass.getMethods()) {
                    PsiCodeBlock body = method.getBody();
                    if (body != null) {
                        for (PsiStatement statement : body.getStatements()) {
                            if (detectSmell(statement, userDefinedMessageChainLength)) {
                                messageChains.add(statement);
                            }
                            checkAndAddSmells(statement, messageChains, userDefinedMessageChainLength);
                        }
                    }
                }
            }
        }
        return messageChains;
    }

    /**
     * Detects if a given PsiStatement contains a message chain that exceeds the threshold length.
     * Checks various types of PsiStatements including expression statements, declaration statements,
     * return statements, and control flow statements like if, while, for, and switch.
     *
     * @param statement   PsiStatement to inspect for message chains.
     * @param chainLength The threshold length for identifying a message chain.
     * @return true if the statement contains a message chain exceeding the threshold length; otherwise, false.
     */
    private boolean detectSmell(PsiStatement statement, int chainLength) {
        PsiExpression expression = null;

        if (statement == null) {
            return false;
        }
        if (statement instanceof PsiExpressionStatement) { // a.method1().method2()
            expression = ((PsiExpressionStatement) statement).getExpression();
        } else if (statement instanceof PsiDeclarationStatement) { // int a = b.method1().method2()
            for (PsiElement element : ((PsiDeclarationStatement) statement).getDeclaredElements()) {
                if (element instanceof PsiVariable) {
                    PsiExpression initializer = ((PsiVariable) element).getInitializer();
                    if (initializer != null) {
                        expression = initializer;
                        break; // Assume the first non-null initializer is the target
                    }
                }
            }
        } else if (statement instanceof PsiReturnStatement) { // return object.method1().method2();
            expression = ((PsiReturnStatement) statement).getReturnValue();
        } else if (statement instanceof PsiIfStatement) { // if(object.method1().method2())
            expression = ((PsiIfStatement) statement).getCondition();
        } else if (statement instanceof PsiWhileStatement) { // while(object.method1().method2())
            expression = ((PsiWhileStatement) statement).getCondition();
        } else if (statement instanceof PsiForStatement) { // for(int i = 0; object.method1().method2(i); i++)
            expression = ((PsiForStatement) statement).getCondition();
        } else if (statement instanceof PsiSwitchStatement) { // switch(object.method1().method2())
            expression = ((PsiSwitchStatement) statement).getExpression();
        }

        return expression != null && calculateChainLength(expression) > chainLength;
    }

    /**
     * Recursively checks for and adds message chain code smells in the given PsiStatement and its nested statements.
     * This method handles block statements and inside the control flow statements like if, while, for, and switch,
     *
     * @param statement   The PsiStatement to check for message chains.
     * @param foundSmells The list to which detected message chains are added.
     * @param chainLength The threshold length for identifying a message chain.
     */
    private void checkAndAddSmells(PsiStatement statement, List<PsiElement> foundSmells, int chainLength) {
        if (statement instanceof PsiBlockStatement) {
            PsiCodeBlock codeBlock = ((PsiBlockStatement) statement).getCodeBlock();
            for (PsiStatement innerStatement : codeBlock.getStatements()) {
                if (detectSmell(innerStatement, chainLength)) {
                    foundSmells.add(innerStatement);
                }
                checkAndAddSmells(innerStatement, foundSmells, chainLength);
            }
        } else if (statement instanceof PsiIfStatement || statement instanceof PsiWhileStatement
            || statement instanceof PsiForStatement) {
            PsiStatement[] branches = getBranches(statement);
            for (PsiStatement branch : branches) {
                if (branch != null) {
                    checkAndAddSmells(branch, foundSmells, chainLength);
                }
            }
        } else if (statement instanceof PsiSwitchStatement switchStatement) { // For message chains in switch case
            for (PsiStatement switchBranch : Objects.requireNonNull(switchStatement.getBody()).getStatements()) {
                // Check and add smells in each switch branch
                if (switchBranch instanceof PsiBlockStatement) {
                    checkAndAddSmells(switchBranch, foundSmells, chainLength);
                } else if (detectSmell(switchBranch, chainLength)) {
                    foundSmells.add(switchBranch);
                }
            }
        }
    }

    /**
     * Extracts the branch statements from a given control flow statement (if, while, for)
     * Handles if, while, and for statements by returning their body or branches.
     * For if statements, both the 'then' and 'else' branches are returned.
     *
     * @param statement The control flow statement from which to extract branches.
     * @return An array of PsiStatements representing the branches of the given control flow statement.
     */
    public PsiStatement[] getBranches(PsiStatement statement) {
        if (statement instanceof PsiIfStatement ifStmt) {
            return new PsiStatement[]{ifStmt.getThenBranch(), ifStmt.getElseBranch()};
        } else if (statement instanceof PsiWhileStatement whileStmt) {
            return new PsiStatement[]{whileStmt.getBody()};
        } else if (statement instanceof PsiForStatement forStmt) {
            return new PsiStatement[]{forStmt.getBody()};
        }
        return new PsiStatement[0];
    }

    /**
     * Calculates the length of a message chain within a given PsiElement.
     *
     * @param element The PsiElement to inspect, a PsiMethodCallExpression.
     * @return The number of chained method calls. Returns 0 if there are none or the element is not a method call.
     */
    private int calculateChainLength(PsiElement element) {
        int length = 0;
        while (element instanceof PsiMethodCallExpression) {
            length++;
            element = ((PsiMethodCallExpression) element).getMethodExpression().getQualifierExpression();
        }
        return length;
    }

}