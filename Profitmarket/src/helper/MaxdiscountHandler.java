package helper;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaxdiscountHandler extends SQLiteOpenHelper {
	
	private static final String TAG = MaxdiscountHandler.class.getSimpleName();

	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "maxdiscount";
 
    // Login table name
    private static final String TABLE_MAX = "max";
    
	public static final String PID = "id";
	public static final String PHOWMUCH = "howmuch";
	
	public MaxdiscountHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_MAX + "("
                + PID + " INTEGER PRIMARY KEY," 
        		+ PHOWMUCH + " TEXT" + ")";
         
        db.execSQL(CREATE_LOGIN_TABLE);
        
        Log.d(TAG, "Database tables created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAX);
		 
        // Create tables again
        onCreate(db);
	}
	
	public void addUser(double howmuch) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(PHOWMUCH, howmuch); // Name

        // Inserting Row
        long id = db.insert(TABLE_MAX, null, values);
        db.close(); // Closing database connection
 
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
	
	
	public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MAX;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("howmuch", cursor.getString(1));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
 
        return user;
    }
	
	public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MAX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
	
	public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_MAX, null, null);
        db.close();
 
        Log.d(TAG, "Deleted all user info from sqlite");
    }
	
}
