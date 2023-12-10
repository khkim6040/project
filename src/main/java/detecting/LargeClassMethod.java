package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class to detect 'LargeClass' based on the number of methods.
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to identify large class method code smells.
 *
 * @author Jinyoung Kim
 */
public class LargeClassMethod extends BaseDetectAction {

    /**
     * Returns a unique identifier for the 'Large Class Method' detection story.
     * This ID is used for differentiating between various detection stories.
     *
     * @return A String representing the story ID, specifically for the large class detection based on methods.
     */
    @Override
    public String storyID() {
        return "LCM";
    }

    /**
     * Provides the name of the detection story.
     * This method returns the name of the story in a String format for displaying in messages or UI elements.
     *
     * @return A String representing the name of the detection story focused on identifying large classes based on the
     * number of methods.
     */
    @Override
    public String storyName() {
        return "Large Class based on number of methods";
    }

    /**
     * Describes the criteria for detecting a large class method code smell.
     * The description is returned in HTML format and used for UI display.
     *
     * @return A String in HTML format describing the criteria for considering a class as 'large' based on the number of
     * methods.
     */
    @Override
    public String description() {
        return "There are more methods in the class than a set standard. When there are too many methods in the class, detect it as code smell large class.";
    }

    /**
     * Identifies and returns a list of elements in the code that are considered 'large classes' based on the number of
     * methods.
     * It checks each class in the given action event to determine if it exceeds the user-defined threshold for methods.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing a class that is considered as 'large' due to an excessive
     * number of methods.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMaxMethods = UserProperties.getParam(storyID());

        return Stream.of(psiFile.getChildren())
            .filter(PsiClass.class::isInstance)
            .map(PsiClass.class::cast)
            .filter(psiClass -> detectSmell(psiClass, userDefinedMaxMethods))
            .collect(Collectors.toList());
    }

    /**
     * Checks if a given PsiClass object is considered 'large' based on the number of methods it contains.
     * Compares the number of methods in the PsiClass against a maximum threshold.
     *
     * @param psiClass   The PsiClass to be evaluated.
     * @param maxMethods The maximum number of methods a class can have before it is considered 'large'.
     * @return true if the PsiClass has more methods than the specified maximum threshold, indicating a 'large class'
     * code smell.
     */
    private boolean detectSmell(PsiClass psiClass, int maxMethods) {
        PsiMethod[] methods = psiClass.getMethods();
        return methods.length > maxMethods;
    }

}
