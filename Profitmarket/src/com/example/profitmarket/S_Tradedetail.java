package com.example.profitmarket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
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
import com.example.profitmarket.S_Coupon_Buy.postnew;

import helper.RulesDbHandler;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import helper.TradedetailDbHandler;

public class S_Tradedetail extends Activity {
	
	//BD - 交易明細
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
	
	
	//DB - 規則
	private static final String DB_DBNAME = "rules.db",
                                DB_TBNAME = "rules";

	public static final String RID = "id";
	public static final String USEMONEY = "usemoney";
	public static final String GRANTMONEY = "grantmoney";

	int[] ciumy;
	int[] cigmy;
	int savetc = 0;
	int yorn = 0;
	int cinum = 0;
	
	ArrayList<Integer> use = new ArrayList<Integer>();
	ArrayList<Integer> grant = new ArrayList<Integer>();

	String[] columns2 = {TID,USEMONEY,GRANTMONEY};

	private SQLiteDatabase MyrulesDb;
	
	
	//MYSQL- issueQpon
	private SQLiteHandler_Stores db;
    private SessionManager_Stores session;
    
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	
	private static String url_create_product = "http://10.51.202.142/addQpon/add_coupon.php";
	
	private static final String TAG_SUCCESS = "success";
	
	
	
	private TextView showtradetype,showtradedate,showtradeconsumption,trademaxdiscount,
	                 tradecoupongrant,tradegrantdenominations,trademembername,
	                 tradecouponuse,tradecouponNo,tradeusedenominations,tradetotalmoney;

	private String tradetype,tradedate,Qpongrant,memname,Qponuse,QponNo;
	
	private int tradeconsumption = 0,
			    tradediscount = 0,
			    grantdenominations = 0,
			    usedenominations = 0,
			    tradettmoney = 0;

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
		mtradeDb = tradedetailDbHandler.getWritableDatabase();
		
		RulesDbHandler rulesDbHandler = 
				new RulesDbHandler(getApplicationContext(), DB_DBNAME, null, 1);
		MyrulesDb = rulesDbHandler.getWritableDatabase();

		// 檢查資料表是否已經存在，如果不存在，就建立一個。
		Cursor cursor = mtradeDb.rawQuery(
	    		"select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
	    		DB_TABLE + "'", null);
		
		//Toast.makeText(S_Tradedetail.this, cursor+"", Toast.LENGTH_SHORT).show();
		
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
	        	
