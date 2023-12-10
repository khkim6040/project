package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class to detect 'LargeClass' based on the number of fields.
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to identify large class field code smells.
 *
 * @author Jinyoung Kim
 */
public class LargeClassField extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Large Class Field' detection story.
     */
    @Override
    public String storyID() {
        return "LCF";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Large Class based on number of fields";
    }

    /**
     * Describes the criteria for detecting a large class code smell.
     *
     * @return A String in HTML format describing when a class is considered 'large'.
     */
    @Override
    public String description() {
        return "There are more fields in the class than a set standard. When there are too many fields in the class, detect it as code smell 'large class'.";
    }

    /**
     * Checks whether a class in the given action event context is a large class due to many fields.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing a class considered as 'large'.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMaxFields = UserProperties.getParam(storyID());

        return Stream.of(psiFile.getChildren())
            .filter(PsiClass.class::isInstance)
            .map(PsiClass.class::cast)
            .filter(psiClass -> detectSmell(psiClass, userDefinedMaxFields))
            .collect(Collectors.toList());
    }

    /**
     * Helper method to check if the class is considered 'large' based on the number of fields.
     *
     * @param psiClass  PsiClass object representing a class in the code.
     * @param maxFields The maximum number of fields defined by the user as the threshold.
     * @return true if the class has more fields than the specified maxFields threshold.
     */
    private boolean detectSmell(PsiClass psiClass, int maxFields) {
        PsiField[] fields = psiClass.getFields();
        return fields.length > maxFields;
    }

}
