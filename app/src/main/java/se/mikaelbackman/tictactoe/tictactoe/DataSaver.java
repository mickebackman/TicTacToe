package se.mikaelbackman.tictactoe.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataSaver {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences shareddata;

    public DataSaver(Context context) {
        shareddata = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

        //Method for saving data to internal application memory
    public void saveData(String playername) {

        SharedPreferences.Editor editdata = shareddata.edit();
        int oldwins = shareddata.getInt(playername, 0);
        editdata.remove(playername);
        int newwins = oldwins+ 1;
        editdata.putInt(playername, newwins);
                editdata.apply();

    }
        //Method for loading data from internal memory
    public int loadScore(String playername) {
        return shareddata.getInt(playername, 0);
    }


        //Returns the highest total scorer for all the players that have played since installing the application
    public ArrayList<String> getHighScore() {
        ArrayList<String> highscores= new ArrayList<String>();
        Map<String,?> keys = shareddata.getAll();

        int temphighscore = 0;
        String highscorename = "No high score yet";

        for(Map.Entry<String, ?> entry : keys.entrySet()){
            int value = (Integer) entry.getValue();
            if (value > temphighscore){
                temphighscore = value;
                highscorename = entry.getKey();
            }
        }
        highscores.add(highscorename + " with the score of " + temphighscore);

        /*
       // Method for sorting the highscores so more than one can be shown effectively.
       // Did however not have the time to finish it but the sorting should work,
       // just not the acutal adding of the pairs to the arraylist.
       // This is also why the return is an arraylist instead of an string.

        if(keys!=null){
            List<Pair<String, Integer>> sortedlist = new LinkedList<>();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Pair<String, Integer> e = new Pair<String, Integer>(entry.getKey(), (Integer) entry.getValue());
                sortedlist.add(e);
            }
            Collections.sort(sortedlist, new Comparator<Pair<String, Integer>>() {
                public int compare(Pair<String, Integer> left, Pair<String, Integer> right) {
                    String lefts = String.valueOf(left.first);
                    String rights = String.valueOf(right.first);
                    int res = lefts.compareTo(rights);
                    return res == 0 ? left.second.compareTo(right.second) : res;
                }
            });

            for (Pair<String, Integer> pair : sortedlist) {
                highscores.add(pair.toString());
            }
        }
        */

        return highscores;
    }
}