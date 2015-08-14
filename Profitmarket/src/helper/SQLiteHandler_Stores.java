package helper;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SQLiteHandler_Stores extends SQLiteOpenHelper {
 
    private static final String TAG1 = SQLiteHandler_Stores.class.getSimpleName();
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "android_Stores";
 
    // Login table name
    private static final String TABLE_LOGIN = "Stores_login";
 
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
 
    public SQLiteHandler_Stores(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db1) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db1.execSQL(CREATE_LOGIN_TABLE);
 
        Log.d(TAG1, "Database tables created");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {
        // Drop older table if existed
        db1.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db1);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db1 = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
 
        // Inserting Row
        long id = db1.insert(TABLE_LOGIN, null, values);
        db1.close(); // Closing database connection
 
        Log.d(TAG1, "New user inserted into sqlite: " + id);
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
 
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db1.close();
        // return user
        Log.d(TAG1, "Fetching user from Sqlite: " + user.toString());
 
        return user;
    }
 
    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db1.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db1 = this.getWritableDatabase();
        // Delete All Rows
        db1.delete(TABLE_LOGIN, null, null);
        db1.close();
 
        Log.d(TAG1, "Deleted all user info from sqlite");
    }
 
}
