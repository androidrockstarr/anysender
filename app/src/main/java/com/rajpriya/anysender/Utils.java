package com.rajpriya.anysender;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by rajkumar on 4/24/14.
 */
public class Utils {
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static void showActionDialog(final Context context, final String packageName, final InstalledAppsFragment caller) {
        final AlertDialog levelDialog;
        // Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = { " Send this App(apk) ", " Launch  ", " View in Google Play ", " View Details ",};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Action");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 1:
                        // Your code when first option seletced
                        Utils.launchApp(context, packageName);
                        break;
                    case 3:
                        // Your code when 2nd  option seletced
                        Utils.launchAppDetails(context, packageName);
                        break;
                    case 0:
                        // Your code when 4th  option seletced
                        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (btAdapter == null) {
                            // Device does not support Bluetooth
                            // Inform user that we're done.
                            Toast.makeText(context, "Your device does not support Bluetooth!", Toast.LENGTH_LONG).show();
                            break;
                        }
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("application/zip");

                        //list of apps that can handle our intent
                        PackageManager pm = context.getPackageManager();
                        List<ResolveInfo> appsList = pm.queryIntentActivities( intent, 0);


                        ApplicationInfo app = null;
                        try {
                            app = (context.getPackageManager()).getApplicationInfo(packageName, 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (app != null ) {
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(app.publicSourceDir)) );
                            context.startActivity(intent);
                        }
                        break;
                    case 2:
                        final String appPackageName = packageName; // getPackageName() from Context or Activity object
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;

                }
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public static void launchAppDetails(final Context context, final String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        context.startActivity(intent);

    }


    public static void launchApp(final Context context, final String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        }

    }
}
