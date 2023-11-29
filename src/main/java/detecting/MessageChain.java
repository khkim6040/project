package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionListStatement;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiForStatement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiReturnStatement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchLabelStatement;
import com.intellij.psi.PsiSwitchLabeledRuleStatement;
import com.intellij.psi.PsiSwitchStatement;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.PsiWhileStatement;
import java.util.ArrayList;
import java.util.List;
import ui.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'Message Chain'
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
        return "Detect Message Chain";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When a sequence of method or property calls is chained together<br/>" +
            " ,detect it as code smell message chain.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>Message chain whose length is longer than a set standard</html>";
    }

    /**
     * Method that checks whether code has message chain
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> messageChains = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                for (PsiMethod method : psiClass.getMethods()) {
                    if (detectSmell(method)) {
                        messageChains.add(method);
                    }
                }
            }
        }
        return messageChains;
    }


    /**
     * Helper method to check if code has message chain.
     *
     * @param method PsiMethod
     * @return true if code has message chain
     */
    private boolean detectSmell(PsiMethod method) {
        PsiCodeBlock body = method.getBody();
        int userDefinedMessageChainLength = UserProperties.getParam("MC");
        
        if (body != null) {
            for (PsiStatement statement : body.getStatements()) {
                if (statement instanceof PsiExpressionStatement) {
                    PsiExpression expression = ((PsiExpressionStatement) statement).getExpression();
                    if (calculateChainLength(expression) > userDefinedMessageChainLength) {
                        return true;
                    }
                } else if (statement instanceof PsiDeclarationStatement) {
                    PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
                    for (PsiElement element : declarationStatement.getDeclaredElements()) {
                        if (element instanceof PsiVariable) {
                            PsiExpression initializer = ((PsiVariable) element).getInitializer();
                            if (initializer != null
                                && calculateChainLength(initializer) > userDefinedMessageChainLength) {
                                return true;
                            }
                        }
                    }
                } else if (statement instanceof PsiReturnStatement) {
                    PsiExpression returnExpression = ((PsiReturnStatement) statement).getReturnValue();
                    if (returnExpression != null
                        && calculateChainLength(returnExpression) > userDefinedMessageChainLength) {
                        return true;
                    }
                } else if (statement instanceof PsiIfStatement) {
                    PsiIfStatement ifStatement = (PsiIfStatement) statement;
                    // Check condition
                    PsiExpression condition = ifStatement.getCondition();
                    if (condition != null && calculateChainLength(condition) > userDefinedMessageChainLength) {
                        return true;
                    }
                    // Recursively check then-branch and else-branch
                    if (detectSmell(ifStatement.getThenBranch()) || detectSmell(ifStatement.getElseBranch())) {
                        return true;
                    }
                } else if (statement instanceof PsiWhileStatement) {
                    PsiWhileStatement whileStatement = (PsiWhileStatement) statement;
                    PsiExpression condition = whileStatement.getCondition();
                    if (condition != null && calculateChainLength(condition) > userDefinedMessageChainLength) {
                        return true;
                    }
                    if (detectSmell(whileStatement.getBody())) {
                        return true;
                    }
                } else if (statement instanceof PsiSwitchStatement) {
                    PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;

                    // Check the switch expression (if needed)
                    PsiExpression switchExpression = switchStatement.getExpression();
                    if (switchExpression != null
                        && calculateChainLength(switchExpression) > userDefinedMessageChainLength) {
                        return true;
                    }

                    // Iterate through the children of the switch statement to find case statements
                    for (PsiElement child : switchStatement.getChildren()) {
                        if (child instanceof PsiSwitchLabelStatement) {
                            PsiSwitchLabelStatement labelStatement = (PsiSwitchLabelStatement) child;
                            // Process the case statement if needed
                        } else if (child instanceof PsiSwitchLabeledRuleStatement) {
                            PsiSwitchLabeledRuleStatement ruleStatement = (PsiSwitchLabeledRuleStatement) child;
                            // Process the case rule if needed, including its body
                            if (detectSmell(ruleStatement.getBody())) {
                                return true;
                            }
                        }
                    }
                } else if (statement instanceof PsiForStatement) {
                    PsiForStatement forStatement = (PsiForStatement) statement;

                    // Check initialization part
                    PsiStatement initialization = forStatement.getInitialization();
                    if (initialization instanceof PsiDeclarationStatement) {
                        for (PsiElement element : initialization.getChildren()) {
                            if (element instanceof PsiVariable) {
                                PsiExpression initializer = ((PsiVariable) element).getInitializer();
                                if (initializer != null
                                    && calculateChainLength(initializer) > userDefinedMessageChainLength) {
                                    return true;
                                }
                            }
                        }
                    }

                    // Check condition part
                    PsiExpression condition = forStatement.getCondition();
                    if (condition != null && calculateChainLength(condition) > userDefinedMessageChainLength) {
                        return true;
                    }

                    // Check the update part
                    PsiStatement update = forStatement.getUpdate();
                    if (update instanceof PsiExpressionListStatement) {
                        for (PsiExpression expression : ((PsiExpressionListStatement) update).getExpressionList()
                            .getExpressions()) {
                            if (calculateChainLength(expression) > userDefinedMessageChainLength) {
                                return true;
                            }
                        }
                    }

                    // Check the body of the for loop
                    if (detectSmell(forStatement.getBody())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean detectSmell(PsiElement element) {
        // Recursive method to handle compound statements like if, while
        if (element instanceof PsiCodeBlock) {
            for (PsiStatement statement : ((PsiCodeBlock) element).getStatements()) {
                if (detectSmell(statement)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateChainLength(PsiElement element) {
        int length = 0;
        while (element instanceof PsiMethodCallExpression || element instanceof PsiReferenceExpression) {
            length++;
            if (element instanceof PsiMethodCallExpression) {
                element = ((PsiMethodCallExpression) element).getMethodExpression().getQualifierExpression();
            } else if (element instanceof PsiReferenceExpression) {
                element = ((PsiReferenceExpression) element).getQualifier();
            }
        }
        return length;
    }
}