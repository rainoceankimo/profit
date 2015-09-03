package com.example.profitmarket;

import java.util.Date;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.AppController;
import android.content.DialogInterface;
import android.content.Intent;


public class S_SettleaCcounts extends Activity {
    
	private EditText Getmoney;
	private TextView txttxt1;
	private Button Btnscalear;
	private TextView showtype;
	public int money;
	public int outputmoney;
	public int maxdiscount;
	
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_settleaccounts);
		
        
		Btnscalear = (Button) findViewById(R.id.btnsclear);
		Getmoney = (EditText) findViewById(R.id.ttmoney);
		txttxt1 = (TextView) findViewById(R.id.textView1);

		

		Btnscalear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		               
				        money = 0;
				        outputmoney = 0;
				        maxdiscount = 0;
				
				  
					    money = Integer.valueOf(Getmoney.getText().toString());
					    AppController globalVariable = (AppController)getApplicationContext();
					    globalVariable.settlea_totalmoney = money;
					    outputmoney = globalVariable.settlea_totalmoney;
						maxdiscount = (int) (outputmoney * 0.2);
						globalVariable.settlea_MaxDiscount = maxdiscount;
						//txttxt1.setText(""+ outputmoney + "&" + globalVariable.settlea_Discount + "��");
					
						MyDialog();
				
			}
	
		});  
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__settleaccounts, menu);
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
	
	public void MyDialog( ){
	//private View.OnClickListener btnBtnscalearOnClick = new View.OnClickListener() {

		//@Override
		//public void onClick(View v) {
			
			MyAlertDialog mDlgLogin = new MyAlertDialog(S_SettleaCcounts.this);
			//mDlgLogin = new Dialog(S_SettleaCcounts.this);
			mDlgLogin.setTitle("�T�{�|��");
			mDlgLogin.setCancelable(true);
			mDlgLogin.setMessage("�馩�̦h���`����"+" "+ outputmoney + " " +"��"+"��20%�� �G"+ maxdiscount  + "��");
			//mDlgLogin.setContentView(R.layout.s__yesor_no__mem);
			
			mDlgLogin.setButton(DialogInterface.BUTTON_POSITIVE, "�@�뵲�b", altDlgPositiveBtnOnClk);
			mDlgLogin.setButton(DialogInterface.BUTTON_NEGATIVE, "�|�����b", altDlgNegativeBtnOnClk);
			
			
			//Discount = (int) (outputmoney *0.2);
			//showmoney.setText("�馩�̦h���`����"+" "+ outputmoney +" " +"��20%�� �G"+ Discount );
			
			mDlgLogin.show();	
		//}
		
	 //};
	};
   
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_SettleaCcounts.this,S_Mainmenu.class);
    	   startActivity(intent);    //Ĳ�o����
    	   finish();   //��������
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    public void account_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_SettleaCcounts.this,S_Tradedetail.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}
    
    private  DialogInterface.OnClickListener altDlgPositiveBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			AppController globalVariable = (AppController)getApplicationContext();
			globalVariable.tradetypeNO = 0;
			globalVariable.tradetype = "";
			globalVariable.tradetype = "�@�뵲�b - ����";

			Intent intent = new Intent(S_SettleaCcounts.this,
					S_Tradedetail.class);
            startActivity(intent);
            			
			
		}
	};
	
	private  DialogInterface.OnClickListener altDlgNegativeBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			AppController globalVariable = (AppController)getApplicationContext();
			globalVariable.tradetypeNO = 1;
			globalVariable.tradetype = "";
			globalVariable.tradetype = "�|�����b - ����";
			
			Intent intent = new Intent(S_SettleaCcounts.this,
					S_Tradedetail.class);
            startActivity(intent);
            
		}
	};

}
