package ui;

/**
 * The class which stores user customized properties.
 *
 * @author Jinmin Goh, Gwanho Kim
 */
public class UserProperties {
    
    private static int ILM = 25;
    private static int DLC_F = 6;
    private static int DLC_M = 10;
    private static int LPL = 5;
    private static int MC = 5;
    private static int COM = 5;

    public static int getParam(String storyID) {
        return switch (storyID) {
            case "ILM" -> ILM;
            case "DLC_M" -> DLC_M;
            case "DLC_F" -> DLC_F;
            case "LPL" -> LPL;
            case "MC" -> MC;
            case "COM" -> COM;
            default -> 0;
        };
    }

    public static void setILM(int ILM) {
        UserProperties.ILM = ILM;
    }

    public static void setDLC_M(int DLC_M) {
        UserProperties.DLC_M = DLC_M;
    }

    public static void setDLC_F(int DLC_F) {
        UserProperties.DLC_F = DLC_F;
    }

    public static void setLPL(int LPL) {
        UserProperties.LPL = LPL;
    }

    public static void setMC(int MC) {
        UserProperties.MC = MC;
    }

    public static void setCOM(int COM) {
        UserProperties.COM = COM;
    }


}
