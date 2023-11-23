package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.List;


public class DeadCode {
    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DC";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "DeadCode";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When variable or method is not used<br/>" +
                " ,detect it as code smell deadcode.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are variables and method that is declared but not used</html>";
    }

    /**
     * Method that checks whether candidate method is long method
     *
     * @param e AnActionEvent
     * @return true if method has code smell, is long method
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        assert editor != null;
        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        assert (psiFile instanceof PsiJavaFile);

        List<PsiVariable> variables = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiVariable.class));
        List<PsiMethod> methods = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiMethod.class));
        List<PsiElement> members= new ArrayList<>();
        List<PsiElement> deadElement = new ArrayList<>();
        members.addAll(variables);
        members.addAll(methods);

        for (PsiElement member : members){
            if(detectSmell(member))
                deadElement.add(member);
        }
        return deadElement;
    }

    /**
     * Helper method to check if a variable or method is not 'long'.
     *
     * @param method PsiMethod
     * @return true if the method is longer than a set threshold
     */
    private boolean detectSmell(PsiElement element) {

        return ReferencesSearch.search(element).findAll().isEmpty();
    }
}
