import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.openapi.ui.Messages;
public class Plugintest extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        System.out.println(project);
        if (project == null) {
            System.out.println("No project");
        }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            System.out.println("No editor");
        }
        else System.out.println(editor);
        // TODO: insert action logic here
        String menu = Messages.showInputDialog("This is the test message", "Enter message", Messages.getQuestionIcon());
        Messages.showMessageDialog(menu, "Enter", null);
    }
}
