package utils;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import ui.CustomizePopupGUI;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomizeAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // TODO: insert action logic here

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
        //String message = dialog.getMessage();

        // Display the message in a dialog box
        //Messages.showMessageDialog(project, message, "Test Customization", Messages.getInformationIcon());
    }


}
