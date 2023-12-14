package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.LoadPsi;


/**
 * Class to provide detecting: 'DeadCode'
 * It extends BaseDetectAction, inheriting its basic functionality and
 * specializing it to find the dead code.
 *
 * @author Hyeonbeen Park
 * @author Chanho Song
 */
public class DeadCode extends BaseDetectAction {

    /**
     * Returns the unique story ID for this detection.
     *
     * @return A String identifier for the 'Dead Code' detection story.
     */
    @Override
    public String storyID() {
        return "DC";
    }

    /**
     * Provides the name of this detection story.
     *
     * @return A String representing the name of this detection story.
     */
    @Override
    public String storyName() {
        return "Dead Code";
    }

    /**
     * Describes the criteria for detecting a dead code.
     *
     * @return A String in HTML format describing what is a dead code.
     */
    @Override
    public String description() {
        return "There are variables and method that is declared but not used. They are code smell 'deadcode'.";
    }

    /**
     * Checks whether variables or methods in the given action event context are dead code.
     *
     * @param e AnActionEvent representing the context in which the action is performed.
     * @return A List of PsiElement objects, each is a dead code.
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        List<PsiVariable> variables = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiVariable.class));
        List<PsiMethod> methods = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, PsiMethod.class));

        return Stream.concat(variables.stream(), methods.stream())
            .filter(this::detectSmell)
            .collect(Collectors.toList());
    }

    /**
     * Checks whether a given PsiElement (variable or method) is used in the code.
     * Excludes the 'main' method and its 'args' parameter, as they are entry points of the application.
     *
     * @param element The PsiElement (either a variable or method) to check.
     * @return true if the element is not used in the code, indicating dead code.
     */
    private boolean detectSmell(PsiElement element) {
        if (element == null) {
            return false;
        }
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;
            if (isMainMethod(method)) {
                return false;
            }
        } else if (element instanceof PsiParameter) {
            PsiParameter parameter = (PsiParameter) element;
            PsiElement parent = parameter.getParent();
            PsiElement grandParent = parent.getParent();
            if (grandParent instanceof PsiMethod && isMainMethod((PsiMethod) grandParent)) {
                return false;
            }

        }
        return ReferencesSearch.search(element).findAll().isEmpty();
    }

    /**
     * Checks whether a given PsiMethod is the main method of a Java application.
     * The main method is identified by having the name "main", exactly one parameter,
     * and the parameter type must be an array of Strings.
     *
     * @param method The PsiMethod to be checked.
     * @return true if the method is the main method, false otherwise.
     */
    private boolean isMainMethod(PsiMethod method) {
        return method.getName().equals("main") &&
            method.getParameterList().getParametersCount() == 1 &&
            method.getParameterList().getParameters()[0].getType().getCanonicalText().endsWith("String[]");
    }
}

