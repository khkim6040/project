package ui;

import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;

/**
 * The GUI class that shows list of messages for analyze button
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class AnalyzeWindowGUI {
    static private AnalyzeWindowGUI instance;

    static {
        instance = new AnalyzeWindowGUI();
    }

    private JPanel mainPanel;

    /**
     * Construct AnalyzeWindowGUI
     */
    public AnalyzeWindowGUI() {
        mainPanel = new JPanel(new BorderLayout());
        String[] listStr = {"AAAAAAA", "BBBBBBB", "CCCCCCC", "DDDDDDD"};

        JBList myList = new JBList(listStr);

        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(myList);
        mainPanel.setVisible(true);
    }

    /**
     * A Method to get instance of AnalyzeWindowGUI
     *
     * @return instance of AnalyzeWindowGUI
     */
    public static AnalyzeWindowGUI getInstance() {
        return instance;
    }

    /**
     * Returns main JPanel
     *
     * @return JPanel mainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
