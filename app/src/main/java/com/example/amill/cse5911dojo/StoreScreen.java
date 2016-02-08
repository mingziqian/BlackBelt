package com.example.amill.cse5911dojo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.example.amill.cse5911dojo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StoreScreen extends AppCompatActivity {

    private boolean test = false;
    private ParseException x;
    private ParseUser currentUser;
    private String user;
    private String ninjaBucks;
    private ParseObject gameStat;
    private User_LocalDB user_db;
    private String obj_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ParseQuery
        currentUser = ParseUser.getCurrentUser();

        if(currentUser != null){
            obj_id = currentUser.getObjectId();
        } else {

        }

        user_db = new User_LocalDB(this);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameStats");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    //object will be Game Stats
                    gameStat = object.get(0);
                    test = true;

                    ninjaBucks = gameStat.getNumber("monkey_bucks").toString();

                    showInfo();
                    storeResults();
                } else {
                    //Query went wrong
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

    private void showInfo(){
        setContentView(R.layout.activity_store_screen);

        TextView username = (TextView) findViewById(R.id.storeUser);
        username.setText(ParseUser.getCurrentUser().getUsername().toString());

        TextView ninja_Bucks = (TextView) findViewById(R.id.ninjaBucks);
        ninja_Bucks.setText("$" + ninjaBucks);
    }

    private void storeResults() {
        //ninjaBucks = user_db.getMonkey_bucks(ninjaBucks).toString();
    }

    public void onClickBlackItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Black Uniform");
        goToItem.putExtra("itemImage", "black_uniform");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "blackUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickBlueItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Blue Uniform");
        goToItem.putExtra("itemImage", "blue_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "blueUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickOrangeItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Orange Uniform");
        goToItem.putExtra("itemImage", "orange_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "orangeUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickPinkItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Pink Uniform");
        goToItem.putExtra("itemImage", "pink_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "pinkUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickPurpleItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Purple Uniform");
        goToItem.putExtra("itemImage", "purple_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "purpleUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickRedItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Red Uniform");
        goToItem.putExtra("itemImage", "red_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "redUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickWhiteItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "White Uniform");
        goToItem.putExtra("itemImage", "white_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "whiteUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickYellowItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Yellow Uniform");
        goToItem.putExtra("itemImage", "yellow_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "yellowUniform");

        startActivityForResult(goToItem, result);
    }

    public void onClickGreenItem(View view) {
        Intent goToItem = new Intent(this, StoreItemScreen.class);

        final int result = 1;

        goToItem.putExtra("itemName", "Green Uniform");
        goToItem.putExtra("itemImage", "green_outfit");
        goToItem.putExtra("itemCost", 50);
        goToItem.putExtra("field", "greenUniform");

        startActivityForResult(goToItem, result);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameStats");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    //object will be Game Stats
                    gameStat = object.get(0);
                    test = true;

                    ninjaBucks = gameStat.getNumber("monkey_bucks").toString();

                    showInfo();
                    storeResults();
                } else {
                    //Query went wrong
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

}