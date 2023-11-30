package ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.LongParameterList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AnalyzeAction extends AnAction {

    private int detectedCount;
    private List<PsiElement> detected;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BaseDetectAction baseDetectAction = new LongParameterList();
        List<PsiElement> result = baseDetectAction.findSmells(e);
        detected = result;
        detectedCount = result.size();
    }
}
