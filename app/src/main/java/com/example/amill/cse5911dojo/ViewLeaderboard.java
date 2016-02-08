package com.example.amill.cse5911dojo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Mingziqian on 16/2/6.
 */
public class ViewLeaderboard extends AppCompatActivity{

    private boolean test = false;
    private ParseException x;
    private ParseUser currentUser;
    private String user;
    private String current_belt;
    private String total_plays;
    private String current_uniform;
    private String obj_id;
    private ParseObject gameStat;
    private User_LocalDB user_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            obj_id=currentUser.getObjectId();
        }
        user_db=new User_LocalDB(this);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("GameStats");
        query1.whereEqualTo("user",ParseUser.getCurrentUser());
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    // object will be your game score
                    gameStat = object.get(0);
                    test = true;
                    Context context = getApplicationContext();
                    CharSequence text = "SUCCESS";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    if (gameStat.getString("glucose_belt") != null) {
                        current_belt = gameStat.getString("glucose_belt").toString();

                    } else if (gameStat.getString("bolus_belt") != null) {
                        current_belt = gameStat.getString("bolus_belt").toString();

                    }
                    total_plays = gameStat.getNumber("total_plays").toString();

                    showInfo();
                    storeResults();

                } else {
                    // something went wrong
                    Log.d("gamestats", "Error: " + e.getMessage());
                    Context context = getApplicationContext();
                    CharSequence text = "FAILED";
                    int duration = Toast.LENGTH_LONG;
                    x = e;
                    test = true;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }


        });


    }

    private void showInfo()
    {
        setContentView(R.layout.activity_view_leaderboard);

        TextView label = (TextView) findViewById(R.id.lbl_name);
        label.setText(ParseUser.getCurrentUser().getUsername().toString());

        TextView lblcurrent_belt = (TextView) findViewById(R.id.lbl_current_belt);
        lblcurrent_belt.setText(current_belt);

        TextView lbltotal_plays = (TextView) findViewById(R.id.lbl_tot_plays);
        lbltotal_plays.setText(total_plays);

        ImageView img = (ImageView) findViewById(R.id.belt_image);
        int imgID = getResources().getIdentifier(current_uniform,"drawable",getPackageName());
        img.setImageResource(imgID);


    }
    private void storeResults()
    {
        current_belt = user_db.getBolus_Belt(current_belt).toString();
        total_plays = user_db.getTotal_plays(total_plays).toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
