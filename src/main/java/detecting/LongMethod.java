package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ui.customizing.UserProperties;
import utils.LoadPsi;

/**
 * Class for detecting 'LongMethod' code smells.
 * This class extends BaseDetectAction, enabling it to analyze code
 * and identify methods that are excessively long, indicating a code smell.
 *
 * @author Jinyoung Kim
 */
public class LongMethod extends BaseDetectAction {

    /**
     * Returns the story ID for the Long Method detection.
     * This ID uniquely identifies the detection story.
     *
     * @return A String representing the unique story ID for 'Long Method'.
     */
    @Override
    public String storyID() {
        return "LM";
    }

    /**
     * Provides the story name in a format suitable for messages.
     * This method returns the name of the detection story focused on identifying long methods.
     *
     * @return A String representing the name of the detection story.
     */
    @Override
    public String storyName() {
        return "Long Method";
    }

    /**
     * Returns the description of the 'Long Method' code smell.
     * The description is in HTML format and details the criteria used to determine a long method.
     *
     * @return A String in HTML format describing the 'Long Method' code smell.
     */
    @Override
    public String description() {
        return "There are more lines in the method than a set standard. When there are too many lines in the method,detect it as code smell 'long method'.";
    }

    /**
     * Identifies and returns a list of methods that are considered long according to a predefined threshold.
     * It checks each method in the given action event context and determines if it exceeds the maximum line count.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each representing a method that is considered 'long'.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        int userDefinedMaxLineCount = UserProperties.getParam(storyID());

        return Stream.of(psiFile.getChildren())
            .filter(PsiClass.class::isInstance)
            .map(PsiClass.class::cast)
            .flatMap(psiClass -> Stream.of(psiClass.getMethods()))
            .filter(method -> detectSmell(method, userDefinedMaxLineCount))
            .collect(Collectors.toList());
    }

    /**
     * Helper method to check if a method exceeds a certain line count, thus considering it 'long'.
     * It calculates the line count of a method and compares it against the maximum allowed line count.
     *
     * @param method       The PsiMethod to check.
     * @param maxLineCount The maximum number of lines allowed for a method.
     * @return true if the method's line count exceeds the specified maximum threshold.
     */
    private boolean detectSmell(PsiMethod method, int maxLineCount) {
        PsiCodeBlock methodBody = method.getBody();

        if (methodBody == null) {
            return false;
        }

        Document document = PsiDocumentManager.getInstance(method.getProject()).getDocument(method.getContainingFile());

        if (document == null) {
            return false;
        }

        // Calculate the line count based on the start and end line numbers
        int startLine = document.getLineNumber(methodBody.getTextRange().getStartOffset());
        int endLine = document.getLineNumber(methodBody.getTextRange().getEndOffset());
        int lineCount = endLine - startLine;

        return lineCount > maxLineCount;
    }

}

