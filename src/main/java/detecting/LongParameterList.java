package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Class to provide detecting: 'LongParameterList'
 *
 * @author Jinyoung Kim
 * @author Chanho Song
 * @author Hyunbin Park
 */
public class LongParameterList extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "LPL";
    }

    /* Returns the story name as string for message. */
    @Override
    public String storyName() {
        return "Detect Long Parameter List";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many parameters in the method<br/>" +
                " ,detect it as code smell long parameter list.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more parameters in the method than a set standard</html>";
    }

    /**
     * Method that checks whether candidate method has long parameter list
     *
     * @param e AnActionEvent
     * @return true if method has smell code
     */
    @Override
    public boolean detectSmell(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            System.out.println("project is null");
            return false;
        }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            System.out.println("editor is null");
            return false;
        }

        Document document = editor.getDocument();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        PsiFile psiFile = psiDocumentManager.getPsiFile(document);
        if (psiFile == null) {
            System.out.println("psiFile is null");
            return false;
        }

        if (!(psiFile instanceof PsiJavaFile)) {
            System.out.println("Not a Java file");
            return false;
        }

        PsiJavaFile javaFile = (PsiJavaFile) psiFile;
        for (PsiClass psiClass : javaFile.getClasses()) {
            for (PsiMethod method : psiClass.getMethods()) {
                if (isLongParameterList(method)) {
                    return true; // Long parameter list code smell detected
                }
            }
        }
        return false; // No long parameter list code smell detected
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
