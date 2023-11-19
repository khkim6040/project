package detecting;

/**
 * Singleton Class with managing features of Multiple BaseDetectActions.
 *
 *
 */
public class BaseDetectManager {
    private static BaseDetectManager manager = null;

    /* Design Pattern: Singleton */
    protected BaseDetectManager () { }

    public static BaseDetectManager getInstance() {
        if (manager == null)
            manager = new BaseDetectManager();
        return manager;
    }

    /**
     * Method that fetches Refactoring Method name by ID.
     *
     * @param id Refactoring Techinque ID
     * @return Corresponding Refactoring name (story name)
     */
    public BaseDetectAction getDetectActionByID (String id) {
        switch (id) {
            // Scope: Class
            case "LPL":
                return new LongParameterList();


            default:
                return null;
        }
    }

}
