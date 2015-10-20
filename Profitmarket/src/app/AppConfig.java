package app;

public class AppConfig {
	
    // Server user login url
    public static String URL_LOGIN = "http://192.168.0.104/android_login_api/";
    
    // Server user register url
    public static String URL_REGISTER = "http://192.168.0.104/android_login_api/";

    //use to C_discount_use
    public static String url_get_qponmessage = "http://192.168.0.104/addQpon/getcoupon.php";
    
    //use to CreatFragment
    //use to HelpFragment
    //use to ReadFragment
    public static String url_all_products = "http://192.168.0.104/android_connect2/get_all_products.php";
    
    //use to C_record
    public static String url_get_memrecord = "http://192.168.0.104/trade_details/getrecords.php";
    
    //use to C_recorditem
    public static String url_get_memrecorditem = "http://192.168.0.104/trade_details/getrecorditem.php";

    //use to C_mod_inform
    public static final String url_mem_inform = "http://192.168.0.104/memberdetail/updatememberdetail.php";
}