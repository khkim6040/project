package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Collection;

/**
 * Class to provide detecting: 'LongParameterList'
 */
public class PoorName extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "PN";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Detect Poor Name";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are variables with poor names. <br/>" +
                "detect names that is hardly reflect its function.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>The variable which is just one alphabet or form of repeated alphabet. " +
                "The variable whose length is less than or equal to 3.</html>";
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
            System.out.println("No File");
            return false;
        }
        System.out.println(psiFile);

        Collection<PsiVariable> variables = PsiTreeUtil.collectElementsOfType(psiFile, PsiVariable.class);

        for (PsiVariable var : variables) {
            if (hasPoorName(var))
                return true;
        }
        return false;
    }

    /**
     * Helper method to check if the method has a poor name
     *
     * @param var PsiVariable
     * @return true if method has poor name
     */
    private boolean hasPoorName(PsiVariable var) {
        if (var == null) return false;

        String name = var.getName();

        //Check the length
        if (name.length() <= 3) {
            return true;
        }
        //Check repeated alphabet
        if (name.matches("(.)\\1+"))
            return true;

        if (isSequential(name))
            return true;

        return false;
    }

    /**
     * Helper method to check if
     *
     * @param name PsiVariable
     * @return true if method has squential name
     */
    private boolean isSequential(String name) {
        for (int i = 0; i < name.length() - 1; i++) {
            if (name.charAt(i) + 1 != name.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

}
