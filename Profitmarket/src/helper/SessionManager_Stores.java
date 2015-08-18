package helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
 
public class SessionManager_Stores {
    // LogCat tag
    private static String TAG = SessionManager_Stores.class.getSimpleName();
 
    // Shared Preferences
    SharedPreferences pref1;
 
    Editor editor1;
    Context _context1;
 
    // Shared pref mode
    int PRIVATE_MODE = 0;
 
    // Shared preferences file name
    private static final String PREF_NAME = "AndroidStoresLogin";
     
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
 
    public SessionManager_Stores(Context context) {
        this._context1 = context;
        pref1 = _context1.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor1 = pref1.edit();
    }
 
    public void setLogin(boolean isLoggedIn) {
 
        editor1.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
 
        // commit changes
        editor1.commit();
 
        Log.d(TAG, "User login session modified!");
    }
     
    public boolean isLoggedIn(){
        return pref1.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
