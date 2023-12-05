package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import java.util.List;

/**
 * Abstract class to provide code smell techniques
 *
 * @author Jinyoung Kim
 * @author CSED332 2020 Team Wanted
 */

public abstract class BaseDetectAction {

    /**
     * Returns the story ID.
     *
     * @return story ID
     */
    public abstract String storyID();

    /**
     * Returns the story name as string for message.
     *
     * @return story name as string
     */
    public abstract String storyName();

    /**
     * Returns the description of each story.
     * Use html-style (<html>content</html>) for multi-line explanation.
     *
     * @return description of each story as string
     */
    public abstract String description();

    /**
     * Returns the precondition of each story.
     * Use html-style (<html>content</html>) for multi-line explanation.
     *
     * @return description of each story as string
     */
    public abstract String precondition();

    /**
     * Method that checks whether candidate method has code smell.
     *
     * @param e AnActionEvent
     * @return true if method has code smell
     */
    public abstract List<PsiElement> findSmells(AnActionEvent e);

}