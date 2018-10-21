package com.vit.mmsb.eureka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "AAAAAAAAAAAAAAAAA\n");

        // Get the view from activity_main.xml
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d(TAG, "After creating user\n");
        if (user != null)
        {
            Intent myIntent = new Intent(MainActivity.this,
                    Feed.class);
            startActivity(myIntent);
        }
        else {
            setContentView(R.layout.activity_main);

            Log.d(TAG, "After setContentView\n");
            // Locate the button in activity_main.xml
            b1 = findViewById(R.id.button0);
            b2 = findViewById(R.id.button4);

            // Capture button clicks
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent myIntent = new Intent(MainActivity.this,
                            Register.class);
                    Log.d(TAG, "Inside setonClickListener of b1\n");
                    startActivity(myIntent);
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent myIntent = new Intent(MainActivity.this,
                            LoginActivity.class);
                    Log.d(TAG, "Inside setonClickListener of b2\n");
                    startActivity(myIntent);
                }
            });
        }

}}