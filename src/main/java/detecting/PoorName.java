package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.List;
import utils.LoadPsi;

/**
 * Class to detect 'Poor Name' based on its variable name.
 *
 * @author Chanho Song
 */
public class PoorName extends BaseDetectAction {

    public Project project;

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
        return "<html>When there are variables with poor names. <br/>" +
            "detect names that is a meaningless name.</html>";
    }

    /**
     * Defines the precondition for this code smell detection.
     *
     * @return A String in HTML format stating the precondition for detecting poor Name code smell.
     */
    @Override
    public String precondition() {
        return "<html>The variable which has a sequential alphabet name or form of repeated alphabet. " +
            "The variable whose length is less than or equal to 3.</html>";
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

        List<PsiVariable> variables = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiVariable.class));
        List<PsiElement> poorNameVariables = new ArrayList<>();
        for (PsiVariable var : variables) {
            if (detectSmell(var)) {
                poorNameVariables.add(var);
            }
        }
        return poorNameVariables;
    }

    /**
     * Helper method to check if the method has a poor name
     *
     * @param var PsiVariable
     * @return true if variable has poor name
     */
    private boolean detectSmell(PsiVariable var) {

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
