package ui;

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
        data = new Object[][]{
            {"Long Method (Lines of code)", UserProperties.getParam("ILM")},
            {"Large Class (Number of fields)", UserProperties.getParam("DLC_F")},
            {"Large Class (Number of methods)", UserProperties.getParam("DLC_M")},
            {"Long Parameter List (Parameter count)", UserProperties.getParam("LPL")},
            {"Message Chain (Length)", UserProperties.getParam("MC")},
            {"Comments;Low Level (Lines of comments)", UserProperties.getParam("COM")}
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
                            UserProperties.setILM(Integer.parseInt(str));
                            break;
                        case 1:
                            UserProperties.setDLC_F(Integer.parseInt(str));
                            break;
                        case 2:
                            UserProperties.setDLC_M(Integer.parseInt(str));
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
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Not valid input. Please enter integer bigger than 0.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
