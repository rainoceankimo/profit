package com.example.profitmarket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import app.AppConfig_Stores;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
	public static final String CONSUMPTION = "consumption";
	public static final String DISCOUNT = "discount";
	public static final String GRANT = "grant";
	public static final String GRANTDENOMINATIONS = "grantdenominations";
	public static final String MEMNAME = "memname";
	public static final String QPONUSE = "Qponuse";
	public static final String QPONNO = "QponNo";
	public static final String USEDENOMINATIONS = "usedenominations";
	public static final String COUNTCONSUMPTION = "countconsumption";
	public static final String TRADETTMONEY = "tradettmoney";
	
	
	String[] columns = 
		{TID,TYPE,DATE,CONSUMPTION,DISCOUNT,GRANT,GRANTDENOMINATIONS,MEMNAME,QPONUSE,QPONNO,USEDENOMINATIONS,COUNTCONSUMPTION,TRADETTMONEY};

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
	int YorN = 0;
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
	JSONParser jParser = new JSONParser();
	JSONParser PParser = new JSONParser();
	
	
	//private static String url_create_product = "http://192.168.43.218/addQpon/add_coupon.php";
	//private static String url_all_products = "http://192.168.43.218/addQpon/getqpontot.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "issueqpon";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_MONEY = "money";
	private static final String TAG_USERNAME = "username";
	public static final String ISSUE_STORE = "issue_store";
	
	
	private TextView showtradetype,showtradedate,showtradeconsumption,trademaxdiscount,
	                 tradecoupongrant,tradegrantdenominations,trademembername,
	                 tradecouponuse,tradecouponNo,tradeusedenominations,tradetotalmoney;

	private String tradetype,tradedate,Qpongrant,memname,Qponuse,QponNo;
	
	private int tradeconsumption = 0,
			    tradediscount = 0,
			    grantdenominations = 0,
			    usedenominations = 0,
			    tradecountconsumption = 0,
			    tradettmoney = 0;

	private Button btnrecode;
	
	
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public String judge,Qponmoney,saveissue_store;
	
	public int qponuseYorN = 0;
	
	ArrayList<HashMap<String, String>> RdQponList;
	
	
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_tradedetail);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		
		AppController globalVariable = (AppController)getApplicationContext();
		Bundle extras = getIntent().getExtras();
		
		
		RdQponList = new ArrayList<HashMap<String, String>>();
		
		
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
	        			          COUNTCONSUMPTION + " TEXT," +
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
		
		showtradetype = (TextView) findViewById(R.id.showtype);
		showtradedate = (TextView) findViewById(R.id.trade_date);
		showtradeconsumption = (TextView) findViewById(R.id.trade_consumption);
		trademaxdiscount = (TextView) findViewById(R.id.trade_maxdiscount);
		tradecoupongrant = (TextView) findViewById(R.id.trade_coupongrant);
		tradegrantdenominations = (TextView) findViewById(R.id.trade_grantdenominations);
		trademembername = (TextView) findViewById(R.id.trade_membername);
		tradecouponuse = (TextView) findViewById(R.id.trade_couponuse);
		tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
		tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
		tradetotalmoney = (TextView) findViewById(R.id.trade_totalmoney);
		
		//讀入Qpon發放規則
		showrules();

		// 交易明細-show
		if (globalVariable.tradetypeNO == 0){

		    tradetype = "一般結帳";
		    showtradetype.setText(globalVariable.tradetype);
		
		    tradedate = sDateFormat.format(new java.util.Date());
		    showtradedate.setText("日期："+ tradedate );

		    tradeconsumption = globalVariable.settlea_totalmoney;
		    showtradeconsumption.setText("消費金錢："+ tradeconsumption);

		    //trademaxdiscount.setText("折扣上限："+ globalVariable.settlea_MaxDiscount);
		    tradediscount = 0;
		    trademaxdiscount.setVisibility(View.GONE);

		    Qpongrant = "無";
		    tradecoupongrant.setVisibility(View.GONE);

		    grantdenominations = 0;
		    tradegrantdenominations.setVisibility(View.GONE);

		    //trademembername.setVisibility(View.GONE);
		    memname = "無";
		    trademembername.setText("會員名稱："+"無");

		    Qponuse = "無";
		    tradecouponuse.setVisibility(View.GONE);

		    QponNo = "無";
		    tradecouponNo.setVisibility(View.GONE);

		    //usedenominations = 0;
		    tradeusedenominations.setVisibility(View.GONE);

		    tradettmoney = globalVariable.settlea_totalmoney;
		    tradetotalmoney.setText("總計金額："+ tradettmoney );

		}
		else if (globalVariable.tradetypeNO == 1){
       
			judge = extras.getString("result");
			
			tradetype = "會員結帳";
			showtradetype.setText(globalVariable.tradetype);

			tradedate = sDateFormat.format(new java.util.Date());
			showtradedate.setText("日期："+ tradedate );

			tradeconsumption = globalVariable.settlea_totalmoney;
			SHOWR();
			showtradeconsumption.setText("消費金錢："+ tradeconsumption);

			tradediscount = globalVariable.settlea_MaxDiscount;
		    trademaxdiscount.setText("折扣上限："+ tradediscount);

		    if(YorN == 1){
		    	Qpongrant = "有";
		    }else{
		    	Qpongrant = "無";
		    }
		    tradecoupongrant.setText("折價券發放：" + Qpongrant);

		    //grantdenominations
		    tradegrantdenominations.setText("發放面額：" + grantdenominations);
		    
		    

		    memname = extras.getString("result");
		   
		    Qponuse = "無";
		    
		    QponNo = "無";
		    
		    usedenominations = 0;
		    
		    
		    
		    new JudgeQponid().execute();
		    
		    //new Createprofitrecord().execute();
		   // Toast.makeText(S_Tradedetail.this, "" + Qponuse, Toast.LENGTH_SHORT).show();
		    
		    
		    // TODO Auto-generated method stub
		    
		    tradettmoney = globalVariable.settlea_totalmoney - usedenominations;
		    tradecountconsumption = globalVariable.settlea_totalmoney - usedenominations;
		    
		    trademembername.setText("會員名稱："+ memname);
		    tradecouponuse.setText("折價券使用：" + Qponuse);
		    tradecouponNo.setText("折價券序號：" + QponNo);
		    tradeusedenominations.setText("使用面額：" +  usedenominations);
		    tradetotalmoney.setText("總計金額：" + tradettmoney);   
		}
		
		
		
		btnrecode = (Button)findViewById(R.id.trade_btn);
		
		btnrecode.setOnClickListener(btnAddOnClick);
	}

	
	//-------------------------------------------------
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
	//-------------------------------------------------
	
	// 讀入Qpon發放規則
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
		}else
		{
			
		}
			
	}
	//-------------------------------------------------
	
	//寫入Qpon發放規則
	public void SHOWR(){
    	
    	if (tradeconsumption != 0){
    		savetc = tradeconsumption;
    		//Toast.makeText(S_Tradedetail.this, ""+ savetc, Toast.LENGTH_SHORT).show();
    	}else
    	{
    		Toast.makeText(S_Tradedetail.this, "NO THING", Toast.LENGTH_SHORT).show();
    	}
    	
    	if(savetc!=0){
    		YorN = 0;
    		grantdenominations=0;
    		
    		for(int i = 0; i < cinum ; i++) {
    			if(savetc >= ciumy[i]){
    				YorN = 1;
    				grantdenominations = cigmy[i];
    				
    			}else{
    				
    			}
    				
    		}
    		
    	}else
    	{
    		Toast.makeText(S_Tradedetail.this, "ERROR", Toast.LENGTH_SHORT).show();
    	}
    	
    }
	//-------------------------------------------------
	
	
	
	
	// SHOW出對話視窗
	public void MyDialog( ){
		MyAlertDialog mDlgLogin = new MyAlertDialog(S_Tradedetail.this);
		mDlgLogin.setTitle("提示!");
		mDlgLogin.setCancelable(true);
		mDlgLogin.setMessage("折價券面額:$"+ usedenominations + " 超過  折扣上限:$" + tradediscount + "\n");
		
		mDlgLogin.setButton(DialogInterface.BUTTON_POSITIVE, "重選折價券", altDlgPositiveBtnOnClk);
		mDlgLogin.setButton(DialogInterface.BUTTON_NEGATIVE, "直接使用", altDlgNegativeBtnOnClk);

		mDlgLogin.show();
	}
	
	// 對話視窗中按 "重選折價券"
    private  DialogInterface.OnClickListener altDlgPositiveBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			Intent intent = new Intent(S_Tradedetail.this,
					S_Capture.class);
            startActivity(intent);
            finish(); 

		}
	};
	
	// 對話視窗中按"直接使用"
	private  DialogInterface.OnClickListener altDlgNegativeBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			usedenominations = tradediscount;
			AppController globalVariable = (AppController)getApplicationContext();
			tradettmoney = globalVariable.settlea_totalmoney - usedenominations;
			
			tradeusedenominations.setText("使用面額：" +  usedenominations);
		    tradetotalmoney.setText("總計金額：" + tradettmoney);
            
		}
	};
	
	//-------------------------------------------------
	
	//SQLite 存店家交易紀錄
	private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
		// TODO Auto-generated method stub
		@Override
		public void onClick(View v) {
			
			
			if( usedenominations > tradediscount ){
				MyDialog( );
				
			}else
			{
		
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
				newRow.put(COUNTCONSUMPTION,tradecountconsumption);
				newRow.put(TRADETTMONEY,tradettmoney);
			
				mtradeDb.insert(DB_TABLE, null, newRow);
			
				//Toast.makeText(S_Tradedetail.this, "紀錄成功", Toast.LENGTH_SHORT).show();
				if (YorN == 1){
					new CreateNewQpon().execute();
				}else
				{
				
				}

				Toast.makeText(S_Tradedetail.this, " "+ qponuseYorN, Toast.LENGTH_SHORT).show();
				
				if(qponuseYorN == 1){
					//new CreateCustomerRcords().execute();
				}else{
					
				}
				
				

			/*	Intent intent = new Intent();
				intent.setClass(S_Tradedetail.this,S_Mainmenu.class);
				startActivity(intent);    //觸發換頁     */
			
			
			}
		}
    };
    //-------------------------------------------------
 
    // keep Customer Rcords
    class CreateCustomerRcords extends AsyncTask<String, String, String> {
        
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(S_Tradedetail.this);
			pDialog.setMessage("Creating Customer Rcords..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
    	
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			db = new SQLiteHandler_Stores(getApplicationContext());
	        session = new SessionManager_Stores(getApplicationContext());
	        
	        HashMap<String, String> user = db.getUserDetails();
	        String userid = user.get("name");
	        
	        String username = memname;
	        String consumption = String.valueOf(tradeconsumption);
	        String discount = String.valueOf(tradediscount);
	        String qpongrant = Qpongrant;
	        String grantqpondenominations = String.valueOf(grantdenominations);
	        String couponuse = Qponuse;
	        String qponid = QponNo;
	        String useqpondenominations = String.valueOf(usedenominations);
	        String totalmoney = String.valueOf(tradettmoney);
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        
	        params.add(new BasicNameValuePair("username", username));
	        params.add(new BasicNameValuePair("storename", userid));
	        params.add(new BasicNameValuePair("consumption", consumption));
	        params.add(new BasicNameValuePair("discount", discount));
	        params.add(new BasicNameValuePair("qpongrant", qpongrant));
	        params.add(new BasicNameValuePair("grantdenominations", grantqpondenominations));
	        params.add(new BasicNameValuePair("qponuse", couponuse));
	        params.add(new BasicNameValuePair("qponid", qponid));
	        params.add(new BasicNameValuePair("usedenominations", useqpondenominations));
	        params.add(new BasicNameValuePair("totalmoney", totalmoney));
	        
	        JSONObject json = jsonParser.makeHttpRequest(AppConfig_Stores.URL_create_ctrrecords, "POST", params);
	        
	        Log.d("Create ctrrecords", json.toString());
	        
	        try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
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
    
    //-------------------------------------------------
    
    // keep profit record
    class Createprofitrecord extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			db = new SQLiteHandler_Stores(getApplicationContext());
	        session = new SessionManager_Stores(getApplicationContext());
	        
	        HashMap<String, String> user = db.getUserDetails();
	        String userid = user.get("name");
	        
	        String couponid = QponNo;
	        String kmoney = String.valueOf(usedenominations);
	        
	        double countmoney = (double) usedenominations * 0.05;
	        
	        String kprofitmoney = String.valueOf(countmoney);
	        String issue_store = saveissue_store ;
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("issue_store", issue_store));
	        params.add(new BasicNameValuePair("couponid", couponid));
	        params.add(new BasicNameValuePair("money", kmoney));
	        params.add(new BasicNameValuePair("profitmoney", kprofitmoney));
	        params.add(new BasicNameValuePair("receive_store", userid));
	        
	        
	        JSONObject jsonp = PParser.makeHttpRequest(AppConfig_Stores.URL_create_profitrecord, "POST", params);
	        Log.d("Create profitrecords", jsonp.toString());
	        
	        try {
	        	
				int success = jsonp.getInt(TAG_SUCCESS);

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
    
    //-------------------------------------------------
    
    //save Qpon
    class CreateNewQpon extends AsyncTask<String, String, String> {
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(S_Tradedetail.this);
			pDialog.setMessage("Creating New Qpon..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			db = new SQLiteHandler_Stores(getApplicationContext());
	        session = new SessionManager_Stores(getApplicationContext());
	        
	        HashMap<String, String> user = db.getUserDetails();
	        String userid = user.get("name");
			
	        String money = String.valueOf(grantdenominations);
			String username = memname;
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("money", money));
			params.add(new BasicNameValuePair("issue_store", userid));
			params.add(new BasicNameValuePair("username", username));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(AppConfig_Stores.url_create_tdaddcoupon, "POST", params);

			// check log cat fro response
			Log.d("Create Qpon", json.toString());
			
			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
				/*	//Toast.makeText(S_Tradedetail.this, "保存MYQL成功", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), S_Mainmenu.class);
					startActivity(i);
					
					// closing this screen
					finish();   */
					
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
    //-------------------------------------------------
    
    
    // judge qponid
    class JudgeQponid extends AsyncTask<String, String, String> {
        
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(S_Tradedetail.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			
			runOnUiThread(new Runnable() {
                public void run() {

                	int success;
                	
                	try {
                		String couponid = judge;
                		List<NameValuePair> params = new ArrayList<NameValuePair>();
                    	params.add(new BasicNameValuePair("couponid",couponid));
    	        
                    	JSONObject json = jParser.makeHttpRequest(AppConfig_Stores.URL_GET_TDCOUPON, "GET", params);
                    	Log.d("gogogo", json.toString());
                		
                    	// TODO Auto-generated method stub
                		// Checking for SUCCESS TAG
                		 success = json.getInt(TAG_SUCCESS);

                		if (success == 1) {
                			// products found
                			// Getting Array of Products
                			JSONArray couponObj = json.getJSONArray(TAG_ISSUE);
                			//coupons = json.getJSONArray(TAG_ISSUE);
					
					
                			JSONObject c = couponObj.getJSONObject(0);

                			trademembername = (TextView) findViewById(R.id.trade_membername);
                			tradecouponNo = (TextView) findViewById(R.id.trade_couponNo);
                			tradeusedenominations = (TextView) findViewById(R.id.trade_usedenominations);
						
                			trademembername.setText("會員名稱："+ c.getString(TAG_USERNAME));
                			tradecouponuse.setText("折價券使用：" + "有");
                			tradecouponNo.setText("折價券序號："+ judge);
                			tradeusedenominations.setText("使用面額：" +c.getString(TAG_MONEY));

                			AppController globalVariable = (AppController)getApplicationContext();
                			tradettmoney = globalVariable.settlea_totalmoney - Integer.valueOf(c.getString(TAG_MONEY));
                			tradetotalmoney.setText("總計金額：" + tradettmoney);
                			
                			
                			saveissue_store = c.getString(ISSUE_STORE);
                			qponuseYorN = 1;
                			Qponuse = "有";
           					memname = c.getString(TAG_USERNAME);
           					QponNo = judge;
           					usedenominations = Integer.valueOf(c.getString(TAG_MONEY));
                			
                			
           					
           					
                	/*		String qponid = judge;
                			String money =  c.getString(TAG_MONEY);
                			String username = c.getString(TAG_USERNAME);
                			
                		    
                		    
                		    HashMap<String, String> map = new HashMap<String, String>();
                		    map.put(TAG_COUPONID,qponid);
                		    map.put(TAG_MONEY,money);
                		    map.put(TAG_USERNAME,username);
                		    
                		    
                		    RdQponList.add(map);     */
						
                		} else {
					
                		}
                	} catch (JSONException e) {
                			e.printStackTrace();
                	} 
	        
                }
            });
 
            return null;
        }
				
		
        protected void onPostExecute(String file_url) 
        {            
        	// dismiss the dialog once done
            pDialog.dismiss();
         }	
    }	
    //-------------------------------------------------	
 
    
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

