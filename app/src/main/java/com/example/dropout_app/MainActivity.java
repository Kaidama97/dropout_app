package com.example.dropout_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private Button button;
    public static Context context;

    public MainActivity()
    {
    }
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this;
        //checkScamMessage();

        button = (Button) findViewById(R.id.buttonCheck);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheck();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1000)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted",Toast.LENGTH_SHORT).show();
            else
                finish();
        }
    }
    public void openCheck(){

        Intent intent = new Intent(this,Check.class);
        startActivity(intent);

    }

    public static void checkScamMessage(String title, String from, Double result)
    {
        getAppContext();
        AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setTitle(title+" Phone Number");
        builder.setMessage("Phone Number: "+  from +"Have a safety rating of:"+ result);


        //Button One : Yes
        builder.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReceiveSms rs = new ReceiveSms();
            }
        });


        //Button Two : No
        builder.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
//        //Button Three : Neutral
//        builder.setNeutralButton("Can't Say!", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });


//        AlertDialog alert = builder.create();
        builder.show();
        System.out.println(from);
    }


    public static Context getAppContext() {

        return MainActivity.context;
    }


}
