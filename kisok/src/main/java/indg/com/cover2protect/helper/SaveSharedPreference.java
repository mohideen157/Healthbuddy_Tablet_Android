package indg.com.cover2protect.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import indg.com.cover2protect.util.ConstKt;

import static indg.com.cover2protect.util.ConstKt.DEVICE2_STATUS;
import static indg.com.cover2protect.util.ConstKt.DEVICE_STATUS;
import static indg.com.cover2protect.util.ConstKt.FIRSTTIME;
import static indg.com.cover2protect.util.ConstKt.LOGGED_IN_PREF;
import static indg.com.cover2protect.util.ConstKt.MOBILE;
import static indg.com.cover2protect.util.ConstKt.NODEVICE;
import static indg.com.cover2protect.util.ConstKt.USER_ID;


public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static void setDeviceExistence(Context context, boolean existence) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(NODEVICE, existence);
        editor.apply();
    }


    public static void setDeviceStatus(Context context, boolean device) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(DEVICE2_STATUS, device);
        editor.apply();
    }

    public static void setFirstTime(Context context, boolean firsttime) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(FIRSTTIME, firsttime);
        editor.apply();
    }

    public static void setUserMobile(Context context, String mobile) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(MOBILE, mobile);
        editor.apply();
    }


    public static void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }


    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static boolean getDeviceExistence(Context context) {
        return getPreferences(context).getBoolean(NODEVICE, false);
    }
    public static boolean getDeviceStatus(Context context) {
        return getPreferences(context).getBoolean(DEVICE2_STATUS, false);
    }

    public static boolean getFirstTime(Context context) {
        return getPreferences(context).getBoolean(FIRSTTIME, true);
    }

    public static String getMobile(Context context) {
        return getPreferences(context).getString(MOBILE,"");
    }

    public static String getUserId(Context context) {
        return getPreferences(context).getString(USER_ID,"");
    }

}