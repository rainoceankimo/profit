package app;

public class AppConfig_Stores {
	
    // Server user login url
    public static String URL_LOGIN = "http://chaoiok.net78.net/android_login_stores/";

    // Server user register url
    public static String URL_REGISTER = "http://chaoiok.net78.net/android_login_stores/";
    
    // use to S_Stores_Center_Amend
    public static String url_update_store = "http://192.168.0.102/storedetail/updatestoresdetail.php";

    // use to S_Tradedetail
    public static String url_create_tdaddcoupon = "http://192.168.0.102/addQpon/add_coupon.php";
    public static String URL_GET_TDCOUPON = "http://192.168.0.102/addQpon/getqpontot.php";
    
    public static String URL_create_ctrrecords = "http://192.168.0.102/trade_details/memberrecord.php";
    public static String URL_create_profitrecord = "http://192.168.0.102/profitrecord/profitrecord.php";

    // use to S_Coupon_Management
    public static String url_get_scmallcoupon = "http://192.168.0.102/couponconnect/getallcoupon.php";

    // use to S_Coupon_Rules
    public static String url_get_crcoupon = "http://192.168.0.102/couponconnect/spinner.php";

    // use to S_Coupon_Buy
    public static String url_create_buycoupon = "http://192.168.0.102/couponconnect/create.php";

    //use to S_Records_ShareGet
    public static String url_get_profitrecord = "http://192.168.0.102/profitrecord/getprofitrecord.php";
    
    //use to S_Records_ShareIssue
    public static String url_get_profitrecordIssue = "http://192.168.0.104/profitrecord/getprofitrecord_issue.php";
    
    //use to S_Analysis_IssueRecover
    public static String url_get_recordtoAIRnouse = "http://192.168.0.104/analysis/get_coupon_no_use.php";
    public static String url_get_recordtoAIRuse = "http://192.168.0.104/analysis/get_coupon_used.php";
    
    //use to S_Analysis_Recover
    
    //use to S_Analysis_Sources
    public static String url_get_recordtoAS = "http://192.168.0.104/analysis/get_all_receive.php"; 
    
    //use to S_Analysis_Whereabouts
    public static String url_get_recordtoAW = "http://192.168.0.104/analysis/get_all_issue.php"; 
    
}