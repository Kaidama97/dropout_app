package com.example.dropout_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class checkManifest extends AppCompatActivity{
    public checkManifest()
    {
    }

    public void checkStatus(){
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
}
