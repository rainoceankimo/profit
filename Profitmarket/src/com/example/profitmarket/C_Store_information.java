package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.CreateFragment.LoadAllProducts;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class C_Store_information extends Activity {
	public static   JSONParser jParser = new JSONParser();
	  
	 public static ArrayList<HashMap<String, String>> productsList;
	 CustomListAdapter2 adapter;
	private float[] ydata[];
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
    public static JSONArray products = null;
	EditText csemail;
	EditText csname;
	EditText csphone;
	EditText csaddress;
	
	private static String url_all_products = "http://10.3.204.2/android_connect/get_all_products.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c__store_information);
		  new LoadAllProducts().execute();
			productsList = new ArrayList<HashMap<String, String>>(10);	 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c__store_information, menu);
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
	 class LoadAllProducts extends AsyncTask <String, String, String> {
	   	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	        }

	        /**
	         * getting All products from url
	         * */
	        protected String doInBackground(String... args) {
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            // getting JSON string from URL
	            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

	            // Check your log cat for JSON reponse
	            Log.d("All Products: ", json.toString());

	            try {
	                // Checking for SUCCESS TAG
	                int success = json.getInt(TAG_SUCCESS);
	                 
	                if (success == 1) {
	                    // products found
	                    // Getting Array of Products
	                    products = json.getJSONArray(TAG_PRODUCTS);

	                    // looping through All Products
	                    for (int i = 0; i < products.length(); i++) {
	                        JSONObject c = products.getJSONObject(i);

	                        // Storing each json item in variable
	                        String id = c.getString(TAG_PID);
	                        String name = c.getString(TAG_NAME);
	                        String price = c.getString(TAG_PRICE);
	                        
	                        // creating new HashMap
	                        HashMap<String, String> map = new HashMap<String, String>();

	                        // adding each child node to HashMap key => value
	                        map.put(TAG_PID, id);
	                        map.put(TAG_NAME, name);
	                        map.put(TAG_PRICE, price);
	                        
	                        // adding HashList to ArrayList
	                        productsList.add(map);
	                    }
	                } 
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	            return null;
	        }
	        
	        protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//adapter = new CustomListAdapter2(getActivity(), z, cost, x, howmuch, y);
			
			     String t = productsList.get(0).get(TAG_NAME);
			        String q = productsList.get(1).get(TAG_NAME);
			        String a = productsList.get(2).get(TAG_NAME);
			        String b = productsList.get(3).get(TAG_NAME);  
			        //String t = productsList.get(0).get(TAG_NAME);
				 csemail=(EditText)findViewById(R.id.scemail);
					csname=(EditText)findViewById(R.id.scname);
					csphone=(EditText)findViewById(R.id.scphone);
					csaddress=(EditText)findViewById(R.id.scaddress);
					csemail.setText(t);
		       
		        		
		        	
		        }
		        	
		        	
		       
	       
   }
	 
	 
	 
	 
}
