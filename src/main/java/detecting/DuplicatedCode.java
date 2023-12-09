package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
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
        return "<html>When there duplicated code. <br/>" +
            "Detect methods where the similar code is repeated..</html>";
    }

    /**
     * Defines the precondition for this code smell detection.
     *
     * @return A String in HTML format stating the precondition for duplicated code.
     */
    @Override
    public String precondition() {
        return "<html>Find the methods where identical or very similar code exists. " +
            "Find methods that are similar to a certain level or higher. </html>";
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

        // Initialize a list to store similar methods
        List<PsiElement> similarMethods = new ArrayList<>();

        // Set to track which methods have already been added
        Set<PsiElement> addedMethods = new HashSet<>();

        // Map to store methods' body text and corresponding method
        Map<String, PsiMethod> methodMap = new HashMap<>();

        for (PsiMethod currentMethod : methods) {
            PsiCodeBlock currentBody = currentMethod.getBody();
            if (currentBody == null) {
                continue;
            }
            String currentText = currentBody.getText();

            // Compare the current method with all previously encountered methods
            for (Map.Entry<String, PsiMethod> entry : methodMap.entrySet()) {
                String existingText = entry.getKey();
                PsiMethod existingMethod = entry.getValue();

                // Compute the similarity between the current method and each existing method
                double distance = computeLevenshteinDistance(currentText, existingText);

                // If the similarity is high enough (distance <= 0.1), consider the methods similar
                if (distance <= 0.1) {
                    // Add the current method to the list of similar methods if not already added
                    if (addedMethods.add(currentMethod)) {
                        similarMethods.add(currentMethod);
                    }
                    // Add the existing method to the list of similar methods if not already added
                    if (addedMethods.add(existingMethod)) {
                        similarMethods.add(existingMethod);
                    }
                }
            }

            // Add the current method and its body text to the map for future comparisons
            methodMap.put(currentText, currentMethod);
        }

        // Return the list of similar methods
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

    /**
     * @param a
     * @param b
     * @param c
     * @return the minimum integer
     */
    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
