package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import helper.TradedetailDbHandler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class S_T_ShowTradedetails extends Activity {
	
	private TextView stxtid,
    				 stxtname,
    				 stxtsex,
    				 stxtaddress,
    				 stxtAA,
    				 stxtBB,
    				 stxtCC,
    				 stxtDD,
    				 stxtEE,
    				 stxtFF,
    				 stxtGG,
    				 stxtHH;
	
	String pid;
	private static final String TAG_PID = "pid";
	
	private static final String DB_FILE = "tradedetail.db",
			                    DB_TABLE = "tradedetail";

	public static final String TID = "id";
	public static final String TYPE = "type";
	public static final String DATE = "date";
	public static final String CONSUMPTION = "cousumption";
	public static final String DISCOUNT = "discount";
	public static final String GRANT = "grant";
	public static final String GRANTDENOMINATIONS = "grantdenominations";
	public static final String MEMNAME = "memname";
	public static final String QPONUSE = "Qponuse";
	public static final String QPONNO = "QponNo";
	public static final String USEDENOMINATIONS = "usedenominations";
	public static final String TRADETTMONEY = "tradettmoney";


	String[] columns = 
		     {TID,TYPE,DATE,CONSUMPTION,DISCOUNT,GRANT,GRANTDENOMINATIONS,MEMNAME,QPONUSE,QPONNO,USEDENOMINATIONS,TRADETTMONEY};

	private SQLiteDatabase mtradeDb;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_t_showtradedetail);
		
		stxtid = (TextView)findViewById(R.id.stxtid);
        stxtname = (TextView)findViewById(R.id.stxtname);
        stxtsex = (TextView)findViewById(R.id.stxtsex);
        stxtaddress = (TextView)findViewById(R.id.stxtaddress);
        stxtAA = (TextView)findViewById(R.id.stxAA);
        stxtBB = (TextView)findViewById(R.id.stxBB);
        stxtCC = (TextView)findViewById(R.id.stxCC);
        stxtDD = (TextView)findViewById(R.id.stxDD);
        stxtEE = (TextView)findViewById(R.id.stxEE);
        stxtFF = (TextView)findViewById(R.id.stxFF);
        stxtGG = (TextView)findViewById(R.id.stxGG);
        stxtHH = (TextView)findViewById(R.id.stxHH);
        
        TradedetailDbHandler tradedetailDbHandler = 
				new TradedetailDbHandler(getApplicationContext(), DB_FILE, null, 1);
		mtradeDb = tradedetailDbHandler.getWritableDatabase();
        
        Intent i = getIntent();
		pid = i.getStringExtra(TAG_PID);
		
		stxtid.setText(pid);
		
		show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__records__transactionre, menu);
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
	
	
    public void show() {
		
		Cursor c = null;
		
		if (!stxtid.getText().toString().equals("")) {
			c = mtradeDb.query(true, DB_TABLE, columns, TID + "= '" +  stxtid.getText().toString()
					 + "'", null, null, null, null, null);
		}
		
		if (c == null)
			return;

		if (c.getCount() == 0) {
			
			Toast.makeText(S_T_ShowTradedetails.this, "沒有這筆資料", Toast.LENGTH_LONG).show();
		} else {
			c.moveToFirst();
		/*	mEdtList.setText(c.getString(0) + c.getString(1)  + c.getString(2) + c.getString(3) + c.getString(4)
                             + c.getString(5) + c.getString(6) + c.getString(7) + c.getString(8) + c.getString(9)
                             + c.getString(10) +  c.getString(11));   */
			stxtname.setText("類型："+c.getString(1));
			stxtsex.setText("日期；"+c.getString(2));
			stxtaddress.setText("消費金額："+c.getString(3));
			stxtAA.setText("折扣上限："+c.getString(4));
			stxtBB.setText("折價券發放："+c.getString(5));
			stxtCC.setText("發放面額："+c.getString(6));
			stxtDD.setText("會員名稱："+c.getString(7));
			stxtEE.setText("折價券使用："+c.getString(8));
			stxtFF.setText("折價券序號："+c.getString(9));
			stxtGG.setText("使用面額："+c.getString(10));
			stxtHH.setText("總計金額："+c.getString(11));
			
		}
		
	}
	
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_T_ShowTradedetails.this,S_TransactionRecords.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
