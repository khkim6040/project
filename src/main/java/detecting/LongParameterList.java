package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import java.util.ArrayList;
import java.util.List;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class for detecting 'LongParameterList' code smells.
 * This class extends BaseDetectAction and is specialized in analyzing methods to identify
 * those with excessively long lists of parameters, indicating a code smell.
 *
 * @author Jinyoung Kim
 */
public class LongParameterList extends BaseDetectAction {

    /**
     * Returns the unique story ID for the Long Parameter List detection.
     * This identifier is used for differentiating this particular detection story.
     *
     * @return A String representing the story ID for 'Long Parameter List'.
     */
    @Override
    public String storyID() {
        return "LPL";
    }

    /**
     * Provides the name of the detection story in a format suitable for display.
     * This method returns the name of the story focused on identifying long parameter lists in methods.
     *
     * @return A String representing the name of the detection story.
     */
    @Override
    public String storyName() {
        return "Long Parameter List";
    }

    /**
     * Returns a description of the 'Long Parameter List' code smell.
     * The description is provided in HTML format and outlines the criteria for determining a long parameter list.
     *
     * @return A String in HTML format describing the 'Long Parameter List' code smell.
     */
    @Override
    public String description() {
        return "<html>When there are too many parameters in the method<br/>" +
            " ,detect it as code smell long parameter list.</html>";
    }

    /**
     * Specifies the precondition for detecting a long parameter list.
     * This method returns the precondition in HTML format, detailing the conditions that qualify a method's parameter
     * list as 'long'.
     *
     * @return A String in HTML format stating the precondition for detecting a long parameter list.
     */
    @Override
    public String precondition() {
        return "<html>There are more parameters in the method than a set standard</html>";
    }

    /**
     * Identifies and returns a list of methods that have long parameter lists, based on a predefined threshold.
     * It examines each method in the given action event context to determine if it exceeds the maximum parameter count.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing a method with a long parameter list.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> longParameters = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        int userDefinedMaxParameters = UserProperties.getParam(storyID());

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass psiClass) {
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
     * Helper method to determine if a method has a long parameter list.
     * This method checks the number of parameters in a method against a maximum threshold.
     *
     * @param method        The PsiMethod to be evaluated.
     * @param maxParameters The maximum number of parameters allowed before a method is considered to have a long
     *                      parameter list.
     * @return true if the method's parameter list exceeds the specified maximum threshold.
     */
    public boolean detectSmell(PsiMethod method, int maxParameters) {
        if (method == null) {
            return false;
        }

        PsiParameter[] parameters = method.getParameterList().getParameters();
        return parameters.length > maxParameters;
    }

}