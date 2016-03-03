package com.example.alisonnileesha.mazegenerator;

import android.util.Log;

import com.example.alisonnileesha.kruskal.TesterMain;

import java.util.Random;

/**
 * Created by alisoncheu on 2/11/16.
 */
public class MazeCreator {

    public static Maze getMaze() {
        Maze maze = new Maze();
        TesterMain tm = new TesterMain();
        tm.start();
        boolean[][] vLines = tm.getVerticalLines();
        boolean[][] hLines = tm.getHorizontalLines();

        maze.setVerticalLines(vLines);
        maze.setHorizontalLines(hLines);
        maze.setStartPosition(0, 0);
        Random r = new Random();
        int row = r.nextInt(7)+1;
        int col = r.nextInt(7)+1;
        while(row == 0 && col == 0 || row==0 && col == 1 || row ==1 && col==0){
            row = r.nextInt(7)+1;
            col = r.nextInt(7)+1;
        }

        Log.d("FinalRow", Integer.toString(row));
        Log.d("FinalCol", Integer.toString(col));
        maze.setFinalPosition(row, col);
        maze.setX(8);
        maze.setY(8);
        return maze;
    }
}
