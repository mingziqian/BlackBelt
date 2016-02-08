package com.example.amill.cse5911dojo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Loading_Screen extends Activity {

    User_LocalDB user;
    User_Class user2;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading__screen);
        user = new User_LocalDB(this);
        user.getLogin();
        username = user.getUsername(username);
        password = user.getPassword(password);


        //Handler handler = new Handler();
       // handler.postDelayed(new Runnable() {

            //@Override
          //  public void run() {
                if(!username.equals("")){
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                //LOGIN GOOD
                                //Start new activity
                                //grab_Game_Stats();
                                Intent openMainActivity = new Intent(Loading_Screen.this, MainActivity.class);
                                startActivity(openMainActivity);
                                finish();
                            }
                        }
                    });
                }
                else{
                    user.clearData();
                    Intent openLoginScreen = new Intent(Loading_Screen.this, Login_Screen.class);
                    startActivity(openLoginScreen);
                    finish();
                }
          //  }
       // }, 2500);
    }

    public void grab_Game_Stats() {
        final ParseObject gameStats = new ParseObject("GameStats");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameStats");
        query.whereEqualTo("user", username);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> stats, ParseException e) {
                if (e == null) {
                      user2.setTotal_plays(stats.get(0).get("total_plays").toString());
        user2.setTotal_time(stats.get(0).get("total_time").toString());
        user2.setAvailable_plays(stats.get(0).get("available_plays").toString());
        user2.setBolus_betl(stats.get(0).get("bolus_belt").toString());
        user2.setGlucose_belt(stats.get(0).get("glucose_belt").toString());
        user2.setMonkey_bucks(stats.get(0).get("monkey_bucks").toString());



                    Log.d("score", "Retrieved " + stats.size() + " scores");
                     //finish();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
  /*  public void store_GameStats(List<ParseObject> stats)
    {





    }*/

}
