package com.example.amill.cse5911dojo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Login_Screen extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView register;
    User_LocalDB userLocalDB;
    User_Class user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.buttonLogin);
        register = (TextView)findViewById(R.id.etRegister);
        bLogin.setOnClickListener(this);
        register.setOnClickListener(this);

        userLocalDB = new User_LocalDB(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User_Class user = new User_Class(username, password);
                userLocalDB.storeData(user);
                validateUser();
                break;
            case R.id.etRegister:
                startActivity(new Intent(this, Register.class));
                finish();
                break;
        }
    }
    public void validateUser()
    {
        ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    //LOGIN GOOD
                    //Start new activity
                    startMain();
                    userLocalDB.userLoggedIn(true);
                    finish();
                } else {
                    //LOGIN BAD
                    fail();

                }
            }
        });
        if(userLocalDB.isUserLoggedIn()) {
            //grab_Game_Stats(user);
            userLocalDB.storeData(user);
        }
    }



    public void startMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public void fail()
    {
        Toast.makeText(getApplicationContext(),"Login Failed, Try Again", Toast.LENGTH_SHORT).show();
    }

}
