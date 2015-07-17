package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class S_Stores_Center_Amend extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_stores_center_amend);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s_storescenteramend, menu);
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
	
	public void s_backtostorescenter_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Stores_Center_Amend.this,S_Stores_Center.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Stores_Center_Amend.this,S_Stores_Center.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
