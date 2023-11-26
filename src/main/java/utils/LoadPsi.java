package utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * Class to load the PsiFile
 *
 * @author Chanho Song
 */

public class LoadPsi {

    public static PsiFile loadPsiFile(AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        assert editor != null;
        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        assert (psiFile instanceof PsiJavaFile);

        return psiFile;
    }
}
