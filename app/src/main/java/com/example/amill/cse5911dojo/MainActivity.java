package com.example.amill.cse5911dojo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    User_LocalDB userLocalDB;
    public Chronometer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalDB = new User_LocalDB(this);
        setContentView(R.layout.activity_main);
    }

    public void chooseGame(View view) {
        Intent intent = new Intent(this, Choose_Game.class);
        startActivity(intent);
    }

    public void takeSurvey(View view) {
        Intent intent = new Intent(this, DailySurvey.class);
        startActivity(intent);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }

    public void viewLeaderboard(View view)
    {
        Intent intent = new Intent(this, ViewLeaderboard.class);
        startActivity(intent);
    }
    public void setReminder(View view){
        Intent intent = new Intent(this, AlarmListActivity.class);
        startActivity(intent);
    }
    public void logOut(View view)
    {
        ParseUser user = ParseUser.getCurrentUser();
        if(user != null) {
            user.logOut();
            userLocalDB.clearData();
            Intent intent = new Intent(this, Login_Screen.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, Login_Screen.class);
            startActivity(intent);
            finish();
        }
    }

    public void openStore(View view) {
        Intent openNinjaStore = new Intent(this, StoreScreen.class);
        startActivity(openNinjaStore);
    }
}
