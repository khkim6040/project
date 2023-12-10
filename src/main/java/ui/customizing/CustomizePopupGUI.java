package ui.customizing;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jetbrains.annotations.Nullable;

/**
 * The GUI class that shows pop-up window for customize button
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class CustomizePopupGUI extends JFrame implements TableModelListener {

    private JTable customizeTable;
    private JBScrollPane scrollPane;
    private String[] columnType = {"Parameter", "Input"};
    private Object[][] data;
    private Project project;

    public CustomizePopupGUI(@Nullable Project p, AnActionEvent e) throws IOException {
        project = p;
        try {
            HandleConfig.getHandler(project);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        data = new Object[][]{
            {"Long Method (Lines of code)", UserProperties.getParam("LM")},
            {"Large Class (Number of fields)", UserProperties.getParam("LCF")},
            {"Large Class (Number of methods)", UserProperties.getParam("LCM")},
            {"Long Parameter List (Parameter count)", UserProperties.getParam("LPL")},
            {"Message Chain (Length)", UserProperties.getParam("MC")},
            {"Comments (Lines of comments)", UserProperties.getParam("COM")}
        };
        setTitle("Parameter Customization");
        setSize(600, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        customizeTable = new JTable(data, columnType);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnType) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1;
            }
        };
        customizeTable.setModel(tableModel);
        scrollPane = new JBScrollPane(customizeTable);
        customizeTable.setFillsViewportHeight(true);
        customizeTable.getModel().addTableModelListener(this);

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Method that gets parameters when user changes input of popup table.
     * This method gets parameters and saves them into UserProperties class.
     *
     * @param e TableModelEvent
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column == 1) {
            TableModel model = (TableModel) e.getSource();
            String str = (String) model.getValueAt(row, column);
            try {
                if (!(Integer.parseInt(str) > 0)) {
                    JOptionPane.showMessageDialog(this, "Not valid input. Please enter integer bigger than 0.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    switch (row) {
                        case 0:
                            UserProperties.setLM(Integer.parseInt(str));
                            break;
                        case 1:
                            UserProperties.setLCF(Integer.parseInt(str));
                            break;
                        case 2:
                            UserProperties.setLCM(Integer.parseInt(str));
                            break;
                        case 3:
                            UserProperties.setLPL(Integer.parseInt(str));
                            break;
                        case 4:
                            UserProperties.setMC(Integer.parseInt(str));
                            break;
                        case 5:
                            UserProperties.setCOM(Integer.parseInt(str));
                            break;
                    }
                }
            } catch (NumberFormatException | IOException exception) {
                JOptionPane.showMessageDialog(this, "Not valid input. Please enter integer bigger than 0.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
