package ui;

import com.intellij.psi.PsiElement;
import java.util.List;

public interface AnalyzeResultListener {

    void onAnalyzeResult(List<PsiElement> result);
}
