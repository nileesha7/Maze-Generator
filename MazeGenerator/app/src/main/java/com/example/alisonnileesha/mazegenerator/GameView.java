package com.example.alisonnileesha.mazegenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by alisoncheu on 2/11/16.
 */
public class GameView extends View {
    private int width, height, lineWidth;
    private int mazeSizeX, mazeSizeY; //w + h for maze
    float cellWidth, cellHeight;
    float totalCellWidth, totalCellHeight;// w + h for screen - depends on resolution of device
    //Android calls the onSizeChanged with screen size after constructing view
    private int mazeFinishX, mazeFinishY; //finishing point
    private Maze maze; //object to display
    private Activity context; //object which calls this view
    private Paint line, red, background;//drawing related objects
    private boolean dragging = false;
    //better to keep local copies than calculate each time
    //saves processor time
    private Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.bobo);
    private Bitmap bg = BitmapFactory.decodeResource(getResources(),R.drawable.grass);
    private Bitmap target = BitmapFactory.decodeResource(getResources(), R.drawable.home);

    public GameView (Context context, Maze maze){
        super(context);
        Log.d("GameView constructor", "Constructor called");
        this.context = (Activity)context;
        this.maze = maze; //store to draw
        mazeFinishX = maze.getFinalX();
        mazeFinishY = maze.getFinalY();
        mazeSizeX = maze.getMazeWidth();
        mazeSizeY = maze.getMazeHeight();
        line = new Paint();
        line.setColor(ContextCompat.getColor(context, R.color.line));
        line.setStrokeWidth(10);
        red = new Paint();
        red.setColor(ContextCompat.getColor(context, R.color.position));//draw F for finishing position
        //cannot do text size because don't know the screen size/cell size

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = (w < h)?w:h;   //check whether the width or height of the screen is smaller
        height = width;         //for now square mazes
        lineWidth = 1;          //for now 1 pixel wide walls
        cellWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
        totalCellWidth = cellWidth+lineWidth;
        cellHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
        totalCellHeight = cellHeight+lineWidth;
        red.setTextSize(cellHeight * 0.75f);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //fill in the background
        bg = Bitmap.createScaledBitmap(bg,width,height,true);
        canvas.drawBitmap(bg,0,0,null);

        boolean[][] hLines = maze.getHorizontalLines();
        boolean[][] vLines = maze.getVerticalLines();
        //iterate over the boolean arrays to draw walls
        for(int i = 0; i < mazeSizeX; i++) {
            for(int j = 0; j < mazeSizeY; j++){
                float x = j * totalCellWidth;
                float y = i * totalCellHeight;
                if(j < mazeSizeX - 1 && vLines[i][j]) {
                    //we'll draw a vertical line
                    canvas.drawLine(x + cellWidth,   //start X
                            y,               //start Y
                            x + cellWidth,   //stop X
                            y + cellHeight,  //stop Y
                            line);
                }
                if(i < mazeSizeY - 1 && hLines[i][j]) {
                    //we'll draw a horizontal line
                    canvas.drawLine(x,               //startX
                            y + cellHeight,  //startY
                            x + cellWidth,   //stopX
                            y + cellHeight,  //stopY
                            line);
                }
            }
        }
        int currentX = maze.getCurrentX(),currentY = maze.getCurrentY();
        //draw the ball
        float x = (currentX * totalCellWidth) + (cellWidth / 2);

        float y =  (currentY * totalCellHeight) + (cellWidth / 2);
        Log.d("FinalX", Integer.toString(mazeFinishX));
        Log.d("FinalY", Integer.toString(mazeFinishY));
        img = Bitmap.createScaledBitmap(img,Math.round(cellWidth),Math.round(cellHeight), true);
        canvas.drawBitmap(img, (currentX * totalCellWidth) + (cellWidth / 2)- (Math.round(cellWidth/2)), //need something  less than cell width
                (currentY * totalCellHeight) + (cellWidth / 2) - (Math.round(cellHeight/2)), null);

        /*
        canvas.drawCircle((currentX * totalCellWidth) + (cellWidth / 2),   //x of center
                (currentY * totalCellHeight) + (cellWidth / 2),  //y of center
                (cellWidth * 0.45f),                           //radius
                red);
        */
        //draw the finishing point indicator

        target = Bitmap.createScaledBitmap(target,Math.round(cellWidth),Math.round(cellHeight), true);
        canvas.drawBitmap(target, (mazeFinishX * totalCellWidth) + (cellWidth * 0.25f),
                (mazeFinishY * totalCellHeight) + (cellHeight * 0.75f) - (Math.round(cellHeight)), null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int currentX = maze.getCurrentX();
        int currentY = maze.getCurrentY();
        Log.d("Cat current Row", Integer.toString(currentX));
        Log.d("Cat current Col", Integer.toString(currentY));
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //touch gesture started
                if(Math.floor(touchX/totalCellWidth) == currentX &&
                        Math.floor(touchY/totalCellHeight) == currentY) {
                    //touch gesture in the cell where the ball is
                    dragging = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //touch gesture completed
                dragging = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(dragging) {
                    int cellX = (int)Math.floor(touchX/totalCellWidth);
                    int cellY = (int)Math.floor(touchY/totalCellHeight);

                    if((cellX != currentX && cellY == currentY) ||
                            (cellY != currentY && cellX == currentX)) {
                        //either X or Y changed
                        boolean moved = false;
                        //check horizontal ball movement
                        switch(cellX-currentX) {
                            case 1:
                                moved = maze.move(Maze.RIGHT);
                                break;
                            case -1:
                                moved = maze.move(Maze.LEFT);
                        }
                        //check vertical ball movement
                        switch(cellY-currentY) {
                            case 1:
                                moved = maze.move(Maze.DOWN);
                                break;
                            case -1:
                                moved = maze.move(Maze.UP);
                        }
                        if(moved) {
                            //the ball was moved so we'll redraw the view
                            invalidate();
                            if(maze.isGameComplete()) {
                                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                adb.setTitle("Bobo found his way home!");
                                adb.setMessage("Do you want to play again?");
                                adb.setNeutralButton("Play Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        resetView();
                                    }
                                });
                                AlertDialog ad = adb.create();
                                ad.show();
                            }
                        }
                    }
                    return true;
                }
        }

        return false;
    }

    public void resetView() {
        this.setVisibility(View.GONE);
        maze = MazeCreator.getMaze();
        mazeFinishX = maze.getFinalX();
        mazeFinishY = maze.getFinalY();
        this.setVisibility(View.VISIBLE);
    }

}
