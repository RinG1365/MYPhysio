package com.example.myphysio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import model.Profile;

public class RegisterActivity extends AppCompatActivity {

    Profile profile;
    EditText edtRIC,edtRName,edtROcc,edtREM,edtRAdd, edtRPhone,edtRAge;
    Spinner edtRGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profile = new Profile();

        edtRIC = findViewById(R.id.edtRIC);
        edtRName = findViewById(R.id.edtRName);
        edtROcc = findViewById(R.id.edtROcc);
        edtREM = findViewById(R.id.edtREM);
        edtRAdd = findViewById(R.id.edtRAdd);
        edtRPhone = findViewById(R.id.edtRPhone);
        edtRGender = findViewById(R.id.edtRGender);
        edtRAge = findViewById(R.id.edtRAge);
    }

    public void fnNextReg(View view)
    {
        String var1 = edtRIC.getText().toString();
        String var2 = edtRName.getText().toString();
        String var3 = edtROcc.getText().toString();
        String var4 = edtREM.getText().toString();
        String var5 = edtRAdd.getText().toString();
        String var6 = edtRPhone.getText().toString();
        String var7 = edtRAge.getText().toString();

        if(var1.equalsIgnoreCase("")||var2.equalsIgnoreCase("")||var3.equalsIgnoreCase("")||
        var4.equalsIgnoreCase("")||var5.equalsIgnoreCase("")||var6.equalsIgnoreCase("")||
        var7.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(),"Please fill in all the blank",Toast.LENGTH_SHORT).show();
        }
        else
            {
                profile.setIcno(edtRIC.getText().toString());
                profile.setPatName(edtRName.getText().toString());
                profile.setOcc(edtROcc.getText().toString());
                profile.setEmail(edtREM.getText().toString());
                profile.setAddress(edtRAdd.getText().toString());
                profile.setPhoneNo(edtRPhone.getText().toString());
                profile.setGender(edtRGender.getSelectedItem().toString());
                profile.setAge(edtRAge.getText().toString());


                Intent intent = new Intent(this,NextRegActivity.class);
                intent.putExtra("regProfile", profile);
                startActivity(intent);
        }

    }

    public void fnBackLogin(View view)
    {
        Intent intent = new Intent(this,MYPhysio.class);
        startActivity(intent);
    }
}
