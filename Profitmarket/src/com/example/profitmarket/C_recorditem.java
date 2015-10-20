package com.example.profitmarket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.C_discount_use.DownloadQponData;
import com.example.profitmarket.C_record.DownloadrecordData;

import android.app.Activity;  
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;  
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;  
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.widget.Button;  
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import app.AppConfig;
import helper.SQLiteHandler;
import helper.SessionManager;
import android.widget.TableRow;
import android.widget.TextView;  
import android.widget.TimePicker;
import android.widget.Toast;  

public class C_recorditem extends Activity {
	
	private static final String TAG_SUCCESS = "success";
	 private static final String TAG_ISSUE = "memberrecord";
	 private static final String UID = "uid";
	 private static final String CREATED_DATE = "created_date";
	 private static final String USERNAME = "username";
	 private static final String STORENAME = "storename";
	 private static final String CONSUMPTION = "consumption";
	 private static final String DISCOUNT = "discount";
	 private static final String QPONGRANT = "qpongrant";
	 private static final String GRANTDENOMINATIONS = "grantdenominations";
	 private static final String QPONUSE = "qponuse";
	 private static final String QPONID = "qponid";
	 private static final String USEDENOMINATIONS = "usedenominations";
	 private static final String TOTALMONEY = "totalmoney";
	
	 String uid;
	 
	 private TextView ritxtdate,ritxtstore,ritxtconsumption,ritxtdiscount,
	                  ritxtqpongrant,ritxtgrantdenominations,ritxtqponuse,ritxtqponid,
	                  ritxtusedenominations,ritxttotalmoney;
	 
	 private ProgressDialog pDialog;
		
	 // Creating JSON Parser object
	 JSONParser jParser = new JSONParser();

	 JSONArray records = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_recorditem);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}  
		
		
		ritxtdate = (TextView)findViewById(R.id.c_rittv1);
		
		ritxtstore = (TextView)findViewById(R.id.c_rittv3);
		ritxtconsumption = (TextView)findViewById(R.id.c_rittv4);
		ritxtdiscount = (TextView)findViewById(R.id.c_rittv5);
		ritxtqpongrant = (TextView)findViewById(R.id.c_rittv6);
		ritxtgrantdenominations = (TextView)findViewById(R.id.c_rittv7);
		ritxtqponuse = (TextView)findViewById(R.id.c_rittv8);
		ritxtqponid = (TextView)findViewById(R.id.c_rittv9);
		ritxtusedenominations = (TextView)findViewById(R.id.c_rittv10);
		ritxttotalmoney = (TextView)findViewById(R.id.c_rittv11);
		
		Intent i = getIntent();
		uid = i.getStringExtra(UID);
		

		new DownloadrecordData().execute();
		
		
		//Toast.makeText( C_recorditem.this, uid, Toast.LENGTH_LONG).show();
		
	}
	
	// SHOW RECODE
		class DownloadrecordData extends AsyncTask<String, String, String> {
	        
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(C_recorditem.this);
				pDialog.setMessage("Loading products. Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			@Override
			protected String doInBackground(String... args) {
				// TODO Auto-generated method stub
				
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("uid",uid));
				
		        // getting JSON string from URL
		        JSONObject json = jParser.makeHttpRequest(AppConfig.url_get_memrecorditem, "GET", params);
		        Log.d("Get item Response", json.toString());
				
		        try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Products
						records = json.getJSONArray(TAG_ISSUE);
						
						for (int i = 0; i < records.length(); i++) {
							JSONObject c = records.getJSONObject(i);
							

							String rdate = c.getString(CREATED_DATE);
							//String rusername = c.getString(USERNAME);
							String rstorername = c.getString(STORENAME);
							String rconsumption = c.getString(CONSUMPTION);
							String rdiscoubt = c.getString(DISCOUNT);
							String rqpongrant = c.getString(QPONGRANT);
							String rgrantdenominations = c.getString(GRANTDENOMINATIONS);
							String rqponuse = c.getString(QPONUSE);
							String rqponid = c.getString(QPONID);
							String rusedenominations = c.getString(USEDENOMINATIONS);
							String rtotalmoney = c.getString(TOTALMONEY);
							
							ritxtdate = (TextView)findViewById(R.id.c_rittv1);
							
							ritxtstore = (TextView)findViewById(R.id.c_rittv3);
							ritxtconsumption = (TextView)findViewById(R.id.c_rittv4);
							ritxtdiscount = (TextView)findViewById(R.id.c_rittv5);
							ritxtqpongrant = (TextView)findViewById(R.id.c_rittv6);
							ritxtgrantdenominations = (TextView)findViewById(R.id.c_rittv7);
							ritxtqponuse = (TextView)findViewById(R.id.c_rittv8);
							ritxtqponid = (TextView)findViewById(R.id.c_rittv9);
							ritxtusedenominations = (TextView)findViewById(R.id.c_rittv10);
							ritxttotalmoney = (TextView)findViewById(R.id.c_rittv11);
							
							ritxtdate.setText("日期：" + rdate );
							
							ritxtstore.setText("店家名稱：" + rstorername );
							ritxtconsumption.setText("消費金錢：" + rconsumption );
							ritxtdiscount.setText("折扣上限：" + rdiscoubt );
							ritxtqpongrant.setText("折價券發放：" + rqpongrant );
							ritxtgrantdenominations.setText("發放面額：" + rgrantdenominations );
							ritxtqponuse.setText("折價券使用：" + rqponuse );
							ritxtqponid.setText("折價券序號：" + rqponid );
			                ritxtusedenominations.setText("使用面額：" + rusedenominations );
			                ritxttotalmoney.setText("總金額：" + rtotalmoney );
							
							
						}
					} else {
						
					}
				} catch (JSONException e) {
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
	    	    intent.setClass(C_recorditem.this,C_record.class);
	    	   startActivity(intent);    //觸發換頁
	    	   finish();   //結束本頁
	            
	            return true;
	        }
	        
	        return super.onKeyDown(keyCode, event);
		 }

}
