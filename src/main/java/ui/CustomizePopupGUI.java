package ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomizePopupGUI extends JFrame implements TableModelListener {
    private JTable customizeTable;
    private JBScrollPane scrollPane;
    private AnActionEvent event;
    private String[] columnType = {"Parameter", "Input"};
    private Object[][] data;

    public CustomizePopupGUI(@Nullable Project project, AnActionEvent e) throws IOException {
        Properties prop = new Properties();
        InputStream input = CustomizePopupGUI.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(input);
        data = new Object[][]{
                {"Long Method", prop.getProperty("PARAM_ILM")},
                {"Large Class", prop.getProperty("PARAM_DLC")},
                {"Long Parameter List", prop.getProperty("PARAM_LPL")},
                {"Message Chain", prop.getProperty("PARAM_MESSAGE_CHAIN")},
                {"Comments - Low Level", prop.getProperty("PARAM_COMMENTS_LOW")}
        };
        event = e;
        setTitle("Parameter Customization");
        setSize(400, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        customizeTable = new JTable(data, columnType);
        scrollPane = new JBScrollPane(customizeTable);
        customizeTable.setFillsViewportHeight(true);
        customizeTable.getModel().addTableModelListener(this);

        TableColumn inputColumn = customizeTable.getColumn("Input");
        JTextField inputBox = new JTextField();
        inputColumn.setCellEditor(new DefaultCellEditor(inputBox));

        add(scrollPane);
        setVisible(true);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (column == 1) {
            TableModel model = (TableModel) e.getSource();
            String colName = model.getColumnName(column);
            String str = (String) model.getValueAt(row, column);
            if (Integer.parseInt(str) > 100) {
                JOptionPane.showMessageDialog(this, "Not valid input", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

//    public String getMessage() {
//        String message;
//        List<Integer> args = userInput(event);
//        message = "Arg1: " + args.get(0).toString() + "\n"
//                + "Arg2: " + args.get(1).toString() + "\n"
//                + "Arg3: " + args.get(2).toString() + "\n"
//                + "Arg4: " + args.get(3).toString();
//        return message;
//    }
//
//    public List<Integer> userInput(AnActionEvent e) {
//        Project project = e.getProject();
//
//        int arg1 = getUserDefinedThreshold(project, "Enter arg1:", 1);
//        int arg2 = getUserDefinedThreshold(project, "Enter arg2:", 2);
//        int arg3 = getUserDefinedThreshold(project, "Enter arg3:", 3);
//        int arg4 = getUserDefinedThreshold(project, "Enter arg4:", 4);
//        List<Integer> args = Arrays.asList(arg1, arg2, arg3, arg4);
//        return args;
//    }
//
//    private int getUserDefinedThreshold(Project project, String prompt, int defaultValue) {
//        String response = Messages.showInputDialog(
//                project,
//                prompt,
//                "Customization Input",
//                Messages.getQuestionIcon(),
//                Integer.toString(defaultValue),
//                new IntegerInputValidator()
//        );
//
//        // User pressed Cancel or closed the dialog
//        if (response == null) {
//            return defaultValue;
//        }
//
//        try {
//            return Integer.parseInt(response);
//        } catch (NumberFormatException e) {
//            return defaultValue;
//        }
//    }
//
//    private static class IntegerInputValidator implements InputValidator {
//        @Override
//        public boolean checkInput(String inputString) {
//            try {
//                int value = Integer.parseInt(inputString);
//                return value > 0;
//            } catch (NumberFormatException e) {
//                return false;
//            }
//        }
//
//        @Override
//        public boolean canClose(String inputString) {
//            return checkInput(inputString);
//        }
//    }
}
