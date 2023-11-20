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
        if (manager == null)
            manager = new BaseDetectManager();
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
            case "DLC":
                return new DetectLargeClass();
            case "ILM":
                return new IdentifyLongMethod();


            default:
                return null;
        }
    }

}
