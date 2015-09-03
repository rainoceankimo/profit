package com.example.profitmarket;


import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import app.AppController;

public class S_Tradedetail extends Activity {
	
	
	private TextView showtradetype;
	private TextView showtradedate;
	private TextView showtradeconsumption;
	private TextView trademaxdiscount;
	private TextView tradecoupongrant;
	private TextView tradegrantdenominations;
	private TextView trademembername;
	private TextView tradecouponuse;
	private TextView tradecouponNo;
	private TextView tradeusedenominations;
	private TextView tradetotalmoney;
	
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_tradedetail);
		
		AppController globalVariable = (AppController)getApplicationContext();

		
		
		if (globalVariable.tradetypeNO == 0){
		
		    showtradetype = (TextView) findViewById(R.id.showtype);
		    showtradetype.setText(globalVariable.tradetype);
		
		    showtradedate = (TextView) findViewById(R.id.trade_date);
		    String date = sDateFormat.format(new java.util.Date());
		    showtradedate.setText("日期："+ date );
		
		    showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
		    showtradeconsumption.setText("消費金錢："+ globalVariable.settlea_totalmoney);
		
		    trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		    //trademaxdiscount.setText("折扣上限："+ globalVariable.settlea_MaxDiscount);
		    trademaxdiscount.setVisibility(View.GONE);
		  
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    tradecoupongrant.setVisibility(View.GONE);
		  
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    tradegrantdenominations.setVisibility(View.GONE);
		  
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    //trademembername.setVisibility(View.GONE);
		    trademembername.setText("會員名稱："+"無");
		  
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    tradecouponuse.setVisibility(View.GONE);
		  
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    tradecouponNo.setVisibility(View.GONE);
		   
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    tradeusedenominations.setVisibility(View.GONE);
		  
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradetotalmoney.setText("總計金額："+ globalVariable.settlea_totalmoney );
		  
		}
		else if (globalVariable.tradetypeNO == 1){
			
			showtradetype = (TextView) findViewById(R.id.showtype);
			showtradetype.setText(globalVariable.tradetype);
			
			showtradedate = (TextView) findViewById(R.id.trade_date);
			String date = sDateFormat.format(new java.util.Date());
			showtradedate.setText("日期："+ date );
			 
			showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
			showtradeconsumption.setText("消費金錢："+ globalVariable.settlea_totalmoney);
			
			trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		    trademaxdiscount.setText("折扣上限："+ globalVariable.settlea_MaxDiscount);
		    
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    tradecoupongrant.setText("折價券發放：");
		    
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    tradegrantdenominations.setText("發放面額：");
		    
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    trademembername.setText("會員名稱：");
		    
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    tradecouponuse.setText("折價券使用：");
		    
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    tradecouponNo.setText("折價券序號：");
		    
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    tradeusedenominations.setText("使用面額：");
		    
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradetotalmoney.setText("總計金額：" );
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__yesor_no__mem, menu);
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
	
	
	public void launchScanner(View v){
		Intent intent = new Intent();
		intent.setClass(S_Tradedetail.this,S_YesorNo_Mem2.class);
		startActivity(intent);    //觸發換頁
		finish();   //結束本頁
	}
}

