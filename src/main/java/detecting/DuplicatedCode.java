package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import utils.LoadPsi;

/**
 * Class to detect 'Duplicated code'.
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to identify duplicated code.
 *
 * @author Chanho Song
 * @author Jinyoung Kim
 */
public class DuplicatedCode extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Duplicated Code' detection story.
     */
    @Override
    public String storyID() {
        return "DPC";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Duplicated Code";
    }

    /**
     * Describes the criteria for detecting duplicated code
     *
     * @return A String in HTML format describing when a class is considered 'Duplicated Code'.
     */
    @Override
    public String description() {
        return "There are identical or very similar methods. Repeated methods are 'duplicated code' code smell.";
    }

    /**
     * Find the very similar methods and return the duplicated methods.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each is the very similar method.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        Collection<PsiMethod> methods = PsiTreeUtil.collectElementsOfType(psiFile, PsiMethod.class);

        return methods.stream()
            .flatMap(currentMethod -> methods.stream()
                .filter(
                    existingMethod -> currentMethod != existingMethod && detectSmell(currentMethod, existingMethod)))
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * method to calculate the Levenshtein distance between the text of the two method bodies.
     * If the methods' bodies are very similar (as defined by a Levenshtein distance threshold),
     * it returns true, indicating a code smell due to high similarity.
     *
     * @param method1 The first PsiMethod object to compare.
     * @param method2 The second PsiMethod object to compare.
     * @return true if the Levenshtein distance between the method bodies is less than or equal to 0.1, indicating high
     * similarity; false otherwise.
     */
    private boolean detectSmell(PsiMethod method1, PsiMethod method2) {
        PsiCodeBlock body1 = method1.getBody();
        PsiCodeBlock body2 = method2.getBody();
        if (body1 == null || body2 == null) {
            return false;
        }
        String text1 = body1.getText();
        String text2 = body2.getText();

        double distance = computeLevenshteinDistance(text1, text2);
        return distance <= 0.1;
    }


    /**
     * Calculate the LevenshteinDistance between two strings.
     * The distance is a measure of the similarity between two strings, where a lower value indicates higher similarity.
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @return The Levenshtein Distance as a double, normalized by the length of the longer string.
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

    /**
     * Finds the minimum of three integers.
     * Used for computation in Levenshtein Distance.
     *
     * @param a The first integer.
     * @param b The second integer.
     * @param c The third integer.
     * @return The smallest of the three integers.
     */
    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
