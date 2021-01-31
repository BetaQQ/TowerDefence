import java.awt.Image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Store {
    
    // Properties
    public static final int BUTTONSIZE = 52;
    public static final int SHOPWIDTH = 8;
    public int nbOfTowers = 3;
    public Block[] button = new Block[SHOPWIDTH];
    public int[] buttonFees = new int[SHOPWIDTH];
    public Image[] towerImages = new Image[nbOfTowers];
    Image buttonBackground;
    Image dropItem;
    public static boolean holdsItem = false;
    public int holdedImageIndex;


    // Constructor
    public Store(){
        for(int i=0; i<button.length; i++){
            int x = Screen.WIDTH / 2 - SHOPWIDTH * BUTTONSIZE / 2  + i * BUTTONSIZE;
            int y = 430;
            int width = BUTTONSIZE;
            int height = BUTTONSIZE;
            button[i] = new Block(x, y, width, height);
        }
        buttonBackground = new ImageIcon("pic/BUTTONBACKGROUND.png").getImage();
        dropItem = new ImageIcon("pic/DROP.png").getImage();
        towerImages[0] = new ImageIcon("pic/TOWER1.png").getImage();
        buttonFees[0] = 5;
        towerImages[1] = new ImageIcon("pic/TOWER2.png").getImage();
        buttonFees[1] = 20;
        towerImages[2] = new ImageIcon("pic/TOWER3.png").getImage();
        buttonFees[2] = 30;
    }

    public void click(int mouseButton){
        if (mouseButton == 1){
            for(int i=0; i<towerImages.length; i++){
                if(button[i].contains(Screen.point)){
                    holdsItem = true;
                    holdedImageIndex = i;
                    break;
                }
            }
            if(button[button.length-1].contains(Screen.point)){
                holdsItem = false;
            }
            if (holdsItem){
                if (GamePicture.coin >= buttonFees[holdedImageIndex]){
                    for (int m=0; m<Map.block.length; m++){
                        for (int n=0; n<Map.block[0].length; n++){
                            Block currBlock = Map.block[m][n];
                            int currType = Map.groundTypeIndices[m][n];
                            if (currBlock.contains(Screen.point) && currType == 0 && Map.isThereTower[m][n] == false){
                                Map.isThereTower[m][n] = true;
                                Map.towerImages[m][n] = towerImages[holdedImageIndex];
                                Map.towerType[m][n] = holdedImageIndex;
                                GamePicture.coin -= buttonFees[holdedImageIndex];  
                            }
                        }
                    }
                }
            }
        }

    }

    public void draw(Graphics g){
        for(int i=0; i<button.length; i++){
            button[i].drawBlock(g, buttonBackground);
            if (i<towerImages.length){
                button[i].drawBlock(g, towerImages[i]);
                String price = "";
                if (i == 0)
                    price = "5$";
                else if (i == 1)
                    price = "20$";
                else
                    price = "30$";
                
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.setColor(new Color(255, 255, 255));
                g.drawString(price, button[i].x+button[i].width/2-5, button[i].y+button[i].height+15);
            }
        }
        button[button.length - 1].drawBlock(g, dropItem);
        	if(holdsItem){
            int width = button[holdedImageIndex].width;
            int height = button[holdedImageIndex].height;
            int x = Screen.point.x - width/2;
            int y =  Screen.point.y - height/2;
            g.drawImage(towerImages[holdedImageIndex], x, y, width, height, null);
        }
    }
}
