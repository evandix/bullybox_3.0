package com.teachertipsforparents.bullybx.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.customclasses.SchoolId;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FirstUseActivity extends ActionBarActivity {

    private EditText schoolCodeEditText;
    private Button continueWithSchoolCodeButton , continueWithoutSchoolCodeButton , visitBullyBoxReportWeb ;
    private String baseUrl = "https://the-bullybox.herokuapp.com/api/v1/schools/password?password=";
    SharedPreference sharedPrefs;

    private final static String TAG = FirstUseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);

       SharedPreference sharedPreference = new SharedPreference();
       boolean ifTheyHaveAlreadySignedIn = sharedPreference.getIfTheyHaveCode(FirstUseActivity.this);
       if (ifTheyHaveAlreadySignedIn) {
            //
       int id = sharedPreference.getSchoolId(FirstUseActivity.this);
     // Toast.makeText(FirstUseActivity.this, "Your schools id is - " + id, Toast.LENGTH_LONG).show();
         Intent intent = new Intent(FirstUseActivity.this , HomeActivity.class);
       startActivity(intent);
      } else {

           schoolCodeEditText = (EditText) findViewById(R.id.schoolCodeEditText);
           continueWithoutSchoolCodeButton = (Button) findViewById(R.id.continueWithoutSchoolCode);
           continueWithSchoolCodeButton = (Button) findViewById(R.id.continueWithCodeButton);
           visitBullyBoxReportWeb = (Button) findViewById(R.id.visitWebsite);

           sharedPrefs = new SharedPreference();

           continueWithoutSchoolCodeButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(FirstUseActivity.this, HomeActivity.class);
                   sharedPrefs.saveIfTheyHaveCode(FirstUseActivity.this, false);
                   startActivity(intent);
               }
           });

           visitBullyBoxReportWeb.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (networkIsAvailable()) {
                       String url = "http://bullyboxreport.com/";
                       Intent i = new Intent(Intent.ACTION_VIEW);
                       i.setData(Uri.parse(url));
                       startActivity(i);
                   } else {
                       Context context;
                       AlertDialog.Builder builder1 = new AlertDialog.Builder(FirstUseActivity.this);
                       builder1.setMessage("No internet connection could be established.");
                       builder1.setCancelable(true);
                       builder1.setPositiveButton("Okay",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                   }
                               });

                       AlertDialog alert11 = builder1.create();
                       alert11.show();

                   }
               }
           });
       }


             continueWithSchoolCodeButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {


                     final String schoolCode = schoolCodeEditText.getText().toString();
                     if (schoolCode.length() > 0) {

                         String schoolCodeUrl = baseUrl + schoolCode;


                         if (networkIsAvailable()) {               // check to see is netwok is availibe before we get online


                             OkHttpClient client = new OkHttpClient();
                             final Request request = new Request.Builder()
                                     .url(schoolCodeUrl)
                                     .build();

                             Call call = client.newCall(request); // request wraped up in a call
                             call.enqueue(new Callback() {
                                 @Override
                                 public void onFailure(Request request, IOException e) {

                                 }

                                 @Override
                                 public void onResponse(Response response) throws IOException {
                                     try {
                                         String jsonData = response.body().string();
                                        // Log.v(TAG, jsonData);
                                         if (response.isSuccessful()) {
                                             SchoolId schoolId = parseForIdDetails(jsonData);
                                             Boolean status = schoolId.getStatus();

                                             if (status) {
                                                 // code is true now save the school id and take them to the main page

                                                 final int schoolIdInt = schoolId.getId();
                                                 sharedPrefs.saveSchoolId(FirstUseActivity.this, schoolIdInt);
                                                 sharedPrefs.saveSchoolCode(FirstUseActivity.this , schoolCode);
                                                 sharedPrefs.saveIfTheyHaveCode(FirstUseActivity.this, true);
                                                 runOnUiThread(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         // updateDisplay();

                                                       //  Log.v(TAG, schoolIdInt + "");

                                                         Intent intent = new Intent(FirstUseActivity.this, HomeActivity.class);
                                                         startActivity(intent);


                                                     }
                                                 });

                                             }

                                         } else {
                                             //   alertUserAboutError();

                                             runOnUiThread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     // updateDisplay();

                                                     Context context;
                                                     AlertDialog.Builder builder1 = new AlertDialog.Builder(FirstUseActivity.this);
                                                     builder1.setMessage("Sorry there was an error. Check the code and please try again.");
                                                     builder1.setCancelable(true);
                                                     builder1.setPositiveButton("Okay",
                                                             new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int id) {
                                                                     dialog.cancel();
                                                                 }
                                                             });

                                                     AlertDialog alert11 = builder1.create();
                                                     alert11.show();

                                                 }
                                             });


                                         }
                                     } catch (IOException e) {
                                       //  Log.e(TAG, "Exception caught", e);
                                     } catch (JSONException e) {
                                      //   Log.e(TAG, "Exception caught", e);
                                     }
                                 }
                             });
                         } else {
                             // alertUserAboutNoNetwork();
                             Context context;
                             AlertDialog.Builder builder1 = new AlertDialog.Builder(FirstUseActivity.this);
                             builder1.setMessage("No internet connection could be established.");
                             builder1.setCancelable(true);
                             builder1.setPositiveButton("Okay",
                                     new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int id) {
                                             dialog.cancel();
                                         }
                                     });

                             AlertDialog alert11 = builder1.create();
                             alert11.show();

                         }


                     }

                 }
             });
         }
  //  }

    private SchoolId parseForIdDetails(String jsonData) throws JSONException {

        SchoolId schoolId = new SchoolId();
        JSONObject schoolIdResult = new JSONObject(jsonData);
        Boolean status  = schoolIdResult.getBoolean("status");
        Integer id = schoolIdResult.getInt("school_id");


        schoolId.setId(id);
        schoolId.setStatus(status);

        return  schoolId;



    }
    /*
      private Hour[] getHourlyForecast(String jsonData) throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i = 0 ; i < data.length() ; i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timeZone);

            hours[i] = hour;

        }
        return hours;


     */


    private boolean networkIsAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();   // had to add permission to manifest
        boolean isAvailible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailible = true;
        }
        return  isAvailible;
    }




}
