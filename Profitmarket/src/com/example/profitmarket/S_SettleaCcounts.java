package com.example.profitmarket;

import java.util.Date;
import java.util.HashMap;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import app.AppController;
import helper.RulesDbHandler;
import helper.SQLiteHandler;
import helper.MaxdiscountHandler; 

public class S_SettleaCcounts extends Activity {
    
	private EditText Getmoney,Getmaxdiscount;
	private TextView txttxt1;
	private Button Btnsclear,btnset;
	public int money,outputmoney,maxdiscount,setoramend,gochange = 0;
	public double percent,showmaxdiscount;
	public String showdiscount;
	
	//rules DB
	private static final String DB_DBNAME = "rules.db",
            					DB_TBNAME = "rules";

	public static final String RID = "id";
	public static final String USEMONEY = "usemoney";
	public static final String GRANTMONEY = "grantmoney";

	String[] columns = {RID,USEMONEY,GRANTMONEY};

	private SQLiteDatabase MyrulesDb;

	//-----------------------
	
	// maxdiscount DB
	private static final String TABLE_MAX = "max";
    
	public static final String PID = "id";
	public static final String PHOWMUCH = "howmuch";
	
	String[] columns1 = {RID,PHOWMUCH};
	
	private MaxdiscountHandler maxDB;
    // ----------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_settleaccounts);
		
		/* 
		 *    check rules DB
		 */
		RulesDbHandler rulesDbHandler = 
				new RulesDbHandler(getApplicationContext(), DB_DBNAME, null, 1);
		MyrulesDb = rulesDbHandler.getWritableDatabase();
		
		// 檢查資料表是否已經存在，如果不存在，就建立一個。
		Cursor cursor = MyrulesDb.rawQuery(
			   "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
					   DB_TBNAME + "'", null);
				
	    //Toast.makeText(getActivity().getApplicationContext(), cursor+"", Toast.LENGTH_SHORT).show();
				
		if(cursor != null) {
			 if(cursor.getCount() == 0){	// 沒有資料表，要建立一個資料表。
			       MyrulesDb.execSQL("CREATE TABLE " + DB_TBNAME + " (" +
				                      RID + " INTEGER PRIMARY KEY," +
				        		      USEMONEY + " TEXT NOT NULL," +
				        		      GRANTMONEY + " TEXT);");
			        	
			        //Toast.makeText(getActivity().getApplicationContext(), "新增成功", Toast.LENGTH_SHORT).show();
			        
			 }else
			 {
			        	//Toast.makeText(getActivity().getApplicationContext(), "新增失敗", Toast.LENGTH_SHORT).show();
			 }

			    cursor.close();
		}else
		{
			  Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}
		
		//   ---------------------------------------------
		
		maxDB = new MaxdiscountHandler(getApplicationContext());

		Btnsclear = (Button) findViewById(R.id.btnsclear);
		btnset = (Button) findViewById(R.id.btnsetting);
		Getmoney = (EditText) findViewById(R.id.ttmoney);
		Getmaxdiscount = (EditText) findViewById(R.id.edtdiscount);
		
		txttxt1 = (TextView) findViewById(R.id.textView1);
        
		HashMap<String, String> user = maxDB.getUserDetails();
		 
        String getmaxdiscount = user.get("howmuch");
		
        
        if( getmaxdiscount != null) {
        	double input = Double.valueOf(getmaxdiscount);
        	showmaxdiscount = (double) input * 100;
        	setoramend = 1;
            Getmaxdiscount.setText("折扣上限為："+ showmaxdiscount + "%");
        }else
        {
        	setoramend = 0;
        }
        
		/**
		 *   Run program
		 */
		
		checkrules();
		
