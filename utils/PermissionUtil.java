package com.example.userlistexample;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Permission Util Screen
 * Description : This file have android device permission check and request.
 */

public class PermissionUtil {
    public static void requestPermission(Context context, String permission, int requestCode) {
        ActivityCompat.requestPermissions((AppCompatActivity) context, new String[] { permission }, requestCode);
    }

    public static boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, permission);
    }
}
