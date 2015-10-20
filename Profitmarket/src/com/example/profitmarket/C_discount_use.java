package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import app.AppConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import com.example.profitmarket.S_Coupon_Management.DownloadData;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;
import android.graphics.Bitmap;
import android.widget.ImageView;
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
import helper.SQLiteHandler;
import helper.SQLiteHandler_Stores;
import helper.SessionManager;
import helper.SessionManager_Stores;

public class C_discount_use extends ListActivity {

	
	private Button Btnclickqr;
	private EditText edtUserName;
	private Dialog mDlgLogin;
	private ImageView qrImgImageView;
	String email;
	
	private SQLiteHandler db;
    private SessionManager session;

	private ProgressDialog pDialog;
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> couponsList;

	// url to get all products list
	//private static String url_all_products = "http://192.168.0.111/addQpon/getcoupon.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "issueqpon";
	private static final String TAG_DEADLINE = "deadline";
	private static final String TAG_MONEY = "money";
	private static final String TAG_YESORNO = "yesorno";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_COUPONID1 = "couponid1";
	
	//JSONArray
	JSONArray coupons = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_discount_use);
		
		// SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
 
        // session manager
        session = new SessionManager(getApplicationContext());
        
        couponsList = new ArrayList<HashMap<String, String>>();
        
		
        new DownloadQponData().execute();
        
		
		Btnclickqr = (Button) findViewById(R.id.btnclickqr);
		
		Btnclickqr.setOnClickListener(btnOnClickQR);
		
		
		 ListView lv = getListView();
	        
	        // on seleting single product
	        // launching Edit Product Screen
	        lv.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                
	            	String qponid = ((TextView) view.findViewById(R.id.dctlttv05)).getText().toString();
	            	
	            	mDlgLogin = new Dialog(C_discount_use.this);
	    			mDlgLogin.setTitle("折價券 QR Code");
	    			mDlgLogin.setCancelable(true);
	    			mDlgLogin.setContentView(R.layout.activity_c_mem_qrcode);
	    			qrImgImageView = (ImageView) mDlgLogin.findViewById(R.id.imageView_abc);
	    			
	    			try {
	    				String contentString = qponid;
	    				if (contentString != null && contentString.trim().length() > 0) {
	    					
	    					Bitmap qrCodeBitmap =EncodingHandler.createQRCode(contentString, 600);
	    					qrImgImageView.setImageBitmap(qrCodeBitmap);
	    					
	    				}else {
	    					Toast.makeText(C_discount_use.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
	    				}
	    				
	    			} catch (WriterException e) {
	    				e.printStackTrace();
	    			}

	    			mDlgLogin.show();
	            	
	            }
	        });	
	}

	
	
//---------   原程式碼
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_discount_use, menu);
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
//-------	
	
	
	class DownloadQponData extends AsyncTask<String, String, String> {
    
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(C_discount_use.this);
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
	        JSONObject json = jParser.makeHttpRequest(AppConfig.url_get_qponmessage, "GET", params);
			
	        try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					coupons = json.getJSONArray(TAG_ISSUE);
					
					for (int i = 0; i < coupons.length(); i++) {
						JSONObject c = coupons.getJSONObject(i);
						
						
						int[] check = new int[coupons.length()];
						
						check[i] = Integer.valueOf(c.getString(TAG_YESORNO));
						
						
						if (check[i] != 1){
							String deadline = "期限：" + c.getString(TAG_DEADLINE);
							String money = "面額："+c.getString(TAG_MONEY) + "元";
							String couponid = "序號："+c.getString(TAG_COUPONID);
							String couponid1 = c.getString(TAG_COUPONID);
						
							HashMap<String, String> map = new HashMap<String, String>();
						
							map.put(TAG_DEADLINE,deadline);
							map.put(TAG_MONEY,money);
							map.put(TAG_COUPONID,couponid);
							map.put(TAG_COUPONID1,couponid1);
						
						
							couponsList.add(map);
						}else{
							
						}
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
			   
			   // updating UI from Background Thread
			   C_discount_use.this.runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
						ListAdapter adapter = new SimpleAdapter(
								C_discount_use.this, couponsList,
								R.layout.activity_c_dctlist, new String[] { TAG_DEADLINE,
										TAG_MONEY,TAG_COUPONID,TAG_COUPONID1},
								new int[] { R.id.dctlttv02, R.id.dctlttv03, R.id.dctlttv04, R.id.dctlttv05 });
						// updating listview
						setListAdapter(adapter);
					}
			   });
	
		}
		
	}
	
	
	
	
	
	
	private View.OnClickListener btnOnClickQR = new View.OnClickListener() {
		public void onClick(View v) {
			
			mDlgLogin = new Dialog(C_discount_use.this);
			mDlgLogin.setTitle("會員 QR Code");
			mDlgLogin.setCancelable(true);
			mDlgLogin.setContentView(R.layout.activity_c_mem_qrcode);
			qrImgImageView = (ImageView) mDlgLogin.findViewById(R.id.imageView_abc);
			HashMap<String, String> user = db.getUserDetails();
	        email = user.get("email");
			
			
			try {
				String contentString = email;
				if (contentString != null && contentString.trim().length() > 0) {
					
					Bitmap qrCodeBitmap =EncodingHandler.createQRCode(contentString, 600);
					qrImgImageView.setImageBitmap(qrCodeBitmap);
					
				}else {
					Toast.makeText(C_discount_use.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
				}
				
			} catch (WriterException e) {
				e.printStackTrace();
			}

			mDlgLogin.show();

		}
	};	
	
	
	
   public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_discount_use.this,C_mem_view.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
   
}
