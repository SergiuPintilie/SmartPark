package com.ic.smartpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="MyPreferences";
    private static final String TOKEN="TOKEN";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        if(sharedPreferences.getString(TOKEN,null)!=null){
            Intent intent=new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
        ConstraintLayout constraintLayout = findViewById(R.id.main);

        // Determine the current theme
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Set the appropriate background drawable based on the current theme
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Dark theme
            constraintLayout.setBackgroundResource(R.drawable.gradient_background_dark);
        } else {
            // Light theme
            constraintLayout.setBackgroundResource(R.drawable.gradient_background);
        }
    }

    public void goToSignIn(View view){
        Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToLogIn(View view){
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
