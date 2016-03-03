package com.example.alisonnileesha.mazegenerator;

import android.util.Log;
import java.io.Serializable;

/**
 * Created by alisoncheu on 2/11/16.
 */
public class Maze implements Serializable{

    private static final long serialVersionUID = 1L;

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;

    private boolean[][] verticalLines;
    private boolean[][] horizontalLines;
    private int sizeX, sizeY;
    private int currentX, currentY;
    private int finalX, finalY;
    private boolean gameComplete;

    public Maze(){
        //default contructor
    }

    public boolean move(int direction) {
        boolean moved = false;
        if(direction == UP) {
            if(currentY != 0 && !horizontalLines[currentY-1][currentX]) { //there exists a cell and no wall
                currentY--;
                moved = true;
            }
        }
        if(direction == DOWN) {
            if(currentY != sizeY-1 && !horizontalLines[currentY][currentX]) {
                currentY++;
                moved = true;
            }
        }
        if(direction == RIGHT) {
            if(currentX != sizeX-1 && !verticalLines[currentY][currentX]) {
                currentX++;
                moved = true;
            }
        }
        if(direction == LEFT) {
            if(currentX != 0 && !verticalLines[currentY][currentX-1]) {
                currentX--;
                moved = true;
            }
        }
        if(moved) {
            if(currentX == finalX && currentY == finalY) {
                gameComplete = true;
                Log.d("Game Complete", "YOU'VE REACHED THE END");
            }
        }
        return moved;
    }

    public void setVerticalLines(boolean[][] vLines){
        verticalLines = vLines;
    }

    public void setHorizontalLines(boolean[][] hLines){
        horizontalLines = hLines;
    }

    public  void setStartPosition(int x, int y){
        currentX = x;
        currentY = y;
    }

    public void setFinalPosition(int x, int y){
        Log.d("setFinalPosX", Integer.toString(x));
        Log.d("setFinalPosY", Integer.toString(y));

        finalX = x;
        finalY = y;
    }

    public void setX(int xx){
        sizeX = xx;
    }

    public void setY(int yy){
        sizeY = yy;
    }
    public int getFinalX(){
        return finalX;
    }

    public int getFinalY(){
        return finalY;
    }

    public int getMazeWidth(){
        return sizeY;
    }

    public int getMazeHeight(){
        return sizeX;
    }

    public int getCurrentX(){
        return currentX;
    }

    public int getCurrentY(){
        return currentY;
    }

    public boolean[][] getVerticalLines(){
        return verticalLines;
    }

    public boolean[][] getHorizontalLines(){
        return horizontalLines;
    }

    public boolean isGameComplete(){
        return gameComplete;
    }

}
