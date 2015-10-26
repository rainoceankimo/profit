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
	 String address,instroduction;
	 
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
	TextView csid,inst;
	  private Handler mUI_Handler=new Handler();
	  private Handler mThreadHandler;
	  private HandlerThread mThread; 

	  JSONParser jsonParser = new JSONParser();
	   //private static final String url_product_detials = "http://192.168.0.109/android_connect2/get_product_details.php";

	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c__store_information);
			productsList = new ArrayList<HashMap<String, String>>(10);	
	     
			   Intent i = getIntent();
			 
			   csemail=(TextView)findViewById(R.id.scemail);
			   csname=(TextView)findViewById(R.id.scname);
			   csphone=(TextView)findViewById(R.id.scphone);
			   csaddress=(TextView)findViewById(R.id.scaddress);
			   inst=(TextView)findViewById(R.id.scintroduction); 
		     
		       email=i.getStringExtra(TAG_EMAIL);
		       name=i.getStringExtra(TAG_NAME);
		       phone=i.getStringExtra(TAG_PHONE);
		       address=i.getStringExtra(TAG_ADDRESS);
		       instroduction=i.getStringExtra("introduction");
		     
		       csemail.setText(email);
		       csname.setText(name);
		       csphone.setText(phone);
		       csaddress.setText(address);
		       inst.setText(instroduction);
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
  
}

