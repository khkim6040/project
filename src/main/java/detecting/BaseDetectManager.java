package detecting;

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
            case "DLC_F":
                return new DetectLargeClassField();
            case "DLC_M":
                return new DetectLargeClassMethod();
            case "ILM":
                return new IdentifyLongMethod();
            case "SS":
                return new SwitchStatement();
            case "FDC":
                return new FindDuplicatedCode();
            case "PN":
                return new PoorName();
            case "DC":
                return new DeadCode();
            case "MC":
                return new MessageChain();
            case "CM":
                return new Comments();

            default:
                return null;
        }
    }

}
