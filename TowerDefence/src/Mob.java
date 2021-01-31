import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.ArrayList;


public class Mob extends Rectangle{

    // Properties
    public double health;
    public String direction;
    public boolean isInGame;
    public int speed;
    public int speedCounter;
    public int travelledDistance;
    public int blockIndexRow, blockIndexCol;
    public Random rand = new Random();
    public int counterMob;
    public int startwait;

    
    // Constructor
    public Mob(int x, int y, int width, int height){
        super(x, y, width, height);
        health = Map.BLOCKSIZE;
        direction = "Right";
        speed = 1;
        speedCounter = 0;
        travelledDistance = 0;
        blockIndexRow = y / Map.BLOCKSIZE;
        blockIndexCol = x / Map.BLOCKSIZE;
        isInGame = true;
        counterMob = 0;
        startwait = 0;
    }

    public void drawMob(Graphics g, Image img){
        if (isInGame){
            g.drawImage(img, x, y, width, height, null);
            g.setColor(new Color(50, 50, 50));
            g.fillRect(x, y - 10, width, 5);
            g.setColor(new Color(50, 180, 50));
            g.drawRect(x, y - 10, (int) (health - 1), 4);
        }
    }

    public void setStartWait(int startwait){
        this.startwait = startwait;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getRow(){
        return blockIndexRow;
    }

    public int getColumn(){
        return blockIndexCol;
    }

    public void damageHealth(double damage){
        if (isInGame){
            if (health > 0){
                health -= damage;
            } else{
                killTheMob();
            }
        }
    }

    public boolean isAlive(){
        return isInGame;
    }

    public void killTheMob(){
        isInGame = false;
        GamePicture.killed++;
        GamePicture.killedOrPass++;
        GamePicture.coin += 5;
    }

    public void physic(){
        if (isInGame){
            if (counterMob >= startwait && speedCounter >= speed){
                if (direction.equals("Right")){
                    x++;
                } else if (direction.equals("Up")){
                    y--;
                } else if (direction.equals("Down")){
                    y++;
                } else if (direction.equals("Left")){
                    x--;
                }
                travelledDistance++;
                if (travelledDistance == Map.BLOCKSIZE){
                    if(blockIndexCol == GamePicture.mapColumnNumber-2){
                        isInGame = false;
                        GamePicture.killedOrPass++;
                        GamePicture.health -= 5;
                    } else{
                        ArrayList<String> possibleDirections = new ArrayList<String>();
                        if (direction.equals("Right")){
                            blockIndexCol++;
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol+1] == 1){
                                possibleDirections.add("Right");
                            }
                            if (Map.groundTypeIndices[blockIndexRow-1][blockIndexCol] == 1){
                                possibleDirections.add("Up");
                            }
                            if (Map.groundTypeIndices[blockIndexRow+1][blockIndexCol] == 1){
                                possibleDirections.add("Down");
                            }
                        }
                        else if (direction.equals("Up")){
                            blockIndexRow--;
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol+1] == 1){
                                possibleDirections.add("Right");
                            }
                            if (Map.groundTypeIndices[blockIndexRow-1][blockIndexCol] == 1){
                                possibleDirections.add("Up");
                            }
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol-1] == 1){
                                possibleDirections.add("Left");
                            }
                        }
                        else if (direction.equals("Down")){
                            blockIndexRow++;
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol+1] == 1){
                                possibleDirections.add("Right");
                            }
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol-1] == 1){
                                possibleDirections.add("Left");
                            }
                            if (Map.groundTypeIndices[blockIndexRow+1][blockIndexCol] == 1){
                                possibleDirections.add("Down");
                            }
                        }
                        else if (direction.equals("Left")){
                            blockIndexCol--;
                            if (Map.groundTypeIndices[blockIndexRow][blockIndexCol-1] == 1){
                                possibleDirections.add("Left");
                            }
                            if (Map.groundTypeIndices[blockIndexRow-1][blockIndexCol] == 1){
                                possibleDirections.add("Up");
                            }
                            if (Map.groundTypeIndices[blockIndexRow+1][blockIndexCol] == 1){
                                possibleDirections.add("Down");
                            }
                        }
                        int randomInt = Math.abs(rand.nextInt()) % possibleDirections.size();
                        direction = possibleDirections.get(randomInt);
                        possibleDirections.clear();
                        travelledDistance = 0;
                    }
                }
                speedCounter = 0;
            } else {
                speedCounter++;
            }
            counterMob++;
        }
    }
}