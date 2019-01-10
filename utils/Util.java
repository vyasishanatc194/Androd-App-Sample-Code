package com.example.userlistexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mysafetynet.R;
import com.mysafetynet.activities.ChildAprovalActivity;
import com.mysafetynet.activities.ParentNotificationActivity;
import com.mysafetynet.network.ApiConstants;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Utils Screen
 * Description : This file have all common util and validation.
 */
public class Util {
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_DDMMYYYY = "dd/MM/yyyy";
    public static final String FORMAT_DDMMYYYY_API = "dd-MM-yyyy";
    public static final String FORMAT_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss a";
    private static final String TAG = Util.class.getSimpleName();

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    public static String convertFormat(String dateStr, String currentFormat, String displayFormat) {

        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return postFormater.format(dateObj);
    }

    public static String convertFormat(Date dateStr, String currentFormat, String displayFormat) {
        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        return postFormater.format(dateStr);
    }

    public static String displayFormat(Date dateStr, String displayFormat) {
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        return postFormater.format(dateStr);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                // noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();
    }

    public static boolean isValidCardMonth(String month) {
        int monthNum = Integer.parseInt(month);
        return monthNum >= 1 && monthNum <= 12;
    }

    public static boolean isValidCardYear(String month) {
        return month.length() >= 2;
    }

    public static boolean isValidCardCvv(String cvv) {

        return cvv.length() == 3;
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        // String pattern =
        // "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
        return password.length() >= 6;
    }

    public static String convert(Map<String, String> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }
}
