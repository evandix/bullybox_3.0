package com.teachertipsforparents.bullybx.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.customclasses.Report;
import com.teachertipsforparents.bullybx.sharedpreference.SharedPreference;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import javax.net.ssl.HttpsURLConnection;


public class ReportBullyingActivity extends ActionBarActivity {

    private EditText nameOfVictim, nameOfBully, location, dateAndTime, wittnesses, moreInfo;
    private ImageButton attatchImage;
    private ImageView imageView;
    private Button reportButton;
    private boolean isThereAnImageTaken = false;
    private boolean isThereAnImageSelected = false;
    Bitmap image;
    Bitmap bm;
    private ProgressDialog progress;
    String encodedImage;
    private final String USER_AGENT = "Mozilla/5.0";
    private final static String TAG = ReportBullyingActivity.class.getSimpleName();
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private Report report = new Report();

    private final OkHttpClient client = new OkHttpClient();

    private SharedPreference sharedPreference;
    private boolean ifTheyHaveCode;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bullying);


        sharedPreference = new SharedPreference();

        nameOfBully = (EditText) findViewById(R.id.nameOfAllegedBullySelected);
        nameOfVictim = (EditText) findViewById(R.id.nameOfVictimSelected);
        location = (EditText) findViewById(R.id.locationEditText);
        dateAndTime = (EditText) findViewById(R.id.dateAndTimeEditText);
        wittnesses = (EditText) findViewById(R.id.wittnesesssSelected);
        moreInfo = (EditText) findViewById(R.id.moreInfoTextSected);

        attatchImage = (ImageButton) findViewById(R.id.attatchImageButton);
        imageView = (ImageView) findViewById(R.id.imageViewForSelected);
        reportButton = (Button) findViewById(R.id.reportBullyingButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ifTheyHaveCode = sharedPreference.getIfTheyHaveCode(ReportBullyingActivity.this);

                if (ifTheyHaveCode) {

                    String nameOfVictimString = nameOfVictim.getText().toString();
                    String nameOfBullyString = nameOfBully.getText().toString();
                    String locationString = location.getText().toString();
                    String dateAndTimeOfIncidentString = dateAndTime.getText().toString();
                    String witnessesString = wittnesses.getText().toString();
                    String descriptionString = moreInfo.getText().toString();
                     progress = new ProgressDialog(ReportBullyingActivity.this);

                    progress.setMessage("Sending your report...");
                    progress.show();
// To dismiss the dialog


                    report.setBullyName(nameOfBullyString);
                    report.setDateAndTime(dateAndTimeOfIncidentString);
                    report.setVictimName(nameOfVictimString);
                    report.setLocation(locationString);
                    report.setMoreInfo(descriptionString);
                    report.setWitnesses(witnessesString);





                    SharedPreference sharedPreference = new SharedPreference();



                    String schoolIdString = sharedPreference.getSchoolId(ReportBullyingActivity.this) + "";

                    if (isThereAnImageSelected) {
                        // bm

                   /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    */
                        bm = convertToMutable(bm);
                     //   bm.setHeight(300);
                     //   bm.setWidth(300);
                        encodedImage = encodeTobase64(bm);
                     report.setImage(bm);

                    } else if (isThereAnImageTaken) {
                        // image
                    /*
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    */

                        encodedImage = encodeTobase64(image);
                     report.setImage(image);
                    } else {
                        encodedImage = "";

                    }


                    String report = String.format("parentOrStudent=student&nameOfVictim=%s&nameOfBully=%s&location=" +
                                    "%s&when=%s&description=%s&school_id=" +
                                    "%s&witnesses=%s&temporary_image=%s",
                            nameOfVictimString,
                            nameOfBullyString,
                            locationString,
                            dateAndTimeOfIncidentString,

                            descriptionString,
                            schoolIdString,
                            witnessesString,
                            encodedImage);


                    // String message = encodeUTF(reportString);   // this is the string we send to the server
                    //   Charset.forName("UTF-8").encode(report);

                    new MyAsyncTask().execute(report);

                } else {
                    Toast.makeText(ReportBullyingActivity.this , "Sorry. You must have a school code to make a report." , Toast.LENGTH_LONG ).show();
                }


             }


        });


        attatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ReportBullyingActivity.this);
                builder1.setMessage("Please complete the action by...");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Choose from library",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                    //Gallery
                                    Intent galleryIntent = new Intent(
                                            Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    try {
                                        startActivityForResult(galleryIntent, 1);
                                    } catch (Exception e) {
                                    }

                            }
                        });
                builder1.setNegativeButton("Take photo",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 0);

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
           // Log.v(TAG  , resultCode + "");
            if (resultCode == -1  ) {
                image = (Bitmap) data.getExtras().get("data");
                // image = getResizedBitmap(image , 200 , 200);
                attatchImage.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                isThereAnImageTaken = true;

                imageView.setImageBitmap(image);
            }

        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            try {


                InputStream inputStream = ReportBullyingActivity.this.getContentResolver().openInputStream(data.getData());
                bm = BitmapFactory.decodeStream(inputStream);


                int downSample = 1;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                try {
                    BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()), null, opts);
                    if (opts.outWidth > 2048 || opts.outHeight > 2048) {
                        downSample = 4;
                    } else if (opts.outWidth > 1024 || opts.outHeight > 1024) {
                        downSample = 2;
                    }

                } catch (Exception e) {
                    downSample = 4;
                }

                opts.inJustDecodeBounds = false;
                opts.inSampleSize = downSample;
                bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()), null, opts);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            attatchImage.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            isThereAnImageSelected = true;

            imageView.setImageBitmap(bm);


        }



    }







    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        int responseCode = 200;


        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
              responseCode = sendPostTwo(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            // pb.setVisibility(View.GONE);

            progress.dismiss();


            if (responseCode == 200 ) {
               //save the report because it was successfull
                SharedPreference sharedPreference = new SharedPreference();
                sharedPreference.addReport(ReportBullyingActivity.this , report);


                AlertDialog.Builder builder1 = new AlertDialog.Builder(ReportBullyingActivity.this);
                builder1.setTitle("Sent Successfully");
                builder1.setMessage("Thank you for making a difference in your school");
                builder1.setCancelable(true);
                builder1.setPositiveButton("You're welcome!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent = new Intent(ReportBullyingActivity.this , HomeActivity.class);

                                startActivity(intent);

                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();

            } else if (responseCode == 422) {
                // failed to send
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(ReportBullyingActivity.this);
                builder1.setTitle("Could Not Send");
                builder1.setMessage("Your report could not be sent at this time. Please contact a trusted adult or try again later.");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Okay, I will.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else if (responseCode == 403) {
                // server denied
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(ReportBullyingActivity.this);
                builder1.setTitle("Report Not Sent");
                builder1.setMessage("Your report has been deemed invalid by our servers, and has not been sent.");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Okay.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }

        protected void onProgressUpdate(Integer... progress) {
            //  pb.setProgress(progress[0]);
        }
    }

/*
    public void postData(String valueIWantToSend) {
        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to
        HttpPost httppost = new HttpPost("https://the-bullybox.herokuapp.com/api/v1/reports/new");
        try {
            // create a list to store HTTP variables and their values
            List nameValuePairs = new ArrayList();
            // add an HTTP variable and value pair
            nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // send the variable and value, in other words post, to the URL
            HttpResponse response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            // process execption
        } catch (IOException e) {
            // process execption
        }


    }
    */



    // HTTP POST request
    private int sendPostTwo(String post) throws Exception {

        String url = "https://the-bullybox.herokuapp.com/api/v1/reports/new";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(post);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return responseCode;
    }
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // String imageEncoded = Base64.encodeToString(b ,Base64.DEFAULT);


        String imageEncoded = Base64.encodeToString(b ,Base64.DEFAULT);

       // Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }




    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    /**
     * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
     * more memory that there is already allocated.
     *
     * @param imgIn - Source image. It will be released, and should not be used more
     * @return a copy of imgIn, but muttable.
     */
    public static Bitmap convertToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes.
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Bitmap.Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available.
            imgIn = Bitmap.createBitmap(100, 100, type);
            map.position(0);
            //load it back from temporary
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgIn;
    }


}


