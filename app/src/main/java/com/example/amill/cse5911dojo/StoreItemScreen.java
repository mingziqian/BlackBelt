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
import android.widget.Button;
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


public class StoreItemScreen extends AppCompatActivity {

    private boolean test = false;
    private String itemImage;
    private ParseException x;
    private ParseUser currentUser;
    private String obj_id;
    private ParseObject gameStat;
    private ParseObject purchasedObjects;
    private int ninjaBucks;
    private int itemCost;
    private String itemField;
    private boolean purchased;
private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();

        if(currentUser != null){
            obj_id = currentUser.getObjectId();
        } else {

        }

        setContentView(R.layout.activity_store_item_screen);

        Intent itemRec = getIntent();

        itemField = itemRec.getExtras().getString("field");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameStats");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    //object will be GameStats
                    gameStat = object.get(0);
                    test = true;

                    ninjaBucks = gameStat.getInt("monkey_bucks");

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
        btn = (Button)findViewById(R.id.button11);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PurchasedObjects");
        query2.whereEqualTo("user", ParseUser.getCurrentUser());
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    //object will be Purchased Objects
                    purchasedObjects = object.get(0);
                    test = true;

                    purchased = purchasedObjects.getBoolean(itemField);
                    if(purchased)
                    {
                        btn.setText("Set as Uniform");
                    }

                } else {
                    //Query went wrong
                    Log.d("purchasedObject", "Error: " + e.getMessage());
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




        //add the name of the item to the item page
        String itemName = itemRec.getExtras().getString("itemName");
        TextView nameOfItem = (TextView) findViewById(R.id.itemName);
        nameOfItem.setText(itemName);

        //add the image of the uniform to the item page
        itemImage = itemRec.getExtras().getString("itemImage");
        int imgID = getResources().getIdentifier(itemImage, "drawable", getPackageName());
        ImageView imageItem = (ImageView) findViewById(R.id.itemImage);
        imageItem.setImageResource(imgID);

        //add the cost of the uniform to the item page
        itemCost = itemRec.getExtras().getInt("itemCost");
        TextView costOfItem = (TextView) findViewById(R.id.itemCost);
        costOfItem.setText("$" + itemCost);

    }

    public void onClickPurchase(View view) {
       // Intent purchaseItem = new Intent(this, StoreScreen.class);

        int cost = itemCost;

        if (purchased == true) {
            //make item no longer be able to be purchased
            Context context = getApplicationContext();
            CharSequence text = "Uniform Updated";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            gameStat.put("current_uniform", itemImage);
            gameStat.saveInBackground();

            finish();
        } else {
            if (itemCost > ninjaBucks) {
                Context context = getApplicationContext();
                CharSequence text = "Not Enough Ninja Bucks";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                finish();
            } else {
                //subtract the cost of the item from the total ninja bucks
                ninjaBucks = ninjaBucks - cost;
                gameStat.put("monkey_bucks", ninjaBucks);
                gameStat.put("current_uniform", itemImage);
                gameStat.saveInBackground();

                //add the ninjas uniform to its inventory or profile page
                purchased = true;
                purchasedObjects.put(itemField, purchased);
                purchasedObjects.saveInBackground();

                Context context = getApplicationContext();
                CharSequence text = "Item Purchased";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

               // startActivity(purchaseItem);

                finish();
            }
        }
    }

    public void onBackPressed() {
        //Intent purchaseItem = new Intent(this, StoreScreen.class);
        //startActivity(purchaseItem);

        finish();
    }

}
