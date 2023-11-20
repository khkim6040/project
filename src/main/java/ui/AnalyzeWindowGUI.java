package ui;

import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;

public class AnalyzeWindowGUI {
    static private AnalyzeWindowGUI instance;

    static {
        instance = new AnalyzeWindowGUI();
    }

    private JPanel mainPanel;

    /**
     * Construct MessageWindowGUI
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
     * A Method to get instance of MessageWindowGUI
     *
     * @return instance of MessageWindowGUI
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
