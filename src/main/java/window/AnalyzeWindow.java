package window;

import ui.AnalyzeWindowGUI;

import javax.swing.*;

public class AnalyzeWindow implements WindowInterface {
    private final JPanel testWindowContent;

    public AnalyzeWindow() {
        AnalyzeWindowGUI gui = AnalyzeWindowGUI.getInstance();
        testWindowContent = gui.getMainPanel();
    }

    @Override
    public JPanel getContent() {
        return testWindowContent;
    }
}
