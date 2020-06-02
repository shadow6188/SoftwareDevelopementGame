package tankgame.objects;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject implements Collidable {
    private int state;
    private BufferedImage secondState;

    public BreakableWall(int x, int y, BufferedImage wall, BufferedImage Breaking){
        super(x,y, wall);
        state = 2;
        secondState = Breaking;
    }
    public boolean intact(){
        return state > 0;
    }

    public void hit(){
        state--;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (state > 1)
            g2d.drawImage(this.image,AffineTransform.getTranslateInstance(x,y), null);
        else
            g2d.drawImage(this.secondState,AffineTransform.getTranslateInstance(x,y), null);
    }
}