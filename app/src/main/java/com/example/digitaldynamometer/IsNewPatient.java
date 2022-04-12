package com.example.digitaldynamometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IsNewPatient extends AppCompatActivity {

   RadioGroup radioGroup;
   Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_new_patient);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        nextBtn = (Button) findViewById(R.id.button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selection = radioGroup.getCheckedRadioButtonId();
                if(selection == -1){
                    Toast toast = Toast.makeText(getApplicationContext(),"Please Select a Input First",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    RadioButton radioButton = findViewById(selection);
                    String selected_btn = radioButton.getText().toString();
                    if(selected_btn.equals("Existing Patient")){//existing patient
                        Toast toast = Toast.makeText(getApplicationContext(),"Existing Patient",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(IsNewPatient.this,PatientLogin.class);
                        startActivity(intent);

                    }else if(selected_btn.equals("New Patient")){//New patient
                        Toast toast = Toast.makeText(getApplicationContext(),"New Patient",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(IsNewPatient.this,RegisterPatient.class);
                        startActivity(intent);
                    }
                }
            }
        });


    }
}