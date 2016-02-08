package com.example.amill.cse5911dojo;

import android.app.ProgressDialog;

import com.parse.Parse;



public class Parse_Initialize extends android.app.Application {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "p92d3HFOlLTP2IcmfqS3nb7fVkXd0HudYkqG7a1q", "eKMHxia32ujqB1yMgIkCU5hHkDhQrHJmJZAoBrZ7");

    }


}
