package com.teachertipsforparents.bullybx.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.customclasses.Report;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;

import java.util.ArrayList;

public class ViewSelectedReportActivity extends ActionBarActivity {

    SharedPreference mSharedPreference = new SharedPreference();
   private TextView nameOfVictim, nameOfBully , wittnesses , dateAndTime , location , moreInfo;
    private ImageView imageView;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_report);

        Intent intent = getIntent();
        position = intent.getIntExtra("reportInt" , 1);

        imageView = (ImageView) findViewById(R.id.imageViewForSelected);
        nameOfBully = (TextView) findViewById(R.id.nameOfAllegedBullySelected);
        nameOfVictim = (TextView) findViewById(R.id.nameOfVictimSelected);
        wittnesses = (TextView) findViewById(R.id.wittnesesssSelected);
        dateAndTime = (TextView) findViewById(R.id.dateAndTimeSelected);
        location = (TextView) findViewById(R.id.locationSelected);
        moreInfo = (TextView) findViewById(R.id.moreInfoTextSected);

        ArrayList<Report> reportArrayList = mSharedPreference.getReports(ViewSelectedReportActivity.this);

       Report report = reportArrayList.get(position);

        imageView.setImageBitmap(report.getImage());

        nameOfBully.setText("Name of alleged bully: " + report.getBullyName());
        nameOfVictim.setText("Name of bully: " + report.getVictimName());
        wittnesses.setText("Witnesses: " + report.getWitnesses());
        dateAndTime.setText("Date and time: " + report.getDateAndTime());
        location.setText("Location: " + report.getLocation());
        moreInfo.setText("More info: " + report.getMoreInfo());





    }


}
