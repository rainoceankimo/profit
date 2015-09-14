package com.example.profitmarket;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import helper.TradedetailDbHandler;

public class S_TransactionRecords extends Activity {
	
	private static final String DB_FILE = "friends.db",
            DB_TABLE = "friends";

	 private SQLiteDatabase mtradedetailDb;
	 
	 private EditText showrecod;
	 private Button mBtnshow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_transactionrecords);
		
		TradedetailDbHandler tradedetailDbHandler = 
				new TradedetailDbHandler(getApplicationContext(), DB_FILE, null, 1);
		mtradedetailDb = tradedetailDbHandler.getWritableDatabase();
		
		// 檢查資料表是否已經存在，如果不存在，就建立一個。
		Cursor cursor = mtradedetailDb.rawQuery(
			    		"select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
			    		DB_TABLE + "'", null);
		
		showrecod = (EditText)findViewById(R.id.edttr);

	    mBtnshow = (Button)findViewById(R.id.btnclickqr);
		
	    mBtnshow.setOnClickListener(btnListOnClick);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__transaction_records, menu);
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
	
	private View.OnClickListener btnListOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//String[] columns = {"type","date","consumption","discount","grant","grantdenominations","memname","Qponuse","QponNo","usedenominations","tradettmoney"};
			
			Cursor c = mtradedetailDb.query(true, DB_TABLE, new String[] {"type","date","consumption","discount","grant","grantdenominations","memname","Qponuse","QponNo","usedenominations","tradettmoney"} , null, null, null, null,null,null);
			
			if(c == null)
				 return;
			
			if(c.getCount() == 0) {
				Toast.makeText(getApplicationContext(), "沒有資料", Toast.LENGTH_SHORT).show();
			}
			else{
				
				showrecod.setText("hello");
				 
				/*
				c.moveToFirst();
				
				showrecod.setText("類型:" + c.getString(0) + "日期:"+ c.getString(1));
				
				*/
				
				
				/* showrecod.setText("類型:" + c.getString(0) + "日期:"+ c.getString(1) + "消費金額:"+ c.getString(2) 
				+ "會員:"+ c.getString(6) + "總計金錢:" + c.getString(10));  
				 */
				
				/*
				    while(c.moveToNext())
					showrecod.setText("\n" + "類型:" + c.getString(0) + "日期:"+ c.getString(1));
			    */
					
					/*  showrecod.setText("\n" + "類型:" + c.getString(0) + "日期:"+ c.getString(1) + "消費金額:"+ c.getString(2) 
					+ "會員:"+ c.getString(6) + "總計金錢:" + c.getString(10));  */
			
			}
			
		}
	};
	
}
