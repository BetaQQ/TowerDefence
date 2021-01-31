import java.awt.Font;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePicture extends JPanel implements Runnable {

	// Properties
	public static Thread thread;
	public static int gameWidth;
	public static int gameHeight;
	public static int health = 1;
	public static int coin;
	public static int killed = 0;
	public static int killedOrPass = 0;
	// public int killsToWin;
	public int level;
	public int maxLevel = 4;
	public boolean goNextLevel = false;
	public boolean endOfTheGame = false;
	public boolean isGameStarted = false;
	public int buttonSize = 52;
	public static int mapRowNumber = 8;
	public static int mapColumnNumber = 12;
	public Map gameMap;
	public Store gameStore;
	public InfoBar gameInfoBar;

	// Constructor
	public GamePicture() {
		coin = 100;
		level = 1;
		gameMap = new Map(level, mapRowNumber, mapColumnNumber);
		gameStore = new Store();
		gameInfoBar = new InfoBar();
		thread = new Thread(this);
		thread.start();

	}

	public void paintComponent(Graphics g) {
		
		if (goNextLevel) {
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (level == maxLevel) {
				g.setColor(new Color(0, 255, 0));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(255, 51, 51));
				g.setFont(new Font("Courier New", Font.BOLD, 14));
				g.drawString("You Finished the game.", 50, 50);
			} else  {
				g.setColor(new Color(0, 255, 0));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(255, 51, 51));
				g.setFont(new Font("Courier New", Font.BOLD, 14));
				g.drawString("You Won, Congratulations! Now, you are entering the next level", 50, 50);
			}
			goNextLevel = false;
		} else {
			gameWidth = getWidth();
			gameHeight = getHeight();
			g.setColor(new Color(70, 70, 70)); // Background is dark.
			g.fillRect(0, 0, getWidth(), getHeight()); // Whole background is selected.
			isGameStarted = true;
			gameMap.draw(g);
			gameStore.draw(g);
			gameInfoBar.draw(g);
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			g.setColor(new Color(255, 255, 255));
			g.drawString("" + health, gameInfoBar.healthInfo.x + 3 * gameInfoBar.healthInfo.width / 2,
					gameInfoBar.healthInfo.y + 3 * gameInfoBar.healthInfo.height / 4);
			g.drawString(coin + "$", gameInfoBar.coinInfo.x + 3 * gameInfoBar.coinInfo.width / 2,
					gameInfoBar.coinInfo.y + 3 * gameInfoBar.coinInfo.height / 4);
			g.drawString("" + killed, gameInfoBar.killedMobInfo.x + gameInfoBar.killedMobInfo.width / 4,
					gameInfoBar.killedMobInfo.y + 3 * gameInfoBar.killedMobInfo.height / 2);
		}
	}

	public void run() {

		while (true) {
			gameMap.physic();
			if (killedOrPass == gameMap.getTotalMobNumber() && killedOrPass != 0) {
				level++;
				if (level == maxLevel) {
					endOfTheGame = true;
				}
				killed = 0;
				killedOrPass = 0;
				goNextLevel = true;
				gameMap = new Map(level, mapRowNumber, mapColumnNumber);
			}
			repaint();
			if (!goNextLevel) {
				try {

					Thread.sleep(5);

				} catch (Exception e) {
					// Do Nothing.
				}
			} else {
				if (endOfTheGame) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						// Do Nothing.
					}
					System.exit(0);
				} else {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						// Do Nothing.
					}
				}

			}

		}
	}
}