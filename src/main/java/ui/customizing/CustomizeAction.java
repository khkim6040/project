package ui.customizing;

import static ui.customizing.HandleConfig.getHandler;
import static ui.customizing.HandleConfig.initializeConfig;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;


/**
 * Customize Action class implements the action when the CustomizePopupGUI has been invoked.
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class CustomizeAction extends AnAction {

    /**
     * Invoke when CustomizePopupGUI action is performed.
     *
     * @param e action event.
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        CustomizePopupGUI dialog = null;
        try {
            getHandler(e.getProject());
            initializeConfig();
            dialog = new CustomizePopupGUI(project, e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
