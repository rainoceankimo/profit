package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class C_mem_view extends Activity {
	
	private ImageButton showstore,membercenter,coupon,records;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_mem_view);
		
		showstore = (ImageButton)findViewById(R.id.cmvibtn1);
		membercenter = (ImageButton)findViewById(R.id.cmvibtn2);
		coupon = (ImageButton)findViewById(R.id.cmvibtn3);
		records = (ImageButton)findViewById(R.id.cmvibtn4);
		
		showstore.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_search_store.class);
        	    startActivity(intent);    //觸發換頁
        	    finish();   //結束本頁
            }         
        }); 
		
		membercenter.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_mem_center.class);
        	    startActivity(intent);    //觸發換頁
        	    finish();   //結束本頁
            }         
        }); 
		
		coupon.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_discount_use.class);
        	    startActivity(intent);    //觸發換頁
        	    finish();   //結束本頁
            }         
        }); 
		
		records.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_record.class);
        	    startActivity(intent);    //觸發換頁
        	    finish();   //結束本頁
            }         
        }); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_mem_view, menu);
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
/*	public void c_mem_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_view.this,C_mem_center.class);
	    startActivity(intent);    //觸發換頁
	    finish();   //結束本頁
	}
	public void c_sstore__onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_view.this,C_search_store.class);
	    startActivity(intent);    //觸發換頁
	    finish();   //結束本頁
	
    }
	
	public void c_use_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_view.this,C_discount_use.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	
    }
	public void C_research_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_view.this,C_record.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	
    } */
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mem_view.this,C_Logout.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}