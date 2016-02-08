package com.example.amill.cse5911dojo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ViewProfile extends AppCompatActivity {

private boolean test = false;
    private ParseException x;
    private ParseUser currentUser;
    private String user;
    private String current_belt;
    private String total_plays;
    private String monkey_bucks;
    private String available_plays;
    private String time_played;
    private String current_uniform;
    private String obj_id;
    private ParseObject gameStat;
    private User_LocalDB user_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                    currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                      // do stuff with the user
                               //TextView label = (TextView) findViewById(R.id.lbl_name);
                    //  label.setText(currentUser.get("username").toString());
                obj_id=currentUser.getObjectId();

            } else {

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

                  if(gameStat.getString("glucose_belt")!=null)
                  {
                      current_belt = gameStat.getString("glucose_belt");

                  }else if(gameStat.getString("bolus_belt")!=null)
                  {
                      current_belt = gameStat.getString("bolus_belt").toString();

                  }
                  total_plays = gameStat.getNumber("total_plays").toString();
                  monkey_bucks = gameStat.getNumber("monkey_bucks").toString();
                  time_played = gameStat.getNumber("total_time").toString();
                  available_plays = gameStat.getNumber("available_plays").toString();
                    current_uniform = gameStat.getString("current_uniform");

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


/*        current_belt = gameStat.getString("glucose_belt").toString();
        total_plays = gameStat.getNumber("total_plays").toString();
        monkey_bucks = gameStat.getNumber("monkey_bucks").toString();
        time_played = gameStat.getNumber("total_time").toString();
        available_plays = gameStat.getNumber("available_plays").toString();
*/

    }

    private void showInfo()
    {
        setContentView(R.layout.activity_view_profile);

        TextView label = (TextView) findViewById(R.id.lbl_name);
        label.setText(ParseUser.getCurrentUser().getUsername().toString());

        TextView lblcurrent_belt = (TextView) findViewById(R.id.lbl_current_belt);
        TextView lbltotal_plays = (TextView) findViewById(R.id.lbl_tot_plays);
        TextView lblmonkey_bucks = (TextView) findViewById(R.id.lbl_monkey_bucks);
        TextView lblavail_plays = (TextView) findViewById(R.id.lbl_avail_plays);
        TextView lbl_time_played = (TextView) findViewById(R.id.lbl_time_played);
        ImageView img = (ImageView) findViewById(R.id.belt_image);

        int imgID = getResources().getIdentifier(current_uniform,"drawable",getPackageName());
        img.setImageResource(imgID);

        /*switch(current_belt) {
            case "Red":
                img.setImageResource(R.mipmap.red);
                break;
            case "Black":
                img.setImageResource(R.mipmap.black);
            break;
            case "Blue":
                img.setImageResource(R.mipmap.blue);
                break;
            case "Green":
                img.setImageResource(R.mipmap.green);
                break;
            case "White":
                img.setImageResource(R.mipmap.white);
                break;
            case "Yellow":
                img.setImageResource(R.mipmap.yellow);
                break;
        }
*/
        lbl_time_played.setText(time_played);
        lblavail_plays.setText(available_plays);
        lblcurrent_belt.setText(current_belt);
        lbltotal_plays.setText(total_plays);
        lblmonkey_bucks.setText(monkey_bucks);

    }
    private void storeResults()
    {
                    current_belt = user_db.getBolus_Belt(current_belt).toString();
                    total_plays = user_db.getTotal_plays(total_plays).toString();
                    monkey_bucks = user_db.getMonkey_bucks(monkey_bucks).toString();
                    time_played = user_db.getTotal_time(time_played).toString();
                    available_plays = user_db.getAvailable_plays(available_plays).toString();

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
