package com.teachertipsforparents.bullybx.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;

public class SettingsActivity extends ActionBarActivity {

    private TextView schoolIdTextView;
    private Button moreAboutTheBullyBox;
    private Button signUpForTheBullyBox;
    private Button deleteAllSavedReports;
    private boolean ifTheyHaveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        schoolIdTextView = (TextView) findViewById(R.id.schoolsCodeText);
        moreAboutTheBullyBox = (Button) findViewById(R.id.moreAboutTheBullyBoxButton);
        signUpForTheBullyBox = (Button) findViewById(R.id.signUpForTheBullyBoxButton);
        deleteAllSavedReports = (Button) findViewById(R.id.deletePastSavedReportsButton);


        final SharedPreference sharedPreference = new SharedPreference();
        ifTheyHaveId =  sharedPreference.getIfTheyHaveCode(SettingsActivity.this);

        // here is the new code

            String code = sharedPreference.getSchoolCode(SettingsActivity.this);
            schoolIdTextView.setText(code + "");


        moreAboutTheBullyBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bullyboxreport.com/about/"));
                startActivity(browserIntent);
            }
        });

        signUpForTheBullyBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dashboard.bullyboxreport.com/"));
                startActivity(browserIntent);
            }
        });

        deleteAllSavedReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.removeReports(SettingsActivity.this);
                Toast.makeText(SettingsActivity.this , "Your reports have been removed"  ,Toast.LENGTH_LONG).show();
            }
        });

    }


}
