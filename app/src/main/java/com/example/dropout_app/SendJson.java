package com.example.dropout_app;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class SendJson extends AsyncTask<String, Void, String>{
    public SendJson() {
    }

    @Override
    protected String doInBackground(String... strings) {
        System.out.println(strings[0]);
        System.out.println(strings[1]);
        String reply = sendJson(strings[0], strings[1]);
        return reply;
    }
    private String sendJson(String from, String message) {
        URL url = null;
        try {
            url = new URL("https://us-central1-dropouts-54029.cloudfunctions.net/widgets");
            System.out.println("Made" + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpsURLConnection con = null;
        try {
            con = (HttpsURLConnection) url.openConnection();
            System.out.println("Made Con");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("POST");
            System.out.println("Made POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        //String jsonInputString = "{" + "\"text\"" + from + "\"," + "\"from\"" + message + "\"" + "}";


        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("text", message) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonParam.put("from",  from);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        byte[] data= new byte[0];
//        try {
//            data = str.getBytes("UTF-8");
//            System.out.println("Converted JSON to bytes");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        try {
//            wr = new DataOutputStream(con.getOutputStream ());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        String str = jsonParam.toString();
//
//        try {
//            wr.writeBytes(str);
//            System.out.println("WriteBYytes");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            printout = new DataOutputStream(con.getOutputStream ());
//            System.out.println("Sent JSON");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            printout.writeBytes(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
//            System.out.println("Recieved??? JSON");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            wr.flush ();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            wr.close ();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try (OutputStream os = con.getOutputStream()) {
            final byte[] input = str.getBytes("utf-8");
            try( OutputStreamWriter wr = new OutputStreamWriter( con.getOutputStream())) {
                wr.write( str );
                wr.flush();
                System.out.println(str);
                System.out.println(input);
            }
            os.write(str.getBytes("UTF-8"));
            os.close();
            System.out.println("Made Send");
        } catch (IOException e) {
            e.printStackTrace();
        } //Sending the JSON
        try(BufferedReader br = new BufferedReader( new InputStreamReader(con.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
                System.out.println(responseLine);
                System.out.println(response);
            }
            System.out.println(response.toString());
            System.out.println("Got REPLY");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }

}
