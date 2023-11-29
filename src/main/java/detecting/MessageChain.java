package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiVariable;
import java.util.ArrayList;
import java.util.List;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'Message Chain'
 *
 * @author Jinyoung Kim
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
                    messageChains.addAll(detectSmell(method));
                }
            }
        }
        return messageChains;
    }

    /**
     * Helper method to check if code has message chain.
     *
     * @param method PsiMethod
     * @return list of PsiElement
     */
    private List<PsiElement> detectSmell(PsiMethod method) {
        List<PsiElement> chains = new ArrayList<>();
        PsiCodeBlock body = method.getBody();
        if (body != null) {
            for (PsiStatement statement : body.getStatements()) {
                checkStatementForChains(statement, chains);
            }
        }
        return chains;
    }

    private void checkStatementForChains(PsiStatement statement, List<PsiElement> chains) {
        if (statement instanceof PsiExpressionStatement) {
            PsiExpression expression = ((PsiExpressionStatement) statement).getExpression();
            checkExpressionForChain(expression, chains);
        } else if (statement instanceof PsiDeclarationStatement) {
            // Extract any initializers from the declaration and check them
            for (PsiElement element : statement.getChildren()) {
                if (element instanceof PsiVariable) {
                    PsiExpression initializer = ((PsiVariable) element).getInitializer();
                    if (initializer != null) {
                        checkExpressionForChain(initializer, chains);
                    }
                }
            }
        }
        // Handle other types of statements that might contain method call expressions
    }

    private void checkExpressionForChain(PsiExpression expression, List<PsiElement> chains) {
        if (expression instanceof PsiMethodCallExpression) {
            int chainLength = calculateChainLength(expression);
            if (chainLength > 3) {
                chains.add(expression);
            }
        } else if (expression instanceof PsiAssignmentExpression) {
            PsiExpression rValue = ((PsiAssignmentExpression) expression).getRExpression();
            if (rValue instanceof PsiMethodCallExpression) {
                checkExpressionForChain(rValue, chains);
            }
        }
        // Handle other expression types that might contain method call expressions
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