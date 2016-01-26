package se.mikaelbackman.tictactoe.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    public String player1name = "";
    public String player2name = "";
    public boolean versusphone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText player1 = (EditText) findViewById(R.id.player1textfield);
        final EditText player2 = (EditText) findViewById(R.id.player2textfield);
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.aibutton);
        final ImageButton playbutton = (ImageButton) findViewById(R.id.playButton);
        playbutton.setImageResource(R.drawable.playbutton_inactive);
        playbutton.setClickable(false);


        // Resolve player 1's name and check the game that is about to be played
        player1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1name = player1.getText().toString();
                if (toggle.isChecked() || !player2name.equals("")) {
                    playbutton.setImageResource(R.drawable.playbutton);
                    playbutton.setClickable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Resolve player 2's name.
        player2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2name = player2.getText().toString();
                if (!player1name.equals("")) {
                    playbutton.setImageResource(R.drawable.playbutton);
                    playbutton.setClickable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Listener for the togglebutton to change states and determine the gametype.
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (!player1name.equals("")) {
                        playbutton.setImageResource(R.drawable.playbutton);
                        playbutton.setClickable(true);
                    }
                    player2.setBackgroundResource(R.drawable.textcontainer_inactive);
                    player2.setFocusable(false);
                    player2.setEnabled(false);
                    versusphone = true;
                } else {
                    player2.setBackgroundResource(R.drawable.textcontainer);
                    player2.setFocusableInTouchMode(true);
                    player2.setFocusable(true);
                    player2.setEnabled(true);
                    versusphone = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Sending the intent to change activity to the PlayActivity.
    public void playGame(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("player1", player1name);
        if (player2name != null) intent.putExtra("player2", player2name);
        intent.putExtra("versusphone", versusphone);
        startActivity(intent);

    }

    // Starting the highscoreactivity
    public void highScore(View view) {
        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
    }
}
