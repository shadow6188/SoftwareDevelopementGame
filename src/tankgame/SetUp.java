package tankgame;

import tankgame.objects.BreakableWall;
import tankgame.objects.GameObject;
import tankgame.objects.PowerUp;
import tankgame.objects.Wall;
import tankgame.resources.ResourceLoader;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SetUp {

    public static ArrayList<GameObject> populateWorld() {
        ArrayList<GameObject> stuff;
        stuff = new ArrayList<>();
        InputStreamReader input = new InputStreamReader((InputStream) ResourceLoader.getResource("mapInfo"));
        BufferedReader mapReader = new BufferedReader(input);
        try {
            String row = mapReader.readLine();
            String[] mapInfo = row.split("\\s+");

            int namOfColumns = Integer.parseInt(mapInfo[0]);
            int numOfRows = Integer.parseInt(mapInfo[1]);

            for (int currentRow = 0; currentRow < numOfRows; currentRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\\s+");
                for (int currentColumn = 0; currentColumn < namOfColumns; currentColumn++) {
                    switch (mapInfo[currentColumn]){
                        case "1":
                            break;
                        case "2":
                            stuff.add(new BreakableWall(32*currentColumn,32*currentRow,
                                    (BufferedImage)ResourceLoader.getResource("wall"),
                                    (BufferedImage)ResourceLoader.getResource("breakable_wall")));
                            break;
                        case "3":
                            stuff.add(new Wall(32*currentColumn,32*currentRow, (BufferedImage) ResourceLoader.getResource("wall")));
                            break;
                        case "4":
                            stuff.add(new PowerUp(32*currentColumn,32*currentRow,(BufferedImage)ResourceLoader.getResource("life")));
                        default:
                    }

                }
            }

        } catch (IOException e){
            System.err.println("Problem Reading map");
        }

        return stuff;
    }
}
