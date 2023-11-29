package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.ArrayList;
import java.util.List;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'Message Chain'
 *
 * @author Jinyoung Kim
 */
public class MessageChain extends BaseDetectAction {

    public Project project;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "MC";
    }

    /* Returns the story name as string for message. */
    @Override
    public String storyName() {
        return "Detect Message Chain";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When a sequence of method or property calls is chained together<br/>" +
            " ,detect it as code smell message chain.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>Message chain whose length is longer than a set standard</html>";
    }

    /**
     * Method that checks whether candidate method has message chain
     *
     * @param e AnActionEvent
     * @return true if method has smell code
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> messageChains = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        return messageChains;
    }

    /**
     * Helper method to check if the method has a long parameter list.
     *
     * @param method PsiMethod
     * @return true if method has long parameter list
     */


}