package com.teachertipsforparents.bullybx.customclasses;

import android.graphics.Bitmap;

/**
 * Created by christinajackey on 8/2/15.
 */
public class Report {


    private String victimName;
    private String bullyName;
    private String location;
    private String dateAndTime;
    private String witnesses;
    private String moreInfo;

    public String getImageEncodedString() {
        return imageEncodedString;
    }

    public void setImageEncodedString(String imageEncodedString) {
        this.imageEncodedString = imageEncodedString;
    }

    private String imageEncodedString;

    public Bitmap getImage() {

        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;


    }

    private Bitmap image;


    public Report() {

    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getBullyName() {
        return bullyName;
    }

    public void setBullyName(String bullyName) {
        this.bullyName = bullyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(String witnesses) {
        this.witnesses = witnesses;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }


}
