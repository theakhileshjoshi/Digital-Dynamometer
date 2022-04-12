package com.example.digitaldynamometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TakeReadings extends AppCompatActivity {

    String espReading,grip;
    TextView textView;
    Button leftBtn,left11,left12,left21,left22;
    Button rightBtn,right11,right12,right21,right22;
    Button nextBtnFinal;
    String loginId;
    String curHand;
    int nextCheck = 0;
    boolean check;
    String FF_KG,LF_KG,MF_KG,RF_KG;



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
        setContentView(R.layout.activity_take_readings);

        loginId = getLoginId();
        textView =  (TextView) findViewById(R.id.textView6);

        leftBtn = (Button) findViewById(R.id.leftBtn);
        left11 = (Button) findViewById(R.id.left11);
        left12 = (Button) findViewById(R.id.left12);
        left21 = (Button) findViewById(R.id.left21);
        left22 = (Button) findViewById(R.id.left22);

        rightBtn = (Button) findViewById(R.id.rightBtn);
        right11 = (Button) findViewById(R.id.right11);
        right12 = (Button) findViewById(R.id.right12);
        right21 =  (Button) findViewById(R.id.right21);
        right22 = (Button) findViewById(R.id.right22);

        left11.setClickable(false);
        left12.setClickable(false);
        left21.setClickable(false);
        left22.setClickable(false);
        rightBtn.setClickable(false);
        right11.setClickable(false);
        right12.setClickable(false);
        right21.setClickable(false);
        right22.setClickable(false);


        textView.setText(getReadings());

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FF_KG = getReadings();
                curHand = "Left";
                grip = FF_KG;
                leftBtn.setText("Left Hand ✓");
                left11.setClickable(true);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                leftBtn.setClickable(false);
            }
        });

        left11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(true);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                left11.setText("Left Index ✓");
            }
        });

        left12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(true);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                left12.setText("Left Middle ✓");
            }
        });

        left21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(true);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                left21.setText("Left Ring ✓");
            }
        });

        left22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(true);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                left22.setText("Left Little ✓");
                if(ContextCompat.checkSelfPermission(TakeReadings.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    check = addReading(loginId,curHand);
                }else{
                    askPermission();
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grip = getReadings();
                leftBtn.setClickable(false);
                curHand = "Right";
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(true);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                rightBtn.setText("Right Hand ✓");
            }
        });

        right11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(true);
                right21.setClickable(false);
                right22.setClickable(false);
                right11.setText("Right Index ✓");
            }
        });

        right12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(true);
                right22.setClickable(false);
                right12.setText("Right Middle ✓");
            }
        });

        right21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(true);
                right21.setText("Right Ring ✓");
            }
        });


        right22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LF_KG = getReadings();
                left11.setClickable(false);
                left12.setClickable(false);
                left21.setClickable(false);
                left22.setClickable(false);
                rightBtn.setClickable(false);
                right11.setClickable(false);
                right12.setClickable(false);
                right21.setClickable(false);
                right22.setClickable(false);
                right22.setText("Right Little ✓");
                if(ContextCompat.checkSelfPermission(TakeReadings.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    check = addReading(loginId,curHand);
                }else{
                    askPermission();
                }
            }
        });

        nextBtnFinal = (Button)findViewById(R.id.nextBtnFinal);

        nextBtnFinal.setOnClickListener(view -> {

            if (nextCheck >= 2){
                Intent nextPage = new Intent(TakeReadings.this,FinalPage.class);
                nextPage.putExtra("loginId",loginId);
                startActivity(nextPage);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Please take readings before",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String getReadings(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ESPApi espApi = retrofit.create(ESPApi.class);

        Call<List<Post>> call = espApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    /*
                        Show an 'Error Message', maybe a toast.
                        Response response gives Error Number
                            -> response.code()
                        Example:
                            "Code: " + response.code()
                            -> Code:  404
                    */

                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Post> posts = response.body();

                assert posts != null;
                for(Post post : posts){
                    espReading = Float.toString(post.getEspReading());

                    // Now pass them where required.

                }



            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                /*
                    Show an 'Error Message', maybe a toast.
                    Throwable t gives Error Description
                        -> t.getMessage()
                */
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getApplicationContext(),"Readings"+espReading,Toast.LENGTH_SHORT).show();
        return espReading;
    }


    private void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               check = addReading(loginId,curHand);
            }else{
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean addReading(String loginId,String hand){
        String filename = loginId+".csv";
        Toast.makeText(getApplicationContext(),filename,Toast.LENGTH_SHORT);
        File csv =  new File(Environment.getExternalStorageDirectory()+"/DigitalDynamometer/"+filename);
        if(!csv.exists()){
            Toast.makeText(getApplicationContext(),"Invalid Login id.",Toast.LENGTH_SHORT).show();
            return false;
        }else{

            try{
                CSVWriter csvWriter = new CSVWriter(new FileWriter(csv,true));

                String[] curHand = new String[]{"Hand",hand};
                String[] emptyLine = new String[]{""};
                String[] fingerTitles = new String[]{"Grip","Index Finger","Middle Finger", "Ring Finger","Little Finger"};
                String[] fingerReadings = new String[]{grip,FF_KG,MF_KG,RF_KG,LF_KG};

                csvWriter.writeNext(emptyLine);
                csvWriter.writeNext(curHand);
                csvWriter.writeNext(emptyLine);
                csvWriter.writeNext(fingerTitles);
                csvWriter.writeNext(fingerReadings);
                csvWriter.close();
                nextCheck++;
                return true;

            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),"Error While Fetching File.",Toast.LENGTH_SHORT).show();
                return false;
            }

        }
    }

}