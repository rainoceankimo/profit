package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class S_Records extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_records);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__records, menu);
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
	
	public void s_inquiretransaction_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Records.this,S_TransactionRecords.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
	public void s_inquireshare_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Records.this,S_Records_InquireShare.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
	public void s_analysis_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Records.this,S_Analysis.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Records.this,S_Mainmenu.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
