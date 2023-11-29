package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
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
        if (body != null) {
            for (PsiStatement statement : body.getStatements()) {
                if (statement instanceof PsiExpressionStatement) {
                    PsiExpression expression = ((PsiExpressionStatement) statement).getExpression();
                    if (calculateChainLength(expression) > 3) {
                        return true;
                    }
                } else if (statement instanceof PsiDeclarationStatement) {
                    for (PsiElement element : statement.getChildren()) {
                        if (element instanceof PsiVariable) {
                            PsiExpression initializer = ((PsiVariable) element).getInitializer();
                            if (initializer != null && calculateChainLength(initializer) > 3) {
                                return true;
                            }
                        }
                    }
                }
                //Else if
                
                /*
                 * TODO: check switch, if, return ...
                 */
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