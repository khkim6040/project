package ui.customizing;

import static ui.customizing.UserProperties.setCOM;
import static ui.customizing.UserProperties.setLCF;
import static ui.customizing.UserProperties.setLCM;
import static ui.customizing.UserProperties.setLM;
import static ui.customizing.UserProperties.setLPL;
import static ui.customizing.UserProperties.setMC;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * The class which handles configuration file.
 *
 * @author Jinmin Goh, Gwanho Kim
 */
public class HandleConfig {

    private static String configPath;
    private static File configFile;
    private static Properties prop;
    private static FileInputStream fis;
    private static HandleConfig handler = null;

    public HandleConfig(Project project) {
        prop = new Properties();
        configPath = String.valueOf(
            ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0]);
        configPath = configPath.replace("file://", "") + "/codescent.properties";
        configFile = new File(configPath);
    }

    /**
     * Method that initializes parameters with config file's parameters.
     * If there is no config file, creates new config file.
     */
    public static void initializeConfig() throws IOException {
        // create file if no config file
        if (!configFile.exists()) {
            createConfig();
        }
        fis = new FileInputStream(configFile);
        // if file exists, set userProperties
        setLM(getConfigParam("LM"));
        setLCM(getConfigParam("LCM"));
        setLCF(getConfigParam("LCF"));
        setLPL(getConfigParam("LPL"));
        setMC(getConfigParam("MC"));
        setCOM(getConfigParam("COM"));
    }

    /**
     * Method that returns config file handler(singleton).
     *
     * @param project current project
     * @return current config file handler
     */
    public static HandleConfig getHandler(Project project) throws IOException {
        if (handler == null) {
            handler = new HandleConfig(project);
        }
        return handler;
    }

    /**
     * Method that fetches parameter of story ID from config file.
     *
     * @param storyID story ID
     * @return parameter of storyID from config file
     */
    public static Integer getConfigParam(String storyID) throws IOException {
        prop.load(fis);
        return Integer.parseInt(prop.getProperty(storyID));
    }

    /**
     * Method that saves parameter of story ID into config file.
     *
     * @param storyID story ID
     * @param param   parameter to be saved
     */
    public static void setConfigParam(String storyID, int param) throws IOException {
        FileWriter writer = new FileWriter(configPath);
        prop.setProperty(storyID, String.valueOf(param));
        prop.store(writer, "");
    }

    /**
     * Method that creates new config file with default parameters.
     */
    private static void createConfig() {
        try {
            configFile.createNewFile();
            FileWriter myWriter = new FileWriter(configPath);
            myWriter.write("LM=25\n");
            myWriter.write("LCM=6\n");
            myWriter.write("LCF=10\n");
            myWriter.write("LPL=5\n");
            myWriter.write("MC=5\n");
            myWriter.write("COM=5\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
