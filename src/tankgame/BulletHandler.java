package tankgame;

import tankgame.objects.Bullet;
import tankgame.objects.Collidable;
import tankgame.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class BulletHandler {
    private ArrayList<Bullet> ammo;
    private ArrayList<Bullet> collided;

    public BulletHandler(){
        ammo = new ArrayList<>();
        collided = new ArrayList<>() ;
    }
    public void Shoot(Bullet in){
        ammo.add(in);
    }
    public void update(ArrayList<GameObject> check){
        for (Bullet shot: ammo) {
            shot.moveForwards();
        }
        collisions(check);
    }
    private void collisions(ArrayList<GameObject> check){
        for (Bullet shot : ammo){
            for (GameObject in: check) {
                if(shot.getBoundary().intersects(in.getBoundary())){
                    if (in instanceof Collidable)
                        ((Collidable) in).hit();
                    collided.add(shot);
                }
            }
        }
        ammo.removeAll(collided);
        collided.clear();
    }
    public void draw(Graphics2D gameWorld){
        for (Bullet shot: ammo) {
            shot.drawImage(gameWorld);
        }
    }
}
