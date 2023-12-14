package ui.customizing;

import java.io.IOException;

/**
 * The class which stores user customized properties.
 *
 * @author Jinmin Goh, Gwanho Kim
 */
public class UserProperties {

    private static int LM = 25;
    private static int LCF = 6;
    private static int LCM = 10;
    private static int LPL = 5;
    private static int MC = 5;
    private static int COM = 5;

    public static int getParam(String storyID) {
        return switch (storyID) {
            case "LM" -> LM;
            case "LCM" -> LCM;
            case "LCF" -> LCF;
            case "LPL" -> LPL;
            case "MC" -> MC;
            case "COM" -> COM;
            default -> 0;
        };
    }

    public static void setLM(int LM) throws IOException {
        UserProperties.LM = LM;
        HandleConfig.setConfigParam("LM", UserProperties.LM);
    }

    public static void setLCM(int LCM) throws IOException {
        UserProperties.LCM = LCM;
        HandleConfig.setConfigParam("LCM", UserProperties.LCM);
    }

    public static void setLCF(int LCF) throws IOException {
        UserProperties.LCF = LCF;
        HandleConfig.setConfigParam("LCF", UserProperties.LCF);
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
