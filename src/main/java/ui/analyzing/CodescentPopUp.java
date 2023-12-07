package ui.analyzing;

import detecting.BaseDetectAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Class that composes Right Click Popup.
 *
 * @author Seokhwan Choi
 * @author CSED332 2020 Wanted
 */
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
