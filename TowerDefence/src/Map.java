import java.io.File;

import java.awt.Image;
import java.awt.Color;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Map {

	// Properties
	public static final int BLOCKSIZE = 52; // pixel
	public int totalMobNumber;
	public static Block[][] block; // Generate the map
	public static Mob[] mob; // Monsters are in there
	public static int[][] groundTypeIndices;
	public static boolean[][] isThereTower;
	public boolean[][] isShooting;
	public static int[][] towerType;
	public int[][] shotMobID;
	public Rectangle[][] shotRange;
	public int shotRangeSize = 130; // Tower Fire Range
	public int nbOfGroundTypes = 3;
	public Image[] mapImages = new Image[nbOfGroundTypes];
	public int nbOfMobTypes = 3;
	public int[] nbOfDifferentMobs = new int[nbOfMobTypes];
	public Image[] mobImages = new Image[nbOfMobTypes];
	public int[] mobImageIndices;
	public static Image[][] towerImages;
	public int mobCounter;

	// Constructor
	public Map(int level, int rowNumber, int columnNumber) {
		mapImages[0] = new ImageIcon("pic/GRASS.png").getImage();
		mapImages[1] = new ImageIcon("pic/PATH.png").getImage();
		mapImages[2] = new ImageIcon("pic/ENDLINE.png").getImage();
		mobImages[0] = new ImageIcon("pic/GREENMOB.png").getImage();
		mobImages[1] = new ImageIcon("pic/YELLOWMOB.png").getImage();
		mobImages[2] = new ImageIcon("pic/REDMOB.png").getImage();
		block = new Block[rowNumber][columnNumber];
		shotRange = new Rectangle[rowNumber][columnNumber];
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				int x = Screen.WIDTH / 2 - columnNumber * BLOCKSIZE / 2 + j * BLOCKSIZE;
				int y = i * BLOCKSIZE;
				int width = BLOCKSIZE;
				int height = BLOCKSIZE;
				block[i][j] = new Block(x, y, width, height);
				shotRange[i][j] = new Rectangle(x - shotRangeSize / 2, y - shotRangeSize / 2, width + shotRangeSize,
						height + shotRangeSize);
			}
		}
		shotMobID = new int[rowNumber][columnNumber];
		groundTypeIndices = new int[rowNumber][columnNumber];
		towerType = new int[rowNumber][columnNumber];
		Scanner scn;
		int m = 0;
		int n = -1;
		try {
			scn = new Scanner(new File("maps/map" + level)); // File Scanning and then Generate the road path and grass
			totalMobNumber = scn.nextInt();
			for (int i = 0; i < block.length; i++) {
				for (int j = 0; j < block[0].length; j++) {
					groundTypeIndices[i][j] = scn.nextInt(); // Creating the path form file
					if (j == 0 && groundTypeIndices[i][j] == 1) {
						m = i;
					}
				}
			}
		} catch (Exception e) {
			//DoNothing.
		}
		mobImageIndices = new int[totalMobNumber];
		nbOfDifferentMobs[2] = totalMobNumber / 5; // Number of spawn
		nbOfDifferentMobs[1] = totalMobNumber / 3; // Number of spawn
		nbOfDifferentMobs[0] = totalMobNumber - nbOfDifferentMobs[1] - nbOfDifferentMobs[2]; // Number of spawn
		mobCounter = 0;
		mob = new Mob[totalMobNumber];
		for (int i = 0; i < mob.length; i++) { // Spawning the monsters
			mob[i] = new Mob(n * BLOCKSIZE, m * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
			if (mobCounter < nbOfDifferentMobs[0]) {
				mob[i].setSpeed(5);
				mobImageIndices[i] = 0;
				mobCounter++;
			} else if (mobCounter < nbOfDifferentMobs[1] + nbOfDifferentMobs[0]) {
				mob[i].setSpeed(3);
				mobImageIndices[i] = 1;
				mobCounter++;
			} else if (mobCounter < totalMobNumber) {
				mob[i].setSpeed(0);
				mobImageIndices[i] = 2;
				mobCounter++;
			}
		}
		isThereTower = new boolean[rowNumber][columnNumber];
		isShooting = new boolean[rowNumber][columnNumber];
		for (int i = 0; i < groundTypeIndices.length; i++) {
			for (int j = 0; j < groundTypeIndices[0].length; j++) {
				isThereTower[i][j] = false;
				isShooting[i][j] = false;
			}
		}
		towerImages = new Image[rowNumber][columnNumber];
	}

	public boolean isMapOver() {
		return GamePicture.killedOrPass == totalMobNumber && totalMobNumber != 0;
	}

	public int getTotalMobNumber() {
		return totalMobNumber;
	}

	public void draw(Graphics g) {
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				int imageIndex = groundTypeIndices[i][j];
				block[i][j].drawBlock(g, mapImages[imageIndex]);
				if (j == block[0].length - 1 && imageIndex == 1) {
					block[i][j].drawBlock(g, mapImages[imageIndex + 1]);
				}
				if (isThereTower[i][j]) {
					block[i][j].drawBlock(g, towerImages[i][j]);
				}
			}
		}

		for (int i = 0; i < mob.length; i++) {
			mob[i].drawMob(g, mobImages[mobImageIndices[i]]);
		}

		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				if (isShooting[i][j]) {
					g.setColor(new Color(255, 255, 0)); //  laser color
					int x = block[i][j].x;
					int y = block[i][j].y;
					int width = block[i][j].width;
					int height = block[i][j].height;
					int mobX = mob[shotMobID[i][j]].x;
					int mobY = mob[shotMobID[i][j]].y;
					int mobWidth = mob[shotMobID[i][j]].width;
					int mobHeight = mob[shotMobID[i][j]].height;
					g.drawLine(x + width / 2, y + height / 2, mobX + mobWidth / 2, mobY + mobHeight / 2); // Laserrsss!!!
				}
			}
		}

	}

	public void physic() {
		int startwait = 50;
		for (int i = 0; i < mob.length; i++) {
			mob[i].setStartWait(startwait += 400);
			mob[i].physic();
		}
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				if (isThereTower[i][j]) {
					if (isShooting[i][j] == false) {
						for (int a = 0; a < mob.length; a++) {
							if (mob[a].getColumn() != -1 && mob[a].isAlive() && shotRange[i][j].intersects(mob[a])) {
								isShooting[i][j] = true;
								shotMobID[i][j] = a;
								break;
							}
						}
					} else {
						if (mob[shotMobID[i][j]].isAlive() && shotRange[i][j].intersects(mob[shotMobID[i][j]])) { // intersect is basically  locked on or off
							double damageAmount = (double) (towerType[i][j] + 1) / 30;
							mob[shotMobID[i][j]].damageHealth(damageAmount);
						} else {
							isShooting[i][j] = false;
						}
					}
				}
			}
		}

	}
}
