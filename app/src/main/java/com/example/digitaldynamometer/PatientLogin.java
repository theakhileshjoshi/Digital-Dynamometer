package com.example.digitaldynamometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class PatientLogin extends AppCompatActivity {

    EditText editText;
    Button nextBtn;
    String id;

    private static final int PERMISSION_REQUEST_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        editText = (EditText) findViewById(R.id.editText);
        nextBtn = (Button) findViewById(R.id.nextbtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editText.getText().toString();

                if(ContextCompat.checkSelfPermission(PatientLogin.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    searchDirectory(id);
                }else{
                    askPermission();
                }
            }
        });
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                searchDirectory(id);
            }else{
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void searchDirectory(String id){

        File csv =  new File(Environment.getExternalStorageDirectory()+"/DigitalDynamometer/"+id+".csv");
        Intent nextPage = new Intent(getApplicationContext(),ReadingType.class);
        if(!csv.exists()){
            Toast.makeText(getApplicationContext(),"Invalid Login I'd.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Logged in.",Toast.LENGTH_SHORT).show();
            nextPage.putExtra("loginId",id);
            startActivity(nextPage);
            finish();
        }
    }

}