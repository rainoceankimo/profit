package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class S_Records_SharegIssue_item extends Activity {
    
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
		setContentView(R.layout.s_records_sharegissue_item);
		
				//showuid = (TextView)findViewById(R.id.rsittV3);
				showdate = (TextView)findViewById(R.id.rsissttV3);
				showissuestore = (TextView)findViewById(R.id.rsissttV4);
				//showreceivestore = (TextView)findViewById(R.id.rsissttV4);
				showcoupon = (TextView)findViewById(R.id.rsissttV5);
				showmoney = (TextView)findViewById(R.id.rsissttV6);
				showprofitmoney = (TextView)findViewById(R.id.rsissttV7);
				
				
				Intent i = getIntent();
				//uids = i.getStringExtra(TAG_UID);
				dates = i.getStringExtra(TAG_CREATED_DATE);
				issuestores = i.getStringExtra(TAG_ISSUE_STORE);
				coupons = i.getStringExtra(TAG_COUPONID);
				moneys = i.getStringExtra(TAG_MONEY);
				profitmoneys = i.getStringExtra(TAG_PROFITMONEY);
				//receivestores = i.getStringExtra(TAG_RECEIVE_STORE);
				
				//showuid.setText("編號：" + uids);
				showdate.setText("日期：" + dates);
				//showreceivestore.setText("兌換店家：" + receivestores);
				showissuestore.setText("來源店家：" + issuestores);
				showcoupon.setText("折價券序號：" + coupons);
				showmoney.setText("折價券面額：" + moneys);
				showprofitmoney.setText("分潤金額：" + profitmoneys);
		
		
	}
	
}
