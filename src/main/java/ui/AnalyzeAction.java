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

/**
 * The Analyze Action class implements the action when the ToolWindow(Analyze) has been invoked.
 *
 * @author Seokhwan Choi, Hyunbin Park
 */
public class AnalyzeAction extends AnAction {

    private AnalyzeResultListener resultListener;

    /**
     * Save the result of action in AnalyzeResultListener.
     *
     * @param listener AnalyzeResult.
     */
    public void setResultListener(AnalyzeResultListener listener) {
        this.resultListener = listener;
    }

    /**
     * Invoke when ToolWindow(CodeScent > Analyze) action is performed.
     *
     * @param e action event.
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<String> actionIDs = Arrays.asList("LPL", "LCF", "LCM", "LM", "SS", "DPC", "PN", "DC", "MC", "COM");
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
    }
}
