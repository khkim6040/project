package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ui.customizing.UserProperties;
import utils.LoadPsi;


/**
 * Class to detect smelly 'Comments'.
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to identify Comments of code smells.
 *
 * @author Jinyoung Kim
 * @author Chanho Song
 */
public class Comments extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Comments' detection story.
     */
    @Override
    public String storyID() {
        return "COM";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Comments";
    }

    /**
     * Describes the criteria for detecting a Comments code smell.
     *
     * @return A String in HTML format describing when a comments are smelly.
     */
    @Override
    public String description() {
        return "There are comments longer than user defined length or include TODO, or Fix. These are smelly comments.";
    }

    /**
     * Checks whether a Comments have code smell.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing smelly comments.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMaxLineCount = UserProperties.getParam(storyID());

        return Stream.of(psiFile.getChildren())
            .flatMap(child -> PsiTreeUtil.findChildrenOfType(child, PsiComment.class).stream())
            .filter(comment -> detectSmell(comment, userDefinedMaxLineCount))
            .collect(Collectors.toList());
    }


    /**
     * Evaluates if a PsiComment is considered a code smell based on criteria.
     * A comment is smelly if it includes keywords like "TODO" or "FIX",
     * or if it exceeds a threshold maximum line count.
     * exception: javadoc is not a code smell.
     *
     * @param comment      The PsiComment to be evaluated.
     * @param maxLineCount The maximum number of lines allowed in a comment before it's considered a smell.
     * @return true if the comment meets the criteria for being a smell, false otherwise.
     */
    private boolean detectSmell(PsiComment comment, int maxLineCount) {
        String commentText = comment.getText();

        if (commentText == null) {
            return false;
        }

        //exclude javadoc comments
        if (commentText.startsWith("/**")) {
            return false;
        }

        // Check for "TODO" or "FIX" comments
        {
            if (commentText.contains("TODO") || commentText.contains("Todo") || commentText.contains("FIX")
                || commentText.contains("Fix")) {
                return true;
            }
        }

        // Check for comments longer than maxLineCount
        int lineCount = commentText.split("\n").length;
        if (lineCount > maxLineCount) {
            return true;
        }

        return false;
    }

}
