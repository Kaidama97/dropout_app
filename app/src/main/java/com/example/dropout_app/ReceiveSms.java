package com.example.dropout_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


public class ReceiveSms extends BroadcastReceiver {
    private String msgBody = null, sms_from;

    public static Context context;
    private String blockedNumber[] = {"6505551212"};
    private int index = 0, check = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msg = null;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    System.out.println(pdus);
                    msg = new SmsMessage[pdus.length];
                    for (int i = 0; i < msg.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        this.sms_from = msg[i].getOriginatingAddress().toString();// var hold sms receiver
                    }
                    for(int i=0; i< msg.length;i++)
                    {
                        msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        this.msgBody += msg[i].getMessageBody().toString();// var hold message
                    }
                    for (int x = 0; x < blockedNumber.length; x++) {
                        if (this.sms_from.equals(blockedNumber[x])) {
                            index++;
                            check = x;
                        }

                    }
                    if (index >= 1) {
                        System.out.println("BLOCKED NUMBER!!!!!!!!!!!!");
                        //blockedNumber[blockedNumber.length+1]= sms_from;
                        // deleteSMS(context,this.msgBody,this.sms_from);
//                        ContentResolver contentResolver = context.getContentResolver();
//                        Uri uriSms = Uri.parse("content://sms/inbox");
//                        String sms ="address='"+ blockedNumber[check] + "'";
//                        Cursor cursor = contentResolver.query(uriSms, new String[] { "_id", "body" }, sms, null,   null);
//                        if (cursor.moveToFirst()) {
//                            ////Changed to 0 to get Message id instead of Thread id :
//                            String MsgId = cursor.getString(0);
//                            context.getContentResolver().delete(Uri.parse("content://sms/inbox" + MsgId), null, null);
                    } else {
                        Toast.makeText(context, "From: " + this.sms_from + " Message: " + this.msgBody.toString(), Toast.LENGTH_LONG).show();

                        final SendJson sj = (SendJson) new SendJson().execute(this.sms_from, this.msgBody);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        String txt= sj.text;
                                        String from = sj.fromm;
                                        System.out.println("check");
                                        Double responseResult = sj.responseResultt;
                                        MainActivity.checkScamMessage(txt, from, responseResult);
                                        System.out.println(txt);
                                    }
                                },
                                1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }
    public void blockedNumber()
    {
        blockedNumber[check+1]= this.sms_from ;
    }
//    public void checkScamMessage(String title, String from, Double result)
//    {
//
//        AlertDialog builder = new AlertDialog.Builder(this.context).create();
//        builder.setTitle(title+" Phone Number");
//        builder.setMessage("Phone Number: "+  from +"Have a safety rating of:"+ result);
//
//
//        //Button One : Yes
//        builder.setButton(Dialog.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ReceiveSms rs = new ReceiveSms();
//                rs.blockedNumber();
//            }
//        });
//
//
//        //Button Two : No
//        builder.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
////        //Button Three : Neutral
////        builder.setNeutralButton("Can't Say!", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.cancel();
////            }
////        });
//
//
////        AlertDialog alert = builder.create();
//        builder.show();
//        System.out.println(from);
//    }


//
//    public static Context getAppContext() {
//
//        return ReceiveSms.context;
//    }


}


