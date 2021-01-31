import java.awt.Image;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle{
    
    // Constructor
    public Block(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void drawBlock(Graphics g, Image img){
        g.drawImage(img, x, y, width, height, null);
    }
}
