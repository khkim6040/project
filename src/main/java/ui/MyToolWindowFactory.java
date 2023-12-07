package ui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import org.jetbrains.annotations.NotNull;
import ui.analyzing.AnalyzeAction;
import ui.analyzing.TreeStructureWindow;

/**
 * The factory class which creates MyToolWindow on the IDE.
 *
 * @author Jinmin Goh, Seokhwan Choi
 * @author Hyunbin Park
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private static Map<String, List<PsiElement>> resultAll = new HashMap<>();
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
                System.out.println(currentFile);
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
                analyzeAction.setResultListener(this::updateUIWithAnalyzeResult);
                AnActionEvent event = AnActionEvent.createFromDataContext(
                    ActionPlaces.UNKNOWN, action.getTemplatePresentation().clone(), customDataContext);
                action.actionPerformed(event);
            }
        });

        analyzeallButton.addActionListener(e -> {
            AnAction action = ActionManager.getInstance().getAction("Analyze");
            AnalyzeAction analyzeAction = (AnalyzeAction) action;
            List<VirtualFile> virtualFiles;
            List<PsiFile> psiFiles = new ArrayList<>();
            DataContext myDataContext;

            final File folder = new File(String.valueOf(
                    ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0])
                .replace("file://", "") + "/src");
            virtualFiles = listFilesForFolder(folder);
            analyzeAction.setResultListener(this::updateUIWithAnalyzeAllResult);

            for (VirtualFile virtualFile : virtualFiles) {
                System.out.println(virtualFile.getName());
                myDataContext = customDataContext(virtualFile);
                AnActionEvent event = AnActionEvent.createFromDataContext(
                    ActionPlaces.UNKNOWN, action.getTemplatePresentation().clone(), myDataContext);
                action.actionPerformed(event);
            }

            resultAll = new HashMap<>();

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
     * method that update tree structure to be shown in toolwindow
     *
     * @param result Map<String, List<PsiElement>>
     */
    public void updateUIWithAnalyzeResult(Map<String, List<PsiElement>> result) {
        mainPanel.remove(treeWindow);
        treeWindow.remove(tree);
        project = getActiveProject();
        tree = new TreeStructureWindow(project, result);
        treeWindow.setViewportView(tree);
        mainPanel.add(treeWindow, BorderLayout.CENTER);
    }

    public void updateUIWithAnalyzeAllResult(Map<String, List<PsiElement>> result) {
        System.out.println(String.valueOf(result));
        mergeMaps(result, resultAll);
        mainPanel.remove(treeWindow);
        treeWindow.remove(tree);
        project = getActiveProject();
        tree = new TreeStructureWindow(project, resultAll);
        treeWindow.setViewportView(tree);
        mainPanel.add(treeWindow, BorderLayout.CENTER);
    }

    // method that return ActiveProject
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

    public List<VirtualFile> listFilesForFolder(final File folder) {
        List<VirtualFile> virtualFiles = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                virtualFiles.addAll(listFilesForFolder(fileEntry));
            } else {
                if (fileEntry.getAbsolutePath().contains("java")) {
                    LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
                    VirtualFile virtualFile = localFileSystem.refreshAndFindFileByIoFile(fileEntry);
                    virtualFiles.add(virtualFile);
                    //System.out.println(virtualFile);
                }
            }
        }
        return virtualFiles;
    }

    public DataContext customDataContext(VirtualFile currentFile) {
        DataContext dataContext = DataManager.getInstance().getDataContext(analyzeButton);
        Editor editor = null;
        PsiFile psiFile = null;

        if (currentFile != null) {
            FileEditor[] fileEditors = FileEditorManager.getInstance(project).getEditors(currentFile);
            if (fileEditors.length > 0 && fileEditors[0] instanceof TextEditor) {
                editor = ((TextEditor) fileEditors[0]).getEditor();
            }

            psiFile = PsiManager.getInstance(project).findFile(currentFile);
        }
        Editor finalEditor = editor;
        PsiFile finalPsiFile = psiFile;
        DataContext myDataContext = new DataContext() {
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
        return myDataContext;
    }

    public void mergeMaps(Map<String, List<PsiElement>> map1, Map<String, List<PsiElement>> map2) {
        map1.forEach((key, value) ->
            map2.merge(key, value, (existingList, newList) -> {
                List<PsiElement> combinedList = new ArrayList<>(existingList);
                combinedList.addAll(newList);
                return combinedList;
            })
        );
    }
}
