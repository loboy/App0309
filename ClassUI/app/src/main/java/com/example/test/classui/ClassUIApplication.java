package com.example.test.classui;

import android.app.Application;

import com.parse.Parse;

import org.xml.sax.Parser;

/**
 * Created by Loboy on 2016/3/23.
 */

public class ClassUIApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();

        // For Parse Server Initialization
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
