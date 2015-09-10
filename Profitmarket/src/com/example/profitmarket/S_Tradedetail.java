package com.example.profitmarket;


import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.AppController;

import com.example.profitmarket.R;
import helper.TradedetailDbHandler;


public class S_Tradedetail extends Activity {
	
	private static final String DB_FILE = "tradedetail.db",
			                    DB_TABLE = "details";
	
    private SQLiteDatabase mtradedetailDb;
	
	private TextView showtradetype,showtradedate,showtradeconsumption,trademaxdiscount,
	                 tradecoupongrant,tradegrantdenominations,trademembername,
	                 tradecouponuse,tradecouponNo,tradeusedenominations,tradetotalmoney;
	                 
	private String tradetype,tradedate,Qpongrant,memname,Qponuse,QponNo;
	
	private int tradeconsumption,tradediscount,grantdenominations,usedenominations,tradettmoney;
	
	private Button btnrecode;
	
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_tradedetail);
		
		AppController globalVariable = (AppController)getApplicationContext();
		Bundle extras = getIntent().getExtras();
		
		TradedetailDbHandler tradedetailDbHandler = 
				new TradedetailDbHandler(getApplicationContext(), DB_FILE, null, 1);
		mtradedetailDb = tradedetailDbHandler.getWritableDatabase();
		
		        // �ˬd��ƪ�O�_�w�g�s�b�A�p�G���s�b�A�N�إߤ@�ӡC
				Cursor cursor = mtradedetailDb.rawQuery(
			    		"select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
			    		DB_TABLE + "'", null);
				
				if(cursor != null) {
			        if(cursor.getCount() == 0)	// �S����ƪ�A�n�إߤ@�Ӹ�ƪ�C
			        	mtradedetailDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
				        		"t_id INTEGER PRIMARY KEY," +
				        		"type TEXT NOT NULL," + "date TEXT," + "consumption TEXT," +
				        		"discount TEXT," + "grant TEXT," + "grantdenominations TEXT," + 
				        		"memname TEXT," + "Qponuse TEXT," + "QponNo TEXT," + 
				        		"usedenominations TEXT," + "tradettmoney TEXT);");

			        cursor.close();
			    }
		
		if (globalVariable.tradetypeNO == 0){
		
		    showtradetype = (TextView) findViewById(R.id.showtype);
		    tradetype = "general";
		    showtradetype.setText(globalVariable.tradetype);
		
		    showtradedate = (TextView) findViewById(R.id.trade_date);
		    tradedate = sDateFormat.format(new java.util.Date());
		    showtradedate.setText("����G"+ tradedate );
		
		    showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
		    tradeconsumption = globalVariable.settlea_totalmoney;
		    showtradeconsumption.setText("���O�����G"+ tradeconsumption);
		
		    trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		    //trademaxdiscount.setText("�馩�W���G"+ globalVariable.settlea_MaxDiscount);
		    tradediscount = 0;
		    trademaxdiscount.setVisibility(View.GONE);
		  
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    Qpongrant = "null";
		    tradecoupongrant.setVisibility(View.GONE);
		  
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    grantdenominations = 0;
		    tradegrantdenominations.setVisibility(View.GONE);
		  
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    //trademembername.setVisibility(View.GONE);
		    memname = "null";
		    trademembername.setText("�|���W�١G"+"�L");
		  
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    Qponuse = "null";
		    tradecouponuse.setVisibility(View.GONE);
		  
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    QponNo = "null";
		    tradecouponNo.setVisibility(View.GONE);
		   
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    usedenominations = 0;
		    tradeusedenominations.setVisibility(View.GONE);
		  
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradettmoney = globalVariable.settlea_totalmoney;
		    tradetotalmoney.setText("�`�p���B�G"+ tradettmoney );

		}
		else if (globalVariable.tradetypeNO == 1){
			
			showtradetype = (TextView) findViewById(R.id.showtype);
			showtradetype.setText(globalVariable.tradetype);
			
			showtradedate = (TextView) findViewById(R.id.trade_date);
			String date = sDateFormat.format(new java.util.Date());
			showtradedate.setText("����G"+ date );
			 
			showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
			showtradeconsumption.setText("���O�����G"+ globalVariable.settlea_totalmoney);
			
			trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		    trademaxdiscount.setText("�馩�W���G"+ globalVariable.settlea_MaxDiscount);
		    
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    tradecoupongrant.setText("�����o��G");
		    
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    tradegrantdenominations.setText("�o���B�G");
		    
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    String result = extras.getString("result");
		    trademembername.setText("�|���W�١G"+ result);
		    
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    tradecouponuse.setText("�����ϥΡG");
		    
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    tradecouponNo.setText("�����Ǹ��G");
		    
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    tradeusedenominations.setText("�ϥέ��B�G");
		    
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradetotalmoney.setText("�`�p���B�G" );
		}
		
		btnrecode = (Button)findViewById(R.id.trade_btn);
		btnrecode.setOnClickListener(btnAddOnClick);
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mtradedetailDb.close(); 
	}
	
	
	//DB
	private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

	        ContentValues newRow = new ContentValues();
	        
	        newRow.put("type", tradetype);
	        newRow.put("date", tradedate);
	        newRow.put("consumption", tradeconsumption);
	        newRow.put("discount", tradediscount);
	        newRow.put("grant", Qpongrant);
	        newRow.put("grantdenominations", grantdenominations);
	        newRow.put("memname", memname);
	        newRow.put("Qponuse", Qponuse);
	        newRow.put("QponNo", QponNo);
	        newRow.put("usedenominations", usedenominations);
	        newRow.put("tradettmoney", tradettmoney);
	        
	        mtradedetailDb.insert(DB_TABLE, null, newRow);
		}
    };
	
	
	
	//Next
	public void launchScanner(View v){
		Intent intent = new Intent();
		intent.setClass(S_Tradedetail.this,S_YesorNo_Mem2.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
	
}

