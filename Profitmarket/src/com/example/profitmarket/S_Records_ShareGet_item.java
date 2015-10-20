package com.example.profitmarket;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import app.AppConfig_Stores;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Records_ShareGet.DownloadProfitRecord;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import android.graphics.Bitmap;
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
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;

public class S_Records_ShareGet_item extends Activity {
	
	private static final String TAG_UID = "uid";
	private static final String TAG_ISSUE_STORE = "issue_store";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_MONEY = "money";
	private static final String TAG_PROFITMONEY = "profitmoney";
	private static final String TAG_RECEIVE_STORE = "receive_store";
	private static final String TAG_CREATED_DATE = "created_date";
	
	private TextView showuid,showissuestore,showcoupon,showmoney,
					 showprofitmoney,showreceivestore,showdate;
	
	String uids,dates,issuestores,coupons,moneys,profitmoneys,receivestores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_records_shareget_item);

		//showuid = (TextView)findViewById(R.id.rsittV3);
		showdate = (TextView)findViewById(R.id.rsittV4);
		//showissuestore = (TextView)findViewById(R.id.rsittV4);
		showreceivestore = (TextView)findViewById(R.id.rsittV5);
		showcoupon = (TextView)findViewById(R.id.rsittV6);
		showmoney = (TextView)findViewById(R.id.rsittV7);
		showprofitmoney = (TextView)findViewById(R.id.rsittV8);
		
		
		Intent i = getIntent();
		//uids = i.getStringExtra(TAG_UID);
		dates = i.getStringExtra(TAG_CREATED_DATE);
		//issuestores = i.getStringExtra(TAG_ISSUE_STORE);
		coupons = i.getStringExtra(TAG_COUPONID);
		moneys = i.getStringExtra(TAG_MONEY);
		profitmoneys = i.getStringExtra(TAG_PROFITMONEY);
		receivestores = i.getStringExtra(TAG_RECEIVE_STORE);
		
		//showuid.setText("編號：" + uids);
		showdate.setText("日期：" + dates);
		showreceivestore.setText("兌換店家：" + receivestores);
		//showissuestore.setText("發出店家：" + issuestores);
		showcoupon.setText("折價券序號：" + coupons);
		showmoney.setText("折價券面額：" + moneys);
		showprofitmoney.setText("分潤金額：" + profitmoneys);
		
		
	}

}
