package com.example.myphysio;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.CustomAdapterPhyList;
import model.Physio;

public class SearchResult extends AppCompatActivity {

    Physio physio;
    ArrayList<Physio> physioList;
    String searchDate;
    TextView txtSearchDate,txtTargetPhy,Slot1,Slot2,Slot3,Slot4,Slot5,Slot7,Slot8,Slot9;
    Date d1,d2,d3,d4,d5,d6,d7,d8,d9,d10;
    String strURL = "http://192.168.0.159:8888/myPhysioWebService/phyService.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        physio = new Physio();
        physioList = new ArrayList<>();
        Intent intent = getIntent();
        physio.setStaffid(intent.getStringExtra("phyID"));
        physio.setPhyName(intent.getStringExtra("phyName"));
        searchDate = intent.getStringExtra("searchDate");

        txtSearchDate = findViewById(R.id.txtSearchDate);
        txtTargetPhy = findViewById(R.id.txtTargetPhy);
        Slot1 = findViewById(R.id.Slot1);
        Slot2 = findViewById(R.id.Slot2);
        Slot3 = findViewById(R.id.Slot3);
        Slot4 = findViewById(R.id.Slot4);
        Slot5 = findViewById(R.id.Slot5);
        Slot7 = findViewById(R.id.Slot7);
        Slot8 = findViewById(R.id.Slot8);
        Slot9 = findViewById(R.id.Slot9);

        String s1 = "08:00:00";
        String s2 = "09:00:00";
        String s3 = "10:00:00";
        String s4 = "11:00:00";
        String s5 = "12:00:00";
        String s6 = "13:00:00";
        String s7 = "14:00:00";
        String s8 = "15:00:00";
        String s9 = "16:00:00";
        String s10 = "17:00:00";

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            d1 = dateFormat.parse(s1);
             d2 = dateFormat.parse(s2);
             d3 = dateFormat.parse(s3);
             d4 = dateFormat.parse(s4);
             d5 = dateFormat.parse(s5);
             d6 = dateFormat.parse(s6);
             d7 = dateFormat.parse(s7);
             d8 = dateFormat.parse(s8);
             d9 = dateFormat.parse(s9);
            d10 = dateFormat.parse(s10);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtSearchDate.setText("Date: "+searchDate);
        txtTargetPhy.setText(physio.getStaffid() + " - " + physio.getPhyName());

        /* Get Btime */
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        try {

                            JSONArray jsnArr = new JSONArray(response);

                            for(int i=0;i<jsnArr.length();i++)
                            {
                                JSONObject jObj = jsnArr.getJSONObject(i);
                                physio = new Physio();
                                physio.setBtime(jObj.optString("BOOKING_TIME"));

                                String strTime = physio.getBtime();
                                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date date = dateFormat.parse(strTime);



                                if(date.after(d1) && date.before(d2))
                                {
                                    Slot1.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                 if(date.after(d2) && date.before(d3))
                                {
                                    Slot2.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                if(date.after(d3) && date.before(d4))
                                {
                                    Slot3.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                 if(date.after(d4) && date.before(d5))
                                {
                                    Slot4.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                               if(date.after(d5) && date.before(d6))
                                {
                                    Slot5.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                 if(date.after(d7) && date.before(d8))
                                {
                                    Slot7.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                 if(date.after(d8) && date.before(d9))
                                {
                                    Slot8.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }

                                if(date.after(d9) && date.before(d10))
                                {
                                    Slot9.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                }
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
                params.put("selectFn","fnSearchSch");
                params.put("phyID",physio.getStaffid());
                params.put("searchDate",searchDate);

                return params;
            }
        };
        requestQueue.add(stringRequest);
        /*END Get Btime */



    }

    public void fnBackToSearch(View view)
    {
        Intent intent = new Intent(this, SearchSchedule.class);
        intent.putExtra("phyID",physio.getStaffid());
        intent.putExtra("phyName",physio.getPhyName());
        startActivity(intent);
    }

    public void fnGoToBook(View view)
    {
        Intent intent = new Intent(this, AddAppointment.class);
        startActivity(intent);
    }
}
