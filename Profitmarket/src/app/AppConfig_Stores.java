package app;

public class AppConfig_Stores {
    // Server user login url
    public static String URL_LOGIN = "http://192.168.0.111/android_login_stores/";

    // Server user register url
    public static String URL_REGISTER = "http://192.168.0.111/android_login_stores/";
    
    // use to S_Tradedetail
    public static String url_create_tdaddcoupon = "http://192.168.0.111/addQpon/add_coupon.php";
    public static String URL_GET_TDCOUPON = "http://192.168.0.111/addQpon/getqpontot.php";
    public static String URL_create_ctrrecords = "http://192.168.0.111/trade_details/memberrecord.php";

    // use to S_Coupon_Management
    public static String url_get_scmallcoupon = "http://192.168.0.111/couponconnect/getallcoupon.php";
    
    // use to S_Coupon_Rules
    public static String url_get_crcoupon = "http://192.168.0.111/couponconnect/spinner.php";
    
    // use to S_Coupon_Buy
    public static String url_create_buycoupon = "http://192.168.0.111/couponconnect/create.php";
    
}