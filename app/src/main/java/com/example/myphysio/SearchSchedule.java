package com.example.myphysio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import model.Physio;

public class SearchSchedule extends AppCompatActivity implements View.OnClickListener{

    Physio physio;
    EditText edtTxtSDate;
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_scedule);

        physio = new Physio();
        Intent intent = getIntent();
        physio.setStaffid(intent.getStringExtra("phyID"));
        physio.setPhyName(intent.getStringExtra("phyName"));

        edtTxtSDate = findViewById(R.id.edtTxtSDate);
        edtTxtSDate.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == edtTxtSDate)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                        {
                            edtTxtSDate.setText(year + "-" + (month+1)+"-"+dayOfMonth);
                        }
                    },mYear,mMonth,mDay);

            datePickerDialog.show();;
        }
    }

    public void fnGoToSearchResult(View view)
    {
        Intent intent = new Intent(this, SearchResult.class);
        intent.putExtra("phyID",physio.getStaffid());
        intent.putExtra("phyName",physio.getPhyName());
        intent.putExtra("searchDate",edtTxtSDate.getText().toString());
        startActivity(intent);
    }

    public void fnGoToViewSch(View view)
    {
        Intent intent = new Intent(this, ViewSchedule.class);
        startActivity(intent);
    }
}