package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Class to provide detecting: 'LongMethod'
 *
 * @author Jinyoung Kim
 */
public class IdentifyLongMethod extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "ILM";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Identify Long Method";
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
     * @return true if method has code smell, is long method
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

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (psiFile == null || !(psiFile instanceof PsiJavaFile)) {
            System.out.println("psiFile is null or not a Java file");
            return false;
        }

//        int userDefinedMaxLineCount = getUserDefinedMaxLineCount(project, 25);
        // 25 is the default value
        int userDefinedMaxLineCount = 25;

        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        for (PsiClass psiClass : psiJavaFile.getClasses()) {
            for (PsiMethod method : psiClass.getMethods()) {
                if (isLongMethod(method, userDefinedMaxLineCount)) {
                    return true; // Long method code smell detected
                }
            }
        }
        return false; // No long method code smell detected
    }

    /**
     * Helper method to check if a method is considered 'long'.
     *
     * @param method PsiMethod
     * @return true if the method is longer than a set threshold
     */
    private boolean isLongMethod(PsiMethod method, int maxLineCount) {

        PsiCodeBlock methodBody = method.getBody();
        if (methodBody == null) {
            return false; // Method is abstract or similar
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

    /**
     private int getUserDefinedMaxLineCount(Project project, int defaultMaxLineCount) {
     String response = Messages.showInputDialog(
     project,
     "Enter the maximum line count for a method:",
     "Configure Max Line Count",
     Messages.getQuestionIcon(),
     Integer.toString(defaultMaxLineCount),
     new IntegerInputValidator()
     );

     if (response == null) {
     return defaultMaxLineCount; // User pressed Cancel or closed the dialog
     }

     try {
     return Integer.parseInt(response);
     } catch (NumberFormatException e) {
     return defaultMaxLineCount;
     }
     }

     private static class IntegerInputValidator implements InputValidator {
    @Override public boolean checkInput(String inputString) {
    try {
    int value = Integer.parseInt(inputString);
    return value > 0;
    } catch (NumberFormatException e) {
    return false;
    }
    }

    @Override public boolean canClose(String inputString) {
    return checkInput(inputString);
    }
    }
     **/
}

