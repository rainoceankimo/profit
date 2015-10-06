package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import app.AppController;
import helper.TradedetailDbHandler;

public class S_TransactionRecords extends ListActivity {
	
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
	
	List<Map<String, Object>> mList;
	ArrayList<String> id = new ArrayList<String>();
	ArrayList<String> name = new ArrayList<String>();
	
	private static final String TAG_PID = "pid";
	 
	private TextView showrecod;
	private Button mBtnshow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_transactionrecords);
		
		AppController globalVariable = (AppController)getApplicationContext();
		Bundle extras = getIntent().getExtras();
		
		TradedetailDbHandler tradedetailDbHandler = 
				new TradedetailDbHandler(getApplicationContext(), DB_FILE, null, 1);
		mtradeDb = tradedetailDbHandler.getWritableDatabase();
		
		mList = new ArrayList<Map<String,Object>>();
		
		showrecod = (TextView)findViewById(R.id.edttr);
	    mBtnshow = (Button)findViewById(R.id.btnclickqr);
	    
	    Cursor cursor = mtradeDb.rawQuery(
	    		"select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
	    		DB_TABLE + "'", null);
	    
	    if(cursor != null) {
	        if(cursor.getCount() == 0){	// 沒有資料表，要建立一個資料表。
	        	mtradeDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
	        			          TID + " INTEGER PRIMARY KEY," +
	        			          TYPE + " TEXT NOT NULL," +
	        			          DATE + " TEXT," +
	        			          CONSUMPTION + " TEXT," +
	        			          DISCOUNT + " TEXT," +
	        			          GRANT + " TEXT," +
	        			          GRANTDENOMINATIONS + " TEXT," +
	        			          MEMNAME + " TEXT," +
	        			          QPONUSE + " TEXT," +
	        			          QPONNO + " TEXT," +
	        			          USEDENOMINATIONS + " TEXT," +
	        			          TRADETTMONEY + " TEXT);");
	        	
	        	Toast.makeText(S_TransactionRecords.this, "新增成功", Toast.LENGTH_SHORT).show();
		        
	        }else
	        {
	        	//Toast.makeText(S_Tradedetail.this, "新增失敗", Toast.LENGTH_SHORT).show();
	        }

	        cursor.close();
	    }else
	    {
	    	Toast.makeText(S_TransactionRecords.this, "ERROR", Toast.LENGTH_SHORT).show();
	    }
	    
	    
	    	ABCC();  
			SHOWLIST();
			clicklist();
	   
	   
	    
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
			
			Intent intent = new Intent();
			intent.setClass(S_TransactionRecords.this,S_Mainmenu.class);
			startActivity(intent);    //觸發換頁
			finish(); 
			
		}
	};
	
    public void SHOWLIST(){
		
		Cursor c = mtradeDb.query(true, DB_TABLE, columns, null, null, null, null, null, null);
		
		if (c == null)
	        return;

        
	  	  while(c.moveToNext())
		  {
			
			  String pid = c.getString(0);
			  String pname = c.getString(2);
			  id.add(pid);
			  name.add(pname);
		  }

		
		int recodeNum = name.size();
		String[] pid = new String[recodeNum];
		String[] names = new String[recodeNum];
		
		
		for (int i = 0; i < recodeNum ; i++) {

			pid[i] = id.get(i);
			names[i] = name.get(i);
			
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("pid", pid[i]);
			item.put("pname", names[i]);
			
			mList.add(item);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, mList,
      		R.layout.s_list_trade,
      		new String[] {"pid","pname" },
      		new int[] {R.id.ttV1,R.id.ttV2 });

		setListAdapter(adapter);
		
		
	}
	
	public void clicklist(){
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				String pid1 = ((TextView) view.findViewById(R.id.ttV1)).getText()
						.toString();
				
				Intent in = new Intent(S_TransactionRecords.this,
						S_T_ShowTradedetails.class);
				// sending pid to next activity
				in.putExtra(TAG_PID, pid1);
				
				startActivityForResult(in, 100);
			}
			
		}); 
		
	}
	
	
	
	public void ABCC()
    {
      	Cursor c = mtradeDb.query(true, DB_TABLE, columns, null, null, null, null, null, null);

        if (c == null)
        return;

        if (c.getCount() == 0) {
        	showrecod.setText("");
              Toast.makeText(S_TransactionRecords.this, "沒有資料", Toast.LENGTH_LONG).show();
        }
        else {
        	c.moveToFirst();
        	showrecod.setText(c.getString(0) + c.getString(1)  + c.getString(2) + c.getString(3) + c.getString(4)
			                 + c.getString(5) + c.getString(6) + c.getString(7) + c.getString(8) + c.getString(9)
			                 + c.getString(10) +  c.getString(11));
			
			while (c.moveToNext())
				showrecod.append("\n" + c.getString(0) + c.getString(1)  + c.getString(2) + c.getString(3) + c.getString(4)
                                     + c.getString(5) + c.getString(6) + c.getString(7) + c.getString(8) + c.getString(9)
                                     + c.getString(10) +  c.getString(11));
        }
        
    }
	
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_TransactionRecords.this,S_Records.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	
}
