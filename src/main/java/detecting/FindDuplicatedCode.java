package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.*;

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
     * @return smelly PsiElmment list
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {

        Project project = e.getProject();
        assert project!= null;

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        assert editor!= null;

        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        assert (psiFile instanceof PsiJavaFile);

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


//        String fileText = psiFile.getText();
//        Scanner scanner = new Scanner(fileText);
//        Set<String> lineSet = new HashSet<>();
//
//        List<PsiElement> duplicatedCodes = new ArrayList<>();
//
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine().trim();
//
//            /*
//            Todo : This code is analyze code line by line,
//                    but we need to see the "code block"
//             */
//            if (isCommentOrBlankLine(line) || line.length() < 10) {
//                continue;
//            }
//            if (!lineSet.add(line)) {
//                duplicatedCodes.add();
//            }
//        }
//        return duplicatedCodes;
    }

//    private boolean isCommentOrBlankLine(String line) {
//        /*
//        Todo : multiline commnet is not implemented yet.
//         */
//        return line.isEmpty() || line.startsWith("//") || line.startsWith("/*") || line.startsWith("*");
//    }

}
