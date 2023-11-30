package ui;

import com.intellij.psi.PsiElement;
import java.util.List;
import java.util.Map;

public interface AnalyzeResultListener {

    void onAnalyzeResult(Map<String, List<PsiElement>> result);
}
