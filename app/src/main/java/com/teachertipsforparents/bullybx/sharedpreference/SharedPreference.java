package com.teachertipsforparents.bullybx.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.teachertipsforparents.bullybx.customclasses.Report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by christinajackey on 7/29/15.
 */
public class SharedPreference {


    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String SCHOOLID = "id of school";
    private static final String SCHOOLBOOL = "if they have school code bool";
    private static final String REPORTLIST = "report_list";
    private static final String SCHOOLCODE = "school_code";


    public SharedPreference() {
        super();
    }

    public void saveSchoolId(Context context, int id) {
        SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        int schoolId = id;
        editor.putInt(SCHOOLID, schoolId);
        editor.commit();
    }

    public void saveSchoolCode(Context context , String code) {
        SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        String scode = code;
        editor.putString(SCHOOLCODE, scode);
        editor.commit();

    }

    public String getSchoolCode(Context context) {
        SharedPreferences settings;
        String code = "Your school code...";

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SCHOOLCODE)) {
           String schoolCode = settings.getString(SCHOOLCODE , "Your school code...");
            code = schoolCode;

        }

        return code;
    }





    public int getSchoolId(Context context) {
        SharedPreferences settings;
        int id = 0;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SCHOOLID)) {
            int idFromPrefs = settings.getInt(SCHOOLID, 999999);
            id = idFromPrefs;

        }

        return id;

    }

    public void saveIfTheyHaveCode(Context context, boolean ifTheyHaveIt) {
        SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        boolean ifHave = ifTheyHaveIt;
        editor.putBoolean(SCHOOLBOOL, ifHave);
        editor.commit();
    }

    public boolean getIfTheyHaveCode(Context context) {
        SharedPreferences settings;
       boolean ifTheyHave = false;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SCHOOLBOOL)) {
            boolean ifTheyHaveSchoolCode = settings.getBoolean(SCHOOLBOOL, false);
           ifTheyHave = ifTheyHaveSchoolCode;
        }

        return ifTheyHave;



    }



    // This four methods are used for maintaining reports.
   public void saveReports(Context context, List<Report> reports) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonReports = gson.toJson(reports);

        editor.putString(REPORTLIST, jsonReports);

        editor.commit();
    }


    public void addReport(Context context, Report report) {
        List<Report> reportList = getReports(context);
        if (reportList == null)
            reportList = new ArrayList<Report>();
        reportList.add(report);
        saveReports(context, reportList);
    }
    // Person person

    public void removeReports(Context context) {
        ArrayList<Report> reports = getReports(context);
        if (reports != null) {
            // favorites.remove(person);
            reports.clear();
            saveReports(context, reports);
        }
    }

    public ArrayList<Report> getReports(Context context) {
        SharedPreferences settings;
        List<Report> reports;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(REPORTLIST)) {
            String jsonFavorites = settings.getString(REPORTLIST, null);
            Gson gson = new Gson();
            Report[] reportItems = gson.fromJson(jsonFavorites,
                    Report[].class);

            reports = Arrays.asList(reportItems);
            reports = new ArrayList<Report>(reports);
        } else
            return null;

        return (ArrayList<Report>) reports;
    }
    /*
    public class SharedPreference {

        public static final String PREFS_NAME = "PRODUCT_APP";
        public static final String LOVEDLIST = "List_Of_Loved";

    public static final String SELECTED_PERSON_SHARED = "selected_person";

        public SharedPreference() {
            super();
        }

        // This four methods are used for maintaining favorites.
        public void saveFavorites(Context context, List<Person> favorites) {
            SharedPreferences settings;
            SharedPreferences.Editor editor;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            editor = settings.edit();

            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);

            editor.putString(LOVEDLIST, jsonFavorites);

            editor.commit();
        }

        public void addFavorite(Context context, Person person) {
            List<Person> favorites = getFavorites(context);
            if (favorites == null)
                favorites = new ArrayList<Person>();
            favorites.add(person);
            saveFavorites(context, favorites);
        }
        // Person person

        public void removeFavorite(Context context, int i) {
            ArrayList<Person> favorites = getFavorites(context);
            if (favorites != null) {
                // favorites.remove(person);
                favorites.remove(i);
                saveFavorites(context, favorites);
            }
        }

        public ArrayList<Person> getFavorites(Context context) {
            SharedPreferences settings;
            List<Person> favorites;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);

            if (settings.contains(LOVEDLIST)) {
                String jsonFavorites = settings.getString(LOVEDLIST, null);
                Gson gson = new Gson();
                Person[] favoriteItems = gson.fromJson(jsonFavorites,
                        Person[].class);

                favorites = Arrays.asList(favoriteItems);
                favorites = new ArrayList<Person>(favorites);
            } else
                return null;

            return (ArrayList<Person>) favorites;
        }




}

     */


}