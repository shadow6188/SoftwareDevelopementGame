package tankgame.objects;

import tankgame.BulletHandler;
import tankgame.GameDriver;
import tankgame.resources.ResourceLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 * modified by Gerardo OChoa
 */
public class Tank extends GameObject implements Collidable{

    private final int InitialX;
    private final int InitialY;
    private final int InitialAngle;
    private int vx;
    private int vy;
    private int angle;
    private int health;
    private int life;

    private final int R = 2;
    private final int ROTATION_SPEED = 4;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private Rectangle nextLocation;

    private boolean status = true;

    private ArrayList<GameObject> collide;
    private PowerUp remove;
    private BulletHandler shoot;
    private int timer;


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImage, ArrayList<GameObject> collidable, BulletHandler shoot) {
        super(x,y, tankImage);
        this.vx = vx;
        this.vy = vy;
        this.InitialX = x; // initial values for respawn placement
        this.InitialY = y;
        this.InitialAngle = angle;
        this.angle = angle;
        collide = collidable;
        nextLocation = new Rectangle(this.getBoundary());
        this.shoot = shoot;
        timer = 0;
        health = 100;
        life = 3;
        remove = null;
    }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && timer < 0){
            this.fire();
            timer = 40;
        }
        timer--;
        if (life == 0) {
            this.status = false;
        }
        if (health <= 0){
            life--;
            reset();
        }
        //checkBorder();
        if(remove != null){
            collide.remove(remove);
        }
    }

    private void fire(){
        int posX = x + this.width/2; // resets the position to middle of tank
        int posY = y + this.height/2;
        posX += (int) Math.round((this.width+15)/2.0 * Math.cos(Math.toRadians(angle)));
        posY += (int) Math.round((this.height +15)/2.0 * Math.sin(Math.toRadians(angle)));
        shoot.Shoot(new Bullet(posX, posY, this.angle, (BufferedImage) ResourceLoader.getResource("bullet")));
    }
    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        nextLocation.setLocation(x-vx,y-vy);
        if(checkCollision(nextLocation)) {
            x -= vx;
            y -= vy;
            getBoundary().setLocation(x,y);
        }
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        nextLocation.setLocation(x+vx,y+vy);
        if (checkCollision(nextLocation)){
            x += vx;
            y += vy;
        }
    }

    private void checkBorder() {
        if (x < width) {
            x = width;
        }
        if (x >= GameDriver.WORLD_WIDTH - width) {
            x = GameDriver.WORLD_WIDTH - width;
        }
        if (y < height) {
            y = height;
        }
        if (y >= GameDriver.WORLD_HEIGHT - height) {
            y = GameDriver.WORLD_HEIGHT - height;
        }
    }

    private boolean checkCollision(Rectangle next){ // checks if tank is gonna collide with something if it moves to said location
        if (collide.isEmpty()) {System.out.println("broken");}
        for (GameObject thing: collide) {
            if (thing.getBoundary().intersects(next))
            {
                if(thing instanceof PowerUp){
                    remove = (PowerUp) thing;
                    ((PowerUp) thing).acquired();
                    life();
                }
                return false;//collision
            }

        }
        return true;//no collision
    }

    @Override
    public String toString() {
        //return "x=" + x + ", y=" + y + ", angle=" + angle;
        return "tank";
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), height/ 2.0, width / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
    }
    public void drawHud(Graphics2D g,int x,int y, String player){
        g.drawString(player+" Lives :" + life,x,y);
        for (int r = -health; r <= 0;r += 20){
            Rectangle temp = new Rectangle(20,10);
            temp.setLocation(x+90-r, y);
            g.fill(temp);
        }
        g.drawString(player+" Health :" ,x,y+10);
    }

    @Override
    public Rectangle getBoundary() {
        rect.setLocation(x,y);
        return super.getBoundary();
    }

    @Override
    public void hit() {
        health -= 20;
    }
    private void reset(){
        x = InitialX;
        y = InitialY;
        angle = InitialAngle;
        health = 100;
    }
    public boolean alive() {
        return !this.status;
    }
    public int getX(){
        return this.x + width/2;
    }
    public int getY(){
        return this.y - height;
    }
    private void life(){
        life++;
    }
}
