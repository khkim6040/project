package utils;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import ui.CustomizePopupGUI;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Customize Action class implements the action when the CustomizePopupGUI has been invoked.
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class CustomizeAction extends AnAction {
    /**
     * Invoke when CustomizePopupGUI(Tools > CodeScent > Customize) action is performed.
     *
     * @param e action event.
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        CustomizePopupGUI dialog = null;
        try {
            dialog = new CustomizePopupGUI(project, e);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
