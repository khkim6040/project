package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LongMethod'
 *
 * @author Chanho Song
 */
public class FindDuplicatedCode extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;


    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "FDC";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Find Duplicated Code";
    }

    /* Returns the description of each story. (in html-style) */
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

        List<PsiCodeBlock> codeBlocks = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiCodeBlock.class));

        Set<String> uniqueBlocks = new HashSet<>();
        List<PsiElement> duplicatedCodeBlocks = new ArrayList<>();

        for (PsiCodeBlock block : codeBlocks) {
            String blockText = block.getText();

            if (blockText.length() <= 10) {
                continue;
            }

            if (!uniqueBlocks.add(blockText)) {
                // find the duplicated code
                duplicatedCodeBlocks.add(block);
            }
        }

        return duplicatedCodeBlocks;
    }
}
