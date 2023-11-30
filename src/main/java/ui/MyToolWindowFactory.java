package ui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import org.jetbrains.annotations.NotNull;

/**
 * The factory class which creates MyToolWindow on the IDE.
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton analyzeButton;
    private JButton analyzeallButton;
    private JButton customizeButton;
    private JBList<String> analyzeList;
    private DefaultListModel<String> listModel;

//    public static void updateToolWindowContent(Project project, String toolWindowId,
//        DefaultListModel<PsiElement> newContent) {
//        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(toolWindowId);
//        if (toolWindow != null) {
//            ContentFactory contentFactory = ContentFactory.getInstance();
//
//            Content content = contentFactory.createContent(newContent, "", false);
//
//            toolWindow.getContentManager().removeAllContents(true);
//
//            toolWindow.getContentManager().addContent(content);
//        }
//    }

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        analyzeButton = new JButton("Analyze");
        analyzeallButton = new JButton("Analyze all");
        customizeButton = new JButton("Customize");
        analyzeList = new JBList<>();
        listModel = new DefaultListModel<>();
        analyzeList.setModel(listModel);
        analyzeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        analyzeButton.addActionListener(e -> {
            AnAction action = ActionManager.getInstance().getAction("Analyze");
            if (action != null) {
                DataContext dataContext = DataManager.getInstance().getDataContext(analyzeButton);
                VirtualFile currentFile = FileEditorManager.getInstance(project).getSelectedFiles()[0];
                Editor editor = null;
                PsiFile psiFile = null;

                if (currentFile != null) {
                    editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    psiFile = PsiManager.getInstance(project).findFile(currentFile);
                }
                Editor finalEditor = editor;
                PsiFile finalPsiFile = psiFile;
                DataContext customDataContext = new DataContext() {
                    @Override
                    public Object getData(String dataId) {
                        if (CommonDataKeys.VIRTUAL_FILE.is(dataId)) {
                            return currentFile;
                        } else if (CommonDataKeys.EDITOR.is(dataId)) {
                            return finalEditor;
                        } else if (CommonDataKeys.PSI_FILE.is(dataId)) {
                            return finalPsiFile;
                        }
                        return dataContext.getData(dataId);
                    }
                };
                AnActionEvent event = AnActionEvent.createFromDataContext(
                    ActionPlaces.UNKNOWN, action.getTemplatePresentation().clone(), customDataContext);
                action.actionPerformed(event);

//                Project currentProject = CommonDataKeys.PROJECT.getData(dataContext);
//                FileEditorManager fileEditorManager = FileEditorManager.getInstance(currentProject);
//                VirtualFile[] files = fileEditorManager.getSelectedFiles();
//                VirtualFile currentFile = files.length > 0 ? files[0] : null;
//
//                listModel.addElement(String.valueOf(currentProject));
//                listModel.addElement(String.valueOf(currentFile));

//                int detectedCount = analyzeAction.getDetectedCount();
//                for (int i = 0; i < detectedCount; i++) {
//                    PsiElement element = analyzeAction.getDetected(i);
//                    if (element != null) {
//                        listModel.addElement(element);
//                    }
//                }
            }
        });

        customizeButton.addActionListener(e -> {
            AnAction action = ActionManager.getInstance().getAction("Customize");
            if (action != null) {
                DataContext dataContext = DataManager.getInstance().getDataContext(customizeButton);
                AnActionEvent event = AnActionEvent.createFromDataContext(
                    ActionPlaces.UNKNOWN, action.getTemplatePresentation().clone(), dataContext);
                action.actionPerformed(event);
            }
        });

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(analyzeButton);
        buttonPanel.add(analyzeallButton);
        buttonPanel.add(customizeButton);
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(new JBScrollPane(analyzeList), BorderLayout.CENTER);
        mainPanel.setVisible(true);

        ContentFactory contentFactory = ContentFactory.getInstance();
        toolWindow.getContentManager().addContent(contentFactory.createContent(mainPanel, "", false));
    }

    public void updateUIWithAnalyzeResult(int result) {
        listModel.addElement(String.valueOf(result));
    }
//    /**
//     * Adds the tab into the ToolWindow.
//     *
//     * @param toolWindow current tool window
//     * @param type       type of tab
//     */
//    public static void addTab(ToolWindow toolWindow, TabType type) {
//        WindowInterface myWindow = windowFactory(type);
//        Content content = toolWindow.getContentManager().getFactory()
//                .createContent(myWindow.getContent(), type.getName(), false);
//        toolWindow.getContentManager().addContent(content);
//    }
//
//    public static WindowInterface windowFactory(TabType type) {
//        switch (type) {
//            case RESULT:
//                return new AnalyzeWindow();
//        }
//        return null;
//    }
//
//    /**
//     * Create the tool window content.
//     *
//     * @param project    current project
//     * @param toolWindow current tool window
//     */
//    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
//        Arrays.asList(TabType.values())
//                .forEach(x -> addTab(toolWindow, x));
//    }
//
//    enum TabType {
//        RESULT("Analyze Result");
//        private final String name;
//
//        TabType(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }
//    }
}
