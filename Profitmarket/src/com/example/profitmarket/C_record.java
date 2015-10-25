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

import com.example.profitmarket.C_discount_use.DownloadQponData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;  

public class C_record extends ListActivity {

	 private TextView tvDate, tvTime;  
	 private Button btDate, btTime;  
	 private int mYear, mMonth, mDay, mHour, mMinute; 
	 public int year;
	 private SQLiteHandler db;
	 private SessionManager session;

	 private ProgressDialog pDialog;
	
	 // Creating JSON Parser object
	 JSONParser jParser = new JSONParser();

	 ArrayList<HashMap<String, String>> recordsList;
	 ArrayList<HashMap<String, String>> recordsList2;
	 ArrayList<HashMap<String, String>> recordsList3;
	 JSONArray records = null;
	 
	 private static final String TAG_SUCCESS = "success";
	 private static final String TAG_ISSUE = "memberrecord";
	 private static final String UID = "uid";
	 private static final String CREATED_DATE = "created_date";
	 //private static final String USERNAME = "username";
	 private static final String STORENAME = "storename";
	 private static final String CONSUMPTION = "consumption";
	 private static final String DISCOUNT = "discount";
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
        recordsList2 = new ArrayList<HashMap<String, String>>();
        recordsList3 = new ArrayList<HashMap<String, String>>();
		
		tvDate = (TextView) findViewById(R.id.tvDate);  
		btDate = (Button) findViewById(R.id.btDate);
		
		
		new DownloadrecordData().execute();
		
