package ui;

import detecting.BaseDetectAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class CodescentPopUp extends JPopupMenu {

    JMenuItem anItem;

    public CodescentPopUp(Object object) {
        if (object != null) {
            if (object instanceof BaseDetectAction) {
                anItem = new JMenuItem(((BaseDetectAction) object).description());
                add(anItem);
            }
        }
    }
}
