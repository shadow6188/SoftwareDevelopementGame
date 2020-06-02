package tankgame;

import tankgame.objects.BreakableWall;
import tankgame.objects.GameObject;
import tankgame.objects.Tank;
import tankgame.resources.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


// main driver for game
public class GameDriver extends JPanel {
    public static final int WORLD_HEIGHT = 896;
    public static final int WORLD_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 800;
    public static final int SCREEN_WIDTH = 1000;
    public static final int HUD_HEIGHT = (SCREEN_HEIGHT-600);
    public static final int HUD_WIDTH = HUD_HEIGHT * 16/9;
    private JFrame frame;
    private BufferedImage screen;
    private Graphics2D GameWorld;

    private Tank tankOne;
    private Tank tankTwo;

    private ArrayList<GameObject> stuff;
    private BulletHandler shoot;
    private ArrayList<GameObject> Player1;
    private ArrayList<GameObject> Player2;
    private ArrayList<GameObject> collidable;
    String end;

    private boolean ongoing;


    public static void main(String[] args){
        GameDriver game = new GameDriver();
        game.setVisible(true);
        game.ongoing =true;
        
        try {
            do {
                if(game.tankOne.alive() || game.tankTwo.alive()) {
                    game.ongoing = false;
                    if (game.tankOne.alive()){
                        game.end = "P2";
                    } else game.end = "P1";
                }
                game.tankOne.update();
                game.tankTwo.update();
                game.shoot.update(game.collidable);
                updateWalls(game);
                game.repaint();
                Thread.sleep(1000 / 144);

            } while (true);
        } catch (Exception e){/*ignore*/}
        System.out.println("game ended"); // got to switch to winner announcement somehow.
    }
    public GameDriver(){
        init();
    }
    private void init() {// initialize game
        frame = new JFrame("TankGame");
        screen = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT,BufferedImage.TYPE_INT_RGB);


        stuff = SetUp.populateWorld();

        Player1 = new ArrayList<>(stuff);
        Player2 = new ArrayList<>(stuff);

        shoot = new BulletHandler();

        tankOne = new Tank(50,50 ,0,0,0,
                (BufferedImage) ResourceLoader.getResource("tank1"), Player1, shoot);
        tankTwo = new Tank(1450,750,0,0,180,
                (BufferedImage) ResourceLoader.getResource("tank2"), Player2, shoot);

        Player1.add(tankTwo);
        Player2.add(tankOne);

        collidable = new ArrayList<>(stuff);
        collidable.add(tankOne);
        collidable.add(tankTwo);

        {
            TankControl firstPlayer = new TankControl(tankOne,
                    KeyEvent.VK_W,
                    KeyEvent.VK_S,
                    KeyEvent.VK_A,
                    KeyEvent.VK_D,
                    KeyEvent.VK_SHIFT);

            frame.addKeyListener(firstPlayer);

            TankControl secondPlayer = new TankControl(tankTwo,
                    KeyEvent.VK_UP,
                    KeyEvent.VK_DOWN,
                    KeyEvent.VK_LEFT,
                    KeyEvent.VK_RIGHT,
                    KeyEvent.VK_ENTER);

            frame.addKeyListener(secondPlayer);
        }//creating listeners

        {
            frame.setVisible(true);
            frame.setSize(new Dimension(SCREEN_WIDTH, WORLD_HEIGHT +36));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // makes it spawn in middle of screen
            frame.setLayout(new BorderLayout());//not sure if this is this is needed
            frame.add(this, 0);
            frame.setResizable(false);
            frame.setBackground(Color.BLACK);
        }// frame settings
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        if(ongoing) {
            GameWorld = screen.createGraphics();
            // add things to game world here
            GameWorld.drawImage(((BufferedImage) ResourceLoader.getResource("background")
            ).getScaledInstance(WORLD_WIDTH, WORLD_HEIGHT, Image.SCALE_FAST), 0, 0, null);

            tankOne.drawImage(GameWorld);//first player onto screen
            tankTwo.drawImage(GameWorld);//second player onto screen
            // draw game world to frame

            shoot.draw(GameWorld);
            stuff.forEach(item -> item.drawImage(GameWorld));

            int P1X = tankOne.getX() - SCREEN_WIDTH / 4;
            int P1Y = tankOne.getY() - SCREEN_HEIGHT / 4;
            int P2X = tankTwo.getX() - SCREEN_WIDTH / 4;
            int P2Y = tankTwo.getY() - SCREEN_HEIGHT / 4;
            P1X = checkBoundariesX(P1X);
            P1Y = checkBoundariesY(P1Y);
            P2X = checkBoundariesX(P2X);
            P2Y = checkBoundariesY(P2Y);

            g2.drawImage(screen.getSubimage(P1X, P1Y, SCREEN_WIDTH / 2, SCREEN_HEIGHT - HUD_HEIGHT), 0, 0, null);
            g2.drawImage(screen.getSubimage(P2X, P2Y, SCREEN_WIDTH / 2, SCREEN_HEIGHT - HUD_HEIGHT), SCREEN_WIDTH / 2, 0, null);

            tankOne.drawHud(g2, 0, SCREEN_HEIGHT - (HUD_HEIGHT - 20), "Player 1");
            tankTwo.drawHud(g2, (int) (SCREEN_WIDTH * .75), SCREEN_HEIGHT - (HUD_HEIGHT - 20), "Player 2");

            g2.drawImage(screen.getScaledInstance(HUD_WIDTH, HUD_HEIGHT, Image.SCALE_SMOOTH), SCREEN_WIDTH / 2 - HUD_WIDTH / 2, SCREEN_HEIGHT - HUD_HEIGHT, null);
        } else {
            g2.drawImage((BufferedImage) ResourceLoader.getResource(end),0,0,null);
        }
    }
    private int checkBoundariesX(int x){// function to check that the value of x does not go out of bounds and cause rastor error
        if (x < 0){
            return 0;
        } else return Math.min(x, WORLD_WIDTH - SCREEN_WIDTH / 2);
    }
    private int checkBoundariesY(int y){
        if (y < 0){
            return  0;
        } else return Math.min(y, WORLD_HEIGHT - 600);
    }

    private static void updateWalls(GameDriver game){
        ArrayList<GameObject> temp = new ArrayList<>();
        for (GameObject item: game.stuff) {
            if(item instanceof BreakableWall){
                if (!((BreakableWall) item).intact()){
                    temp.add(item);
                }
            }
        }
        game.stuff.removeAll(temp);
        game.collidable.removeAll(temp);
        game.Player1.removeAll(temp);
        game.Player2.removeAll(temp);
        temp.clear();
    }
}
