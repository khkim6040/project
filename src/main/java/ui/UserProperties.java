package ui;

import java.io.IOException;

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

    public static void initializeParam() throws IOException {
        HandleConfig.initializeConfig();
    }

    public static void setILM(int ILM) throws IOException {
        UserProperties.ILM = ILM;
        HandleConfig.setConfigParam("ILM", UserProperties.ILM);
    }

    public static void setDLC_M(int DLC_M) throws IOException {
        UserProperties.DLC_M = DLC_M;
        HandleConfig.setConfigParam("DLC_M", UserProperties.DLC_M);
    }

    public static void setDLC_F(int DLC_F) throws IOException {
        UserProperties.DLC_F = DLC_F;
        HandleConfig.setConfigParam("DLC_F", UserProperties.DLC_F);
    }

    public static void setLPL(int LPL) throws IOException {
        UserProperties.LPL = LPL;
        HandleConfig.setConfigParam("LPL", UserProperties.LPL);
    }

    public static void setMC(int MC) throws IOException {
        UserProperties.MC = MC;
        HandleConfig.setConfigParam("MC", UserProperties.MC);
    }

    public static void setCOM(int COM) throws IOException {
        UserProperties.COM = COM;
        HandleConfig.setConfigParam("COM", UserProperties.COM);
    }
}
