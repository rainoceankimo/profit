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

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class CreateFragment extends Fragment {
	private View v;
	ListView lv;
	public static   JSONParser jParser = new JSONParser();
	  
	 public static ArrayList<HashMap<String, String>> productsList;
	 public static ArrayList<HashMap<String, String>> productsList2;
	 CustomListAdapter2 adapter;
	private float[] ydata[];
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "users";
	public static String TAG_EMAIL = "email";
	public static  String TAG_NAME = "name";
	public static  String TAG_ADDRESS = "address";
	public static  String TAG_PHONE = "phone";
	public static  String TAG_UID = "uid";
	public static  String TAG_TYPE = "type";
	String[] z = new String[100];
	String[] cost = new String[100];
	String[] x = new String[100];
	String[] howmuch = new String[100];
	String[] y = new String[100];
	// products JSONArray
	public static JSONArray products = null;
	
	private static String url_all_products = "http://192.168.0.103/android_connect2/get_all_products.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new LoadAllProducts().execute();
    	productsList = new ArrayList<HashMap<String, String>>();
		 
    }
	public CreateFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_create, container, false);
		
		//adapter = new CustomListAdapter2(getActivity(), z, cost, x, howmuch, y);
		// ListView lv = (ListView)v.findViewById(R.id.listView1);
		// String[] arr = new String[]{
		         //  " t","q","a","b"};
      // ArrayAdapter<String> adapter = 
            //new ArrayAdapter<String>(getActivity(),
            //    android.R.layout.simple_list_item_1,arr);
     //   lv.setAdapter(adapter);
		
		return v;
		
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
	                        String uid = c.getString(TAG_UID);
	                        String email = c.getString(TAG_EMAIL);
	                        String name = c.getString(TAG_NAME);
	                        String address = c.getString(TAG_ADDRESS);
	                        String phone = c.getString(TAG_PHONE);
	                        String type = c.getString(TAG_TYPE);
	                        Double type1=Double.parseDouble(type);
	                        if(type1==0){
	                         HashMap<String, String> map = new HashMap<String, String>();
	                           map.put(TAG_UID, uid);
	  	                        map.put(TAG_EMAIL, email);
	  	                        map.put(TAG_NAME, name);
	  	                        map.put(TAG_ADDRESS, address);
	  	                        map.put(TAG_PHONE,phone);
	  	                        map.put(TAG_TYPE,type);
	  	                        // adding HashList to ArrayList
	  	                        productsList.add(map);
	                        	
	                        }
	                        // creating new HashMap
	                      

	                        // adding each child node to HashMap key => value
	                      
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
			 ListView lv = (ListView)v.findViewById(R.id.listView1);
			 //String[] arr = new String[productsList.size()];
			           
			// for(int i=0;i<productsList.size();i++)	{
			//		 String r = productsList.get(i).get(TAG_UID);
			//		 
			//	     arr[i]=r;
				 
			// }
			 
			 //SimpleAdapter simpleAdapter = new SimpleAdapter(this,value,R.layout.object_list,ContentItem,TextViewID);
			 ListAdapter adapter = new SimpleAdapter(
						getActivity(), productsList,
						R.layout.list_item, new String[] { TAG_UID,
                                TAG_NAME,TAG_PHONE,TAG_EMAIL,TAG_ADDRESS,TAG_TYPE},
						new int[] {R.id.uid,R.id.name,R.id.phone,R.id.email,R.id.address,R.id.type});
				// updating listview
				lv.setAdapter(adapter);
			 // ArrayAdapter<String> adapter = 
			 //           new ArrayAdapter<String>(getActivity(),
			 //               android.R.layout.simple_list_item_1,arr);
		   //  lv.setAdapter(adapter);   
	     
            lv.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	@Override
				public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
	        	       // if(position==0){
	        		   // Intent intent = new Intent();  
	        		   // intent.setClass(getActivity(),C_Store_information.class);
	        		   // startActivity(intent);    //Ä²µo´«­¶
	        	       // }
	        		
	        	    String uid = ((TextView) view.findViewById(R.id.uid)).getText()
	                        .toString();
	        	    String email = ((TextView) view.findViewById(R.id.email)).getText()
	                        .toString();
	        	    String name = ((TextView) view.findViewById(R.id.name)).getText()
	                        .toString();
	        	    String phone = ((TextView) view.findViewById(R.id.phone)).getText()
	                        .toString();
	        	    String address = ((TextView) view.findViewById(R.id.address)).getText()
	                        .toString();
	                // Starting new intent
	                Intent in = new Intent(getActivity(),
	                        C_Store_information.class);
	                // sending pid to next activity
	                in.putExtra(TAG_UID, uid);
	                in.putExtra(TAG_EMAIL, email);
	                in.putExtra(TAG_NAME, name);
	                in.putExtra(TAG_PHONE, phone);
	                in.putExtra(TAG_ADDRESS, address);
	                // starting new activity and expecting some response back
	                startActivity(in);
	        		
	        		
	        		
	        		
	        		
	             //      	        if(position==1){
	        	   //     	Intent intent = new Intent();  
		        	//	    intent.setClass(getActivity(),C_mem_view.class);
		        		//    startActivity(intent);    //Ä²µo´«­¶
	        	       // }
	       
	        		
	        	
	        }
	        	
	        	
	       });
	     
	    
	}
}
	 }


