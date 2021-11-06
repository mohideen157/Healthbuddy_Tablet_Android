package indg.com.cover2protect.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {

    public static final String userName = "userName";
    public static final String CREDENTIALS_STORE_PREF_FILE = "CREDENTIALS_STORE_PREF_FILE";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(AppPreference, Context.MODE_PRIVATE);
    }

    public static void setApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(AppToken, apiKey);
        editor.apply();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString(AppToken, "");
    }

    public static String getMemberId(Context context) {
        return getSharedPreferences(context).getString(userId, "");
    }

    public static void setFitBitMemberId(Context context, String FbitID) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FBID, FbitID);
        editor.apply();
    }
    public static void setFitBitToken(Context context, String FbitToken) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FBTOKEN, FbitToken);
        editor.apply();
    }

    public static String getFBID(Context context) {
        return getSharedPreferences(context).getString(FBID, "");
    }

    public static String getFBTOKEN (Context context) {
        return getSharedPreferences(context).getString(FBTOKEN, "");
    }


    public static String AppPreference = "AppPreference";
    public static String SkipLogin = "SkipLogin" ;
    public static String OtpVerify = "OtpVerify";
    public static String AppToken = "AppToken";
    public static String userId = "userId";
    public static String FBID = "FBID";
    public static String FBTOKEN = "FBTOKEN";

    public static String USER_AGE = "USER_AGE";
    public static String USER_HEIGHT = "USER_HEIGHT";
    public static String USER_TARGET = "USER_TARGET";
    public static String USER_DETAILS_SAVED = "USER_DETAILS_SAVED";
    public static String USER_SEX = "USER_SEX";
    public static String BMI_PAIRED = "BMI_PAIRED";
    public static String userMobileNumber = "userMobileNumber";
    public static String CHANNEL_ID = "CHANNEL_ID";
    public static int notificationId = 0;



}
