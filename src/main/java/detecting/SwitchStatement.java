package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSwitchStatement;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeCastExpression;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.LoadPsi;

/**
 * Class to provide detecting code smell: 'Switch statement'
 *
 * @author Hyeonbenn Park
 * @author Chanho Song
 */
public class SwitchStatement extends BaseDetectAction {

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "SS";
    }

    /* Returns the story name as a string format, for message. */
    @Override
    public String storyName() {
        return "Switch Statement";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>There are conditional statements that identify class of object that leads to " +
            "casting of the object to use method of the class</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>instanceof in if statement and multiple casting of object dependent to condition</html>";
    }

    /**
     * Method that checks whether code has switch statement
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        List<PsiElement> smellySwitchStatement = new ArrayList<>();
        Set<PsiIfStatement> visitedIfStatements = new HashSet<>();
        List<PsiStatement> allStatements = new ArrayList<>(
            PsiTreeUtil.collectElementsOfType(psiFile, PsiStatement.class));

        for (PsiStatement statement : allStatements) {
            if (statement instanceof PsiIfStatement && !visitedIfStatements.contains(statement)) {
                if (detectSmell(statement)) {
                    smellySwitchStatement.add(statement);
                }
                trackIfElseIfChain((PsiIfStatement) statement, visitedIfStatements);
            } else if (statement instanceof PsiSwitchStatement) {
                if (detectSmell(statement)) {
                    smellySwitchStatement.add(statement);
                }
            }
        }

        return smellySwitchStatement;
    }

    /**
     * To resolve an error where the else if statement is processed multiple times
     *
     * @param ifStatement
     * @param visitedIfStatements
     */
    private void trackIfElseIfChain(PsiIfStatement ifStatement, Set<PsiIfStatement> visitedIfStatements) {
        PsiIfStatement current = ifStatement;
        while (current != null) {
            visitedIfStatements.add(current);
            PsiElement elseBranch = current.getElseBranch();
            if (elseBranch instanceof PsiIfStatement) {
                current = (PsiIfStatement) elseBranch;
            } else {
                break;
            }
        }
    }

    /**
     * Helper method to check if code has switch statement
     *
     * @param statement PsiStatement
     * @return true if method has type casting switch statement
     */
    private boolean detectSmell(PsiStatement statement) {
        List<Map<String, PsiType>> castingMapList = new ArrayList<>();

        if (statement instanceof PsiIfStatement) {
            PsiIfStatement ifstatement = (PsiIfStatement) statement;

            PsiStatement thenBranch = ifstatement.getThenBranch();
            PsiStatement elseBranch = ifstatement.getElseBranch();

            if (CreateCastingMap(thenBranch) != null) {
                castingMapList.add(CreateCastingMap(thenBranch));
            }

            if (CreateCastingMap(elseBranch) != null) {
                castingMapList.add(CreateCastingMap(elseBranch));
            }

            while (elseBranch != null && elseBranch instanceof PsiIfStatement) {
                elseBranch = ((PsiIfStatement) elseBranch).getElseBranch();
                if (CreateCastingMap(elseBranch) != null) {
                    castingMapList.add(CreateCastingMap(elseBranch));
                }
            }

            return FindMultiCastedObject(castingMapList);

        } else if (statement instanceof PsiSwitchStatement) {
            PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;
            PsiCodeBlock switchBody = switchStatement.getBody();

            if (switchBody != null) {
                for (PsiStatement childStatement : switchBody.getStatements()) {
                    Map<String, PsiType> castingMap = CreateCastingMap(childStatement);
                    if (castingMap != null) {
                        castingMapList.add(castingMap);
                    }
                }
            }
            return FindMultiCastedObject(castingMapList);
        }

        return false;

    }

    private Map<String, PsiType> CreateCastingMap(PsiStatement statement) {
        Map<String, PsiType> casting = new HashMap<>();
        statement.accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiTypeCastExpression) {
                    casting.put(((PsiTypeCastExpression) element).getOperand().getText(),
                        ((PsiTypeCastExpression) element).getType());
                }
            }
        });
        if (casting.isEmpty()) {
            return null;
        } else {
            return casting;
        }
    }

    private boolean FindMultiCastedObject(List<Map<String, PsiType>> castingMapList) {
        if (castingMapList.isEmpty()) {
            return false;
        }

        Set<String> commonKeys = castingMapList.get(0).keySet();

        for (Map<String, PsiType> map : castingMapList) {
            commonKeys.retainAll(map.keySet());
        }
        if (!commonKeys.isEmpty()) {
            for (String key : commonKeys) {
                Set<PsiType> values = new HashSet<>();
                for (Map<String, PsiType> map : castingMapList) {
                    if (map.get(key) != null) {
                        values.add(map.get(key));
                    }
                }
                if (values.size() > 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
