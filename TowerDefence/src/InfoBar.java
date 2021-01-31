import java.awt.Image;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class InfoBar {
    
    // Properties
    public static final int BUTTONSIZE = 20;
    public Block healthInfo;
    public Block coinInfo;
    public Block killedMobInfo;
    public Image healthImage;
    public Image coinImage;
    public Image killedMobImage;
 


    // Constructor
    public InfoBar(){ // Extra images (coin, health, killing info)
        int x = 10;
        int y = 430;
        int width = BUTTONSIZE;
        int height = BUTTONSIZE;
        healthInfo = new Block(x, y, width, height);
        coinInfo = new Block(x, y + BUTTONSIZE + 10, width, height);
        killedMobInfo = new Block(550, y, width*3/2, height*3/2);
        healthImage = new ImageIcon("pic/HEART.png").getImage();
        coinImage = new ImageIcon("pic/MONEY.png").getImage();
        killedMobImage = new ImageIcon("pic/KILLEDMOB.png").getImage();
    }

    public void draw(Graphics g){
        healthInfo.drawBlock(g, healthImage);
        coinInfo.drawBlock(g, coinImage);
        killedMobInfo.drawBlock(g, killedMobImage);
    }
}
