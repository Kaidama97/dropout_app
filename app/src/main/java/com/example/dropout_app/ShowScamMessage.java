package com.example.dropout_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowScamMessage extends AppCompatActivity {

    public ShowScamMessage()
    {
    }
    public static Context passed;
    public void checkScamMessage(String title, String from, Double result)
    {

        System.out.println(title);
        AlertDialog builder = new AlertDialog.Builder(MainActivity.getAppContext()).create();
        builder.setTitle(title+" Phone Number");
        builder.setMessage("Phone Number: "+  from +"Have a safety rating of: "+ result);


        //Button One : Yes
        builder.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReceiveSms rs = new ReceiveSms();
                rs.blockedNumber();
                finish();
            }
        });


        //Button Two : No
        builder.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
        System.out.println(from);
    }


}