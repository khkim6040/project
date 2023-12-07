package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.ArrayList;
import java.util.List;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LongMethod'
 *
 * @author Jinyoung Kim
 */
public class LongMethod extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "LM";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Long Method";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many lines in the method<br/>" +
            " ,detect it as code smell long method.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more lines in the method than a set standard</html>";
    }

    /**
     * Method that checks whether candidate method is long method
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> longMethods = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        int userDefinedMaxLineCount = UserProperties.getParam(storyID());

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                for (PsiMethod method : psiClass.getMethods()) {
                    if (detectSmell(method, userDefinedMaxLineCount)) {
                        longMethods.add(method);
                    }
                }
            }
        }
        return longMethods;
    }

    /**
     * Helper method to check if a method is considered 'long'.
     *
     * @param method       PsiMethod
     * @param maxLineCount maximum line count
     * @return true if the method is longer than a set threshold
     */
    private boolean detectSmell(PsiMethod method, int maxLineCount) {

        PsiCodeBlock methodBody = method.getBody();
        if (methodBody == null) {
            return false;
        }

        // Calculate the line count based on the start and end line numbers
        Document document = PsiDocumentManager.getInstance(method.getProject()).getDocument(method.getContainingFile());
        if (document == null) {
            return false;
        }

        int startLine = document.getLineNumber(methodBody.getTextRange().getStartOffset());
        int endLine = document.getLineNumber(methodBody.getTextRange().getEndOffset());

        int lineCount = endLine - startLine;
        return lineCount > maxLineCount;
    }

}

