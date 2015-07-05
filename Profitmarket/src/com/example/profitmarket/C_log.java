package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class C_log extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_log);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_log, menu);
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
	public void c_log_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_log.this,C_mem_view.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	}
	public void c_register_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_log.this,C_register.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	}
}
