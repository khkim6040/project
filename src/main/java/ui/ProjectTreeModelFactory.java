package ui;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 * Factory Class responsible for creation of Project Structure Tree: Refactoring Techinques
 */
class ProjectTreeModelFactory {

    /**
     * Create a tree model that describes the 'Refactoring' structure of a java project.
     * This method use JavaRecursiveElementVisitor to traverse the whole project with the Java hierarchy
     * from each root package in the source directory to the one tiny single statement,
     * and finds out whether every particular 'Refactoring Techinque' is applicable or not.
     *
     * Instance of {@link DefaultMutableTreeNode} that can have a user object. The user object of root is the project
     * itself, and other nodes have corresponding instances of 'Refactoring Techinque's,
     * which has corresponding 'PsiElement's as a child. (refactoring applicable)
     *
     * @param project a project
     * @return a tree model to describe the structure of project
     */
    public static TreeModel createProjectTreeModel(Project project, Map<String, List<PsiElement>> result) {
        // the root node of the tree
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);
        final Map<String, DefaultMutableTreeNode> rootRef = new HashMap<>();
        
        for (String actionId : result.keySet()) {
            List<PsiElement> codeSmellElements = result.get(actionId);
            if (!actionId.isEmpty()) {
                addTreeNodes(root, rootRef, actionId, codeSmellElements);
            }
        }

        return new DefaultTreeModel(root);
    }

    /**
     * Adds new DefaultMutableTreeNode (Category) if missing,
     * and Adds new DefaultMutableTreeNode (PsiElement).
     *
     * @param root        Root node of this JTree
     * @param rootRef     Map with ID Keys and corresponding 'Refactoring Technique' Nodes
     * @param id          Refactoring ID
     * @param psiElements Target List of PsiElement to add
     */
    private static void addTreeNodes(
        DefaultMutableTreeNode root,
        Map<String, DefaultMutableTreeNode> rootRef,
        String id,
        List<PsiElement> psiElements) {

        DefaultMutableTreeNode rootRefNode = rootRef.get(id);
        if (rootRefNode == null) {
            addCodeSmells(root, rootRef, id);
        }

        rootRefNode = rootRef.get(id);
        for (PsiElement psiElement : psiElements) {
            rootRefNode.add(
                new DefaultMutableTreeNode(psiElement));
        }
    }

    /**
     * Adds new DefaultMutableTreeNode (Category) and connect to the root.
     *
     * @param root    Root node of this JTree
     * @param rootRef Map with ID Keys and corresponding 'Refactoring Technique' Nodes
     * @param id      Refactoring ID
     */
    private static void addCodeSmells(
        DefaultMutableTreeNode root,
        Map<String, DefaultMutableTreeNode> rootRef,
        String id) {

        DefaultMutableTreeNode rootRefNode =
            new DefaultMutableTreeNode(
                BaseDetectManager.getInstance().getDetectActionByID(id));
        rootRef.put(id, rootRefNode);
        root.add(rootRefNode);
    }
}