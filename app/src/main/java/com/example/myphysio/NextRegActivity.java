package com.example.myphysio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.Profile;

public class NextRegActivity extends AppCompatActivity {

    Profile profile;
    EditText edtRPass, edtRRptPass;
    String strURL = "http://192.168.0.159:8888/myPhysioWebService/registerService.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_reg);

        Intent intent = getIntent();
        profile = (Profile) intent.getSerializableExtra("regProfile");

        edtRPass = findViewById(R.id.edtRPass);
        edtRRptPass = findViewById(R.id.edtRRptPass);




    }

    public void fnRegPat(View view)
    {
        String var1 = edtRPass.getText().toString();
        String var2 = edtRRptPass.getText().toString();




        if(var1.equalsIgnoreCase("")||var2.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(),"Please fill in the blank",Toast.LENGTH_SHORT).show();

        }
        else
            {
                if(var2.equalsIgnoreCase(var1))
                {
                    profile.setPassword(var2);

                    /* RegUser */
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response)
                                {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        String result = jsonObject.optString("respond");

                                        if(result.equalsIgnoreCase("1"))
                                        {


                                            Intent intent = new Intent(NextRegActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_SHORT).show();

                                        }
                                        else if(result.equalsIgnoreCase("0"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Fail to Register.Please Try Again",Toast.LENGTH_SHORT).show();
                                        }





                                    } catch (Exception ee) {
                                        Toast.makeText(getApplicationContext(),ee.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }



                            },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("selectFn","fnRegPat");
                            params.put("icno",profile.getIcno());
                            params.put("patName",profile.getPatName());
                            params.put("age",profile.getAge());
                            params.put("gender",profile.getGender());
                            params.put("occupation",profile.getOcc());
                            params.put("address",profile.getAddress());
                            params.put("email",profile.getEmail());
                            params.put("phoneNo",profile.getPhoneNo());
                            params.put("password",profile.getPassword());

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                    /*END RegUser */

                }
                else
                    {
                    Toast.makeText(getApplicationContext(),"The password is not match",Toast.LENGTH_SHORT).show();

                }

            }


    }

    public void fnCancelReg(View view)
    {
        Intent intent = new Intent(this,MYPhysio.class);
        startActivity(intent);
    }
}
