package tankgame.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject{
    boolean active;
    public PowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
        active = true;
    }

    @Override
    public Rectangle getBoundary() {
        return super.getBoundary();
    }

    @Override
    public void drawImage(Graphics g) {
        if(active){
            g.drawImage(image, x, y,null);
        }
    }
    public void acquired(){
        active = false;
    }
}
