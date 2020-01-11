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
import java.text.DecimalFormat;


public class SendJson extends AsyncTask<String, Void, String>{
    private Double responseResult =0.0;
    public String fromm = "";
    public String text = "";
    public Double responseResultt = 0.0;
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
            url = new URL("http://165.22.105.12/predict");
            System.out.println("Made" + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
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
        String str = jsonParam.toString();
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
            String res="";
            while ((responseLine = br.readLine()) != null) {
                res+=responseLine;
            }
            response.append(res.trim());
            System.out.println(res);
            //String predictArr[] = responseLine.split(":");

            responseResult= Double.parseDouble(res);

            System.out.println(responseResult);
            System.out.println(from);

                //responseResult = (float)parsefloat(responseLine); // Make use of autoboxing.  It's also easier to read.

            //Double.valueOf(responseLine);
            String txt="";
            if(responseResult>=60.0)
                txt="High Risk";
            else if(responseResult>=40.0)
                txt="Medium Risk";
            else if(responseResult>=20.0)
                txt="Low Risk";
//            MainActivity main1= new MainActivity();
//            main1.checkScamMessage(txt, from, responseResult);
            this.responseResultt = responseResult;
            this.fromm = from;
            this.text = txt;


            System.out.println(response.toString());
            System.out.println("Got REPLy");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }

    protected void onPostExecute(Boolean success) {
        if(success){
            //update layout
        } else {
            //show error
        }


    }
}