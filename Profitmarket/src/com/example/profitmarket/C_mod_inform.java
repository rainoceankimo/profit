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
import helper.SQLiteHandler;
import helper.SessionManager;

public class C_mod_inform extends Activity {
	
	
	private EditText textName;
    private EditText textEmail;
    private EditText textPhone;
    
    private SQLiteHandler db;
    private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_mod_inform);
		
		textName = (EditText) findViewById(R.id.txtemail);
        textEmail = (EditText) findViewById(R.id.txtname);
        textPhone = (EditText) findViewById(R.id.editText5);
		
     // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
 
        // session manager
        session = new SessionManager(getApplicationContext());
		
        HashMap<String, String> user = db.getUserDetails();
        
        String name = user.get("name");
        String email = user.get("email");
        String phone = user.get("phone");
 
        // Displaying the user details on the screen
        textName.setText(name);
        textEmail.setText(email);
        textPhone.setText(phone);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_mod_inform, menu);
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
	
	
	public void c_mem_check_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mod_inform.this,C_mem_view.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mod_inform.this,C_mem_center.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}

