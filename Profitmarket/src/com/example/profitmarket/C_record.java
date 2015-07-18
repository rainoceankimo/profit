package com.example.profitmarket;
import java.util.Calendar;  

import android.app.Activity;  
import android.app.DatePickerDialog;  
import android.app.TimePickerDialog;  
import android.content.Intent;
import android.os.Bundle;  
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.widget.Button;  
import android.widget.DatePicker;  
import android.widget.TextView;  
import android.widget.TimePicker;  


public class C_record extends Activity {

	private TextView tvDate, tvTime;  
	 private Button btDate, btTime;  
	 private int mYear, mMonth, mDay, mHour, mMinute; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c_record);
		tvDate = (TextView) findViewById(R.id.tvDate);  
		  tvTime = (TextView) findViewById(R.id.tvTime);  
		  
		  btDate = (Button) findViewById(R.id.btDate);  
		  btTime = (Button) findViewById(R.id.btTime);  
		  
		  btDate.setOnClickListener(new Button.OnClickListener() {  
		   public void onClick(View v) {  
		    showDatePickerDialog();  
		   }  
		  });  
		  btTime.setOnClickListener(new Button.OnClickListener() {  
		   public void onClick(View v) {  
		    showTimePickerDialog();  
		   }  
		  });  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.c_record, menu);
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
	
	 public void showDatePickerDialog() {  
		  // 設定初始日期  
		  final Calendar c = Calendar.getInstance();  
		  mYear = c.get(Calendar.YEAR);  
		  mMonth = c.get(Calendar.MONTH);  
		  mDay = c.get(Calendar.DAY_OF_MONTH);  
		  
		  // 跳出日期選擇器  
		  DatePickerDialog dpd = new DatePickerDialog(this,  
		    new DatePickerDialog.OnDateSetListener() {  
		     public void onDateSet(DatePicker view, int year,  
		       int monthOfYear, int dayOfMonth) {  
		      // 完成選擇，顯示日期  
		      tvDate.setText(year + "-" + (monthOfYear + 1) + "-"  
		        + dayOfMonth);  
		  
		     }  
		    }, mYear, mMonth, mDay);  
		  dpd.show();  
		 }  
		  
		 public void showTimePickerDialog() {  
		  // 設定初始時間  
		  final Calendar c = Calendar.getInstance();  
		  mHour = c.get(Calendar.HOUR_OF_DAY);  
		  mMinute = c.get(Calendar.MINUTE);  
		  
		  // 跳出時間選擇器  
		  TimePickerDialog tpd = new TimePickerDialog(this,  
		    new TimePickerDialog.OnTimeSetListener() {  
		     public void onTimeSet(TimePicker view, int hourOfDay,  
		       int minute) {  
		      // 完成選擇，顯示時間  
		      tvTime.setText(hourOfDay + ":" + minute);  
		     }  
		    }, mHour, mMinute, false);  
		  tpd.show();  
		 }  
		  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(C_record.this,C_mem_view.class);
    	   startActivity(intent);    //觸發換頁
    	   finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
