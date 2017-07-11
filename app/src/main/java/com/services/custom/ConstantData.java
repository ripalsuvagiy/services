package com.services.custom;

/**
 * Created by JAYU on 30-Jun-16.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class ConstantData {

    static String id = "";
    static String data = "";
   public static   int mYear;
    public static   int mMonth;
    public static int mDay;

    public static Handler handler = new Handler();
    public static Timer t = new Timer();

    public static void showAlertDialog(final Context context, final String message) {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    public static String emailValidation(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {
            return "true";
        }
        else
        {
            return "false";
        }
    }
    public static String getString(Context context, final String key, final String value) {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String data = sh.getString(key, value);
        return data;
    }
    public static void SharedPrefClear(final Context context) {
        SharedPreferences.Editor sha = PreferenceManager.getDefaultSharedPreferences(context).edit();
        sha.clear();
        sha.apply();
        sha.commit();
    }
    public static String getDateFormat(String input, String output, String mydate) {
        SimpleDateFormat srcDf = new SimpleDateFormat(input);

        try {
            Date date = srcDf.parse(mydate);
            SimpleDateFormat destDf = new SimpleDateFormat(output);
            mydate = destDf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mydate;
    }
    public static void setString(Context context, final String key, final String value) {
        SharedPreferences.Editor sha = PreferenceManager.getDefaultSharedPreferences(context).edit();
        sha.putString(key, value);
        sha.commit();
    }
    public static void generateDatePicker(Context context,final TextView edtDate){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        edtDate.setText(year+"-"+ConstantData.getDateFormat("M", "MM", String.valueOf((monthOfYear + 1)))+ "-"+ConstantData.getDateFormat("d", "dd", String.valueOf(dayOfMonth)));

//                        final String change_date = edtDate.getText().toString();




                    }
                }, mYear, mMonth, mDay);
        dpd.show();


    }

//    public  static String getDateFormat(String input, String output, String mydate){
//        SimpleDateFormat sdf=new SimpleDateFormat(input);
//
//        try{
//            Date date=sdf.parse(mydate);
//            SimpleDateFormat destDf= new SimpleDateFormat(output);
//            mydate=destDf.format(date);
//
//        }catch (Exception e){
//         Log.v("dateExceptionInCD",""+e.toString());
//        }


//        return mydate;
//    }
}
