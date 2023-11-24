package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to provide detecting: 'LongParameterList'
 *
 * @author Jinyoung Kim
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
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> longParameters = new ArrayList<>();
        Project project = e.getProject();
          if (project == null) {
          return longParameters;
      }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
          if (editor == null) {
              return longParameters;
          }

        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
          if (psiFile == null) {
          return longParameters;
      }

//      int userDefinedMaxParameters = getUserDefinedMaxParameters(project, 5); // 5 is the default value
        int userDefinedMaxParameters = 5;

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                for (PsiMethod method : psiClass.getMethods()) {
                    if (detectSmell(method, userDefinedMaxParameters)) {
                        longParameters.add(method); // Long parameter list code smell detected
                    }
                }
            }
        }
        return longParameters; // No long parameter list code smell detected
    }

    /**
     * Helper method to check if the method has a long parameter list.
     *
     * @param method PsiMethod
     * @return true if method has long parameter list
     */

    public boolean detectSmell(PsiMethod method, int maxParameters) {
        if (method == null) return false;

        PsiParameter[] parameters = method.getParameterList().getParameters();
        return parameters.length > maxParameters;
    }

//    private int getUserDefinedMaxParameters(Project project, int defaultMaxParameters) {
//        String response = Messages.showInputDialog(
//                project,
//                "Enter the maximum number of parameters for a method:",
//                "Configure Max Parameters",
//                Messages.getQuestionIcon(),
//                Integer.toString(defaultMaxParameters),
//                new IntegerInputValidator()
//        );
//
//        if (response == null) {
//            return defaultMaxParameters; // User pressed Cancel or closed the dialog
//        }
//
//        try {
//            return Integer.parseInt(response);
//        } catch (NumberFormatException e) {
//            return defaultMaxParameters;
//        }
//    }
//
//    private static class IntegerInputValidator implements InputValidator {
//        @Override
//        public boolean checkInput(String inputString) {
//            try {
//                int value = Integer.parseInt(inputString);
//                return value > 0;
//            } catch (NumberFormatException e) {
//                return false;
//            }
//        }
//
//        @Override
//        public boolean canClose(String inputString) {
//            return checkInput(inputString);
//        }
//    }
}