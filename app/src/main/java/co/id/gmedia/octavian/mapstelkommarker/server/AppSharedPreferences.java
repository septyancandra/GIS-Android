package co.id.gmedia.octavian.mapstelkommarker.server;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {
    private static final String LOGIN_PREF = "login";
    private static final String STATUS_PREF = "user_status";
    private static final String FCM_PREF = "fcm_id";
    private static final String TOKEN_PREF = "token";
    private static final String UID_PREF = "id";
    private static final String EMAIL_PREF = "email";
    private static final String REGISTER_PREF = "registered";
    private static final String NILAI_PIUTANG = "piutang";
    private static final String NAMA = "firtsname";

    private static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getNama (Context context){
        return getPreferences(context).getString(NAMA, "");
    }

    public static String getFcmId(Context context){
        return getPreferences(context).getString(FCM_PREF, "");
    }

    public static String getUid(Context context){
        return getPreferences(context).getString(UID_PREF, "");
    }

    public static String getToken(Context context){
        return getPreferences(context).getString(TOKEN_PREF, "");
    }

    public static void setFcmId(Context context, String fcm){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(FCM_PREF, fcm);
        editor.apply();
    }

    public static void setRegister(Context context, boolean registered){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, true);
        editor.putBoolean(REGISTER_PREF, registered);
        editor.apply();
    }

    public static boolean getRegistered(Context context) {
        return getPreferences(context).getBoolean(REGISTER_PREF, false);
    }

    public static String getEmail(Context context) {
        return getPreferences(context).getString(EMAIL_PREF, "");
    }

    public static boolean isLoggedIn(Context context){
        return getPreferences(context).getBoolean(LOGIN_PREF, false);
    }

    public static void Login(Context context, String uid, String nama, String email){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, true);
        editor.putString(UID_PREF, uid);
        editor.putString(NAMA, nama);
        editor.putString(EMAIL_PREF, email);
        editor.apply();
    }

    public static void Logout(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, false);
        editor.putString(UID_PREF, "");
        editor.putString(TOKEN_PREF, "");
        editor.putString(EMAIL_PREF, "");
        editor.apply();
    }

    public static void setStatusPref(Context context, boolean penjual){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(STATUS_PREF, penjual);
        editor.apply();
    }
}
