package ui;

import static ui.UserProperties.setCOM;
import static ui.UserProperties.setLCF;
import static ui.UserProperties.setLCM;
import static ui.UserProperties.setLM;
import static ui.UserProperties.setLPL;
import static ui.UserProperties.setMC;

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

    static String configPath;
    static File configFile;
    static Properties prop;
    static FileInputStream fis;
    private static HandleConfig handler = null;

    public HandleConfig(Project project) throws IOException {
        prop = new Properties();
        configPath = String.valueOf(
            ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0]);
        configPath = configPath.replace("file://", "") + "/codescent.properties";
        System.out.println(configPath);
        configFile = new File(configPath);
    }

    public static void initializeConfig() throws IOException {
        // create file if no config file
        if (!configFile.exists()) {
            createConfig(configPath, configFile);
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

    public static HandleConfig getHandler(Project project) throws IOException {
        if (handler == null) {
            handler = new HandleConfig(project);
        }
        return handler;
    }

    public static Integer getConfigParam(String storyID) throws IOException {
        prop.load(fis);
        return Integer.parseInt(prop.getProperty(storyID));
    }

    public static void setConfigParam(String storyID, int param) throws IOException {
        FileWriter writer = new FileWriter(configPath);
        prop.setProperty(storyID, String.valueOf(param));
        prop.store(writer, "");
    }

    private static void createConfig(String configPath, File file) {
        try {
            file.createNewFile();
            FileWriter myWriter = new FileWriter(configPath);
            myWriter.write("LM=25\n");
            myWriter.write("LCM=6\n");
            myWriter.write("LCF=10\n");
            myWriter.write("LPL=5\n");
            myWriter.write("MC=5\n");
            myWriter.write("COM=5\n");
            myWriter.close();
            System.out.println("Successfully created new config file.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
