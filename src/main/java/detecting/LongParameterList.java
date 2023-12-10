package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
        return "There are more parameters in a method than a set standard. When there are too many parameters in the method, detect it as code smell 'long parameter list'.";
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
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMaxParameters = UserProperties.getParam(storyID());

        return Stream.of(psiFile.getChildren())
            .filter(PsiClass.class::isInstance)
            .map(PsiClass.class::cast)
            .flatMap(psiClass -> Stream.of(psiClass.getMethods()))
            .filter(method -> detectSmell(method, userDefinedMaxParameters))
            .collect(Collectors.toList());
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