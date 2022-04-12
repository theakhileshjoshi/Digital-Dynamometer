package com.example.digitaldynamometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class ReadingType extends AppCompatActivity {

    TextView loginShow;
    Button nextButton;
    String loginId;
    RadioGroup radioGroup;
    String readingType;
    private static final int PERMISSION_REQUEST_CODE = 7;

    public String getLoginId() {
        Intent intent = getIntent();
        loginId = intent.getStringExtra("loginId");
        return loginId;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_type);

        String id = getLoginId();

        loginShow = (TextView) findViewById(R.id.loginDisplay);
        loginShow.setText("Login I'd: "+id);


        nextButton = (Button) findViewById(R.id.nextToTakeReadings);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        nextButton.setOnClickListener(view -> {
            int radId = radioGroup.getCheckedRadioButtonId();
            if(radId!= -1){
                RadioButton radioButton = findViewById(radId);
                readingType = radioButton.getText().toString();

                if(ContextCompat.checkSelfPermission(ReadingType.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    addReadingType(loginId,readingType);
                }else{
                    askPermission();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Please Select a option.",Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                addReadingType(loginId,readingType);
            }else{
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void addReadingType(String loginId,String readingType){
        File csv =  new File(Environment.getExternalStorageDirectory()+"/DigitalDynamometer/"+loginId+".csv");
        if(!csv.exists()){
            Toast.makeText(getApplicationContext(),"Invalid Login.",Toast.LENGTH_SHORT).show();
        }else{

            try{
                CSVWriter csvWriter = new CSVWriter(new FileWriter(csv,true));
                String[] pReadingType = new String[]{"Reading Type",readingType};

                csvWriter.writeNext(pReadingType);
                csvWriter.close();

                Intent nextPage = new Intent(ReadingType.this,TakeReadings.class);
                nextPage.putExtra("loginId",loginId);
                startActivity(nextPage);

            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),"Error While Fetching File.",Toast.LENGTH_SHORT).show();
            }

        }
    }

}