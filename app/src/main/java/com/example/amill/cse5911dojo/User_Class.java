package com.example.amill.cse5911dojo;



public class User_Class {

    String username, password;
    String total_plays;
    String monkey_bucks;
    String total_time;
    String bolus_betl;
    String glucose_belt;
    String available_plays;

    public User_Class(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }


    public String getAvailable_plays() {
        return available_plays;
    }

    public void setAvailable_plays(String available_plays) {
        this.available_plays = available_plays;
    }


    public String getBolus_betl() {
        return bolus_betl;
    }

    public void setBolus_betl(String bolus_betl) {
        this.bolus_betl = bolus_betl;
    }


    public String getGlucose_belt() {
        return glucose_belt;
    }

    public void setGlucose_belt(String glucose_belt) {
        this.glucose_belt = glucose_belt;
    }


    public String getMonkey_bucks() {
        return monkey_bucks;
    }

    public void setMonkey_bucks(String monkey_bucks) {
        this.monkey_bucks = monkey_bucks;
    }



    public void setTotal_plays(String name) {
        this.total_plays = name;
    }

    public String getTotal_plays() {
        return total_plays;
    }

}
