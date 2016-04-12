package com.example.test.classui;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;

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
            //Parse.initialize(this); // 若有於 AndroidManifest.xml設定 "com.parse.APPLICATION_ID" & "com.parse.CLIENT_KEY" ==> 採 Parse.initialize(this)

        // 若要在此直接設定 Parse.Configuration.Builder 內容   // EX: 並無在 AndroidManifest.xml宣告<meta-data>，而且這邊想設定自己已知的Parse Sever URL
        Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("76ee57f8e5f8bd628cc9586e93d428d5")
                    .clientKey(null)
                    .server("http://parseserver-b3322-env.us-east-1.elasticbeanstalk.com/parse/") // 這邊的URL String 記得要以 / 結尾
                    .build()
        );

        // For Facebook API Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
