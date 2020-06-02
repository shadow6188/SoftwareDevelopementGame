package tankgame.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected BufferedImage image;
    protected int width;
    protected int height;
    protected Rectangle rect;

    GameObject(int x,int y, BufferedImage image){
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.rect = new Rectangle(x,y,width,height);
    }

    public Rectangle getBoundary(){
        return rect;
    }

    public abstract void drawImage(Graphics g);

}
