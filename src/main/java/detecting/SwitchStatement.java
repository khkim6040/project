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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.LoadPsi;

/**
 * Class to detect 'SwitchStatement' that has code semll.
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to identify large class field code smells.
 *
 * @author Hyeonbenn Park
 * @author Chanho Song
 */
public class SwitchStatement extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Switch Statement' detection story.
     */
    @Override
    public String storyID() {
        return "SS";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Switch Statement";
    }

    /**
     * Describes the criteria for detecting a switch statement code smell.
     *
     * @return A String in HTML format describing when a switch statement is considered as a code smell.
     */
    @Override
    public String description() {
        return "<html>There are conditional statements that identify class of object that leads to " +
            "casting of the object to use method of the class</html>";
    }

    /**
     * Defines the precondition for this code smell detection.
     *
     * @return A String in HTML format stating the precondition for detecting large class code smell.
     */
    @Override
    public String precondition() {
        return "<html>If type casting is used in a conditional statement, it is a code smell.</html>";
    }

    /**
     * Find conditional statements that has a type casting.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each is a smelly conditional statement.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);
        Set<PsiIfStatement> visitedIfStatements = new HashSet<>();

        return Stream.of(psiFile.getChildren())
            .flatMap(child -> PsiTreeUtil.findChildrenOfType(child, PsiStatement.class).stream())
            .filter(statement -> (statement instanceof PsiIfStatement && !visitedIfStatements.contains(statement))
                || statement instanceof PsiSwitchStatement)
            .peek(statement -> {
                if (statement instanceof PsiIfStatement) {
                    trackIfElseIfChain((PsiIfStatement) statement, visitedIfStatements);
                }
            })
            .filter(this::detectSmell)
            .collect(Collectors.toList());
    }

    /**
     * Traverses and records a chain of if-else-if statements from a given ifStatement.
     * Each visited statement is added to the visitedIfStatements set.
     * Traversal stops when there are no more else-if branches.
     *
     * @param ifStatement         The starting if statement.
     * @param visitedIfStatements Set to record visited if and else-if statements.
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
     * Detects code smells in a given PsiStatement by identifying objects that are casted to different types
     * within the branches of an if or switch statement.
     * This method examines if-else and switch statements to see if the same object is casted to different types
     * in different branches.
     *
     * @param statement The PsiStatement to analyze, either a PsiIfStatement or a PsiSwitchStatement.
     * @return true if a multi-casted object is found in the statement's branches, false otherwise.
     */
    private boolean detectSmell(PsiStatement statement) {
        List<Map<String, PsiType>> castingMapList = new ArrayList<>();

        if (statement instanceof PsiIfStatement) {
            PsiIfStatement ifstatement = (PsiIfStatement) statement;

            PsiStatement thenBranch = ifstatement.getThenBranch();
            PsiStatement elseBranch = ifstatement.getElseBranch();

            if (createCastingMap(thenBranch) != null) {
                castingMapList.add(createCastingMap(thenBranch));
            }

            if (createCastingMap(elseBranch) != null) {
                castingMapList.add(createCastingMap(elseBranch));
            }

            while (elseBranch != null && elseBranch instanceof PsiIfStatement) {
                elseBranch = ((PsiIfStatement) elseBranch).getElseBranch();
                if (createCastingMap(elseBranch) != null) {
                    castingMapList.add(createCastingMap(elseBranch));
                }
            }

            return FindMultiCastedObject(castingMapList);

        } else if (statement instanceof PsiSwitchStatement) {
            PsiSwitchStatement switchStatement = (PsiSwitchStatement) statement;
            PsiCodeBlock switchBody = switchStatement.getBody();

            if (switchBody != null) {
                for (PsiStatement childStatement : switchBody.getStatements()) {
                    Map<String, PsiType> castingMap = createCastingMap(childStatement);
                    if (castingMap != null) {
                        castingMapList.add(castingMap);
                    }
                }
            }
            return FindMultiCastedObject(castingMapList);
        }

        return false;

    }

    /**
     * Creates a map of variable names to their cast types within a given PsiStatement.
     * It traverses the statement and identifies all type casting expressions.
     * Each cast expression is added to the map with the operand's text as the key and the cast type as the value.
     * If no cast expressions are found, the method returns null.
     *
     * @param statement The PsiStatement to be analyzed for type casting expressions.
     * @return A Map of variable names to PsiType representing the type casts, or null if no casts are found.
     */
    private Map<String, PsiType> createCastingMap(PsiStatement statement) {
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

    /**
     * Determines if there is a common object across multiple casting maps that is casted to different types.
     * This method checks if any object (represented by its key) in the list of casting maps is casted to more than one
     * type.
     * If it finds an object that is casted to multiple types in different maps, it returns true.
     * If no such object is found or the list of casting maps is empty, it returns false.
     *
     * @param castingMapList A list of maps, each containing keys (object names) and their corresponding casted types
     *                       (PsiType).
     * @return true if an object is casted to multiple types across the maps, false otherwise.
     */
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
