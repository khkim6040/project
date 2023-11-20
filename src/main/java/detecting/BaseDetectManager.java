package detecting;

/**
 * Singleton Class with managing features of Multiple BaseDetectActions.
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
            // Scope: Class
            case "LPL":
                return new LongParameterList();


            default:
                return null;
        }
    }

}
