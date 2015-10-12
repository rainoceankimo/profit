package com.example.profitmarket;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import android.content.Intent;

import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Mainmenu extends Activity {
    
	private TextView showname;
	
	private SQLiteHandler_Stores db;
    private SessionManager_Stores session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_mainmenu);
		

		showname = (TextView) findViewById(R.id.s_mttV1);
		
		db = new SQLiteHandler_Stores(getApplicationContext());
        session = new SessionManager_Stores(getApplicationContext());
        
        HashMap<String, String> user = db.getUserDetails();
        String userid = user.get("name");
		
        showname.setText(userid);
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__mainmenu, menu);
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
	
	public void s_storescenter_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Mainmenu.this,S_Stores_Center.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	
	public void s_settleaccounts_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_SettleaCcounts.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	
	public void s_couponbuy_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_Coupon_Buy.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	public void s_couponmanagement_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_Couponmain.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	public void s_records_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_Records.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Mainmenu.this,S_Login.class);
    	   startActivity(intent);    //Ĳ�o����
    	   finish();   //��������
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
