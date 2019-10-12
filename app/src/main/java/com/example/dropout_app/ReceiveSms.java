package com.example.dropout_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;


public class ReceiveSms extends BroadcastReceiver {
    private String msgBody = null, sms_from;
   // private RequestQueue requestQueue;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msg = null;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msg = new SmsMessage[pdus.length];
                    for (int i = 0; i < msg.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        this.sms_from = msg[i].getOriginatingAddress().toString();// var hold sms receiver
                        this.msgBody = msg[i].getMessageBody().toString();// var hold message
                        Toast.makeText(context, "From: " + this.sms_from + " Message: " + this.msgBody.toString(), Toast.LENGTH_SHORT).show();
                        //String data = "{" + "\"moblieNumber\"" + this.sms_from + "\"," + "\"body\"" + this.msgBody.toString() + "\"" + "}";
                        new SendJson().execute(this.sms_from,this.msgBody);
                        //System.out.println(this.sms_from + this.msgBody);
                        //j1.doInBackground(this.sms_from,this.msgBody);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /*public void sendJSON(String data) {
        final String saveData = data;
        Object appContext =MainActivity.appContext() ;
        String URL = "https://us-central1-dropouts-54029.cloudfunctions.net/widgets";

        requestQueue = Volley.newRequestQueue(appContext);

    }*/
}


