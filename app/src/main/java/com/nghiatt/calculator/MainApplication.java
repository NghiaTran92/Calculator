package com.nghiatt.calculator;

import android.app.Application;

import com.nghiatt.calculator.database.HistoryDatabase;

/**
 * Created by ngh on 9/6/2015.
 */
public class MainApplication extends Application {
    private static MainApplication instance;
    public static HistoryDatabase historyDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        historyDatabase=new HistoryDatabase(this);
    }



    public static MainApplication getApplication(){
        return instance;
    }
}
