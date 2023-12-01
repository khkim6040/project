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
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        mainPanel.add(new JBScrollPane(analyzeList), BorderLayout.CENTER);
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
        List<String> actionIDs = Arrays.asList("LPL", "DLC", "ILM", "SS", "FDC", "PN", "DC");
        for (String actionID : actionIDs) {
            listModel.addElement(actionID);
            listModel.addElement(String.valueOf(result.get(actionID)));
        }
    }
}
