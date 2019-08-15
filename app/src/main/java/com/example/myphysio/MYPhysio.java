package com.example.myphysio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MYPhysio extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String icno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myphysio);
        sharedPreferences = getApplicationContext().getSharedPreferences("MYPhysio",0);
        editor = sharedPreferences.edit();

        icno = sharedPreferences.getString("username","");

        if(!(icno.equalsIgnoreCase("")))
        {
            Intent intent = new Intent(MYPhysio.this, MainMenu.class);
            startActivity(intent);
        }

    }

    public void fnGoToReg(View view)
    {
        Intent intent = null;
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void fnGoToLogin(View view)
    {
        Intent intent = null;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
