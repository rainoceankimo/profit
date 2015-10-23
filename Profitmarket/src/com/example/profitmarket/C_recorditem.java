package com.example.profitmarket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.C_discount_use.DownloadQponData;
import com.example.profitmarket.C_record.DownloadrecordData;

import android.app.Activity;  
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;  
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;  
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.widget.Button;  
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import app.AppConfig;
import android.widget.TextView;  
import android.widget.TimePicker;
import android.widget.Toast;  

public class C_recorditem extends Activity {
	
	private static final String TAG_SUCCESS = "success";
	 private static final String TAG_ISSUE = "memberrecord";
	 private static final String UID = "uid";
	 private static final String CREATED_DATE = "created_date";
	 //private static final String USERNAME = "username";
	 private static final String STORENAME = "storename";
	 private static final String CONSUMPTION = "consumption";
	 private static final String DISCOUNT = "discount";
	 private static final String QPONGRANT = "qpongrant";
	 private static final String GRANTDENOMINATIONS = "grantdenominations";
	 private static final String QPONUSE = "qponuse";
	 private static final String QPONID = "qponid";
	 private static final String USEDENOMINATIONS = "usedenominations";
	 private static final String TOTALMONEY = "totalmoney";
	
	 String uid,date,storename,consumption,discount,qpongrant,
	 		grantdenominations,qponuse,qponid,usedenominations,
	 		totalmoney;
	 
	 private TextView ritxtdate,ritxtstore,ritxtconsumption,ritxtdiscount,
	                  ritxtqpongrant,ritxtgrantdenominations,ritxtqponuse,ritxtqponid,
	                  ritxtusedenominations,ritxttotalmoney;
	 
	 private ProgressDialog pDialog;
		
	 // Creating JSON Parser object
	 JSONParser jParser = new JSONParser();

	 JSONArray records = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_recorditem);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}  
		
		
		ritxtdate = (TextView)findViewById(R.id.c_rittv1);
		ritxtstore = (TextView)findViewById(R.id.c_rittv3);
		ritxtconsumption = (TextView)findViewById(R.id.c_rittv4);
		ritxtdiscount = (TextView)findViewById(R.id.c_rittv5);
		ritxtqpongrant = (TextView)findViewById(R.id.c_rittv6);
		ritxtgrantdenominations = (TextView)findViewById(R.id.c_rittv7);
		ritxtqponuse = (TextView)findViewById(R.id.c_rittv8);
		ritxtqponid = (TextView)findViewById(R.id.c_rittv9);
		ritxtusedenominations = (TextView)findViewById(R.id.c_rittv10);
		ritxttotalmoney = (TextView)findViewById(R.id.c_rittv11);
		
		Intent i = getIntent();
		uid = i.getStringExtra(UID);
		date = i.getStringExtra(CREATED_DATE);
		storename = i.getStringExtra(STORENAME);
		consumption = i.getStringExtra(CONSUMPTION);
		discount = i.getStringExtra(DISCOUNT);
		qpongrant = i.getStringExtra(QPONGRANT);
 		grantdenominations = i.getStringExtra(GRANTDENOMINATIONS);
 		qponuse = i.getStringExtra(QPONUSE);
 		qponid = i.getStringExtra(QPONID);
 		usedenominations = i.getStringExtra(USEDENOMINATIONS);
 		totalmoney = i.getStringExtra(TOTALMONEY);
 		
 		ritxtdate.setText("日期：" + date );
		ritxtstore.setText("店家名稱：" + storename );
		ritxtconsumption.setText("消費金錢：" + consumption );
		ritxtdiscount.setText("折扣上限：" + discount );
		ritxtqpongrant.setText("折價券發放：" + qpongrant );
		ritxtgrantdenominations.setText("發放面額：" + grantdenominations );
		ritxtqponuse.setText("折價券使用：" + qponuse );
		ritxtqponid.setText("折價券序號：" + qponid );
        ritxtusedenominations.setText("使用面額：" + usedenominations );
        ritxttotalmoney.setText("總金額：" + totalmoney );
		
		
	}

}
