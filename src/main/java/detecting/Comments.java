package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ui.UserProperties;
import utils.LoadPsi;


/**
 * Abstract class to implement light test through files.
 *
 * @author Jinyoung Kim
 * @author Chanho Song
 */
public class Comments extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "COM";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Comments";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When comments are smelly<br/>" +
            " ,detect it as a code smell.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are comments longer than three line or TODO, or Fix.</html>";
    }

    /**
     * Method that returns all smelly comments
     *
     * @param e AnActionEvent
     * @return list of all smelly comments
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        List<PsiElement> smellyComments = new ArrayList<>();
        int userDefinedMaxLineCount = UserProperties.getParam(storyID());

        Collection<PsiComment> comments = PsiTreeUtil.findChildrenOfType(psiFile, PsiComment.class);
        for (PsiComment comment : comments) {
            if (detectSmell(comment, userDefinedMaxLineCount)) {
                smellyComments.add(comment);
            }
        }
        return smellyComments;
    }


    /**
     * Evaluates if a PsiComment is considered a code smell based on criteria.
     * A comment is smelly if it includes keywords like "TODO" or "FIX",
     * or if it exceeds a threshold maximum line count.
     *
     * @param comment      The PsiComment to be evaluated.
     * @param maxLineCount The maximum number of lines allowed in a comment before it's considered a smell.
     * @return true if the comment meets the criteria for being a smell, false otherwise.
     */
    private boolean detectSmell(PsiComment comment, int maxLineCount) {
        String commentText = comment.getText();

        // Check for "TODO" or "FIX" comments
        if (commentText.contains("TODO") || commentText.contains("Todo") || commentText.contains("FIX")
            || commentText.contains("Fix")) {
            return true;
        }

        // Check for comments longer than 5 lines
        int lineCount = commentText.split("\n").length;
        if (lineCount >= maxLineCount) {
            return true;
        }

        return false;
    }

}
