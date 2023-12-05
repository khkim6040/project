package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
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
import ui.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'Message Chain'
 * When sequence of method calls is chained together ove a threshold length
 *
 * @author Jinyoung Kim
 * @author Chanho Song
 */
public class MessageChain extends BaseDetectAction {

    public Project project;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "MC";
    }

    /* Returns the story name as string for message. */
    @Override
    public String storyName() {
        return "Message Chain";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When a sequence of method calls is chained together<br/>" +
            " ,detect it as code smell message chain.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>Message chain whose length is longer than a set standard</html>";
    }

    /**
     * Scans through the given project's PSI tree to find and list 'message chain' code smells.
     * Code smell occurs when a sequence of method calls is chained together over a defined threshold length.
     *
     * @param e AnActionEvent, represents some interaction by the user
     * @return list of PsiElements where each element represents a detected message chain code smell.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> messageChains = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMessageChainLength = UserProperties.getParam(storyID());

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
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
        if (statement instanceof PsiExpressionStatement) { // a.method1().method2()
            PsiExpression expression = ((PsiExpressionStatement) statement).getExpression();
            return calculateChainLength(expression) > chainLength;
        } else if (statement instanceof PsiDeclarationStatement) { // int a = b.method1().method2()
            PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
            for (PsiElement element : declarationStatement.getDeclaredElements()) {
                if (element instanceof PsiVariable) {
                    // If declared element is variable, checks the initializer of the variable for message chain
                    PsiExpression initializer = ((PsiVariable) element).getInitializer();
                    if (initializer != null && calculateChainLength(initializer) > chainLength) {
                        return true;
                    }
                }
            }
        } else if (statement instanceof PsiReturnStatement) { // check return object.method1().method2();
            PsiExpression returnExpression = ((PsiReturnStatement) statement).getReturnValue();
            return returnExpression != null && calculateChainLength(returnExpression) > chainLength;
        } else if (statement instanceof PsiIfStatement) { // check if(object.method1().method2())
            PsiExpression condition = ((PsiIfStatement) statement).getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiWhileStatement) { // check while(object.method1().method2())
            PsiExpression condition = ((PsiWhileStatement) statement).getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiForStatement) { // check for(int i = 0; object.method1().method2(i); i++)
            PsiForStatement forStatement = (PsiForStatement) statement;
            PsiExpression condition = forStatement.getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiSwitchStatement) { // check switch(object.method1().method2())
            PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;
            PsiExpression switchExpression = switchStatement.getExpression();
            return switchExpression != null && calculateChainLength(switchExpression) > chainLength;
        }

        return false;
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
        } else if (statement instanceof PsiSwitchStatement) { // For message chains in switch case
            PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;
            for (PsiStatement switchBranch : switchStatement.getBody().getStatements()) {
                // Check and add smells in each switch branch
                if (switchBranch instanceof PsiBlockStatement) {
                    checkAndAddSmells(switchBranch, foundSmells, chainLength);
                } else {
                    if (detectSmell(switchBranch, chainLength)) {
                        foundSmells.add(switchBranch);
                    }
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
    private PsiStatement[] getBranches(PsiStatement statement) {
        if (statement instanceof PsiIfStatement) {
            return new PsiStatement[]{((PsiIfStatement) statement).getThenBranch(),
                ((PsiIfStatement) statement).getElseBranch()};
        } else if (statement instanceof PsiWhileStatement) {
            return new PsiStatement[]{((PsiWhileStatement) statement).getBody()};
        } else if (statement instanceof PsiForStatement) {
            return new PsiStatement[]{((PsiForStatement) statement).getBody()};
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