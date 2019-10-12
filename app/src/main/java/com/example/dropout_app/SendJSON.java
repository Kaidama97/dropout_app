package com.example.dropout_app;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class SendJSON extends AppCompatActivity {
    public SendJSON() {
    }

    public void sendJSON(String msg_from, String msg_body) {
        Map<String, String> jsonMap;
        jsonMap = new HashMap<>();
        jsonMap.put("text", msg_body);
        jsonMap.put("from", msg_from);




        /*RequestQueue requestQueue;
        final String saveData = data;
        String URL = "https://us-central1-dropouts-54029.cloudfunctions.net/widgets/";
        //System.out.println("Senddddddddddd");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return saveData == null ? null : saveData.getBytes("utf-8");
                }catch(UnsupportedEncodingException Uee)
                {
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);*/
    }
    public static HttpURLConnection makeRequest(String path, Map params) throws Exception
    {
        //instantiates httpclient to make request
        final HttpURLConnection httpclient = new HttpURLConnection(){
            @Override
            public void disconnect() {
                httpclient.disconnect();
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {

            }
        };

        //url with the post data
        HttpPost httpost = new HttpPost(path);

        //convert parameters into JSON object
        JSONObject holder = getJsonObjectFromMap(params);

        //passes the results to a string builder/entity
        StringEntity se = new StringEntity(holder.toString());

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        //Handles what is returned from the page
        ResponseHandler responseHandler = new BasicResponseHandler();
        return httpclient.execute(httpost, responseHandler);
    }

}
