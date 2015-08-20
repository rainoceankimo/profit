package com.example.profitmarket;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

public class S_Mainmenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_mainmenu);
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
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
	public void s_settleaccounts_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_SettleaCcounts.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
	public void s_couponbuy_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_Coupon_Buy.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	public void s_couponmanagement_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,Couponmain.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	public void s_records_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Mainmenu.this,S_Records.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Mainmenu.this,S_Login.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
