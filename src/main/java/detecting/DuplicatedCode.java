package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LongMethod'
 *
 * @author Chanho Song
 */
public class DuplicatedCode extends BaseDetectAction {

    public Project project;


    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DPC";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Duplicated Code";
    }

    @Override
    public String description() {
        return "<html>When there duplicated code. <br/>" +
            "Detect codes where the same code is repeated..</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>Find the parts where identical or very similar code exists in multiple locations. " +
            "Identical or similar code blocks or methods .</html>";
    }

    /**
     * Method that checks duplicated code
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {

        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        Collection<PsiMethod> methods = PsiTreeUtil.collectElementsOfType(psiFile, PsiMethod.class);
        List<PsiElement> similarMethods = new ArrayList<>();
        Set<PsiElement> addedMethods = new HashSet<>();

        Map<String, PsiMethod> methodMap = new HashMap<>();

        for (PsiMethod currentMethod : methods) {
            PsiCodeBlock currentBody = currentMethod.getBody();
            if (currentBody == null) {
                continue;
            }

            String currentText = currentBody.getText();
            for (Map.Entry<String, PsiMethod> entry : methodMap.entrySet()) {
                String existingText = entry.getKey();
                PsiMethod existingMethod = entry.getValue();

                double distance = computeLevenshteinDistance(currentText, existingText);
                if (distance <= 0.1) {
                    if (addedMethods.add(currentMethod)) {
                        similarMethods.add(currentMethod);
                    }
                    if (addedMethods.add(existingMethod)) {
                        similarMethods.add(existingMethod);
                    }
                }
            }

            methodMap.put(currentText, currentMethod);
        }

        return similarMethods;
    }

    /**
     * calculate the distance between two string
     *
     * @param str1
     * @param str2
     * @return distance
     */
    public double computeLevenshteinDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost);
            }
        }

        int maxLength = Math.max(len1, len2);

        //normalize
        return (double) dp[len1][len2] / maxLength;
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
