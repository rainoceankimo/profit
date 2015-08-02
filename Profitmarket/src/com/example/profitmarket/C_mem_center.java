package com.example.profitmarket;

import helper.SQLiteHandler;
import helper.SessionManager;
 
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class C_mem_center extends Activity implements SurfaceHolder.Callback
{
	
	private EditText textName;
    private EditText textEmail;
    private EditText textPhone;
    private Button QRcode;
    
    private SQLiteHandler db;
    private SessionManager session;
    private String TAG = "HIPPO";
    private SurfaceView mSurfaceView01;
    private SurfaceHolder mSurfaceHolder01;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_c_mem_center);
		
		textName = (EditText) findViewById(R.id.txtemail);
        textEmail = (EditText) findViewById(R.id.txtname);
        textPhone = (EditText) findViewById(R.id.textphone);
        QRcode = (Button) findViewById(R.id.button2);
        
        /*眔秆猂钩*/
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        mSurfaceView01 = (SurfaceView) findViewById(R.id.surfaceView1);
        mSurfaceHolder01 = mSurfaceView01.getHolder();
        mSurfaceHolder01.addCallback(C_mem_center.this);
        
        
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
        
        //玻ネQR Code
        QRcode = (Button)findViewById(R.id.button2);
        QRcode.setOnClickListener(new Button.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(QRcode.getText().toString()!="")
		        {
		          /* 传入setQrcodeVersion为4，仅能接受62个字符 */
		          AndroidQREncode(textName.getText().toString(), 4);
		        }
				
			}
        });
	}
	
	
	
	public void AndroidQREncode(String strEncoding, int qrcodeVersion)
	{
	    try
	    {
	      /* 建构QRCode编码对象 */
	      com.swetake.util.Qrcode testQrcode = new com.swetake.util.Qrcode();
	      /* L','M','Q','H' */
	      testQrcode.setQrcodeErrorCorrect('M');
	      /* "N","A" or other */
	      testQrcode.setQrcodeEncodeMode('B');
	      /* 0-20 */
	      testQrcode.setQrcodeVersion(qrcodeVersion);
	      
	      // getBytes
	      byte[] bytesEncoding = strEncoding.getBytes("utf-8");
	      
	      if (bytesEncoding.length>0 && bytesEncoding.length <256)
	      {
	        /* 将字符串透过calQrcode函数转换成boolean数组 */
	        boolean[][] bEncoding = testQrcode.calQrcode(bytesEncoding);
	        /* 依据编码后的boolean数组，绘图 */
	        DrawQRCode(bEncoding, getResources().getColor(R.drawable.black));
	      }
	    }
	    catch (Exception e)
	    {
	      Log.i("HIPPO", Integer.toString(QRcode.getText().length()) );
	      e.printStackTrace();
	    }
	 }
	


	private void DrawQRCode(boolean[][] bRect, int colorFill) {
		// TODO Auto-generated method stub
		/* test Canvas*/
	    int intPadding = 20;
	    
	    /* 欲在SurfaceView上绘图，需先lock锁定SurfaceHolder */
	    Canvas mCanvas01 = mSurfaceHolder01.lockCanvas();
	    
	    /* 设定画布绘制颜色 */
	    mCanvas01.drawColor(getResources().getColor(R.drawable.white));
	    
	    /* 建立画笔 */
	    Paint mPaint01 = new Paint();
	    
	    /* 设定画笔颜色及样式 */
	    mPaint01.setStyle(Paint.Style.FILL);
	    mPaint01.setColor(colorFill);
	    mPaint01.setStrokeWidth(1.0F);
	    
	    /* 逐一加载2维boolean数组 */
	    for (int i=0;i<bRect.length;i++)
	    {
	      for (int j=0;j<bRect.length;j++)
	      {
	        if (bRect[j][i])
	        {
	          /* 依据数组值，绘出条形码方块 */
	          mCanvas01.drawRect(new Rect( intPadding+j*9+6,intPadding+i*9+6, intPadding+j*9+6+9, intPadding+i*9+6+9), mPaint01);
	        }
	      }
	    }
	    mSurfaceHolder01.unlockCanvasAndPost(mCanvas01);
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
	
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void c_mod_inform_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mem_center.this,C_mod_inform.class);
	    startActivity(intent);    //牟祇传
	    finish();   //挡セ
    }
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mem_center.this,C_mem_view.class);
    	   startActivity(intent);    //牟祇传
    	   finish();   //挡セ
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	
}



