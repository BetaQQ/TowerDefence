import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouse implements MouseMotionListener, MouseListener{
    
    public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
        Screen.gamePic.gameStore.click(e.getButton());
    }

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	public void mouseDragged(MouseEvent e) {
        Screen.point = new Point(e.getX() - (Screen.WIDTH - GamePicture.gameWidth)/2, e.getY()-((Screen.HEIGHT - GamePicture.gameHeight)-(Screen.WIDTH-GamePicture.gameWidth)/2));
    }
	
	public void mouseMoved(MouseEvent e) {
        Screen.point = new Point(e.getX() - (Screen.WIDTH - GamePicture.gameWidth)/2, e.getY()-((Screen.HEIGHT - GamePicture.gameHeight)-(Screen.WIDTH-GamePicture.gameWidth)/2));
    }

}