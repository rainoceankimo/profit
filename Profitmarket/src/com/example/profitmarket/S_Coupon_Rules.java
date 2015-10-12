package com.example.profitmarket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import app.AppConfig_Stores;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helper.RulesDbHandler;
import app.AppController;

import com.example.profitmarket.S_Coupon_Management.DownloadData;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class S_Coupon_Rules extends Fragment {
	
	CustomListAdapter2 adapter;
	EditText input;
	ListView list;
	Spinner spinner;
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	private ProgressDialog pDialog;
	private Dialog mDlgLogin;
	Button add, clear, save;
	String item;
	int k = 0;
	String[] z = new String[100];
	String[] cost = new String[100];
	String[] x = new String[100];
	String[] howmuch = new String[100];
	String[] y = new String[100];
	
	List<Integer> list1;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "coupon";
	private static final String TAG_HOWMUCH = "howmuch";
	JSONParser Parser = new JSONParser();
	ArrayList<HashMap<String, String>> spinnerList;
	JSONArray spin = null;
	private View v;
	
	//private static String url_all_products = "http://192.168.43.218/couponconnect/spinner.php";
	
	
	private static final String DB_DBNAME = "rules.db",
                                DB_TBNAME = "rules";
	
	public static final String RID = "id";
	public static final String USEMONEY = "usemoney";
	public static final String GRANTMONEY = "grantmoney";
	
	String[] columns = {RID,USEMONEY,GRANTMONEY};
	
	private SQLiteDatabase MyrulesDb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list1 = new ArrayList<Integer>();
		spinnerList = new ArrayList<HashMap<String, String>>();
		new downloadData().execute();
       
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.s_coupon_rules, container, false);
		input = (EditText) v.findViewById(R.id.abc);
		
		
		AppController globalVariable = (AppController)getActivity().getApplicationContext();
		
		RulesDbHandler rulesDbHandler = 
				new RulesDbHandler(getActivity().getApplicationContext(), DB_DBNAME, null, 1);
		MyrulesDb = rulesDbHandler.getWritableDatabase();
		
		// 檢查資料表是否已經存在，如果不存在，就建立一個。
		Cursor cursor = MyrulesDb.rawQuery(
			   "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
					   DB_TBNAME + "'", null);
		
		//Toast.makeText(getActivity().getApplicationContext(), cursor+"", Toast.LENGTH_SHORT).show();
		
		if(cursor != null) {
	        if(cursor.getCount() == 0){	// 沒有資料表，要建立一個資料表。
	        	MyrulesDb.execSQL("CREATE TABLE " + DB_TBNAME + " (" +
		        		RID + " INTEGER PRIMARY KEY," +
		        		USEMONEY + " TEXT NOT NULL," +
		        		GRANTMONEY + " TEXT);");
	        	
	        //Toast.makeText(getActivity().getApplicationContext(), "新增成功", Toast.LENGTH_SHORT).show();
	        
	        }else
	        {
	        	//Toast.makeText(getActivity().getApplicationContext(), "新增失敗", Toast.LENGTH_SHORT).show();
	        }

	        cursor.close();
	    }else
	    {
	    	Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
	    }
		

		adapter = new CustomListAdapter2(getActivity(), z, cost, x, howmuch, y);
		list = (ListView) v.findViewById(R.id.jkl);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

				if (cost[position] == null) {
					Toast.makeText(getActivity(), "請輸入", Toast.LENGTH_SHORT).show();
				} else {
					mDlgLogin = new Dialog(getActivity());
					mDlgLogin.setTitle("編輯");

					mDlgLogin.setContentView(R.layout.dialog2);
					Button loginBtnOK = (Button) mDlgLogin.findViewById(R.id.btnO);
					Button loginBtnCancel = (Button) mDlgLogin.findViewById(R.id.btnCance);
					
					final String strSelectedItem = cost[+position];
			
					EditText editHowMuch = (EditText) mDlgLogin.findViewById(R.id.edithowmuc);
					final Spinner spinn = (Spinner) mDlgLogin.findViewById(R.id.spinner1);

					editHowMuch.setText(strSelectedItem);
					ArrayAdapter<Integer> ad = new ArrayAdapter<Integer>(getActivity(),
							android.R.layout.simple_dropdown_item_1line, list1);
					spinn.setAdapter(ad);
					spinn.setOnItemSelectedListener(new OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


							item = spinn.getSelectedItem().toString();

							Toast.makeText(getActivity().getApplicationContext(), "面額" + item + "元折價券", Toast.LENGTH_SHORT)
									.show();

						}

						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

					loginBtnOK.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							EditText editHowMuch = (EditText) mDlgLogin.findViewById(R.id.edithowmuc);
						

							// do something

							if (editHowMuch.getText().toString().startsWith("0")) {

								Toast.makeText(getActivity(), "面額開頭不能為零", Toast.LENGTH_SHORT).show();
							} else {
								 {
									if (editHowMuch.getText().toString().isEmpty()) {
										Toast.makeText(getActivity(), "欄位不能為空", Toast.LENGTH_SHORT).show();
									} 

										else {{

											cost[position] = editHowMuch.getText().toString();
											howmuch[position] = item;
											adapter.notifyDataSetChanged();

										}
									}
								}
							}

							mDlgLogin.cancel();

						}
					});
					loginBtnCancel.setOnClickListener(loginDlgBtnCancelOnClic);
					mDlgLogin.show();
				}
			}
		});
		
		
		add = (Button) v.findViewById(R.id.def);
		clear = (Button) v.findViewById(R.id.ghi);
		save = (Button) v.findViewById(R.id.mno);
		
		
		add.setOnClickListener(addOnClickListener);
		clear.setOnClickListener(clearOnClickListener);
		save.setOnClickListener(saveOnClickListener);

		return v;

	}

	private Button.OnClickListener addOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {


			String moneyinput = input.getText().toString();

			try {
				if (input.getText().toString().startsWith("0")) {
					input.setText("");

					Toast.makeText(getActivity(), "不能為零", Toast.LENGTH_SHORT).show();
				}

				else if (moneyinput.isEmpty()) {
					Toast.makeText(getActivity(), "不能為空白", Toast.LENGTH_SHORT).show();
				}

				else {
                    
					
					
					cost[k] = input.getText().toString();
					howmuch[k] = item;

					list.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					z[k] = "消費達";
					x[k] = "元發放折價券";
					y[k] = "元";
					k++;
					
					
					

					input.setText("");

					Toast.makeText(getActivity(), "新增成功", Toast.LENGTH_SHORT).show();

				}
			} catch (NumberFormatException e) {
				Toast.makeText(getActivity(), "欄位不能為空", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getActivity(), "不能超過十個選項喔", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private View.OnClickListener loginDlgBtnCancelOnClic = new View.OnClickListener() {
		public void onClick(View v) {

			mDlgLogin.cancel();
		}
	};
	private Button.OnClickListener clearOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			
			
			
			for (int i = 0; i < cost.length; i++) {
				cost[i] = "";
				howmuch[i] = "";
				x[i] = "";
				y[i] = "";
				z[i] = "";
			}
			k = 0;
			
			
			input.setText("");
			
			adapter.notifyDataSetChanged();
		}
	};
	
	private Button.OnClickListener saveOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
			ContentValues newRow = new ContentValues();
			
			
			int recodeNum = k;
			
			
			if (recodeNum != 0){
				for(int i=0;i<=k;i++){

					newRow.put("usemoney",cost[i]);
					newRow.put("grantmoney",howmuch[i]);
					
					MyrulesDb.insert(DB_TBNAME, null, newRow);

				}
				Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(); 
				intent.setClass(getActivity(),S_Mainmenu.class);
				startActivity(intent);    //觸發換頁
				
			}else{
				Toast.makeText(getActivity(), "請新增規則", Toast.LENGTH_SHORT).show();
			}
			
			
		}
			
	};
	
	
	
	
	
	

	class downloadData extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 */
		protected String doInBackground(String... args) {
			// Building Parameters
			db = new SQLiteHandler_Stores(getActivity().getApplicationContext());

			// session manager
			session = new SessionManager_Stores(getActivity().getApplicationContext());
			HashMap<String, String> user = db.getUserDetails();
			String userid = user.get("email");
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("userid", userid));
			// getting JSON string from URL
			JSONObject json = Parser.makeHttpRequest(AppConfig_Stores.url_get_crcoupon, "GET", params);

			// Check your log cat for JSON reponse

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					spin = json.getJSONArray(TAG_ISSUE);

					// looping through All Products
					for (int i = 0; i < spin.length(); i++) {
						JSONObject c = spin.getJSONObject(i);

						// Storing each json item in variable

						String str2 = c.getString(TAG_HOWMUCH);

						// creating new HashMap

						// adding each child node to HashMap key => value

						int Many = Integer.valueOf(str2);

						list1.add(Many);

						Collections.sort(list1, new Comparator<Integer>() {
							@Override
							public int compare(Integer x, Integer y) {
								return x - y;
							}
						});
					}
					/*
					 * HashMap<String, String> map = new HashMap<String,
					 * String>(); for(int i = 0;i<spin.length();i++){ String a =
					 * String.valueOf(list1[i]); map.put(TAG_HOWMUCH, a);
					 * spinnerList.add(map); }
					 */

				} else {

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
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			final Spinner sp = (Spinner) v.findViewById(R.id.spinner1);
			// updating UI from Background Thread
			ArrayAdapter<Integer> adp1 = new ArrayAdapter<Integer>(getActivity(),
					android.R.layout.simple_dropdown_item_1line, list1);
			sp.setAdapter(adp1);
			sp.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


					item = sp.getSelectedItem().toString();

					Toast.makeText(getActivity().getApplicationContext(), "面額" + item + "元折價券", Toast.LENGTH_SHORT)
							.show();

				}

				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});

		}

	}

}