package com.example.profitmarket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


public class C_records extends Activity {
	private DatePicker mDatePicker;
	private TextView mTxtResult;
	private Button mBtnOK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_records);
	mDatePicker = (DatePicker) findViewById(R.id.datePicker);
	mTxtResult = (TextView) findViewById(R.id.txtResult);
	mBtnOK = (Button) findViewById(R.id.c_btnok);
	
	mBtnOK.setOnClickListener(btnOKClick);
	}
       
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_records, menu);
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
	private View.OnClickListener btnOKClick = new View.OnClickListener() {
		
		public void onClick(View v) {
			String s = getString(R.string.result);
			mTxtResult.setText(s + mDatePicker.getYear()+"¦~"+
			(mDatePicker.getMonth()+1)+ "¤ë"+
			mDatePicker.getDayOfMonth()+"¤é");
					
				 
		}
	};
	
}
