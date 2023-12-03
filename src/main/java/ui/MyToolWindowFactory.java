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
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import java.awt.BorderLayout;
import java.awt.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
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
    private JTree tree;
    private JBScrollPane treeWindow;

    private Project project;

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        analyzeButton = new JButton("Analyze");
        analyzeallButton = new JButton("Analyze all");
        customizeButton = new JButton("Customize");
        treeWindow = new JBScrollPane();
        this.project = getActiveProject();
        Map<String, List<PsiElement>> result = new HashMap<>();
        tree = new TreeStructureWindow(this.project, result);

        analyzeButton.addActionListener(e -> {
            AnAction action = ActionManager.getInstance().getAction("Analyze");
            AnalyzeAction analyzeAction = (AnalyzeAction) action;
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
                analyzeAction.setResultListener(this::updateUIWithAnalyzeResult);
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
        treeWindow.setViewportView(tree);
        mainPanel.add(treeWindow, BorderLayout.CENTER);
        mainPanel.setVisible(true);

        ContentFactory contentFactory = ContentFactory.getInstance();
        toolWindow.getContentManager().addContent(contentFactory.createContent(mainPanel, "", false));
    }

    /**
     * class that add each codeSmell id and codeSmell list in listModel to be shown in toolwindow
     *
     * @author Hyunbin Park, Seokhwan Choi
     */
    public void updateUIWithAnalyzeResult(Map<String, List<PsiElement>> result) {
        mainPanel.remove(treeWindow);
        treeWindow.remove(tree);
        project = getActiveProject();
        tree = new TreeStructureWindow(project, result);
        treeWindow.setViewportView(tree);
        mainPanel.add(treeWindow, BorderLayout.CENTER);
    }

    private Project getActiveProject() {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                return project;
            }
        }
        // if there is no active project, return an arbitrary project (the first)
        return ProjectManager.getInstance().getOpenProjects()[0];
    }

}
