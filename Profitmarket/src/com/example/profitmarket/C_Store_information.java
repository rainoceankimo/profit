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

import com.example.profitmarket.CreateFragment.LoadAllProducts;
import com.example.profitmarket.EditProductActivity.GetProductDetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class C_Store_information extends Activity {
	public static   JSONParser jParser = new JSONParser();
	 private ProgressDialog pDialog;
	 String pid;
	 String uid;
	 String email;
	 String name;
	 String phone;
	 String address;
	 
	 
	 
	 
	 
	 
	 public static ArrayList<HashMap<String, String>> productsList;
	 CustomListAdapter2 adapter;
	private float[] ydata[];
	  private static final String TAG_PID = "uid";
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "users";
	public static final String TAG_EMAIL = "email";
	public static final  String TAG_NAME = "name";
	public static  final String TAG_ADDRESS = "address";
	public static final String TAG_PHONE = "phone";
	public static  final String TAG_UID = "uid";
	private static final String TAG_PRODUCT = "users";
    public static JSONArray products = null;
    TextView csemail;
   // TextView txtName;
    TextView csname;
    TextView csphone;
    TextView csaddress;
	TextView csid;
	  private Handler mUI_Handler=new Handler();
	  private Handler mThreadHandler;

	  private HandlerThread mThread; 
	  
	
	
	   JSONParser jsonParser = new JSONParser();
	   private static final String url_product_detials = "http://192.168.0.109/android_connect2/get_product_details.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c__store_information);
		 // new LoadAllProducts().execute();
			productsList = new ArrayList<HashMap<String, String>>(10);	
			    
			  
		        // getting product id (pid) from intent
			   Intent i = getIntent();
			   csid=(TextView)findViewById(R.id.csid);
			   csemail=(TextView)findViewById(R.id.scemail);
			   csname=(TextView)findViewById(R.id.scname);
			   csphone=(TextView)findViewById(R.id.scphone);
			   csaddress=(TextView)findViewById(R.id.scaddress);
			   
			   
			   
			   
		       uid = i.getStringExtra(TAG_PID);
		       email=i.getStringExtra(TAG_EMAIL);
		       name=i.getStringExtra(TAG_NAME);
		       phone=i.getStringExtra(TAG_PHONE);
		       address=i.getStringExtra(TAG_ADDRESS);
		       
		       csid.setText(uid);
		       csemail.setText(email);
		       csname.setText(name);
		       csphone.setText(phone);
		       csaddress.setText(address);
		       
		       
		       
		       
		       
		       
		       
		       
		      
		        // Getting complete product details in background thread
		      
		       // new GetProductDetails().execute();
		
		
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
	
	
	/*public void setui( JSONObject product){
		   // display product data in EditText
		
        try{
        csemail.setText(product.getString(TAG_EMAIL));
        csname.setText(product.getString(TAG_NAME));
        csphone.setText(product.getString(TAG_PHONE));
        csaddress.setText(product.getString(TAG_ADDRESS)); 
        }catch(Exception e){
     	   e.printStackTrace();
        }
        Log.d("pleas", product.toString());
	}*/
	//protected void onResume(){
	//	super.onResume();
		
	  	 
		// new GetProductDetails().execute();
		
		
		
		
		
	
	/* public boolean onKeyDown(int keyCode, KeyEvent event) {
	        
	        if (keyCode == KeyEvent.KEYCODE_BACK)
	        {
	            // Show home screen when pressing "back" button,
	            //  so that this app won't be closed accidentally
	        	Intent intent = new Intent();  
	    	    intent.setClass(C_Store_information.this,CreateFragment.class);
	    	   startActivity(intent);    //Ä²µo´«­¶
	    	   finish();   //µ²§ô¥»­¶
	            
	            return true;
	        }
	        
	        return super.onKeyDown(keyCode, event);
	    }*/
	
	/* class LoadAllProducts extends AsyncTask <String, String, String> {
	   	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	        }

	        /**
	         * getting All products from url
	         * 
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
	                        // creating new HashMap
	                        HashMap<String, String> map = new HashMap<String, String>();

	                        // adding each child node to HashMap key => value
	                        map.put(TAG_UID, uid);
	                        map.put(TAG_EMAIL, email);
	                        map.put(TAG_NAME, name);
	                        map.put(TAG_ADDRESS, address);
	                        map.put(TAG_PHONE,phone);

	                        
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
			        String q = productsList.get(0).get(TAG_EMAIL);
			        String a = productsList.get(0).get(TAG_ADDRESS);
			        String b = productsList.get(0).get(TAG_PHONE);  
			        //String t = productsList.get(0).get(TAG_NAME);
				    csemail=(EditText)findViewById(R.id.scemail);
					csname=(EditText)findViewById(R.id.scname);
					csphone=(EditText)findViewById(R.id.scphone);
					csaddress=(EditText)findViewById(R.id.scaddress);
					csemail.setText(q);
		            csname.setText(t);
		        	csaddress.setText(a);
		        	csphone.setText(b);
		        	
		        }
		        	
		        	
		       
	       
   }*/
	
	

	
	 /* class GetProductDetails extends AsyncTask<String, String, String> {
		  
	        /**
	         * Before starting background thread Show Progress Dialog
	         * 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();

	       
	            
	            //setResult(100, null);
	         
	        }
	 
	        /**
	         * Getting product details in background thread
	         * 
	        protected String doInBackground(String... params1) {
	 
	            // updating UI from Background Thread
	          //  runOnUiThread(new Runnable() {
	           //     public void run() {
	                    // Check for success tag
	                    int success;
	                    try {
	                        // Building Parameters
	                        List<NameValuePair> params = new ArrayList<NameValuePair>();
	                        params.add(new BasicNameValuePair("uid", uid));
	 
	                        // getting product details by making HTTP request
	                        // Note that product details url will use GET request
	                        JSONObject json = jsonParser.makeHttpRequest(
	                                url_product_detials, "GET", params);
	 
	                        // check your log for json response
	                        Log.d("Single Product Details", json.toString());
	 
	                        // json success tag
	                        success = json.getInt(TAG_SUCCESS);
	                        if (success == 1) {
	                            // successfully received product details
	                            JSONArray productObj = json
	                                    .getJSONArray(TAG_PRODUCT); // JSON Array
	 
	                            // get first product object from JSON Array
	                            JSONObject product = productObj.getJSONObject(0);
	                           // setui(product);
	                            // product with this pid found
	                            // Edit Text.
	                            csemail = (EditText) findViewById(R.id.scemail);
	                            csname = (EditText) findViewById(R.id.scname);
	                            csphone = (EditText) findViewById(R.id.scphone);
	                            csaddress= (EditText) findViewById(R.id.scaddress);
	                            
	                            csemail.setText(product.getString(TAG_EMAIL));
	                            csname.setText(product.getString(TAG_NAME));
	                            csphone.setText(product.getString(TAG_PHONE));
	                            csaddress.setText(product.getString(TAG_ADDRESS)); 
	                            
	                        }else{
	                            // product with pid not found
	                        }
	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	               // }
	          //  });
	 
	            return null;
	        }
	 
	  } */
	  
}

