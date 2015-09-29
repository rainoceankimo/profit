package com.example.profitmarket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Analysis_Sources.LoadAllProducts;
import com.example.profitmarket.S_Coupon_Management.DownloadData;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class CreateFragment extends Fragment {
	View v;
	ListView lv;
	public static   JSONParser jParser = new JSONParser();
	  
	 public static ArrayList<HashMap<String, String>> productsList;
		
	private float[] ydata[];
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
	// products JSONArray
	public static JSONArray products = null;
	
	private static String url_all_products = "http://10.3.204.2/android_connect/get_all_products.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new LoadAllProducts().execute();
		productsList = new ArrayList<HashMap<String, String>>(10);	 
    }
	public CreateFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
		View rootView = inflater.inflate(R.layout.fragment_create, container, false);
		
	        lv = (ListView)rootView.findViewById(R.id.listView1);
	        String t = productsList.get(0).get(TAG_PID);
	        String q = productsList.get(1).get(TAG_PID);
	        String a = productsList.get(2).get(TAG_PID);
	        String b = productsList.get(3).get(TAG_PID);
	       String[] arr = new String[]{
	            t,q,a,b
	        };
	        ArrayAdapter<String> adapter = 
	            new ArrayAdapter<String>(getActivity(),
	                android.R.layout.simple_list_item_1,arr);
	        lv.setAdapter(adapter);
	    
		return rootView;
		
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
	    
	 }

	}


