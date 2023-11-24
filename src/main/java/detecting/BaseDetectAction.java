package detecting;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;

import java.util.List;

/**
 * Abstract class to provide code smell techniques
 *
 * @author Jinyoung Kim
 * @author CSED332 2020 Team Wanted
 */

public abstract class BaseDetectAction extends AnAction {

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
    //public abstract boolean detectSmell(PsiMethod method, int maxParameters);


    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // Check if the current context has a code smell
        List<PsiElement> smellList = findSmells(e);

        // Get the project from the action event
        Project project = e.getProject();
        if (project == null) return;

        // Prepare the message to be displayed
        String message = String.valueOf(smellList.size());
        // String message;
//        if (!smellList.isEmpty()) {
//            message = "Code smell detected: " + storyName() + "\n"
//                  + "Number of smells: " + smellList.size() + "\n"
//                  + description();
//    } else {
//        message = "No code smell detected for: " + storyName();
//    }

        // Display the message in a dialog box
        Messages.showMessageDialog(project, message, storyName(), Messages.getInformationIcon());
    }


}