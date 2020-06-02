package tankgame.objects;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{

    public Wall (int x, int y, BufferedImage wall){
        super(x,y, wall);
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image,AffineTransform.getTranslateInstance(x,y), null);
    }

    @Override
    public String toString() {
        return "Wall";
    }
}
