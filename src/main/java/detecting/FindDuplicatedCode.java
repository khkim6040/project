package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.HashMap;
import java.util.Map;

public class FindDuplicatedCode extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "FDC";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Find Duplicated Code";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there duplicated code. <br/>" +
                "Detect codes where the same code is repeated..</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>Find the parts where identical or very similar code exists in multiple locations. " +
                "Identical or similar code blocks or methods .</html>";
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

        PsiElement[] allElements = PsiTreeUtil.collectElements(psiFile, element -> true);
        Map<String, PsiElement> codeBlocks = new HashMap<>();

        for (PsiElement element : allElements) {
            String codeText = element.getText();
            if (codeBlocks.containsKey(codeText)) {
                System.out.println(codeText);
                return true;
            } else {
                codeBlocks.put(codeText, element);
            }
        }
        return false;
    }

}
