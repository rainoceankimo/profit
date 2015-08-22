package com.example.profitmarket;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Stores_Center extends Activity {
	
	
	private EditText textName;
    private EditText textEmail;
    private EditText textPhone;
    private EditText textAddress;
    
    private SQLiteHandler_Stores db;
    private SessionManager_Stores session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_stores_center);
		
		textName = (EditText) findViewById(R.id.scname);
		textEmail = (EditText) findViewById(R.id.scemail);
		textPhone = (EditText) findViewById(R.id.scphone);
		textAddress = (EditText) findViewById(R.id.scaddress);
		
		// SqLite database handler
        db = new SQLiteHandler_Stores(getApplicationContext());
 
        // session manager
        session = new SessionManager_Stores(getApplicationContext());
        
        HashMap<String, String> user = db.getUserDetails();
        
        String name = user.get("name");
        String email = user.get("email");
        String phone = user.get("phone");
        String address = user.get("address");
        
        // Displaying the user details on the screen
        textName.setText(name);
        textEmail.setText(email);
        textPhone.setText(phone);
        textAddress.setText(address);
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s_storescenter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void s_storesinformationamend_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Stores_Center.this,S_Stores_Center_Amend.class);
		startActivity(intent);    //觸發換頁
		S_Stores_Center.this.finish();   //結束本頁
	}
	
	public void s_merchandise_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Stores_Center.this,AllProductsActivity.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Stores_Center.this,S_Mainmenu.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
