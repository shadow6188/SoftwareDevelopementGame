package tankgame.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
    private int angle;
    private int vx = 0;
    private int vy = 0;

    public Bullet(int x, int y,int angle, BufferedImage image){

        super(x + (int)Math.round(image.getWidth() * Math.cos(Math.toRadians(angle))),
                y + (int)Math.round(image.getHeight() * Math.sin(Math.toRadians(angle))), image);
        this.angle = angle;
    }

    public void moveForwards() {
        vx = (int) Math.round(2 * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(2 * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

        rect.setLocation(x,y);
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), height/ 2.0, width / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
    }

    @Override
    public String toString() {
        return "Bullet";
    }
}
