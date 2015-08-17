package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

public class C_discount_use extends Activity {

	
	private Button Btnclickqr;
	private EditText edtUserName;
	private Dialog mDlgLogin;
	private ImageView qrImgImageView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_discount_use);
		
		Btnclickqr = (Button) findViewById(R.id.btnclickqr);
		
		Btnclickqr.setOnClickListener(btnOnClickQR);
		
		
		
		
		
		
		
		
		
		
	}

	
	
//---------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_discount_use, menu);
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
//-------	
	
	private View.OnClickListener btnOnClickQR = new View.OnClickListener() {
		public void onClick(View v) {
			
			mDlgLogin = new Dialog(C_discount_use.this);
			mDlgLogin.setTitle("QR Code");
			mDlgLogin.setCancelable(true);
			mDlgLogin.setContentView(R.layout.activity_c_mem_qrcode);
			qrImgImageView = (ImageView) mDlgLogin.findViewById(R.id.imageView_abc);
			edtUserName = (EditText) mDlgLogin.findViewById(R.id.edtUserName);
			edtUserName.setText("chaomaniok");
			
			try {
				String contentString = edtUserName.getText().toString();
				if (contentString != null && contentString.trim().length() > 0) {
					
					Bitmap qrCodeBitmap =EncodingHandler.createQRCode(contentString, 600);
					qrImgImageView.setImageBitmap(qrCodeBitmap);
					
				}else {
					Toast.makeText(C_discount_use.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
				}
				
			} catch (WriterException e) {
				e.printStackTrace();
			}

			mDlgLogin.show();
			
			
			
		   

		}
	};	
	
	
	
   public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_discount_use.this,C_mem_view.class);
    	   startActivity(intent);    //Ä²µo´«­¶
    	   finish();   //µ²§ô¥»­¶
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
   
}
