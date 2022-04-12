package com.example.digitaldynamometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ReactiveGuide;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPatient extends AppCompatActivity {

    EditText patientName,patientOccupation,patientMobileNumber,patientAddress;
    RadioGroup maleFemale,dominance;
    Button birthday,register;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView ageShow;
    String dob;
    String rightLeft,gender;
    int age = 0;
    String addZero;
    String fldrname = "DigitalDynamometer";
    String directory;

    private static final int PERMISSION_REQUEST_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        register = (Button) findViewById(R.id.regisBtn);
        birthday = (Button) findViewById(R.id.calenderbtn);

        maleFemale = (RadioGroup) findViewById(R.id.maleFemale);
        dominance = (RadioGroup) findViewById(R.id.dominance);

        patientName = (EditText) findViewById(R.id.patientName);
        patientOccupation = (EditText) findViewById(R.id.occupation);
        patientAddress = (EditText) findViewById(R.id.address);
        patientMobileNumber = (EditText) findViewById(R.id.mobileNumber);

        ageShow = (TextView) findViewById(R.id.age);

        birthday.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(RegisterPatient.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (datePicker, i, i1, i2) -> {
            addZero = String.valueOf(i2%10);
            dob = i2+"/"+i1+"/"+i;
            Calendar cal = Calendar.getInstance();
            int currYear = cal.get(Calendar.YEAR);
            age = currYear - i;
            String showAge = String.valueOf(age)+"yrs";
            ageShow.setText(showAge);
        };


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = patientName.getText().toString();
                String occupation = patientOccupation.getText().toString();
                String mobNum = patientMobileNumber.getText().toString();
                String address = patientAddress.getText().toString();


                int genderId = maleFemale.getCheckedRadioButtonId();
                if(genderId != -1){
                    RadioButton radioButton = findViewById(genderId);
                    gender = radioButton.getText().toString();

                }


                int dominanceId = dominance.getCheckedRadioButtonId();
                if(dominanceId != -1){
                    RadioButton radioButton = findViewById(dominanceId);
                    rightLeft = radioButton.getText().toString();

                }

                SharedPreferences checkLogin = getSharedPreferences("loginCheck",MODE_PRIVATE);
                String docName = checkLogin.getString("name","0000000000");


                if(name.isEmpty() || occupation.isEmpty() || mobNum.isEmpty() || address.isEmpty()
                || rightLeft.isEmpty() || gender.isEmpty() || dob.isEmpty() || age == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Fill All the Details.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{

                    String nameForLogin = name.replace(" ","");
                    String loginId = createLoginId(nameForLogin.toLowerCase(),mobNum);

                    if(ContextCompat.checkSelfPermission(RegisterPatient.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        createDirectory(fldrname);

                        String csv = Environment.getExternalStorageDirectory()+"/DigitalDynamometer/"+loginId+".csv";

                        try {

                            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv,true));

                            String pNameRow[] = new String[]{"Patient Name",name};
                            String pDobRow[] = new String[]{"Date of Birth",dob};
                            String pGender[] = new String[]{"Gender",gender};
                            String pDominance[] = new String[]{"Dominance",rightLeft};
                            String pOccupation[] = new String[]{"Occupation",occupation};
                            String pMobile[] = new String[]{"Mobile Number",mobNum};
                            String pAddress[] = new String[]{"Address",address};
                            String pAge[] = new String[]{"Age",String.valueOf(age)};
                            String docname[] = new String[]{"Doctor's name",docName};

                            csvWriter.writeNext(pNameRow);
                            csvWriter.writeNext(pDobRow);
                            csvWriter.writeNext(pGender);
                            csvWriter.writeNext(pDominance);
                            csvWriter.writeNext(pOccupation);
                            csvWriter.writeNext(pMobile);
                            csvWriter.writeNext(pAddress);
                            csvWriter.writeNext(pAge);
                            csvWriter.writeNext(docname);

                            csvWriter.close();
                            Intent nextPage = new Intent(getApplicationContext(),ReadingType.class);
                            nextPage.putExtra("loginId",loginId);
                            startActivity(nextPage);
                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else{
                        askPermission();
                    }

                }


            }
        });

    }


    String createLoginId(String name,String mobile){
        String loginId = String.valueOf(name.length());
        int aCount=0,eCount=0,iCount=0,oCount=0,uCount=0;


        for(int i =0;i<name.length();i++){
            if(name.charAt(i) == 'a'){
                aCount++;
            }
        }

        for(int i =0;i<name.length();i++){
            if(name.charAt(i) == 'e'){
                eCount++;
            }
        }

        for(int i =0;i<name.length();i++){
            if(name.charAt(i) == 'i'){
                iCount++;
            }
        }

        for(int i =0;i<name.length();i++){
            if(name.charAt(i) == 'o'){
                oCount++;
            }
        }

        for(int i =0;i<name.length();i++){
            if(name.charAt(i) == 'u'){
                uCount++;
            }
        }

        if(aCount == 0)loginId+=addZero;
        else loginId+=String.valueOf(aCount);

        if(eCount == 0)loginId+=addZero;
        else loginId+=String.valueOf(eCount);

        if(iCount == 0)loginId+=addZero;
        else loginId+=String.valueOf(iCount);

        if(oCount == 0)loginId+=addZero;
        else loginId+=String.valueOf(oCount);

        if(uCount == 0)loginId+=addZero;
        else loginId+=String.valueOf(uCount);

        if(mobile.length()==10){
            loginId+= mobile.charAt(8);
            loginId+= mobile.charAt(9);
        }else{
            Toast.makeText(getApplicationContext(),"Invalid Mobile Number.",Toast.LENGTH_SHORT).show();
        }

        Toast toast = Toast.makeText(getApplicationContext(), loginId, Toast.LENGTH_SHORT);
        toast.show();
        return loginId;
    }


    private void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                createDirectory(fldrname);
            }else{
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void createDirectory(String name){

        File file = new File(Environment.getExternalStorageDirectory(),name);
        if(!file.exists()){
            file.mkdir();
            Toast.makeText(getApplicationContext(),"Folder Made",Toast.LENGTH_SHORT).show();
            directory = file.getAbsolutePath();
        }else{
            Toast.makeText(getApplicationContext(),"Folder Already exists",Toast.LENGTH_SHORT).show();
        }
    }

}