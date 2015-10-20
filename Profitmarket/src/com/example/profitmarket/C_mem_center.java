package com.example.profitmarket;

import helper.SQLiteHandler;
import helper.SessionManager;
 
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;
import android.graphics.Bitmap;


public class C_mem_center extends Activity 
{
	
	private EditText textName;
    private EditText textEmail;
    private EditText textPhone;
    private ImageView qrcodeImageView;
    
    
    private SQLiteHandler db;
    private SessionManager session;
    

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_mem_center);
		
		
		textName = (EditText) findViewById(R.id.c_mctxtname);
        textEmail = (EditText) findViewById(R.id.c_mctxtemail);
        textPhone = (EditText) findViewById(R.id.c_mctextphone);
        qrcodeImageView = (ImageView) findViewById(R.id.QRimageshow1);
              
        
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
        
        try {
			String contentString = textEmail.getText().toString();
			if (contentString != null && contentString.trim().length() > 0) {
				
				Bitmap qrCodeBitmap =EncodingHandler.createQRCode(contentString, 500);
				qrcodeImageView.setImageBitmap(qrCodeBitmap);
				
			}else {
				Toast.makeText(C_mem_center.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
			}
			
		} catch (WriterException e) {
			e.printStackTrace();
		}
        
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_mem_center, menu);
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
	
	
	public void c_mod_inform_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_center.this,C_mod_inform.class);
	    startActivity(intent);    //觸發換頁
	    finish();   //結束本頁
    }
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mem_center.this,C_mem_view.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	
}



