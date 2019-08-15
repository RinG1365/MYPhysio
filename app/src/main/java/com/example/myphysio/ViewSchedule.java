package com.example.myphysio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.CustomAdapterHRList;
import model.CustomAdapterPhyList;
import model.HealthRecord;
import model.Physio;
import model.Profile;

public class ViewSchedule extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String strURL = "http://192.168.0.159:8888/myPhysioWebService/phyService.php";
    Profile myProfile;

    RecyclerView recyListPhy;
    ArrayList<Physio> physioList;
    Physio physio;
    CustomAdapterPhyList customAdapterPhyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        sharedPreferences = getApplicationContext().getSharedPreferences("MYPhysio",0);
        editor = sharedPreferences.edit();

        physio = new Physio();
        physioList = new ArrayList<>();


        recyListPhy = findViewById(R.id.recyListPhy);

        /* Get Phy */
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        try {

                            JSONArray jsnArr = new JSONArray(response);

                            for(int i=1;i<jsnArr.length();i++)
                            {
                                JSONObject jObj = jsnArr.getJSONObject(i);
                                physio = new Physio();
                                physio.setStaffid(jObj.optString("STAFFID"));
                                physio.setPhyName(jObj.optString("PHY_NAME"));
                                physio.setPhyAge(jObj.optString("AGE"));
                                physio.setPhyGender(jObj.optString("GENDER"));
                                physio.setPhyHos(jObj.optString("HOSPITAL"));
                                physio.setPhyPos(jObj.optString("POSITION"));
                                physio.setPhyRate(jObj.optInt("RATING"));
                                physioList.add(physio);

                            }


                            customAdapterPhyList = new CustomAdapterPhyList(physioList);
                            recyListPhy.setLayoutManager(new LinearLayoutManager(ViewSchedule.this));
                            recyListPhy.setAdapter(customAdapterPhyList);
                            customAdapterPhyList.notifyDataSetChanged();


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
                params.put("selectFn","fnSelectPhy");

                return params;
            }
        };
        requestQueue.add(stringRequest);
        /*END Get Phy */


        /**
         * RecyclerView: Implementing single item click and long press (Part-II)
         * */
        recyListPhy.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyListPhy, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well


                Physio physio = physioList.get(position);

                String phyID = physio.getStaffid();
                String phyName = physio.getPhyName();


               Intent intent = new Intent(ViewSchedule.this, SearchSchedule.class);
                intent.putExtra("phyID", phyID);
                intent.putExtra("phyName", phyName);
                startActivity(intent);



            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(ViewSchedule.this, "Long press on position :" + position,
                        Toast.LENGTH_LONG).show();
            }

        }) {
        });

    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an Interface for single tap and long press
     * - Parameters are its respective view and its position
     * */

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));

            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public void fnBackToApp(View view)
    {
        Intent intent = new Intent(this, AppointmentActivity.class);
        startActivity(intent);
    }

}
