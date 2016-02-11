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

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by Mingziqian on 16/2/6.
 */
public class ViewLeaderboard extends AppCompatActivity{

    private boolean test;
    private ParseException x;
    private ParseUser currentUser;
    private String user;
    private String current_belt;
    private String total_plays;
    private String obj_id;
    private ParseObject gameStat;
    private User_LocalDB user_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_leaderboard);

        //find the listView in the XML of leaderboard's page, act as a container of listItems.
        ListView list = (ListView) findViewById(R.id.leaderboard_listView);

        //create an arraylist for listItems.
        ArrayList<HashMap<String, String>> mylist = new ArrayList<>();

        String[] names = {"David", "Andrew", "Austin", "Karl", "Mike", "Dean", "Sam", "Jess"};
        String[] belts = {"Black", "Brown", "Blue", "Green", "Orange", "Orange", "Yellow", "White"};
        String[] uniforms = {"black_uniform", "black_uniform", "blue_outfit", "green_outfit", "orange_outfit", "orange_outfit", "yellow_outfit", "white_outfit"};
        for(int i=0;i<8;i++)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("leaderboard_name", "No."+Integer.toString(i+1)+"    "+names[i]);
            map.put("leaderboard_belt", "Belt Level: "+belts[i]);
            map.put("leaderboard_tot_plays","Total Plays: "+Integer.toString(100-10*i));
            mylist.add(map);
        }

        //generate adapter for listView
        SimpleAdapter mSchedule = new SimpleAdapter(this,
                mylist, //date source
                R.layout.leaderboard_list_item,   //XML implementation of listItem
                //dynamic array with listItems' items
                new String[] {"leaderboard_name", "leaderboard_belt", "leaderboard_tot_plays"},
                //TextView id in listView
                new int[] {R.id.leaderboard_name,R.id.leaderboard_belt,R.id.leaderboard_tot_plays});
        //display
        list.setAdapter(mSchedule);
    }

}