		if(setoramend == 0){
			Getmaxdiscount.setText("");
			btnset.setEnabled(true);
			btnset.setText("設定");
		}else if(setoramend == 1){
			Getmaxdiscount.setEnabled(false);
			btnset.setEnabled(true);
			btnset.setText("修改");
			gochange = 1;
		}
		
		
		Getmaxdiscount.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Getmaxdiscount.setText("");
				btnset.setEnabled(true);
				Getmaxdiscount.setEnabled(true);
			}
			
		});
		  
		
        
		btnset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				 double getdiscount;
				 String put;
				 
				 percent = 0;
				 getdiscount = 0;
				 
				 maxDB.deleteUsers();
				 
				 if(gochange == 0){
				 
					 if (!Getmaxdiscount.getText().toString().equals("")){
					  
						 getdiscount = Integer.valueOf(Getmaxdiscount.getText().toString());
					  
						 if(getdiscount >= 1 && getdiscount <= 100){
							 
							 getdiscount = Integer.valueOf(Getmaxdiscount.getText().toString());
							 percent = (double) getdiscount / 100;
							 
							 maxDB.addUser(percent);
							 
							 put = String.valueOf(percent);
							 showdiscount = String.valueOf(getdiscount);
							 
							 Getmaxdiscount.setText("折扣上限為："+ showdiscount + "%");
							 
							 Getmaxdiscount.setEnabled(false);
							 
							 gochange = 1;
							 
						 }else if(getdiscount < 1)
						 {
							 Getmaxdiscount.setText("");
							 Toast.makeText(S_SettleaCcounts.this, "不能少於1%", Toast.LENGTH_SHORT).show();
						 }else if(getdiscount > 100)
						 {
							 Getmaxdiscount.setText("");
							 Toast.makeText(S_SettleaCcounts.this, "不能大於100%", Toast.LENGTH_SHORT).show();
						 }
					  
					 }else{
						 Toast.makeText(S_SettleaCcounts.this, "請輸入折扣上限!", Toast.LENGTH_SHORT).show();
					 }
				 
				 }else if(gochange == 1){
					 Getmaxdiscount.setEnabled(true);
					 Getmaxdiscount.setText("");
					 gochange = 0;
				 }
				 
				
			}
		}); 
		
		
		
		Btnsclear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub      
				       
				        if (!Getmoney.getText().toString().equals("") && !Getmaxdiscount.getText().toString().equals("") ){
				        	
				        	money = 0;
					        outputmoney = 0;
					        maxdiscount = 0;
					        
					        HashMap<String, String> user = maxDB.getUserDetails();
							 
					        String get = user.get("howmuch");
					        double input = Double.valueOf(get);
					       
					        showmaxdiscount = (double) input * 100;
					       
						    money = Integer.valueOf(Getmoney.getText().toString());
						    AppController globalVariable = (AppController)getApplicationContext();
						    globalVariable.settlea_totalmoney = money;
						    outputmoney = globalVariable.settlea_totalmoney;
							maxdiscount = (int) (outputmoney * input);
							globalVariable.settlea_MaxDiscount = maxdiscount;
							//txttxt1.setText(""+ outputmoney + "&" + globalVariable.settlea_Discount + "元");
						
							MyDialog();
				        	
				        }else if(Getmoney.getText().toString().equals(""))
				        {
				        	Toast.makeText(S_SettleaCcounts.this, "請輸入金額!", Toast.LENGTH_SHORT).show();
				        }else if(Getmaxdiscount.getText().toString().equals(""))
				        {
				        	Toast.makeText(S_SettleaCcounts.this, "請設定折扣上限!", Toast.LENGTH_SHORT).show();
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
	
	// check rules DB
	public void checkrules(){
		
		Cursor c = MyrulesDb.query(true, DB_TBNAME, columns,null, null, null, null, null, null);
		
		if (c == null)
			return;

		if (c.getCount() == 0) {
			Toast.makeText(getApplicationContext(), "請先設定折價券發行規則喔!", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(); 
			intent.setClass(getApplicationContext(),S_Couponmain.class);
			startActivity(intent);    //觸發換頁
		}
		else {
			
		}
    }
	// ---------------
	
	
	// SHOW出對話視窗
	public void MyDialog( ){
			
			MyAlertDialog mDlgLogin = new MyAlertDialog(S_SettleaCcounts.this);
			mDlgLogin.setTitle("提示折扣上限 & 選擇結帳方式");
			mDlgLogin.setCancelable(true);
			mDlgLogin.setMessage("折扣最多為總金錢" + " " + outputmoney + " " +"元"+"的"+ showmaxdiscount+ "%" + "為 ："+ maxdiscount  + "元");

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
	

}
