package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.LoadPsi;

/**
 * Class to detect 'Poor Name' based on its variable name.
 *
 * @author Chanho Song
 */
public class PoorName extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Poor Name' detection story.
     */
    @Override
    public String storyID() {
        return "PN";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Poor Name";
    }

    /**
     * Describes the criteria for detecting a Poor Name code smell.
     *
     * @return A String in HTML format describing when a variable has 'Poor Name'.
     */
    @Override
    public String description() {
        return "There are variables which have too short name, sequential alphabet name or form of repeated alphabet. Detect the variables as a 'poor name' code smell.";
    }

    /**
     * Checks whether a variable has poor name.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each is the variable that has a poor name.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        return Stream.of(psiFile.getChildren())
            .flatMap(element -> PsiTreeUtil.collectElementsOfType(element, PsiVariable.class).stream())
            .filter(this::detectSmell)
            .collect(Collectors.toList());
    }

    /**
     * Helper method to check if the method has a poor name
     *
     * @param var PsiVariable
     * @return true if variable has poor name
     */
    private boolean detectSmell(PsiVariable var) {

        if (var == null) {
            return false;
        }
        String name = var.getName();

        //Check the length
        if (name.length() <= 3) {
            return true;
        }
        //Check repeated alphabet
        if (name.matches("(.)\\1+")) //aaaa
        {
            return true;
        }

        if (isSequential(name)) //abcd
        {
            return true;
        }

        return false;
    }

    /**
     * Helper method to check if it is a sequential alphabet.ex) abcd, efgh
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
