package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Stores_Center_Amend.updatenew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import helper.SQLiteHandler;
import helper.SessionManager;

public class C_mod_inform extends Activity {
	private ProgressDialog nDialog;
	Button bt;
	private EditText textName;
    private EditText textEmail;
    private EditText textPhone;
    String mail;
    private SQLiteHandler db;
    private SessionManager session;
    private static final String TAG_SUCCESS = "success";

	private static final String TAG_EMAIL = "email";
	private static final String TAG_NAME = "name";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_DB = "android_stores";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_mod_inform);
		
		textName = (EditText) findViewById(R.id.txtemail);
        textEmail = (EditText) findViewById(R.id.txtname);
        textPhone = (EditText) findViewById(R.id.scpassward);
        bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// creating new product in background thread

			
				Toast.makeText(C_mod_inform.this, "上傳成功", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();
				intent.setClass(C_mod_inform.this, C_mem_view.class);
				startActivity(intent); // 觸發換頁

			}
		}

		);
     // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
 
        // session manager
        session = new SessionManager(getApplicationContext());
		
        HashMap<String, String> user = db.getUserDetails();
        mail = user.get("email");
        String name = user.get("name");
        String email = user.get("email");
        String phone = user.get("phone");
 
        // Displaying the user details on the screen
        textName.setText(name);
        textEmail.setText(email);
        textPhone.setText(phone);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_mod_inform, menu);
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
	
	
	public void c_mem_check_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_mod_inform.this,C_mem_view.class);
	   startActivity(intent);    //觸發換頁
	   finish();   //結束本頁
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_mod_inform.this,C_mem_center.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
  /*  class updatenew extends AsyncTask<String, String, String> {

		
		@Override
	/*	protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(C_mod_inform.this);
			nDialog.setMessage("更新中");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		/**
		 * Saving product
		 */
	/*	protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String email = mail;
			String name = textName.getText().toString();
			String phone = textPhone.getText().toString();
			String password = textPassword.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_EMAIL, email));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PHONE, phone));
			params.add(new BasicNameValuePair(TAG_ADDRESS, address));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject jjson = jasonParser.makeHttpRequest(url_update_product, "POST", params);

			// check json success tag
			try {
				int success = jjson.getInt(TAG_SUCCESS);

				if (success == 1) {
                 db.deleteUsers();
                 db.addUser(name, email, phone, address, uid, created_at);
				} else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
	/*	protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			nDialog.dismiss();
		}
	}*/
}

