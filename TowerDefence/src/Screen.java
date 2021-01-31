import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class Screen extends JFrame {

	// Properties
	public static final int WIDTH = 624;
	public static final int HEIGHT = 550;
	public static Point point;
	public static GamePicture gamePic;

	// Constructor
	public Screen() {
		setTitle("Tower Defence Game");
		setSize(WIDTH, HEIGHT);
		setResizable(false); // Cannot set the screen size
		setLocationRelativeTo(null); // start the game on the middle
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		point = new Point(0, 0); // Point for the the Mouse movement
		gamePic = new GamePicture();
	}

	// Main
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		//playSound();
		
		Screen screen = new Screen();
		screen.setLayout(new GridLayout(1, 1, 0, 0));
		screen.add(Screen.gamePic);
		screen.setVisible(true);
		screen.addMouseListener(new MyMouse());
		screen.addMouseMotionListener(new MyMouse());
		
	}

	public static void playSound() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/Music2.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
}