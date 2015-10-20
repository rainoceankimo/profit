package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import app.AppConfig_Stores;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import app.AppController;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.EditText;
import android.widget.TextView;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Records_ShareGet extends ListActivity {
	
	//MYSQL- issueQpon
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	
	//JSONArray
	JSONArray profitrecord = null;
	
	ArrayList<HashMap<String, String>> profitrecordList;
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "profitrecord";
	private static final String TAG_UID = "uid";
	private static final String TAG_ISSUE_STORE = "issue_store";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_MONEY = "money";
	private static final String TAG_PROFITMONEY = "profitmoney";
	private static final String TAG_RECEIVE_STORE = "receive_store";
	private static final String TAG_CREATED_DATE = "created_date";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_records_shareget);
		
		// SqLite database handler
        db = new SQLiteHandler_Stores(getApplicationContext());
 
        // session manager
        session = new SessionManager_Stores(getApplicationContext());
		
		profitrecordList = new ArrayList<HashMap<String, String>>();
		
		new DownloadProfitRecord().execute();
		
		

		
	}
	
	/* 
	*  
        get profit record
	*
	*/
	class DownloadProfitRecord extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(S_Records_ShareGet.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			// SqLite database handler
			db = new SQLiteHandler_Stores(getApplicationContext());
	        // session manager
	        session = new SessionManager_Stores(getApplicationContext());
	        
	        HashMap<String, String> user = db.getUserDetails();
	        String issue_store = user.get("name");
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("issue_store",issue_store));
			
	        JSONObject json = jsonParser.makeHttpRequest(AppConfig_Stores.url_get_profitrecord, "GET", params);
	        
	        Log.d("get profitrecords", json.toString());
	        
	        try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Products
						profitrecord = json.getJSONArray(TAG_ISSUE);
					
						for (int i = 0; i < profitrecord.length(); i++) {
							JSONObject c = profitrecord.getJSONObject(i);
						    
							// TODO Auto-generated method stub
							String uid = c.getString(TAG_UID);
							String date = c.getString(TAG_CREATED_DATE);
							String issuestore = c.getString(TAG_ISSUE_STORE);
							String qponid = c.getString(TAG_COUPONID);
							String money = c.getString(TAG_MONEY);
							String profitmoney = c.getString(TAG_PROFITMONEY);
							String receivestore = c.getString(TAG_RECEIVE_STORE);

							HashMap<String, String> map = new HashMap<String, String>();
							
							map.put(TAG_UID,uid);
							map.put(TAG_CREATED_DATE,date);
							map.put(TAG_ISSUE_STORE,issuestore);
							map.put(TAG_COUPONID,qponid);
							map.put(TAG_MONEY,money);
							map.put(TAG_PROFITMONEY,profitmoney);
							map.put(TAG_RECEIVE_STORE,receivestore);
							
							
							profitrecordList.add(map);
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
			   
			   S_Records_ShareGet.this.runOnUiThread(new Runnable() {
				   @Override
				   public void run() {
					   // TODO Auto-generated method stub
					   ListAdapter adapter = new SimpleAdapter(
							   S_Records_ShareGet.this, profitrecordList,
								R.layout.s_records_shareget_list, new String[] { TAG_UID,
										TAG_CREATED_DATE,TAG_ISSUE_STORE,TAG_COUPONID,TAG_MONEY,
										TAG_PROFITMONEY,TAG_RECEIVE_STORE},
								new int[] { R.id.rslttv1, R.id.rslttv2, R.id.rslttv3, R.id.rslttv4,
										R.id.rslttv5,R.id.rslttv6,R.id.rslttv7 });
						// updating listview
						setListAdapter(adapter);

						ListView lv = getListView();
						lv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								// TODO Auto-generated method stub
								
								String uids = ((TextView) view.findViewById(R.id.rslttv1)).getText().toString();
								String dates = ((TextView) view.findViewById(R.id.rslttv2)).getText().toString();
								String issuestores = ((TextView) view.findViewById(R.id.rslttv3)).getText().toString();
								String qponids = ((TextView) view.findViewById(R.id.rslttv4)).getText().toString();
								String moneys = ((TextView) view.findViewById(R.id.rslttv5)).getText().toString();
								String profitmoneys = ((TextView) view.findViewById(R.id.rslttv6)).getText().toString();
								String receivestores = ((TextView) view.findViewById(R.id.rslttv7)).getText().toString();
								
								Intent in = new Intent(S_Records_ShareGet.this,
										S_Records_ShareGet_item.class);
								// sending pid to next activity
								in.putExtra(TAG_UID, uids);
								in.putExtra(TAG_CREATED_DATE, dates);
								in.putExtra(TAG_ISSUE_STORE, issuestores);
								in.putExtra(TAG_COUPONID, qponids);
								in.putExtra(TAG_MONEY, moneys);
								in.putExtra(TAG_PROFITMONEY, profitmoneys);
								in.putExtra(TAG_RECEIVE_STORE, receivestores);
								
								
								startActivityForResult(in, 100);
							}
							
						}); 
				   }
			   });
		}
	}

	/* 
	*  
        call profit record
	*
	*/
	public void clicklist(){
		
		
	}
    
	//原程式碼
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__records__share_get, menu);
		return true;
	}
	
	//原程式碼
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
	
	 /* 
	 *  
                    返回上一頁	  
	 *
	 */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Records_ShareGet.this,S_Records_InquireShare.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
