import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;
import window.AnalyzeWindow;
import window.WindowInterface;

import java.util.Arrays;

/**
 * The factory class which creates MyToolWindow on the IDE.
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class MyToolWindowFactory implements ToolWindowFactory {
    /**
     * Adds the tab into the ToolWindow.
     *
     * @param toolWindow current tool window
     * @param type       type of tab
     */
    public static void addTab(ToolWindow toolWindow, TabType type) {
        WindowInterface myWindow = windowFactory(type);
        Content content = toolWindow.getContentManager().getFactory()
                .createContent(myWindow.getContent(), type.getName(), false);
        toolWindow.getContentManager().addContent(content);
    }

    public static WindowInterface windowFactory(TabType type) {
        switch (type) {
            case RESULT:
                return new AnalyzeWindow();
        }
        return null;
    }

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        Arrays.asList(TabType.values())
                .forEach(x -> addTab(toolWindow, x));
    }

    enum TabType {
        RESULT("Analyze Result");
        private final String name;

        TabType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
