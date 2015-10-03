package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import app.AppController;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class S_Coupon_Management  extends ListFragment {
	// Progress Dialog
	 private SQLiteHandler_Stores db;
	 private SessionManager_Stores session;
	 
	 
	private ProgressDialog pDialog;
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_all_products = "http://192.168.2.142/couponconnect/getallcoupon.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "coupon";
	private static final String TAG_ID = "userid";
	private static final String TAG_TITLE = "SUM(howmany)";
	private static final String TAG_CONTENT = "howmuch";

	// products JSONArray
	JSONArray products = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.s_coupon_management, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new DownloadData().execute();

		// Get listview
	
	
	}

	// 取得建議
	class DownloadData  extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
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
		 * */
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
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
	
			// Check your log cat for JSON reponse
		

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_ISSUE);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
					
						String title = c.getString(TAG_TITLE);
						String content = c.getString(TAG_CONTENT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						
						
						map.put(TAG_CONTENT, content);
						map.put(TAG_TITLE, title);

						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							getActivity(), productsList,
							R.layout.mylist3, new String[] { TAG_CONTENT,
									TAG_TITLE},
							new int[] { R.id.content22, R.id.name1 });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	 

}