	        	Toast.makeText(S_Tradedetail.this, "新增成功", Toast.LENGTH_SHORT).show();
		        
	        }else
	        {
	        	//Toast.makeText(S_Tradedetail.this, "新增失敗", Toast.LENGTH_SHORT).show();
	        }

	        cursor.close();
	    }else
	    {
	    	Toast.makeText(S_Tradedetail.this, "ERROR", Toast.LENGTH_SHORT).show();
	    }
		
		showrules();

		
		// 交易明細-show
		if (globalVariable.tradetypeNO == 0){

		    showtradetype = (TextView) findViewById(R.id.showtype);
		    tradetype = "一般結帳";
		    showtradetype.setText(globalVariable.tradetype);
		
		    showtradedate = (TextView) findViewById(R.id.trade_date);
		    tradedate = sDateFormat.format(new java.util.Date());
		    showtradedate.setText("日期："+ tradedate );
		
		    showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
		    tradeconsumption = globalVariable.settlea_totalmoney;
		    showtradeconsumption.setText("消費金錢："+ tradeconsumption);
		
		    trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		    //trademaxdiscount.setText("折扣上限："+ globalVariable.settlea_MaxDiscount);
		    tradediscount = 0;
		    trademaxdiscount.setVisibility(View.GONE);
		  
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    Qpongrant = "無";
		    tradecoupongrant.setVisibility(View.GONE);
		  
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    grantdenominations = 0;
		    tradegrantdenominations.setVisibility(View.GONE);
		  
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    //trademembername.setVisibility(View.GONE);
		    memname = "無";
		    trademembername.setText("會員名稱："+"無");
		  
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    Qponuse = "無";
		    tradecouponuse.setVisibility(View.GONE);
		  
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    QponNo = "無";
		    tradecouponNo.setVisibility(View.GONE);
		   
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    usedenominations = 0;
		    tradeusedenominations.setVisibility(View.GONE);
		  
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradettmoney = globalVariable.settlea_totalmoney;
		    tradetotalmoney.setText("總計金額："+ tradettmoney );

		}
		else if (globalVariable.tradetypeNO == 1){
			
			
			
			showtradetype = (TextView) findViewById(R.id.showtype);
			tradetype = "會員結帳";
			showtradetype.setText(globalVariable.tradetype);
			
			showtradedate = (TextView) findViewById(R.id.trade_date);
			tradedate = sDateFormat.format(new java.util.Date());
			showtradedate.setText("日期："+ tradedate );
			 
			showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
			tradeconsumption = globalVariable.settlea_totalmoney;
			SHOWR();
			showtradeconsumption.setText("消費金錢："+ tradeconsumption);
			
			trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
			tradediscount = globalVariable.settlea_MaxDiscount;
		    trademaxdiscount.setText("折扣上限："+ tradediscount);
		    
		    tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		    if(yorn == 1){
		    	Qpongrant = "有";
		    }else{
		    	Qpongrant = "無";
		    }
		    tradecoupongrant.setText("折價券發放：" + Qpongrant);
		    
		    tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		    //grantdenominations
		    tradegrantdenominations.setText("發放面額：" + grantdenominations);
		    
		    trademembername = (TextView) findViewById(R.id.trade_membername);
		    memname = extras.getString("result");
		    trademembername.setText("會員名稱："+ memname);
		    
		    tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		    Qponuse = "無";
		    tradecouponuse.setText("折價券使用：" + Qponuse);
		    
		    tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		    QponNo = "無";
		    tradecouponNo.setText("折價券序號：" + QponNo);
		    
		    tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		    usedenominations = 0;
		    tradeusedenominations.setText("使用面額：" +  usedenominations);
		    
		    tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		    tradettmoney = globalVariable.settlea_totalmoney - usedenominations;
		    tradetotalmoney.setText("總計金額：" + tradettmoney);
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
		mtradeDb.close();
		//pDialog.dismiss();
	}
	
	
	//DB
	private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			ContentValues newRow = new ContentValues();
			newRow.put(TYPE,tradetype);
			newRow.put(DATE,tradedate);
			newRow.put(CONSUMPTION,tradeconsumption);
			newRow.put(DISCOUNT,tradediscount);
			newRow.put(GRANT,Qpongrant);
			newRow.put(GRANTDENOMINATIONS,grantdenominations);
			newRow.put(MEMNAME,memname);
			newRow.put(QPONUSE,Qponuse);
			newRow.put(QPONNO,QponNo);
			newRow.put(USEDENOMINATIONS,usedenominations);
			newRow.put(TRADETTMONEY,tradettmoney);
			
			mtradeDb.insert(DB_TABLE, null, newRow);
			
			//Toast.makeText(S_Tradedetail.this, "紀錄成功", Toast.LENGTH_SHORT).show();
			
			new CreateNewQpon().execute();
			
		/*	Intent intent = new Intent();
			intent.setClass(S_Tradedetail.this,S_Mainmenu.class);
			startActivity(intent);    //觸發換頁   */
			
		}
    };
    
 
    public void showrules(){
		Cursor c = MyrulesDb.query(true, DB_TBNAME, columns2,null, null, null, null, null, null);
		
		if (c.getCount() != 0) {
			while(c.moveToNext())
			{
				String umoney = c.getString(1);
				String gmoney = c.getString(2);
				int umoneys = Integer.valueOf(umoney);
				int gmoneys = Integer.valueOf(gmoney);
				
				use.add(umoneys);
				grant.add(gmoneys);
			}
			
		    cinum = use.size();
			ciumy = new int[cinum];
			cigmy = new int[cinum];
			
			for (int i = 0; i < cinum ; i++) {
				ciumy[i] = use.get(i);
				cigmy[i] = grant.get(i);
				
				
			}
		}	
			
	}
    
    public void SHOWR(){
    	
    	if (tradeconsumption != 0){
    		savetc = tradeconsumption;
    		Toast.makeText(S_Tradedetail.this, ""+ savetc, Toast.LENGTH_SHORT).show();
    	}else
    	{
    		Toast.makeText(S_Tradedetail.this, "NO THING", Toast.LENGTH_LONG).show();
    	}
    	
    	if(savetc!=0){
    		yorn = 0;
    		grantdenominations=0;
    		
    		for(int i = 0; i < cinum ; i++) {
    			if(savetc >= ciumy[i]){
    				yorn = 1;
    				grantdenominations = cigmy[i];
    				
    			}else{
    				
    			}
    				
    		}
    		
    	}else
    	{
    		Toast.makeText(S_Tradedetail.this, "ERROR", Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    //save Qpon
    class CreateNewQpon extends AsyncTask<String, String, String> {
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(S_Tradedetail.this);
			pDialog.setMessage("Creating Product..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			
			db = new SQLiteHandler_Stores(getApplicationContext());
			 
	        // session manager
	        session = new SessionManager_Stores(getApplicationContext());
	        
	        HashMap<String, String> user = db.getUserDetails();
	        String userid = user.get("email");
			
	        String money = String.valueOf(grantdenominations);
			String username = memname;
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("money", money));
			params.add(new BasicNameValuePair("issue_store", userid));
			params.add(new BasicNameValuePair("username", username));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());
			
			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					//Toast.makeText(S_Tradedetail.this, "保存MYQL成功", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), S_Mainmenu.class);
					startActivity(i);
					
					// closing this screen
					finish();
					
				} else 
				{
					
				}
			}
		
			
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
		
			
			
			return null;
		}

		protected void onPostExecute(String file_url) 
		{
				// dismiss the dialog once done
			   pDialog.dismiss();
	
		}
    	
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Tradedetail.this,S_SettleaCcounts.class);
    	    startActivity(intent);    //觸發換頁
    	    finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    
	
}

