package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.*;

public class SwitchStatement extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "SS";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Detect Switch Statement";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html></html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are conditional statements that identify class of object that leads to " +
                "casting of the object to use method of the class</html>";
    }

    /**
     * Method that checks whether candidate method has long parameter list
     *
     * @param e AnActionEvent
     * @return true if method has smell code
     */
    @Override
    public boolean detectSmell(AnActionEvent e) {
        Project project = e.getProject();
        System.out.println(project);
        if (project == null) {
            System.out.println("No project");
            return false;
        }

        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            System.out.println("No editor");
            return false;
        }

        Document document = editor.getDocument();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        PsiFile psiFile = psiDocumentManager.getPsiFile(document);
        if (psiFile == null) {
            System.out.println("No psiFile");
            return false;
        }

        List<PsiIfStatement> IfStatements = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiIfStatement.class));
        for (PsiIfStatement statement : IfStatements) {
            if (isSwitchStatement(statement))
                return true;
        }
        return false;
    }

    /**
     * Helper method to check if the method has a long parameter list.
     *
     * @param method PsiMethod
     * @return true if method has long parameter list
     */
    private boolean isSwitchStatement(PsiIfStatement statement) {
        List<Map<String, PsiType>> castingMapList = new ArrayList<>();
        if (statement == null) return false;
        if (statement.getCondition() == null) return false;

        PsiExpression condition = statement.getCondition();
        PsiStatement thenBranch = statement.getThenBranch();
        PsiStatement elseBranch = statement.getElseBranch();

        if (!condition.getText().contains("instanceof"))
            return false;
        if (CastingMap(thenBranch) != null)
            castingMapList.add(CastingMap(thenBranch));

        if (CastingMap(elseBranch) != null)
            castingMapList.add(CastingMap(elseBranch));

        while (elseBranch != null && elseBranch instanceof PsiIfStatement) {
            elseBranch = ((PsiIfStatement) elseBranch).getElseBranch();
            if (CastingMap(elseBranch) != null)
                castingMapList.add(CastingMap(elseBranch));
        }

        return objectCasted(castingMapList);

    }

    private Map<String, PsiType> CastingMap(PsiStatement statement) {
        Map<String, PsiType> casting = new HashMap<>();
        statement.accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiTypeCastExpression)
                    casting.put(((PsiTypeCastExpression) element).getOperand().getText(), ((PsiTypeCastExpression) element).getType());
            }
        });
        if (casting.isEmpty())
            return null;
        else
            return casting;
    }

    private boolean objectCasted(List<Map<String, PsiType>> castingMapList) {
        Set<String> commonKeys = castingMapList.get(0).keySet();
        System.out.println(castingMapList);
        for (Map<String, PsiType> map : castingMapList) {
            commonKeys.retainAll(map.keySet());
        }
        if (!commonKeys.isEmpty()) {
            for (String key : commonKeys) {
                Set<PsiType> values = new HashSet<>();
                for (Map<String, PsiType> map : castingMapList) {
                    if (map.get(key) != null)
                        values.add(map.get(key));
                }
                if (values.size() > 1)
                    return true;
            }
        }
        return false;
    }
}
