package com.example.profitmarket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Records_ShareGet.DownloadProfitRecord;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import app.AppConfig_Stores;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Records_SharegIssue extends ListActivity {
    
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	
	private ProgressDialog pDialog;
	
	 private TextView tvDate, tvTime;  
	 private Button btDate, btTime; 

	JSONParser jsonParser = new JSONParser();
	
	//JSONArray
	JSONArray profitrecord = null;
	
	ArrayList<HashMap<String, String>> profitrecordList;
	ArrayList<HashMap<String, String>> profitrecordList2;
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "profitrecord";
	private static final String TAG_UID = "uid";
	private static final String TAG_ISSUE_STORE = "issue_store";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_MONEY = "money";
	private static final String TAG_PROFITMONEY = "profitmoney";
	private static final String TAG_RECEIVE_STORE = "receive_store";
	private static final String TAG_CREATED_DATE = "created_date";
	
	String uid,date,issuestore,qponid,money,profitmoney,receivestore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_records_sharegissue);
		
		db = new SQLiteHandler_Stores(getApplicationContext());
		 
        // session manager
        session = new SessionManager_Stores(getApplicationContext());
		
		profitrecordList = new ArrayList<HashMap<String, String>>();
		profitrecordList2 = new ArrayList<HashMap<String, String>>();
		
		new DownloadProfitRecord().execute();
	
		tvDate = (TextView) findViewById(R.id.S_receiveDate_issue); 
		btDate = (Button) findViewById(R.id.S_btreceiveDate_issue);
		btDate.setOnClickListener(new Button.OnClickListener() {  
			   public void onClick(View v) {  
				   
				   final DatePicker datePicker = new DatePicker(S_Records_SharegIssue.this);  
		           datePicker.setCalendarViewShown(false);  

		           //
		           try {  
		               Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");  
		               daySpinner.setAccessible(true);  
		               ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);  
		           } catch (NoSuchFieldException e) {  
		               e.printStackTrace();  
		           } catch (IllegalArgumentException e) {  
		               e.printStackTrace();  
		           } catch (IllegalAccessException e) {  
		               e.printStackTrace();  
		           }  
		           final Calendar	curCalendar = Calendar.getInstance();  
		           datePicker.init(curCalendar.get(Calendar.YEAR),  
		           curCalendar.get(Calendar.MONTH),  
		           curCalendar.get(Calendar.DAY_OF_MONTH),null);  

		           AlertDialog.Builder	builder = new AlertDialog.Builder(S_Records_SharegIssue.this);  
		           builder.setView(datePicker);  
		           builder.setTitle("請選擇日期");  
		           builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			        	public void onClick(DialogInterface dialog, int id) {	        
			        		int yearss =datePicker.getYear();
			    			String years = Integer.toString(yearss); 
			    			int month = (datePicker.getMonth()+1);
			    			String months = Integer.toString(month);
			    			 profitrecordList.clear();
			        		for(int i=0;i< profitrecordList2.size();i++){
			        		//Intent intime = new Intent();
			    			//intime.setClass(C_record.this,S_Analysis_Sources.class);
			    			String yr=profitrecordList2.get(i).get("YEAR(created_date)");
			    			String mh=profitrecordList2.get(i).get("MONTH(created_date)");
			    			int yr2 = Integer.parseInt(yr);
			    			int mh2 = Integer.parseInt(mh);
			    			if(yearss-yr2==0&&month-mh2==0){
			    			
			    				String uid2 = profitrecordList2.get(i).get(TAG_UID);
								String date2 = profitrecordList2.get(i).get(TAG_CREATED_DATE);							
								String storename2 = profitrecordList2.get(i).get(TAG_ISSUE_STORE);							
								String consumption2 =  profitrecordList2.get(i).get(TAG_COUPONID);													
								String discount2 = profitrecordList2.get(i).get(TAG_MONEY);														
								String qpongrant2 = profitrecordList2.get(i).get(TAG_PROFITMONEY);														
								String grantdenominations2 = profitrecordList2.get(i).get(TAG_RECEIVE_STORE);													
							
			    				HashMap<String, String> map3 = new HashMap<String, String>();		    				
			    				map3.put(TAG_UID,uid2);
								map3.put(TAG_CREATED_DATE,date2);
								map3.put(TAG_ISSUE_STORE,storename2);
								map3.put(TAG_COUPONID,consumption2);
								map3.put(TAG_MONEY,discount2);
								map3.put(TAG_PROFITMONEY,qpongrant2);
								map3.put(TAG_RECEIVE_STORE,grantdenominations2);
								//recordsList3.add(map3);
								
								
								 profitrecordList.add(map3);
								SimpleAdapter adapter2 = new SimpleAdapter(
								 S_Records_SharegIssue.this, profitrecordList,
								R.layout.s_records_sharegissue_list, new String[] { TAG_UID,
								TAG_CREATED_DATE,TAG_ISSUE_STORE,TAG_COUPONID,TAG_MONEY,
								TAG_PROFITMONEY,TAG_RECEIVE_STORE},
								new int[] { R.id.rsissuettv1, R.id.rsissuettv2, R.id.rsissuettv3, R.id.rsissuettv4,
								R.id.rsissuettv5,R.id.rsissuettv6,R.id.rsissuettv7 });
									// updating listview
									setListAdapter(adapter2);
									adapter2.notifyDataSetChanged();
			    				
			    			}
			    			  tvDate.setText(yearss+"-"+month);
			        		}	
			        	}
			        });
		           
		           
		           AlertDialog	dialog = builder.create();  
		           dialog.setCanceledOnTouchOutside(true);  
		           dialog.show();  

		       
		            
				   
			         
			   }  
			});
		
		
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
			pDialog = new ProgressDialog(S_Records_SharegIssue.this);
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
			String receive_store = user.get("name");
				        
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("receive_store",receive_store));
			
			JSONObject json = jsonParser.makeHttpRequest(AppConfig_Stores.url_get_profitrecordIssue, "GET", params);
	                                                                       
	        Log.d("get profitrecords issue", json.toString());
	        
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
						String YEARcreated_date = c.getString("YEAR(created_date)");
						String YEARcreated_date2 = c.getString("YEAR(created_date)");
						
						String MONTHcreated_date = c.getString("MONTH(created_date)");
						String MONTHcreated_date2 = c.getString("MONTH(created_date)");
						
						
						HashMap<String, String> map = new HashMap<String, String>();
						HashMap<String, String> map2 = new HashMap<String, String>();
						
						
						map.put(TAG_UID,uid);
						map.put(TAG_CREATED_DATE,date);
						map.put(TAG_ISSUE_STORE,issuestore);
						map.put(TAG_COUPONID,qponid);
						map.put(TAG_MONEY,money);
						map.put(TAG_PROFITMONEY,profitmoney);
						map.put(TAG_RECEIVE_STORE,receivestore);
						map.put("YEAR(created_date)",YEARcreated_date);
						map.put("MONTH(created_date)",MONTHcreated_date);
						
						profitrecordList.add(map);
						
						map2.put(TAG_UID,uid);
						map2.put(TAG_CREATED_DATE,date);
						map2.put(TAG_ISSUE_STORE,issuestore);
						map2.put(TAG_COUPONID,qponid);
						map2.put(TAG_MONEY,money);
						map2.put(TAG_PROFITMONEY,profitmoney);
						map2.put(TAG_RECEIVE_STORE,receivestore);
						map2.put("YEAR(created_date)",YEARcreated_date);
						map2.put("MONTH(created_date)",MONTHcreated_date);
						
						profitrecordList2.add(map2);
						
						
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
			   
			   S_Records_SharegIssue.this.runOnUiThread(new Runnable() {
				   @Override
				   public void run() {
					   // TODO Auto-generated method stub
					   SimpleAdapter adapter = new SimpleAdapter(
							   S_Records_SharegIssue.this, profitrecordList,
								R.layout.s_records_sharegissue_list, new String[] { TAG_UID,
										TAG_CREATED_DATE,TAG_ISSUE_STORE,TAG_COUPONID,TAG_MONEY,
										TAG_PROFITMONEY,TAG_RECEIVE_STORE},
								new int[] { R.id.rsissuettv1, R.id.rsissuettv2, R.id.rsissuettv3, R.id.rsissuettv4,
										R.id.rsissuettv5,R.id.rsissuettv6,R.id.rsissuettv7 });
					   // updating listview
					   setListAdapter(adapter);
						
					   ListView lv = getListView();
					   lv.setDivider(null);
					   lv.setOnItemClickListener(new OnItemClickListener() {

						   	@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								// TODO Auto-generated method stub
								
								String uids = ((TextView) view.findViewById(R.id.rsissuettv1)).getText().toString();
								String dates = ((TextView) view.findViewById(R.id.rsissuettv2)).getText().toString();
								String issuestores = ((TextView) view.findViewById(R.id.rsissuettv3)).getText().toString();
								String qponids = ((TextView) view.findViewById(R.id.rsissuettv4)).getText().toString();
								String moneys = ((TextView) view.findViewById(R.id.rsissuettv5)).getText().toString();
								String profitmoneys = ((TextView) view.findViewById(R.id.rsissuettv6)).getText().toString();
								String receivestores = ((TextView) view.findViewById(R.id.rsissuettv7)).getText().toString();
								
								Intent in = new Intent(S_Records_SharegIssue.this,
										S_Records_SharegIssue_item.class);
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__records__shareg_issue, menu);
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
    	    intent.setClass(S_Records_SharegIssue.this,S_Records_InquireShare.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
