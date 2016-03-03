package com.example.alisonnileesha.mazegenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick (View view){
        switch (view.getId()){
            case R.id.buttonQuit:
                finish();
                break;
            case R.id.buttonNewGame:
                Maze maze = MazeCreator.getMaze();
                Intent game = new Intent(this,Game.class);
                game.putExtra("maze", maze);
                startActivity(game);
        }

    }

    public void makeNewGame(){
        Maze maze = MazeCreator.getMaze();
        Intent game = new Intent(this,Game.class);
        game.putExtra("maze", maze);
        startActivity(game);
    }

}
