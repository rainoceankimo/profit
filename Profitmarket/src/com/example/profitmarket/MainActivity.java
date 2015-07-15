package com.example.profitmarket;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void s_stores_onClick(View view){
		Intent intent = new Intent(); 
		intent.setClass(MainActivity.this,S_Mainmenu.class);
		startActivity(intent);    //Ĳ�o����
		MainActivity.this.finish();   //��������
	}
	public void c_sale_onClick(View view){
		Intent intent = new Intent(); 
		intent.setClass(MainActivity.this,C_log.class);
		startActivity(intent);    //Ĳ�o����
		MainActivity.this.finish();   //��������
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {//������^��
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   
            ConfirmExit();//����^��A�h����h�X�T�{
            return true;   
        }   
        return super.onKeyDown(keyCode, event);   
    }
    public void ConfirmExit(){//�h�X�T�{
        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("���}");
        ad.setMessage("�T�w�n���}?");
        ad.setPositiveButton("�O", new DialogInterface.OnClickListener() {//�h�X���s
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                MainActivity.this.finish();//����activity
  
            }
        });
        ad.setNegativeButton("�_",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //���h�X���ΰ������ާ@
            }
        });
        ad.show();//�ܹ�ܮ�
    }
}
