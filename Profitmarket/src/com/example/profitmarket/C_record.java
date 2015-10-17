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
import android.widget.AdapterView;
import android.widget.Button;  
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import app.AppConfig;
import helper.SQLiteHandler;
import helper.SessionManager;
import android.widget.TableRow;
import android.widget.TextView;  
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;  

public class C_record extends ListActivity {

	 private TextView tvDate, tvTime;  
	 private Button btDate, btTime;  
	 private int mYear, mMonth, mDay, mHour, mMinute; 
	 
	 private SQLiteHandler db;
	 private SessionManager session;

	 private ProgressDialog pDialog;
		
	 // Creating JSON Parser object
	 JSONParser jParser = new JSONParser();

	 ArrayList<HashMap<String, String>> recordsList;
	 
	 JSONArray records = null;
	 
	 private static final String TAG_SUCCESS = "success";
	 private static final String TAG_ISSUE = "memberrecord";
	 private static final String UID = "uid";
	 private static final String CREATED_DATE = "created_date";
	 private static final String USERNAME = "username";
	 private static final String STORENAME = "storename";
	 private static final String CONSUMPTION = "consumption";
	 private static final String DISCOUBT = "discount";
	 private static final String QPONGRANT = "qpongrant";
	 private static final String GRANTDENOMINATIONS = "grantdenominations";
	 private static final String QPONUSE = "qponuse";
	 private static final String QPONID = "qponid";
	 private static final String USEDENOMINATIONS = "usedenominations";
	 private static final String TOTALMONEY = "totalmoney";
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_record);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		// SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
 
        // session manager
        session = new SessionManager(getApplicationContext());
        
        recordsList = new ArrayList<HashMap<String, String>>();

		
		tvDate = (TextView) findViewById(R.id.tvDate);  
		btDate = (Button) findViewById(R.id.btDate);
		
		
		new DownloadrecordData().execute();

		//選擇日期
		btDate.setOnClickListener(new Button.OnClickListener() {  
		   public void onClick(View v) {  
		      showDatePickerDialog();            
		   }  
		});
		// ------------------
		
		clicklist();
		
		
	}
	
    // --------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_record, menu);
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
	// -------------------
	
	
	// SHOW RECODE
	class DownloadrecordData extends AsyncTask<String, String, String> {
        
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(C_record.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			// SqLite database handler
	        db = new SQLiteHandler(getApplicationContext());
	        // session manager
	        session = new SessionManager(getApplicationContext());
	        HashMap<String, String> user = db.getUserDetails();
	        String username = user.get("email");
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("username",username));
			
	        // getting JSON string from URL
	        JSONObject json = jParser.makeHttpRequest(AppConfig.url_get_memrecord, "GET", params);
	        Log.d("Get Response rd", json.toString());
			
	        try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					records = json.getJSONArray(TAG_ISSUE);
					
					for (int i = 0; i < records.length(); i++) {
						JSONObject c = records.getJSONObject(i);
						
						String uid = c.getString(UID);
						String date = c.getString(CREATED_DATE);
						
						HashMap<String, String> map = new HashMap<String, String>();
						
						map.put(UID,uid);
						map.put(CREATED_DATE,date);
						
						recordsList.add(map);
						
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
			   
			   C_record.this.runOnUiThread(new Runnable() {
				   @Override
				   public void run() {
					   // TODO Auto-generated method stub
					   ListAdapter adapter = new SimpleAdapter(
							   C_record.this, recordsList,
								R.layout.activity_c_recodelist, new String[] { UID,
										CREATED_DATE},
								new int[] { R.id.c_rlttv1, R.id.c_rlttv2 });
						// updating listview
						setListAdapter(adapter);
					   
					   
				   }
			   });
			   
		}		   
		
	}
	// ------------------
	
	// List view click
	public void clicklist(){
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				String pid1 = ((TextView) view.findViewById(R.id.c_rlttv1)).getText()
						.toString();
				
				Intent in = new Intent(C_record.this,
						C_recorditem.class);
				// sending pid to next activity
				in.putExtra(UID, pid1);
				
				startActivityForResult(in, 100);
			}
			
		}); 
		
	}
	
	
	// ------------------
	
	// choose date
	public void showDatePickerDialog() {  
		  // 設定初始日期  
		  final Calendar c = Calendar.getInstance();  
		  mYear = c.get(Calendar.YEAR);  
		  mMonth = c.get(Calendar.MONTH);  
		  mDay = c.get(Calendar.DAY_OF_MONTH);  
		  
		  // 跳出日期選擇器  
		  DatePickerDialog dpd = new DatePickerDialog(this,  
		    new DatePickerDialog.OnDateSetListener() {  
		     public void onDateSet(DatePicker view, int year,  
		       int monthOfYear, int dayOfMonth) {  
		      // 完成選擇，顯示日期  
		      tvDate.setText(year + "-" + (monthOfYear + 1) + "-"  
		        + dayOfMonth);  
		  
		     }  
		    }, mYear, mMonth, mDay);  
		  dpd.show();  
	}  	
	// ---------------
	 
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_record.this,C_mem_view.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
	 }
}
