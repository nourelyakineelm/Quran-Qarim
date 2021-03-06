package com.nourayn.quran.Utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;

import com.nourayn.quran.Application.QuranApplication;

/**
 * Class to check some application settings
 */
public class Settings {

    /**
     * Function to check service running of not
     *
     * @param context      Application context
     * @param serviceClass Service class
     * @return Service running or not
     */
    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to check mobile connected to internet or not and connection type
     *
     * @param context Application context
     * @return Connection type
     */
    public static int checkInternetStatus(Context context) {

        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting()) {
            return 1;
        } else if (mobile.isConnectedOrConnecting()) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Function to convert english numbers to arabic
     * if mobile language arabic
     *
     * @param number Number to convert
     * @return Converted number
     */
    public static String ChangeNumbers(Context context, String number) {

        try {
            if (context.getResources().getConfiguration().locale.getDisplayLanguage().equals("??????????????"))
                return number.replaceAll("0", "??").replaceAll("1", "??")
                        .replaceAll("2", "??").replaceAll("3", "??")
                        .replaceAll("4", "??").replaceAll("5", "??")
                        .replaceAll("6", "??").replaceAll("7", "??")
                        .replaceAll("8", "??").replaceAll("9", "??");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * Function convert from dp to pixle
     *
     * @param dp DP value
     * @return Px vale
     */
    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = QuranApplication.getInstance().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
