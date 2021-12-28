import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener{

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private Image dot;
    private Image tree;
    private final int[] TreeX = new int[19];
    private final int[] TreeY = new int[19];
    private int x;
    private int y;
    private Timer timer;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        x = 160;
        y = 160;
        timer = new Timer(50,this);
        timer.start();
        createTree();
    }

    public void createTree(){
        for (int i = 0; i < 19 ; i++) {
            TreeX[i] = 0;
            TreeY[i] = new Random().nextInt(20) * DOT_SIZE;
        }
    }

    public void loadImages(){
        ImageIcon iiс = new ImageIcon("Car.png");
        dot = iiс.getImage();
        ImageIcon iit = new ImageIcon("AnotherCars.png");
        tree = iit.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            for (int i = 0; i < 19; i++) {
                g.drawImage(tree, TreeX[i], TreeY[i], this);
            }
            g.drawImage(dot,x,y,this);

        } else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            // g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        if (up) {
            y -= DOT_SIZE;
        }
        if (down) {
            y += DOT_SIZE;
        }
        up = false;
        down = false;
    }

    public void moveTree(){
        for (int i = 0; i < 19 ; i++) {
            TreeX[i] += 4;
        }
    }

    public void checkTree(){
        for (int i = 0; i < 19; i++) {
            if( x == TreeX[i] && y == TreeY[i])
                inGame = false;
        }
    }

    public void checkCollisions(){

        if(y>SIZE){
            inGame = false;
        }
        if(y<0){
            inGame = false;
        }
        for (int i = 0; i < 19; i++) {
            if(TreeX[i]>SIZE){
                createTree();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkTree();
            checkCollisions();
            move();
            moveTree();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_UP && !down){
                up = true;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
            }
        }
    }

}