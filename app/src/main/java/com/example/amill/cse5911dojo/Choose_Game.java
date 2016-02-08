package com.example.amill.cse5911dojo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.amill.cse5911dojo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Choose_Game extends Activity {

    private long start_time, elapsed_time;
    private ParseUser currentUser;
    private ParseException x;
    private ParseObject gameStat;
    private boolean playing = false;
    private int available_plays, monkey_bucks, total_plays, total_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("GameStats");
        query1.whereEqualTo("user",ParseUser.getCurrentUser());

        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    // object will be your game score
                    gameStat = object.get(0);
                    total_plays = gameStat.getInt("total_plays");
                    monkey_bucks = gameStat.getInt("monkey_bucks");
                    total_time = gameStat.getInt("total_time");
                    available_plays = gameStat.getInt("available_plays");
                } else {
                    // something went wrong
                    Log.d("gamestats", "Error: " + e.getMessage());
                    Context context = getApplicationContext();
                    CharSequence text = "FAILED";
                    int duration = Toast.LENGTH_LONG;
                    x = e;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }


        });
        setContentView(R.layout.activity_choose__game);
    }

    public void playFrozenBubble(View view)
    {
        if(available_plays == 0){
            Toast.makeText(getApplicationContext(),"No more plays!", Toast.LENGTH_SHORT).show();
        }
        else{
            available_plays += -1;
            total_plays += 1;
            monkey_bucks += 10;
            gameStat.put("available_plays", available_plays);
            gameStat.put("monkey_bucks", monkey_bucks);
            gameStat.put("total_plays", total_plays);
            gameStat.saveInBackground();
            Intent intent = new Intent(this, FrozenBubble.class);
            startActivity(intent);
        }
    }

    public void playTiltMaze(View view){
        if(available_plays == 0){
            Toast.makeText(getApplicationContext(),"No more plays!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Remember, only 40 moves allowed!", Toast.LENGTH_SHORT).show();
            total_plays += 1;
            available_plays += -1;
            monkey_bucks += 10;
            gameStat.put("available_plays", available_plays);
            gameStat.put("monkey_bucks", monkey_bucks);
            gameStat.put("total_plays", total_plays);
            gameStat.saveInBackground();
            Intent intent = new Intent(this, TiltMazesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        elapsed_time = 0;
        total_time = gameStat.getInt("total_time");
        start_time = System.currentTimeMillis();
        playing = true;
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(playing){
            elapsed_time = elapsed_time + (System.currentTimeMillis() - start_time);
            elapsed_time /= 1000;
            elapsed_time /= 60;
            total_time = total_time + (int) elapsed_time;
            gameStat.put("total_time", total_time);
            gameStat.saveInBackground();
            playing = false;
        }
    }

}
