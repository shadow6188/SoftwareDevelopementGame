package tankgame.resources;

import tankgame.GameDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
    private static Map<String, Object> resources;

    static {
        resources = new HashMap<>();
        try{
            resources.put("tank1", ImageIO.read(GameDriver.class.getClassLoader().getResource("tank1.png")));
            resources.put("tank2", ImageIO.read(GameDriver.class.getClassLoader().getResource("tank2.png")));
            resources.put("background",ImageIO.read(GameDriver.class.getClassLoader().getResource("Background.bmp")));
            resources.put("wall", ImageIO.read(GameDriver.class.getClassLoader().getResource("Wall.png")));
            resources.put("breakable_wall",ImageIO.read(GameDriver.class.getClassLoader().getResource("BreakWall.png")));
            resources.put("bullet",ImageIO.read(GameDriver.class.getClassLoader().getResource("Shell.png")));
            resources.put("life",ImageIO.read(GameDriver.class.getClassLoader().getResource("1Up.png")));
            resources.put("P1",ImageIO.read(GameDriver.class.getClassLoader().getResource("P1.png")));
            resources.put("P2",ImageIO.read(GameDriver.class.getClassLoader().getResource("P2.png")));
            resources.put("mapInfo", GameDriver.class.getClassLoader().getResourceAsStream("map/map1"));

        } catch (IOException e) {
            System.err.println("Could not load resources");
            System.exit(-2);
        }
    }
    public static Object getResource(String key){
        return ResourceLoader.resources.get(key);
    }
}
