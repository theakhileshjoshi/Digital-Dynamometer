package com.example.digitaldynamometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class LoadingPage extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Intent registerDoc = new Intent(LoadingPage.this,RegisterDoctor.class);
        Intent isNewPatient = new Intent(LoadingPage.this,IsNewPatient.class);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences checkLogin = getSharedPreferences("loginCheck",MODE_PRIVATE);
                    String name = checkLogin.getString("name","0000000000");
                    if(name == "0000000000")startActivity(registerDoc);
                    else startActivity(isNewPatient);
                    finish();
                }catch (Exception exception){
                    startActivity(registerDoc);
                    finish();
                }
            }
        }, 1000);


    }

}