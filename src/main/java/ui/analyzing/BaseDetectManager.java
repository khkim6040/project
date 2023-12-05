package ui.analyzing;

import detecting.BaseDetectAction;
import detecting.Comments;
import detecting.DeadCode;
import detecting.DuplicatedCode;
import detecting.LargeClassField;
import detecting.LargeClassMethod;
import detecting.LongMethod;
import detecting.LongParameterList;
import detecting.MessageChain;
import detecting.PoorName;
import detecting.SwitchStatement;

/**
 * Singleton Class with managing features of Multiple BaseDetectActions.
 *
 * @author Jinyoung Kim
 * @author CSED332 2020 Team Wanted
 */
public class BaseDetectManager {

    private static BaseDetectManager manager = null;

    /* Design Pattern: Singleton */
    protected BaseDetectManager() {
    }

    public static BaseDetectManager getInstance() {
        if (manager == null) {
            manager = new BaseDetectManager();
        }
        return manager;
    }

    /**
     * Method that fetches Code Smell Detection Method name by ID.
     *
     * @param id Code Smell ID
     * @return Corresponding Detecting name (story name)
     */
    public BaseDetectAction getDetectActionByID(String id) {
        switch (id) {
            // Priority 1 functions
            case "LPL":
                return new LongParameterList();
            case "LCF":
                return new LargeClassField();
            case "LCM":
                return new LargeClassMethod();
            case "LM":
                return new LongMethod();
            case "SS":
                return new SwitchStatement();
            case "DPC":
                return new DuplicatedCode();
            case "PN":
                return new PoorName();
            case "DC":
                return new DeadCode();
            case "MC":
                return new MessageChain();
            case "COM":
                return new Comments();

            default:
                return null;
        }
    }

}
