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

    /**
     * Method that adds a description and a precondition of BaseDetectAction to the PopUp.
     *
     * @param object Object
     */
    public CodescentPopUp(Object object) {
        if (object != null) {
            if (object instanceof BaseDetectAction action) {
                JMenuItem descriptionItem = new JMenuItem(action.description());
                add(descriptionItem);
            }
        }
    }
}
