package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AppConfig_Stores;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Coupon_Management.DownloadData;

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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Stores_Center_Amend extends Activity {

	//private static final String url_update_product = "http://192.168.0.102/storedetail/updatestoresdetail.php";
	
	private ProgressDialog nDialog;
	private EditText textName;
	String uid;
	String created_at;
	private TextView textEmail;
	private EditText textPhone;
	private EditText textAddress;
	private Button btnclickqr;
	String mail;
	String idnum;
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	private static final String TAG_SUCCESS = "success";

	private static final String TAG_EMAIL = "email";
	private static final String TAG_NAME = "name";

	private static final String TAG_PHONE = "phone";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_DB = "android_stores";

	JSONParser jasonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_stores_center_amend);

		textName = (EditText) findViewById(R.id.scaname);
		textEmail = (TextView) findViewById(R.id.txtemail);
		textPhone = (EditText) findViewById(R.id.scaphone);
		textAddress = (EditText) findViewById(R.id.scaaddress);
		btnclickqr = (Button) findViewById(R.id.btnclic);
		btnclickqr.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// creating new product in background thread

				new updatenew().execute();
				Toast.makeText(S_Stores_Center_Amend.this, "上傳成功", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();
				intent.setClass(S_Stores_Center_Amend.this, S_Mainmenu.class);
				startActivity(intent); // 觸發換頁

			}
		}

		);
		// SqLite database handler

		db = new SQLiteHandler_Stores(getApplicationContext());

		// session manager
		session = new SessionManager_Stores(getApplicationContext());
		HashMap<String, String> user = db.getUserDetails();
		String name = user.get("name");
		String email = user.get("email");
		mail = user.get("email");
		idnum = user.get("idnumber");
		String phone = user.get("phone");
		String address = user.get("address");
		created_at = user.get("created_at");
		uid = user.get("uid");
		// Displaying the user details on the screen
		textName.setText(name);
		textEmail.setText(email);
		textPhone.setText(phone);
		textAddress.setText(address);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s_storescenteramend, menu);
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

	public void s_backtostorescenter_onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(S_Stores_Center_Amend.this, S_Stores_Center.class);
		startActivity(intent); // 觸發換頁
		finish(); // 結束本頁
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Show home screen when pressing "back" button,
			// so that this app won't be closed accidentally
			Intent intent = new Intent();
			intent.setClass(S_Stores_Center_Amend.this, S_Stores_Center.class);
			startActivity(intent); // 觸發換頁
			finish(); // 結束本頁

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	class updatenew extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(S_Stores_Center_Amend.this);
			nDialog.setMessage("更新中");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		/**
		 * Saving product
		 */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String email = mail;
			
			String name = textName.getText().toString();
			String phone = textPhone.getText().toString();
			String address = textAddress.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_EMAIL, email));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PHONE, phone));
			params.add(new BasicNameValuePair(TAG_ADDRESS, address));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject jjson = jasonParser.makeHttpRequest(AppConfig_Stores.url_update_store, "POST", params);

			// check json success tag
			try {
				int success = jjson.getInt(TAG_SUCCESS);

				if (success == 1) {
                 db.deleteUsers();
                 db.addUser(name, email, idnum, phone, address, uid, created_at);
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
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			nDialog.dismiss();
		}
	}

}