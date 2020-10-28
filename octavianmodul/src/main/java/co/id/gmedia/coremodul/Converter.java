package co.id.gmedia.coremodul;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Converter {

    private static SimpleDateFormat dt_format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static SimpleDateFormat dts_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat t_format = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat d_format_inverse = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    public static int getInteger(String s){
        if(s.equals("")){
            return 0;
        }
        else{
            return Integer.parseInt(s);
        }
    }

    public static String encodeURL(String s){
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String doubleToRupiah(double value){
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        return "Rp " + rupiahFormat.format(Double.parseDouble(String.valueOf(value)));
    }

    public static String getURLForResource (String package_name, int resourceId) {
        return Uri.parse("android.resource://"+ package_name + "/" +resourceId).toString();
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap resizeBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Date stringDTToDate(String date){
        try {
            return dt_format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date stringDTSToDate(String date){
        try {
            return dts_format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date stringDToDate(String date){
        try {
            return d_format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String DTToString(int year, int month, int date, int hour, int minute, int second){
        return String.format(Locale.getDefault(), "%4d-%02d-%02d %02d:%02d:%02d",
                year, month, date, hour, minute, second);
    }

    public static String DToString(Date date){
        return d_format.format(date);
    }

    public static String TToString(Date date){
        return t_format.format(date);
    }

    public static String DToStringInverse(Date date){
        return d_format_inverse.format(date);
    }

    public static String DToString(int year, int month, int date){
        return String.format(Locale.getDefault(), "%4d-%02d-%02d",
                year, month, date);
    }

    public static SpannableStringBuilder setSelectionBold(String fulltext, List<String> textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(fulltext);

        for (String textItem : textToBold) {
            if (textItem.length() > 0 && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = fulltext.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                }
            }
        }

        return builder;
    }

    public static String sha256(String message, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            return Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        Log.w("custom_log", "Return string kosong");
        return "";
    }
}

