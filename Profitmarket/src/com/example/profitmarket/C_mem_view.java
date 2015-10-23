package com.example.profitmarket;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import helper.SQLiteHandler;
import helper.SessionManager;

public class C_mem_view extends Activity {
	
	private ImageButton showstore,membercenter,coupon,records;
	  private SQLiteHandler db;
	    private SessionManager session;
	private TextView textName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_mem_view);
		  db = new SQLiteHandler(getApplicationContext());
		  
	        // session manager
	        session = new SessionManager(getApplicationContext());			
	        HashMap<String, String> user = db.getUserDetails();
	        String name = user.get("name");
	        
		showstore = (ImageButton)findViewById(R.id.cmvibtn1);
		membercenter = (ImageButton)findViewById(R.id.cmvibtn2);
		coupon = (ImageButton)findViewById(R.id.cmvibtn4);
		records = (ImageButton)findViewById(R.id.cmvibtn3);
		textName = (TextView) findViewById(R.id.textView1);
		   textName.setText(name);
		showstore.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_search_store.class);
        	    startActivity(intent);    //Ĳ�o����
        	    finish();   //��������
            }         
        }); 
		
		membercenter.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_mem_center.class);
        	    startActivity(intent);    //Ĳ�o����
        	    finish();   //��������
            }         
        }); 
		
		coupon.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_discount_use.class);
        	    startActivity(intent);    //Ĳ�o����
        	    finish();   //��������
            }         
        }); 
		
		records.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent();  
        	    intent.setClass(C_mem_view.this,C_record.class);
        	    startActivity(intent);    //Ĳ�o����
        	    finish();   //��������
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

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mem_view.this,C_Logout.class);
    	    startActivity(intent);    //Ĳ�o����
    	    finish();   //��������
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}