import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class Plugintest extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String menu = Messages.showInputDialog("What do you want to eat for lunch?", "Enter lunch menu", Messages.getQuestionIcon());
        Messages.showMessageDialog(menu, "Enter", null);
    }
}
