package se.mikaelbackman.tictactoe.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        DataSaver ds = new DataSaver(this);
        TextView highscorelist = (TextView) findViewById(R.id.highscoreList);
        ArrayList<String> list = ds.getHighScore();
        highscorelist.setText(String.format(this.getResources().getString(R.string.highscore), list.get(0)));
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
