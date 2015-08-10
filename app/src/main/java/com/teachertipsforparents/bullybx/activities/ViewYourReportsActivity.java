package com.teachertipsforparents.bullybx.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.adapters.ReportAdapter;
import com.teachertipsforparents.bullybx.customclasses.Report;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;

import java.util.ArrayList;

public class ViewYourReportsActivity extends ListActivity  {

    Report[] reportArray;
    private SharedPreference mSharedPreference = new SharedPreference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_reports);

        ArrayList<Report> reportArrayList = mSharedPreference.getReports(ViewYourReportsActivity.this);
        if (reportArrayList == null ) {

        } else {
            reportArray = new Report[reportArrayList.size()];
            reportArray = reportArrayList.toArray(reportArray);

        /*
        String[] stockArr = new String[stockList.size()];
stockArr = stockList.toArray(stockArr);
         */


            ReportAdapter reportAdapter = new ReportAdapter(ViewYourReportsActivity.this, reportArray);
            setListAdapter(reportAdapter);

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        int positionOfClick = position;

       // Toast.makeText(ViewYourReportsActivity.this, positionOfClick + " ", Toast.LENGTH_LONG).show();


        Intent intent = new Intent(ViewYourReportsActivity.this , ViewSelectedReportActivity.class);
        intent.putExtra("reportInt" , positionOfClick);
        startActivity(intent);



    }
}
