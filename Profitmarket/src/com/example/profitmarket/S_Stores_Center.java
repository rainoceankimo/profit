package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import app.AppConfig;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Stores_Center extends Activity {
	private static final String TAG_INTRODUCTION = "introduction";
	private static final String TAG_USERS = "users";
	private ProgressDialog ialog;
	private static final String TAG_SUCCESS = "success";
	JSONArray intro = null;

	JSONParser jParser = new JSONParser();
	String introd;
	private TextView textName;
    private TextView textEmail;
    private TextView textPhone;
    private TextView textAddress;
    private TextView introduction;
    private SQLiteHandler_Stores db;
    private SessionManager_Stores session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_stores_center);
		new GETINTRODUCTION().execute();
		textName = (TextView) findViewById(R.id.scname);
		textEmail = (TextView) findViewById(R.id.scemail);
		textPhone = (TextView) findViewById(R.id.scphone);
		textAddress = (TextView) findViewById(R.id.scaddress);
		
		// SqLite database handler
        db = new SQLiteHandler_Stores(getApplicationContext());
 
        // session manager
        session = new SessionManager_Stores(getApplicationContext());
        
        HashMap<String, String> user = db.getUserDetails();
        
        String name = user.get("name");
        String email = user.get("email");
        String phone = user.get("phone");
        String address = user.get("address");
        
        // Displaying the user details on the screen
        textName.setText(name);
        textEmail.setText(email);
        textPhone.setText(phone);
        textAddress.setText(address);
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s_storescenter, menu);
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
	
	public void s_storesinformationamend_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Stores_Center.this,S_Stores_Center_Amend.class);
		startActivity(intent);    //觸發換頁
		S_Stores_Center.this.finish();   //結束本頁
	}
	
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Stores_Center.this,S_Mainmenu.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	class GETINTRODUCTION extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ialog = new ProgressDialog(S_Stores_Center.this);
			ialog.setMessage("Loading . Please wait...");
			ialog.setIndeterminate(false);
			ialog.setCancelable(false);
			ialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			// SqLite database handler
			db = new SQLiteHandler_Stores(getApplicationContext());

			// session manager
			session = new SessionManager_Stores(getApplicationContext());
			HashMap<String, String> user = db.getUserDetails();
			String email = user.get("email");
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", email));
			

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(AppConfig.url_intro, "POST", params);
			Log.d("Get Qpon Message", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					intro = json.getJSONArray(TAG_USERS);

				
						JSONObject c = intro.getJSONObject(0);

					
							 introd = c.getString(TAG_INTRODUCTION);
							
							
						
						

						
					}
				 else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			ialog.dismiss();

			// updating UI from Background Thread
			S_Stores_Center.this.runOnUiThread(new Runnable() {
				public void run() {
					 introduction= (TextView) findViewById(R.id.textView5);
				        introduction.setText(introd);
				}
			});

		}

	}
}
