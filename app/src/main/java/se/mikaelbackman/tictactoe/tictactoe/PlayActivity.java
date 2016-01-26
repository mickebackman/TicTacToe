package se.mikaelbackman.tictactoe.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class PlayActivity extends AppCompatActivity {
    public String player1name, player2name;
    public boolean versusphone = false;
    public enum GameState{None, X, O};
    public int counter;
    public int n = 3;
    public GameState[][] gameboard;
    public boolean player1sturn;
    public boolean phonesturn = false;
    public ImageButton[] squares;
    public TextView infotext, statistics;
    public DataSaver ds;
    public PhoneMove pm;
    public Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Receive data from main activity
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            player1name = bundle.getString("player1");
            player2name = bundle.getString("player2");
            versusphone = bundle.getBoolean("versusphone");
        }
        resources = this.getResources();
        //Made an help-file to save the necessary statistics to the phones memory.
        ds = new DataSaver(this);

        // Player 1 always starts and has an X.
        infotext = (TextView) findViewById(R.id.infotext);
        infotext.setText(String.format(resources.getString(R.string.turn), player1name));
        player1sturn = true;
        statistics = (TextView) findViewById(R.id.statistics);


        // Add all the buttons to an vector for easier accesss.
        squares = new ImageButton[n*n];
        ImageButton square1 = (ImageButton) findViewById(R.id.square1);
        squares[0] = square1;
        ImageButton square2 = (ImageButton) findViewById(R.id.square2);
        squares[1] = square2;
        ImageButton square3 = (ImageButton) findViewById(R.id.square3);
        squares[2] = square3;
        ImageButton square4 = (ImageButton) findViewById(R.id.square4);
        squares[3] = square4;
        ImageButton square5 = (ImageButton) findViewById(R.id.square5);
        squares[4] = square5;
        ImageButton square6 = (ImageButton) findViewById(R.id.square6);
        squares[5] = square6;
        ImageButton square7 = (ImageButton) findViewById(R.id.square7);
        squares[6] = square7;
        ImageButton square8 = (ImageButton) findViewById(R.id.square8);
        squares[7] = square8;
        ImageButton square9 = (ImageButton) findViewById(R.id.square9);
        squares[8] = square9;


        //Make the gameboard and check if the game is against the phone or another player
        gameboard = new GameState[n][n];
        if (versusphone){
            pm = new PhoneMove();
            player2name = "Phone";
        }
        // Print the number of wins for each player that is playing.
        // Note that the same name will result in the saved total score.
        statistics.setText(String.format(resources.getString(R.string.statistics), player1name, ds.loadScore(player1name),
                player2name, ds.loadScore(player2name)));

    }
    //Methods for changing the imagebuttons to the correct ones, e.g. X or O
    public void square1 (View view){
        changePicture(squares[0],0,0);
    }
    public void square2 (View view){
        changePicture(squares[1],1,0);
    }
    public void square3 (View view){
        changePicture(squares[2],2,0);
    }
    public void square4 (View view){
        changePicture(squares[3],0,1);
    }
    public void square5 (View view){
        changePicture(squares[4],1,1);
    }
    public void square6 (View view){
        changePicture(squares[5],2,1);
    }
    public void square7 (View view){
        changePicture(squares[6],0,2);
    }
    public void square8 (View view){
        changePicture(squares[7],1,2);
    }
    public void square9 (View view){
        changePicture(squares[8],2,2);
    }

    // The game has an winner
    public void winner(GameState state){

        //Disable all the squares so they cant be pressed anymore.
        for(ImageButton square : squares){
            square.setClickable(false);
            square.setEnabled(false);
        }
        // Annpunce the winner
        if (state == GameState.X){
            infotext.setText(String.format(resources.getString(R.string.wintext), player1name));
        }
        if (state == GameState.O){
            if (versusphone) infotext.setText(R.string.phonewin);
            infotext.setText(String.format(resources.getString(R.string.wintext), player2name));
        }


    }

    // Changes the pictures on the imagebuttons to be the correct ones
    // Disables the button so that the picture can not be changed again
    public void changePicture (ImageButton square, int x, int y) {


        //Player1 always starts and has an X as the symbol.
        if (player1sturn) {
            square.setImageResource(R.drawable.cross);
            gameboard[x][y] = GameState.X;
            player1sturn = false;
            infotext.setText(String.format(resources.getString(R.string.turn), player2name));
            checkWinner(x, y, GameState.X);
            // If the game is against the phone the PhoneMove class will decide a move then call this method again.
            if(versusphone){
                phonesturn = true;
                int[] move = makePhoneMove();
                changePicture(squares[move[0]], move[1], move[2]);
            }
        }

        // The game is against an IRL-opponent
        else if (!versusphone){
            square.setImageResource(R.drawable.ring);
            gameboard[x][y] = GameState.O;
            player1sturn = true;
            infotext.setText(String.format(resources.getString(R.string.turn), player1name));
            checkWinner(x, y, GameState.O);
        }
        // If the game is against the phone the phone will make his move
        if(phonesturn){
            square.setImageResource(R.drawable.ring);
            gameboard[x][y] = GameState.O;
            player1sturn = true;
            infotext.setText(String.format(resources.getString(R.string.turn), player1name));
            phonesturn = false;
            checkWinner(x, y, GameState.O);
        }
        square.setClickable(false);
        square.setEnabled(false);
    }
    public int[] makePhoneMove() {
        // Made this class so the implementation of more advanced AI will be easier
        return pm.phoneMoveRandom(gameboard,n);
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void resetGame(View view) {

        //Clear all the previous states and make the buttons clickable again.
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n;j++){
                gameboard[i][j] = GameState.None;
            }
        }
        for(ImageButton square : squares) {
            square.setClickable(true);
            square.setEnabled(true);
            square.setImageResource(R.drawable.square);
        }
        if (player1sturn) infotext.setText(String.format(resources.getString(R.string.turn), player1name));
        else infotext.setText(String.format(resources.getString(R.string.turn), player2name));
        counter = 0;
    }

    // Algorithm for checking if there is an winner. A bit redudant code still exists, and can be slimmed down.
    // The solution works for any NxN playing field, however I have not implemented an NxN playing board.

    private void checkWinner(int x, int y, GameState gamestate){

        //If the state is None then X/O will be set on the corresponding button.
        if ((gameboard[x][y] == GameState.None) || (gameboard[x][y] == null)) { gameboard[x][y] = gamestate;}
        counter++;
        boolean haswinner = false;

        //Check row, if temporary amount in row is equal to n we have a winner.
        int tempinrow = 0;
        for (int i = 0; i < n; i++){
            if (gameboard[x][i] == gamestate) {
                tempinrow++;
                if (tempinrow == n){
                    if(gamestate == GameState.X) ds.saveData(player1name);
                    else ds.saveData(player2name);
                    statistics.setText(String.format(resources.getString(R.string.statistics), player1name, ds.loadScore(player1name),
                            player2name, ds.loadScore(player2name)));
                    winner(gamestate);
                    haswinner = true;
                }
            }
        }
        tempinrow = 0;

        //Check column, same as above.
        for (int i = 0; i < n; i++){
            if (gameboard[i][y] == gamestate) {
                tempinrow++;
                if (tempinrow == n){
                    if(gamestate == GameState.X) ds.saveData(player1name);
                    else ds.saveData(player2name);
                    statistics.setText(String.format(resources.getString(R.string.statistics), player1name, ds.loadScore(player1name),
                            player2name, ds.loadScore(player2name)));
                    winner(gamestate);
                    haswinner = true;
                }
            }
        }

        //Check the diagonal win
        if (y == x) {
            for (int i = 0; i < n; i++) {
                if (gameboard [i] [i] != gamestate) break;
                if (i == (n-1)) {
                   if(gamestate == GameState.X) ds.saveData(player1name);
                    else {ds.saveData(player2name);}
                    statistics.setText(String.format(resources.getString(R.string.statistics), player1name, ds.loadScore(player1name),
                            player2name, ds.loadScore(player2name)));
                    winner(gamestate);
                    haswinner = true;
                }
            }
        }

        // Checking the backward-diagonal win
        for (int i = 0; i < n; i++) {
            if (gameboard[i][(n-1-i)] != gamestate ) {break;}
            if (i == (n-1)) {
                if(gamestate == GameState.X) ds.saveData(player1name);
                else ds.saveData(player2name);
                statistics.setText(String.format(resources.getString(R.string.statistics), player1name, ds.loadScore(player1name),
                        player2name, ds.loadScore(player2name)));
                winner(gamestate);
                haswinner = true;
            }
        }

        //Check if the board is full and if no winner has been decided.
        if(counter == (n*n) && !haswinner){
            infotext.setText(R.string.drawtext);
        }

    }
}
