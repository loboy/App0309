package com.example.test.classui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DrinkMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        // Change into DrinkMenuActivity: Hello Message.

        Toast.makeText(this, "Hello~Welcome DrinkMenuActivity!!!", Toast.LENGTH_LONG).show();
    }
}
