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
     * Identifies and returns a list of code smells within the code.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing a detected code smell in the code.
     */
    public abstract List<PsiElement> findSmells(AnActionEvent e);

}