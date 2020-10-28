package co.id.gmedia.octavian.mapstelkommarker.server;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;


public class Server {

    //Header Request
    public final static Map<String, String> HEADER_AUTH = new HashMap<String, String>(){{
            put("Auth-Key", "gmedia_kartikars");
            put("Client-Service", "front_end_client");
        }
    };

    //Token heaader dengan enkripsi
    public static Map<String, String> getTokenHeader(Context context){
        Map<String, String> header = new HashMap<>();
        header.put("Auth-Key", "gmedia_kartikars");
        header.put("Client-Service", "front_end_client");
        //header.put("User-id", AppSharedPreferences.getUid(context));
        return header;
    }

   //Customer
   public static String Idcus = "";
   public static String flag_promo = "flag_promo";

   //Key
   public static final String EXTRA_DAERAH = "daerah";

   //type OTP
    public static final String Register = "register";
    public static final String ResetPass = "reset_pass";
    public static final String ResetPin = "reset_pin";


    public static final String TAG = "";

    //MERCHANT LAYOUT TYPE
    public static final int LAYOUT_TERDEKAT = 123;
    public static final int LAYOUT_POPULER = 124;
    public static final int LAYOUT_KATEGORI = 125;

    //EXTRA

    public static final String Login_ok = "succsses";


    public static final String ID = "SITEID";
    public static final String NAMA = "SITENAME";
    public static final String LONGG = "LONGITUDE";
    public static final String LATT = "LATITUDE";
    public static final String RAD = "RADIUS";
    public static final String KAB = "KABUPATEN";


    public static final String EXTRA_NILAI_PIUTANG = "nilaipiutang";


    //URL
    //private static final String baseURL = "https://skripsikucuk.000webhostapp.com/telkomsel/marker/";
    private static final String baseURL = "https://skripsikuku.000webhostapp.com/telkomsel/";
    //private static final String baseURL_LOKAL = "http://192.168.30.1/telkomsel/";
    //private static final String baseURL = "https://kartikaelectric.com/kartikars/api/reseller/";

    public static final String URL_LOGIN = baseURL + "login.php";
    public static final String URL_DATA = baseURL + "data.php";
    public static final String URL_DATA_SEARCH = baseURL + "cari_data_detail.php";


 // http://gmedia.bz/kartika/api/reseller/product/
 // filter_product?start=0&limit=12&keyword=eter&sort_by=terlaris&category=4&merk=2002&stock_status=null
 //inimas Fan yg bawah


    /*public static String getPathfromDrawable(int res_int){
        return Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/" + res_int).toString();*/

}
