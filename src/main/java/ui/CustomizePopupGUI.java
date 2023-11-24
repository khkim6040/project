package ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * The GUI class that shows pop-up window for customize button
 *
 * @author Jinmin Goh, Seokhwan Choi
 */
public class CustomizePopupGUI extends JFrame implements TableModelListener {
    public Properties prop;
    public File configFile;
    private JTable customizeTable;
    private JBScrollPane scrollPane;
    private String[] columnType = {"Parameter", "Input"};
    private Object[][] data;
    private Project project;

    public CustomizePopupGUI(@Nullable Project p, AnActionEvent e) throws IOException {
        project = p;
        prop = new Properties();
        String contentRoot = String.valueOf(ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0]);
        contentRoot = contentRoot.replace("file://", "");
        configFile = new File(contentRoot + "/codescent.properties");
        FileInputStream fis = new FileInputStream(configFile);
        prop.load(fis);
        data = new Object[][]{
                {"Long Method (Lines of code)", prop.getProperty("PARAM_IDENTIFY_LONG_METHOD")},
                {"Large Class (Number of fields)", prop.getProperty("PARAM_DETECT_LARGE_CLASS_FIELD")},
                {"Large Class (Number of methods)", prop.getProperty("PARAM_DETECT_LARGE_CLASS_METHOD")},
                {"Long Parameter List (Parameter count)", prop.getProperty("PARAM_LONG_PARAMETER_LIST")},
                {"Message Chain (Length)", prop.getProperty("PARAM_MESSAGE_CHAIN")},
                {"Comments;Low Level (Lines of comments)", prop.getProperty("PARAM_COMMENTS_LOW")}
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
                    JOptionPane.showMessageDialog(this, "Not valid input. Please enter integer bigger than 0.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    switch (row) {
                        case 0:
                            prop.setProperty("PARAM_IDENTIFY_LONG_METHOD", str);
                            break;
                        case 1:
                            prop.setProperty("PARAM_DETECT_LARGE_CLASS_FIELD", str);
                            break;
                        case 2:
                            prop.setProperty("PARAM_DETECT_LARGE_CLASS_METHOD", str);
                            break;
                        case 3:
                            prop.setProperty("PARAM_LONG_PARAMETER_LIST", str);
                            break;
                        case 4:
                            prop.setProperty("PARAM_MESSAGE_CHAIN", str);
                            break;
                        case 5:
                            prop.setProperty("PARAM_COMMENTS_LOW", str);
                            break;
                    }
                }
                FileWriter writer = new FileWriter(configFile);
                prop.store(writer, "");
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Not valid input. Please enter integer bigger than 0.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
