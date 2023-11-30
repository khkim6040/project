package ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.BaseDetectManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class AnalyzeAction extends AnAction {

    private AnalyzeResultListener resultListener;

    public void setResultListener(AnalyzeResultListener listener) {
        this.resultListener = listener;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<String> actionIDs = Arrays.asList("LPL", "DLC", "ILM", "SS", "FDC", "PN", "DC");
        Map<String, List<PsiElement>> combinedResults = new HashMap<>();
        for (String actionID : actionIDs) {
            BaseDetectAction baseDetectAction = BaseDetectManager.getInstance().getDetectActionByID(actionID);
            List<PsiElement> result = baseDetectAction.findSmells(e);
            if (result != null) {
                combinedResults.put(actionID, result);
            }
        }
        if (resultListener != null) {
            resultListener.onAnalyzeResult(combinedResults);
        }
//        BaseDetectAction baseDetectAction = BaseDetectManager.getInstance().getDetectActionByID("LPL");
//        List<PsiElement> result = baseDetectAction.findSmells(e);
//        if (resultListener != null) {
//            resultListener.onAnalyzeResult(result);
//        }
    }
}
