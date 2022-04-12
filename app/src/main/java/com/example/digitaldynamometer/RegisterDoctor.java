package com.example.digitaldynamometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterDoctor extends AppCompatActivity {

    private EditText doctorName;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        doctorName = (EditText) findViewById(R.id.doctorName);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = doctorName.getText().toString();

                SharedPreferences checkLogin = getSharedPreferences("loginCheck",MODE_PRIVATE);
                SharedPreferences.Editor editor = checkLogin.edit();
                editor.putString("name",name);
                editor.apply();

                Toast toast = Toast.makeText(getApplicationContext(),"Doctor Details Saved!",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(RegisterDoctor.this,IsNewPatient.class);
                startActivity(intent);
                finish();
            }
        });
    }




}