		//選擇日期
		btDate.setOnClickListener(new Button.OnClickListener() {  
		   public void onClick(View v) {  
			   
			   final DatePicker datePicker = new DatePicker(C_record.this);  
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

	           AlertDialog.Builder	builder = new AlertDialog.Builder(C_record.this);  
	           builder.setView(datePicker);  
	           builder.setTitle("請選擇日期");  
	           builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int id) {	        
		        		int yearss =datePicker.getYear();
		    			String years = Integer.toString(yearss); 
		    			int month = (datePicker.getMonth()+1);
		    			String months = Integer.toString(month);
		        		for(int i=0;i< recordsList2.size();i++){
		        		//Intent intime = new Intent();
		    			//intime.setClass(C_record.this,S_Analysis_Sources.class);
		    			String yr=recordsList2.get(i).get("YEAR(created_date)");
		    			String mh=recordsList2.get(i).get("MONTH(created_date)");
		    			int yr2 = Integer.parseInt(yr);
		    			int mh2 = Integer.parseInt(mh);
		    			if(yearss-yr2==0&&month-mh2==0){
		    			
		    				String uid2 = recordsList2.get(i).get(UID);
							String date2 = recordsList2.get(i).get(CREATED_DATE);							
							String storename2 = recordsList2.get(i).get(STORENAME);							
							String consumption2 =  recordsList2.get(i).get(CONSUMPTION);													
							String discount2 = recordsList2.get(i).get(DISCOUNT);														
							String qpongrant2 = recordsList2.get(i).get(QPONGRANT);														
							String grantdenominations2 = recordsList2.get(i).get(GRANTDENOMINATIONS);													
							String qponuse2 = recordsList2.get(i).get(QPONUSE);														
							String qponid2 = recordsList2.get(i).get(QPONID);						 							
							String usedenominations2 = recordsList2.get(i).get(USEDENOMINATIONS);														
							String totalmoney2 = recordsList2.get(i).get(TOTALMONEY);		
		    				HashMap<String, String> map3 = new HashMap<String, String>();		    				
		    				map3.put(UID,uid2);
							map3.put(CREATED_DATE,date2);
							map3.put(STORENAME,storename2);
							map3.put(CONSUMPTION,consumption2);
							map3.put(DISCOUNT,discount2);
							map3.put(QPONGRANT,qpongrant2);
							map3.put(GRANTDENOMINATIONS,grantdenominations2);
							map3.put(QPONUSE,qponuse2);
							map3.put(QPONID,qponid2);
							map3.put(USEDENOMINATIONS,usedenominations2);
							map3.put(TOTALMONEY,totalmoney2);
							//recordsList3.add(map3);
							
							recordsList.clear();
							recordsList.add(map3);
							SimpleAdapter adapter2 = new SimpleAdapter(
									   C_record.this, recordsList,
										R.layout.activity_c_recodelist, new String[] {UID,
												CREATED_DATE,STORENAME,CONSUMPTION,DISCOUNT,
												QPONGRANT,GRANTDENOMINATIONS,QPONUSE,QPONID,
												USEDENOMINATIONS,TOTALMONEY},
										new int[] { R.id.c_rlttv1, R.id.c_rlttv2, R.id.c_rlttv3, 
													R.id.c_rlttv4, R.id.c_rlttv5, R.id.c_rlttv6, 
													R.id.c_rlttv7, R.id.c_rlttv8, R.id.c_rlttv9, 
													R.id.c_rlttv10, R.id.c_rlttv11 });
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
		// ------------------
		
		
		
		
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
			pDialog.setMessage("Loading records. Please wait...");
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
						String uid2 = c.getString(UID);
						
						String date = c.getString(CREATED_DATE);
						String date2 = c.getString(CREATED_DATE);
						
						String storename = c.getString(STORENAME);
						String storename2 = c.getString(STORENAME);
						
						String consumption = c.getString(CONSUMPTION);
						String consumption2 = c.getString(CONSUMPTION);
						
						String discount = c.getString(DISCOUNT);
						String discount2 = c.getString(DISCOUNT);
						
						String qpongrant = c.getString(QPONGRANT);
						String qpongrant2 = c.getString(QPONGRANT);
						
						String grantdenominations = c.getString(GRANTDENOMINATIONS);
						String grantdenominations2 = c.getString(GRANTDENOMINATIONS);
						
						String qponuse = c.getString(QPONUSE);
						String qponuse2 = c.getString(QPONUSE);
						
						String qponid = c.getString(QPONID);
						String qponid2 = c.getString(QPONID);
						
						String usedenominations = c.getString(USEDENOMINATIONS);
						String usedenominations2 = c.getString(USEDENOMINATIONS);
						
						String totalmoney = c.getString(TOTALMONEY);
						String totalmoney2 = c.getString(TOTALMONEY);
						
						String YEARcreated_date = c.getString("YEAR(created_date)");
						String YEARcreated_date2 = c.getString("YEAR(created_date)");
						
						String MONTHcreated_date = c.getString("MONTH(created_date)");
						String MONTHcreated_date2 = c.getString("MONTH(created_date)");
						
						HashMap<String, String> map = new HashMap<String, String>();
						HashMap<String, String> map2 = new HashMap<String, String>();
						
						map.put(UID,uid);
						map.put(CREATED_DATE,date);
						map.put(STORENAME,storename);
						map.put(CONSUMPTION,consumption);
						map.put(DISCOUNT,discount);
						map.put(QPONGRANT,qpongrant);
						map.put(GRANTDENOMINATIONS,grantdenominations);
						map.put(QPONUSE,qponuse);
						map.put(QPONID,qponid);
						map.put(USEDENOMINATIONS,usedenominations);
						map.put(TOTALMONEY,totalmoney);
						map.put("YEAR(created_date)",YEARcreated_date);
						map.put("MONTH(created_date)",MONTHcreated_date);
						
						recordsList.add(map);
						
						map2.put(UID,uid);
						map2.put(CREATED_DATE,date);
						map2.put(STORENAME,storename);
						map2.put(CONSUMPTION,consumption);
						map2.put(DISCOUNT,discount);
						map2.put(QPONGRANT,qpongrant);
						map2.put(GRANTDENOMINATIONS,grantdenominations);
						map2.put(QPONUSE,qponuse);
						map2.put(QPONID,qponid);
						map2.put(USEDENOMINATIONS,usedenominations);
						map2.put(TOTALMONEY,totalmoney);
						map2.put("YEAR(created_date)",YEARcreated_date);
						map2.put("MONTH(created_date)",MONTHcreated_date);
						
						recordsList2.add(map2);
						
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
					   SimpleAdapter adapter = new SimpleAdapter(
							   C_record.this, recordsList,
								R.layout.activity_c_recodelist, new String[] { UID,
										CREATED_DATE,STORENAME,CONSUMPTION,DISCOUNT,
										QPONGRANT,GRANTDENOMINATIONS,QPONUSE,QPONID,
										USEDENOMINATIONS,TOTALMONEY},
								new int[] { R.id.c_rlttv1, R.id.c_rlttv2, R.id.c_rlttv3, 
											R.id.c_rlttv4, R.id.c_rlttv5, R.id.c_rlttv6, 
											R.id.c_rlttv7, R.id.c_rlttv8, R.id.c_rlttv9, 
											R.id.c_rlttv10, R.id.c_rlttv11 });
						// updating listview
						setListAdapter(adapter);
						
						ListView lv = getListView();
						//lv.setDivider(null);
						lv.setOnItemClickListener(new OnItemClickListener() {
						
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								// TODO Auto-generated method stub
						
								String uids = ((TextView) view.findViewById(R.id.c_rlttv1)).getText().toString();
								String dates = ((TextView) view.findViewById(R.id.c_rlttv2)).getText().toString();
								String storenames = ((TextView) view.findViewById(R.id.c_rlttv3)).getText().toString();
								String consumptions = ((TextView) view.findViewById(R.id.c_rlttv4)).getText().toString();
								String discounts = ((TextView) view.findViewById(R.id.c_rlttv5)).getText().toString();
								String qpongrants = ((TextView) view.findViewById(R.id.c_rlttv6)).getText().toString();
								String grantdenominationses = ((TextView) view.findViewById(R.id.c_rlttv7)).getText().toString();
								String qponuses = ((TextView) view.findViewById(R.id.c_rlttv8)).getText().toString();
								String qponids = ((TextView) view.findViewById(R.id.c_rlttv9)).getText().toString();
								String usedenominationses = ((TextView) view.findViewById(R.id.c_rlttv10)).getText().toString();
								String totalmoneys = ((TextView) view.findViewById(R.id.c_rlttv11)).getText().toString();
								
								Intent in = new Intent(C_record.this,
										C_recorditem.class);
								// sending pid to next activity
								in.putExtra(UID, uids);
								in.putExtra(CREATED_DATE, dates);
								in.putExtra(STORENAME, storenames);
								in.putExtra(CONSUMPTION, consumptions);
								in.putExtra(DISCOUNT, discounts);
								in.putExtra(QPONGRANT, qpongrants);
								in.putExtra(GRANTDENOMINATIONS, grantdenominationses);
								in.putExtra(QPONUSE, qponuses);
								in.putExtra(QPONID, qponids);
								in.putExtra(USEDENOMINATIONS, usedenominationses);
								in.putExtra(TOTALMONEY, totalmoneys);
								
								startActivityForResult(in, 100);			
				   		    }
					
					}); 
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
				
				String pid1 = ((TextView) view.findViewById(R.id.c_rlttv1)).getText().toString();
				
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
	/* class DownloadrecordData2 extends AsyncTask<String, String, String> {
	        
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(C_record.this);
				pDialog.setMessage("Loading records. Please wait...");
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
		        
		        Log.d("Get Response rd2", json.toString());
				
		        try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Products
						records = json.getJSONArray(TAG_ISSUE);
						
						for (int i = 0; i < records.length(); i++) {
							JSONObject c2 = records.getJSONObject(i);
							
							String uid = c2.getString(UID);
							String date = c2.getString(CREATED_DATE);
							String storename = c2.getString(STORENAME);
							String consumption = c2.getString(CONSUMPTION);
							String discount = c2.getString(DISCOUNT);
							String qpongrant = c2.getString(QPONGRANT);
							String grantdenominations = c2.getString(GRANTDENOMINATIONS);
							String qponuse = c2.getString(QPONUSE);
							String qponid = c2.getString(QPONID);
							String usedenominations = c2.getString(USEDENOMINATIONS);
							String totalmoney = c2.getString(TOTALMONEY);
							String YEARcreated_date = c2.getString("YEAR(created_date)");
							String MONTHcreated_date = c2.getString("MONTH(created_date)");
							
							HashMap<String, String> map2 = new HashMap<String, String>();
					
							map2.put(UID,uid);
							map2.put(CREATED_DATE,date);
							map2.put(STORENAME,storename);
							map2.put(CONSUMPTION,consumption);
							map2.put(DISCOUNT,discount);
							map2.put(QPONGRANT,qpongrant);
							map2.put(GRANTDENOMINATIONS,grantdenominations);
							map2.put(QPONUSE,qponuse);
							map2.put(QPONID,qponid);
							map2.put(USEDENOMINATIONS,usedenominations);
							map2.put(TOTALMONEY,totalmoney);
							map2.put("YEAR(created_date)",YEARcreated_date);
							map2.put("MONTH(created_date)",MONTHcreated_date);
							recordsList2.add(map2);
							
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
						   ListAdapter adapter2 = new SimpleAdapter(
								   C_record.this, recordsList2,
									R.layout.activity_c_recodelist, new String[] { UID,
											CREATED_DATE,STORENAME,CONSUMPTION,DISCOUNT,
											QPONGRANT,GRANTDENOMINATIONS,QPONUSE,QPONID,
											USEDENOMINATIONS,TOTALMONEY},
									new int[] { R.id.c_rlttv1, R.id.c_rlttv2, R.id.c_rlttv3, 
												R.id.c_rlttv4, R.id.c_rlttv5, R.id.c_rlttv6, 
												R.id.c_rlttv7, R.id.c_rlttv8, R.id.c_rlttv9, 
												R.id.c_rlttv10, R.id.c_rlttv11 });
							// updating listview
							setListAdapter(adapter2);
							adapter2.notify();;
							ListView lv = getListView();
							lv.setOnItemClickListener(new OnItemClickListener() {
							
								@Override
								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									// TODO Auto-generated method stub
							
									String uids = ((TextView) view.findViewById(R.id.c_rlttv1)).getText().toString();
									String dates = ((TextView) view.findViewById(R.id.c_rlttv2)).getText().toString();
									String storenames = ((TextView) view.findViewById(R.id.c_rlttv3)).getText().toString();
									String consumptions = ((TextView) view.findViewById(R.id.c_rlttv4)).getText().toString();
									String discounts = ((TextView) view.findViewById(R.id.c_rlttv5)).getText().toString();
									String qpongrants = ((TextView) view.findViewById(R.id.c_rlttv6)).getText().toString();
									String grantdenominationses = ((TextView) view.findViewById(R.id.c_rlttv7)).getText().toString();
									String qponuses = ((TextView) view.findViewById(R.id.c_rlttv8)).getText().toString();
									String qponids = ((TextView) view.findViewById(R.id.c_rlttv9)).getText().toString();
									String usedenominationses = ((TextView) view.findViewById(R.id.c_rlttv10)).getText().toString();
									String totalmoneys = ((TextView) view.findViewById(R.id.c_rlttv11)).getText().toString();
									
									Intent in = new Intent(C_record.this,
											C_recorditem.class);
									// sending pid to next activity
									in.putExtra(UID, uids);
									in.putExtra(CREATED_DATE, dates);
									in.putExtra(STORENAME, storenames);
									in.putExtra(CONSUMPTION, consumptions);
									in.putExtra(DISCOUNT, discounts);
									in.putExtra(QPONGRANT, qpongrants);
									in.putExtra(GRANTDENOMINATIONS, grantdenominationses);
									in.putExtra(QPONUSE, qponuses);
									in.putExtra(QPONID, qponids);
									in.putExtra(USEDENOMINATIONS, usedenominationses);
									in.putExtra(TOTALMONEY, totalmoneys);
									
									startActivityForResult(in, 100);			
					   		    }
						
				
		});
	 
					   }
				   });
			}}*/
}
