package utils;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UserConfigHandler {

    private static UserConfigHandler handler = null;

    private Properties prop;
    private File configFile;
    private FileInputStream fis;
    private FileWriter writer;

    public UserConfigHandler(Project project) throws IOException {
        prop = new Properties();
        String contentRoot = String.valueOf(
            ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0]);
        contentRoot = contentRoot.replace("file://", "");
        configFile = new File(contentRoot + "/codescent.properties");
        fis = new FileInputStream(configFile);
    }

    public static UserConfigHandler getHandler(Project project) throws IOException {
        if (handler == null) {
            handler = new UserConfigHandler(project);
        }
        return handler;
    }

    // TODO: Implement method that return value in codescent.properties according to storyID
    public String getProperty(String storyID) {
        return null;
    }

    public Properties getProp() {
        return prop;
    }

    public FileWriter getWriter() throws IOException {
        writer = new FileWriter(configFile);
        return writer;
    }

    public FileInputStream getFis() {
        return fis;
    }
}
