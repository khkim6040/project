package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.PsiMethod;

/**
 * Class to provide detecting: 'LongParameterList'
 *
 */
public class LongParameterList extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "LPL";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Detect Long Parameter List";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many parameters in the method<br/>" +
                "detect it as code smell long parameter list.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more parameters in the method than a set standard</html>";
    }

    /**
     * Method that checks whether candidate method is refactorable
     * using 'Remove Unused Parameter'.
     *
     * @param e AnActionevent
     * @return true if method has code smell
     */
    @Override
    public boolean detectSmell(AnActionEvent e) {
        Project project = e.getProject();
        System.out.println(project);
        if (project == null) {
            System.out.println("No project");
            return false;
        }

        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            System.out.println("No editor");
            return false;
        }

        Document document = editor.getDocument();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        PsiFile psiFile = psiDocumentManager.getPsiFile(document);
        if (psiFile == null) {
            System.out.println("Noo2");
            return false;
        }
        System.out.println(psiFile);
        PsiElement elementAtCaret = psiFile.findElementAt(editor.getCaretModel().getOffset());

        PsiMethod focusMethod = PsiTreeUtil.getParentOfType(elementAtCaret, PsiMethod.class);

        return isLongParameterList(focusMethod);
    }

    /**
     * Helper method to check if the method has a long parameter list.
     *
     * @param method PsiMethod
     * @return true if method has long parameter list
     */
    private boolean isLongParameterList(PsiMethod method) {
        if (method == null) return false;

        final int MAX_PARAMETERS = 5;  // Define a threshold for maximum allowed parameters
        PsiParameter[] parameters = method.getParameterList().getParameters();
        return parameters.length > MAX_PARAMETERS;
    }
}
