package file;

import config.ConfigManager;

import java.io.File;
import java.nio.file.Path;

public class FileManager {
    private final String url;
    private FileManager(){
        this.url = ConfigManager.getInstance().getProperties("file.url");
    }

    public FileManager(String url) {
        this.url = url;
    }

    static FileManager getInstance(){
        return FileManagerHolder.INSTANCE;
    }
    private File getFile(){
        return new File(url);
    }

    Path path(){
        return getFile().toPath();
    }

    private static class FileManagerHolder{
        private static final FileManager INSTANCE =new FileManager();
    }
}
