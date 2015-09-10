package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.AppController;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Coupon_Buy extends Activity  {
	JSONParser jsonParser = new JSONParser();
	CustomListAdapter adapter;
	EditText input;
	EditText input1;
	Button add, clear, buy;
	ListView listview;
	TextView tv, tv1;
	private Dialog mDlgLogin;
	ListView list;
	int k = 0;
	String[] itemname = new String[10];
	String[] x = new String[10];
	String[] imgid = new String[10];
	String[] y = new String[10];
	int total = 0;
	int sum = 0;
	ProgressDialog aDialog;
	private static String url_create_product = "http://192.168.0.105/couponconnect/create.php";
	 private SQLiteHandler_Stores db;
	    private SessionManager_Stores session;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_coupon_buy);
		setTitle("購買折價券");
		adapter = new CustomListAdapter(this, itemname, x, imgid, y);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		// adapter.notifyDataSetChanged();
		tv = (TextView) findViewById(R.id.textView1);
		tv1 = (TextView) findViewById(R.id.textView2);
		input = (EditText) findViewById(R.id.input);
		input1 = (EditText) findViewById(R.id.input1);
		add = (Button) findViewById(R.id.add);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(clearOnClickListener);
		add.setOnClickListener(addOnClickListener);
		buy = (Button) findViewById(R.id.button1);
		buy.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// creating new product in background thread
		
				new postnew().execute();
				Toast.makeText(S_Coupon_Buy.this, "上傳成功", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(); 
				intent.setClass(S_Coupon_Buy.this,S_Mainmenu.class);
				startActivity(intent);    //觸發換頁
		
			}}
			
		);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				// TODO Auto-generated method stub

				if (itemname[position] == null) {
					Toast.makeText(S_Coupon_Buy.this, "請輸入", Toast.LENGTH_SHORT).show();
				} else {
					mDlgLogin = new Dialog(S_Coupon_Buy.this);
					mDlgLogin.setTitle("編輯");

					mDlgLogin.setContentView(R.layout.dialog);
					Button loginBtnOK = (Button) mDlgLogin.findViewById(R.id.btnOK);
					Button loginBtnCancel = (Button) mDlgLogin.findViewById(R.id.btnCancel);

					final String strSelectedItem = itemname[+position];
					final String strSelectedItem1 = imgid[+position];
					EditText editHowMuch = (EditText) mDlgLogin.findViewById(R.id.edithowmuch);
					EditText editHowMany = (EditText) mDlgLogin.findViewById(R.id.edithowmany);
					// TODO Auto-generated method stub
					editHowMuch.setText(strSelectedItem);
					editHowMany.setText(strSelectedItem1);

					loginBtnOK.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							EditText editHowMuch = (EditText) mDlgLogin.findViewById(R.id.edithowmuch);
							EditText editHowMany = (EditText) mDlgLogin.findViewById(R.id.edithowmany);

							// do something

							if (editHowMuch.getText().toString().startsWith("0")) {

								Toast.makeText(S_Coupon_Buy.this, "面額開頭不能為零", Toast.LENGTH_SHORT).show();
							} else {
								if (editHowMany.getText().toString().startsWith("0")) {

									Toast.makeText(S_Coupon_Buy.this, "張數開頭不能為零", Toast.LENGTH_SHORT).show();
								} else {
									if (editHowMuch.getText().toString().isEmpty()) {
										Toast.makeText(S_Coupon_Buy.this, "欄位不能為空", Toast.LENGTH_SHORT).show();
									} else {
										if (editHowMany.getText().toString().isEmpty()) {
											Toast.makeText(S_Coupon_Buy.this, "欄位不能為空", Toast.LENGTH_LONG).show();
										}

										else {

											itemname[position] = editHowMuch.getText().toString();
											imgid[position] = editHowMany.getText().toString();
											adapter.notifyDataSetChanged();
											total = 0;
											sum = 0;
											for (int i = 0; i < itemname.length; i++) {
												if (itemname[i] != null) {
													int Many = Integer.valueOf(itemname[i]);
													int Many1 = Integer.valueOf(imgid[i]);
													total = total + (Many * Many1);
													sum = (total * 1 + total * 1 / 20);
												}

												else {
													break;
												}
												tv.setText("總金額為" + total);
												tv1.setText("抽成後總金額為" + sum);
											}
										}
									}
								}
								mDlgLogin.cancel();
							}
						}
					});

					loginBtnCancel.setOnClickListener(loginDlgBtnCancelOnClick);
					mDlgLogin.show();
				}
			}
		});
	}

	private Button.OnClickListener addOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				if (input.getText().toString().startsWith("0")) {
					input.setText("");
					input1.setText("");
					Toast.makeText(S_Coupon_Buy.this, "面額開頭不能為零", Toast.LENGTH_SHORT).show();
				} else {
					if (input1.getText().toString().startsWith("0")) {
						input.setText("");
						input1.setText("");
						Toast.makeText(S_Coupon_Buy.this, "張數開頭不能為零", Toast.LENGTH_SHORT).show();
					}

					else {

						itemname[k] = input.getText().toString();
						imgid[k] = input1.getText().toString();

						list.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						int Much = Integer.valueOf(itemname[k]);
						int Much1 = Integer.valueOf(imgid[k]);
						x[k] = "元折價券";
						y[k] = "張";
						k++;
						total = total + (Much * Much1);
						sum = (total * 1 + total * 1 / 20);
						input.setText("");
						input1.setText("");
						tv.setText("總金額為" + total + "元");
						tv1.setText("抽成後總金額為" + sum + "元");
						Toast.makeText(S_Coupon_Buy.this, "新增成功", Toast.LENGTH_SHORT).show();

					}
				}
			} catch (NumberFormatException e) {
				Toast.makeText(S_Coupon_Buy.this, "欄位不能為空", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(S_Coupon_Buy.this, "不能超過十個選項喔", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Button.OnClickListener clearOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < itemname.length; i++) {
				itemname[i] = "";
				imgid[i] = "";
				x[i] = "";
				y[i] = "";
			}
			k = 0;
			sum = 0;
			total = 0;
			tv.setText("總金額");
			tv1.setText("抽成後總金額");
			input.setText("");
			input1.setText("");
			adapter.notifyDataSetChanged();
		}
	};
	private View.OnClickListener loginDlgBtnCancelOnClick = new View.OnClickListener() {
		public void onClick(View v) {

			mDlgLogin.cancel();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__buycoupon, menu);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Show home screen when pressing "back" button,
			// so that this app won't be closed accidentally
			Intent intent = new Intent();
			intent.setClass(S_Coupon_Buy.this, S_Mainmenu.class);
			startActivity(intent); // 觸發換頁
			finish(); // 結束本頁

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	class postnew extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			aDialog = new ProgressDialog(S_Coupon_Buy.this);
			aDialog.setMessage("Creating Product..");
			aDialog.setIndeterminate(false);
			aDialog.setCancelable(true);
			aDialog.show();
		}

		protected String doInBackground(String... args) {
			 db = new SQLiteHandler_Stores(getApplicationContext());
			 
		        // session manager
		        session = new SessionManager_Stores(getApplicationContext());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			 HashMap<String, String> user = db.getUserDetails();
		      String userid = user.get("email");
			for (int m = 0; m < itemname.length; m++) 
			{
				
				String howmuch = itemname[m];
				String howmany = imgid[m];
				if (itemname[m] == null){break;}
				
				// Building Parameters
				else
				{
				params.add(new BasicNameValuePair("userid", userid));
				params.add(new BasicNameValuePair("howmuch", howmuch));
				params.add(new BasicNameValuePair("howmany", howmany));
			
				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

				// check log cat fro response
				Log.d("Create Response", json.toString());
			
				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
		
					
					} else 
					{
						// failed to create product
					}
				}
			
				
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				}
			}
			return null;
			
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
		protected void onPostExecute(String file_url) 
		{
			// dismiss the dialog once done
			aDialog.dismiss();
			
		}

	}
	
	
}