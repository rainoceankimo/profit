package com.example.profitmarket;

import java.util.Date;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.AppController;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;


public class S_SettleaCcounts extends Activity {
    
	private EditText Getmoney;
	private TextView txttxt1;
	private Button Btnscalear;
	private TextView showtype;
	public int money;
	public int outputmoney;
	public int maxdiscount;
	
	
	private static final String DB_DBNAME = "percents.db",
            					DB_TBNAME = "percents";

	public static final String PID = "id";
	public static final String HOWMUCH = "howmuch";


	String[] columns = {PID,HOWMUCH};

	private SQLiteDatabase MypercentDb;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_settleaccounts);
		
        
		Btnscalear = (Button) findViewById(R.id.btnsclear);
		Getmoney = (EditText) findViewById(R.id.ttmoney);
		txttxt1 = (TextView) findViewById(R.id.textView1);

		

		Btnscalear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		               
				       
				        if (!Getmoney.getText().toString().equals("")){
				        	
				        	money = 0;
					        outputmoney = 0;
					        maxdiscount = 0;
					
					  
						    money = Integer.valueOf(Getmoney.getText().toString());
						    AppController globalVariable = (AppController)getApplicationContext();
						    globalVariable.settlea_totalmoney = money;
						    outputmoney = globalVariable.settlea_totalmoney;
							maxdiscount = (int) (outputmoney * 0.2);
							globalVariable.settlea_MaxDiscount = maxdiscount;
							//txttxt1.setText(""+ outputmoney + "&" + globalVariable.settlea_Discount + "元");
						
							MyDialog();
				        	
				        }else
				        {
				        	Toast.makeText(S_SettleaCcounts.this, "請輸入金額!", Toast.LENGTH_SHORT).show();
				        }
				        
				
			}
	
		});  
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__settleaccounts, menu);
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
	
	
	// SHOW出對話視窗
	public void MyDialog( ){
			
			MyAlertDialog mDlgLogin = new MyAlertDialog(S_SettleaCcounts.this);
			mDlgLogin.setTitle("提示折扣上限 & 選擇結帳方式");
			mDlgLogin.setCancelable(true);
			mDlgLogin.setMessage("折扣最多為總金錢"+" "+ outputmoney + " " +"元"+"的20%為 ："+ maxdiscount  + "元");

			mDlgLogin.setButton(DialogInterface.BUTTON_POSITIVE, "一般結帳", altDlgPositiveBtnOnClk);
			mDlgLogin.setButton(DialogInterface.BUTTON_NEGATIVE, "會員結帳", altDlgNegativeBtnOnClk);

			mDlgLogin.show();	
	};
   
    // 對話視窗中按 "一般結帳"
    private  DialogInterface.OnClickListener altDlgPositiveBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			AppController globalVariable = (AppController)getApplicationContext();
			globalVariable.tradetypeNO = 0;
			globalVariable.tradetype = "";
			globalVariable.tradetype = "一般結帳 - 明細";

			Intent intent = new Intent(S_SettleaCcounts.this,
					S_Tradedetail.class);
            startActivity(intent);
            finish(); 
            			
			
		}
	};
	
	// 對話視窗中按 "會員結帳"
	private  DialogInterface.OnClickListener altDlgNegativeBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			AppController globalVariable = (AppController)getApplicationContext();
			globalVariable.tradetypeNO = 1;
			globalVariable.tradetype = "";
			globalVariable.tradetype = "會員結帳 - 明細";
			
			Intent intent = new Intent(S_SettleaCcounts.this,
					S_Capture.class);
            startActivity(intent);
            finish(); 
            
		}
	};
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_SettleaCcounts.this,S_Mainmenu.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	
 /*   public void account_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_SettleaCcounts.this,S_Tradedetail.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	 }    */
	

}
