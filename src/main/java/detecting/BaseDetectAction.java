package detecting;//package.4nix.detecting;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;


public abstract class BaseDetectAction extends AnAction {

    /**
     * Returns the story ID.
     *
     * @return story ID
     */
    public abstract String storyID();

    /**
     * Returns the story name as a string format, for message.
     *
     * @return story name as a string format
     */
    public abstract String storyName();

    /**
     * Returns the description of each story.
     * You must use html-style (<html>content</html>) for multi-line explanation.
     *
     * @return description of each story as a sting format
     */
    public abstract String description();

    /**
     * Returns the precondition of each story.
     * You must use html-style (<html>content</html>) for multi-line explanation.
     *
     * @return description of each story as a sting format
     */
    public abstract String precondition();

    /**
     * Method that checks whether candidate method has code smell.
     *
     * @param e AnActionEvent
     * @return true if method has code smell
     */
    public abstract boolean detectSmell(AnActionEvent e);


    /**
     * Updates the state of the action.
     * If refactoring is possible, make the function enabled and visible.
     *
     * @param e AnActionEvent
     * @see AnAction#update(AnActionEvent)
     */
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // Check if the current context has a code smell
        boolean hasCodeSmell = detectSmell(e);

        // Get the project from the action event
        Project project = e.getProject();
        if (project == null) return;

        // Prepare the message to be displayed
        String message;
        if (hasCodeSmell) {
            message = "Code smell detected: " + storyName() + "\n" + description();
        } else {
            message = "No code smell detected for: " + storyName();
        }

        // Display the message in a dialog box
        Messages.showMessageDialog(project, message, storyName(), Messages.getInformationIcon());
    }
}
