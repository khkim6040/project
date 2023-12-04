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
     * Method that checks whether code has message chain
     * by iterating through classes and methods.
     * For each method, get method's body and checks each statement in the body
     * If any statement contains a message chain longer than threshold, add the statement
     * to the messageChains list
     *
     * @param e AnActionEvent, represents some interaction by the user
     * @return list of smelly PsiElement
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

                            messageChains.addAll(detectSmell(statement, userDefinedMessageChainLength));
                        }
                    }
                }
            }
        }
        return messageChains;
    }

    /**
     * Helper method to check if given PsiStatement
     * contains a message chain that exceeds the specified length.
     *
     * @param statement   PsiStatement
     * @param chainLength length of message chain
     * @return list of smelly PsiElements
     */
    private List<PsiElement> detectSmell(PsiStatement statement, int chainLength) {
        List<PsiElement> foundSmells = new ArrayList<>();

        // Check the current statement, conditions for if, while, for ,switch loop
        if (isChainInStatement(statement, chainLength)) {
            foundSmells.add(statement);
        }

        // Check message chains inside if, while, for loops
        if (statement instanceof PsiIfStatement) {
            detectSmellInLoop(((PsiIfStatement) statement).getThenBranch(), foundSmells, chainLength);
            detectSmellInLoop(((PsiIfStatement) statement).getElseBranch(), foundSmells, chainLength);
        } else if (statement instanceof PsiWhileStatement) {
            detectSmellInLoop(((PsiWhileStatement) statement).getBody(), foundSmells, chainLength);
        } else if (statement instanceof PsiForStatement) {
            detectSmellInLoop(((PsiForStatement) statement).getBody(), foundSmells, chainLength);
        }

        return foundSmells;
    }

    private boolean isChainInStatement(PsiStatement statement, int chainLength) {
        if (statement instanceof PsiExpressionStatement) { // a.method1().method2()
            PsiExpression expression = ((PsiExpressionStatement) statement).getExpression();
            return calculateChainLength(expression) > chainLength;
        } else if (statement instanceof PsiDeclarationStatement) {  // int a = b.method1().method2()
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
        } else if (statement instanceof PsiReturnStatement) { // return object.method1().method2();
            PsiExpression returnExpression = ((PsiReturnStatement) statement).getReturnValue();
            return returnExpression != null && calculateChainLength(returnExpression) > chainLength;
        } else if (statement instanceof PsiIfStatement) {
            // check the condition of if statement for message chain
            PsiExpression condition = ((PsiIfStatement) statement).getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiWhileStatement) {
            // check the condition of while statement for message chain
            PsiExpression condition = ((PsiWhileStatement) statement).getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiForStatement) {
            // check the condition of for statement for message chain
            PsiForStatement forStatement = (PsiForStatement) statement;
            PsiExpression condition = forStatement.getCondition();
            return condition != null && calculateChainLength(condition) > chainLength;
        } else if (statement instanceof PsiSwitchStatement) {
            // check the condition of switch statement for message chain
            PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;
            PsiExpression switchExpression = switchStatement.getExpression();
            return switchExpression != null && calculateChainLength(switchExpression) > chainLength;
        }

        return false;
    }


    private void detectSmellInLoop(PsiElement body, List<PsiElement> foundSmells, int chainLength) {
        if (body instanceof PsiBlockStatement) {
            PsiCodeBlock codeBlock = ((PsiBlockStatement) body).getCodeBlock();
            for (PsiStatement innerStatement : codeBlock.getStatements()) {
                foundSmells.addAll(detectSmell(innerStatement, chainLength));
            }
        } else if (body instanceof PsiStatement) {
            foundSmells.addAll(detectSmell((PsiStatement) body, chainLength));
        }
    }

    private int calculateChainLength(PsiElement element) {
        int length = 0;
        while (element instanceof PsiMethodCallExpression) {
            length++;
            element = ((PsiMethodCallExpression) element).getMethodExpression().getQualifierExpression();
        }
        return length;
    }

}