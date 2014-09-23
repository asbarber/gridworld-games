package game.assets;

import java.io.InputStream;

/**
 * This class is used for handling all custom assets within GridWorld.
 * @author Aaron
 */
public class AssetManager {
    //public static final String ASSET_PATH = "GridWorldCode\\framework\\Test\\assets\\";    
    
    /**
     * The file will be returned as type InputStream.
     * @param filename The file to get (ex: "PacmanMaze.txt")
     * @return The file as a stream.
     */
    public static InputStream getFile(String filename){
        return AssetManager.class.getResourceAsStream(filename);
    }
}
