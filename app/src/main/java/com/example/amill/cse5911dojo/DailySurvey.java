package com.example.amill.cse5911dojo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DailySurvey extends AppCompatActivity {

    // Create global variables
    private String[] questions;
    private Integer[] selectedQuestions = {12, 12, 12, 12, 12};
    private Integer[] answeredValues = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int counter = 0;
    private int currentQ = 0;
    NumberPicker numPicker = null;
    private User_LocalDB user_db;
    private ParseObject gameStat;
    private ParseObject surveyValue;
    private String available_plays;
    private ParseUser currentUser;
    private String obj_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            obj_id=currentUser.getObjectId();
        }
        user_db=new User_LocalDB(this);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("GameStats");
        query1.whereEqualTo("user", ParseUser.getCurrentUser());
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    gameStat = object.get(0);
                    available_plays = gameStat.getNumber("available_plays").toString();

                } else {
                    // something went wrong
                    Log.d("gamestats", "Error: " + e.getMessage());
                    Context context = getApplicationContext();
                    CharSequence text = "FAILED";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        setContentView(R.layout.activity_daily_survey);
        // Set string array questions with the questions array from strings.xml
        questions = getResources().getStringArray(R.array.questions);
        // Set number pickers id, min, and max value
        numPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numPicker.setMinValue(1);
        numPicker.setMaxValue(5);
        numPicker.setWrapSelectorWheel(false);
        TextView textView = (TextView)findViewById(R.id.textView);

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            //TextView label = (TextView) findViewById(R.id.lbl_name);
            //  label.setText(currentUser.get("username").toString());
            obj_id=currentUser.getObjectId();

        } else {

        }
        user_db=new User_LocalDB(this);
    }

    private void storeAnswers () {
        // May not need to query here since we're just posting data to Parse DB
       /* ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Surveys");
        query2.whereEqualTo("user", currentUser.toString());
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (object != null) {
                    surveyValue = object.get(0);
                    for (int i = 0; i < 12; i++) {
                        if (answeredValues[i] != 0) {
                            switch(i) {
                                case 0:
                                   // surveyValue.add("blood_glucose", answeredValues[i]);
                                    surveyValue.put("blood_glucose", answeredValues[i]);
                                    break;
                                case 1:
                                    surveyValue.put("bolus_breakfast", answeredValues[i]);
                                    break;
                                case 2:
                                    surveyValue.put("bolus_lunch", answeredValues[i]);
                                    break;
                                case 3:
                                    surveyValue.put("bolus_dinner", answeredValues[i]);
                                    break;
                                case 4:
                                    surveyValue.put("bolus_snacks", answeredValues[i]);
                                    break;
                                case 5:
                                    surveyValue.put("bolus_meals", answeredValues[i]);
                                    break;
                                case 6:
                                    surveyValue.put("change_pump", answeredValues[i]);
                                    break;
                                case 7:
                                    surveyValue.put("count_carbs", answeredValues[i]);
                                    break;
                                case 8:
                                    surveyValue.put("exercise_daily", answeredValues[i]);
                                    break;
                                case 9:
                                    surveyValue.put("extra_activity", answeredValues[i]);
                                    break;
                                case 10:
                                    surveyValue.put("eating_fruit_veg", answeredValues[i]);
                                    break;
                                case 11:
                                    surveyValue.put("drinking_water", answeredValues[i]);
                                    break;
                            }
                            surveyValue.saveInBackground();
                        }
                    }

               // } else {
                    // something went wrong
                    Log.d("Surveys", "Error: " + e.getMessage());
                    Context context = getApplicationContext();
                    CharSequence text = "FAILED";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });*/
        ParseObject survey = new ParseObject("Surveys");
        for (int i = 0; i < 12; i++) {
            if (answeredValues[i] != 0) {
                switch(i) {
                    case 0:
                        // surveyValue.add("blood_glucose", answeredValues[i]);
                        survey.put("blood_glucose", answeredValues[i]);
                        break;
                    case 1:
                        survey.put("bolus_breakfast", answeredValues[i]);
                        break;
                    case 2:
                        survey.put("bolus_lunch", answeredValues[i]);
                        break;
                    case 3:
                        survey.put("bolus_dinner", answeredValues[i]);
                        break;
                    case 4:
                        survey.put("bolus_snacks", answeredValues[i]);
                        break;
                    case 5:
                        survey.put("bolus_meals", answeredValues[i]);
                        break;
                    case 6:
                        survey.put("change_pump", answeredValues[i]);
                        break;
                    case 7:
                        survey.put("count_carbs", answeredValues[i]);
                        break;
                    case 8:
                        survey.put("exercise_daily", answeredValues[i]);
                        break;
                    case 9:
                        survey.put("extra_activity", answeredValues[i]);
                        break;
                    case 10:
                        survey.put("eating_fruit_veg", answeredValues[i]);
                        break;
                    case 11:
                        survey.put("drinking_water", answeredValues[i]);
                        break;
                }
                survey.put("user", currentUser);
                survey.saveInBackground();
            }
        }

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

    // TODO:  Here we will check to see if daily survey has been taken and respond accordingly

    // Method that stores the responses, increments available plays, and updates the textView
    public void nextQuestion(View view) {
        TextView textView = (TextView) findViewById(R.id.textView);
        if (counter != 0) {
            // Store the last selected numerical value from numPicker in answeredValues to proper index
            Arrays.asList(answeredValues).set(currentQ, numPicker.getValue());
        }
        // Check to see if the counter is before the last random question
        if (counter < 5) {
            // Generate a random number and if it hasn't been used then update selectedQuestions
            Boolean newQuestion = false;
            while (!newQuestion) {
                Random rNum = new Random();
                currentQ = (rNum.nextInt(12 - 1) + 1);
                if (!Arrays.asList(selectedQuestions).contains(currentQ)) {
                    Arrays.asList(selectedQuestions).set(counter, currentQ);
                    newQuestion = true;
                }
            }
            // Update the text view with the next question
            textView.setText(questions[currentQ - 1]);

            // It was the last question so increment available plays, store data, and return to main
        } else {
            // Increment the available plays here as the reward
                gameStat.increment("available_plays", 1);
                gameStat.saveInBackground();

            // Iterate through answeredVales array and store results in Parse DB
            storeAnswers();

            Toast.makeText(getApplicationContext(), "Congratulations!  You just earned yourself a free game play.  Keep up the good work!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        counter ++;
    }
}
