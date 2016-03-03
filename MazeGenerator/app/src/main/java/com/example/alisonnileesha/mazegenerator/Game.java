package com.example.alisonnileesha.mazegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by alisoncheu on 2/11/16.
 */
public class Game extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Maze maze = (Maze) extras.get("maze");
        Log.d("Game called", "Hello");
        GameView view = new GameView(this, maze);
        setContentView(view);
    }
}
