package com.teachertipsforparents.bullybx.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;


public class HomeActivity extends ActionBarActivity {

    private final static String TAG = HomeActivity.class.getSimpleName();
    private Button reportBullyingButton , viewYourReportsButton , stateBullyingLawButton , manageSettingsButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reportBullyingButton = (Button) findViewById(R.id.reportBullying);
        viewYourReportsButton = (Button) findViewById(R.id.viewReports);
        stateBullyingLawButton = (Button) findViewById(R.id.stateBullyingLaw);
        manageSettingsButton = (Button) findViewById(R.id.manageSettings);


        SharedPreference sharedPreference = new SharedPreference();

        int id = sharedPreference.getSchoolId(HomeActivity.this);
     //   Log.v(TAG , id + "");

        reportBullyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , ReportBullyingActivity.class);
                startActivity(intent);
            }
        });

        manageSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , SettingsActivity.class);
                startActivity(intent);
            }
        });

        viewYourReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , ViewYourReportsActivity.class);
                startActivity(intent);
            }
        });

        stateBullyingLawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stopbullying.gov/laws/"));
                startActivity(browserIntent);
            }
        });




    }


}
