package ui.analyzing;

import com.intellij.psi.PsiElement;
import java.util.List;
import java.util.Map;

/**
 * The AnalyzeResultListener interface which save the result from the action when the ToolWindow(Analyze) has been
 * invoked.
 *
 * @author Seokhwan Choi
 */
public interface AnalyzeResultListener {

    void onAnalyzeResult(Map<String, List<PsiElement>> result);
}
