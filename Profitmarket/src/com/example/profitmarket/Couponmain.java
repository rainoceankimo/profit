package com.example.profitmarket;




import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Couponmain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_couponmain);
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		Button button1 = (Button) this.findViewById(R.id.button2);
		Button button2 = (Button) this.findViewById(R.id.button1);
		Button button3 = (Button) this.findViewById(R.id.btnclickqr);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onButtonClick(view.getId());
			}
		});

		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onButtonClick(view.getId());
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onButtonClick(view.getId());
			}
		});
	}

	private void onButtonClick(int id) {
		FragmentManager fm = getFragmentManager();

		Fragment fragment = fm.findFragmentById(R.id.layout_container);

		if (fragment == null) {

			if (id == R.id.button2)
			{	fragment = new S_Coupon_Rules();
			replaceFragment(fragment);}
			if (id == R.id.button1)
				{fragment = new S_Coupon_Management();
				replaceFragment(fragment);
				}
				
			if(id == R.id.btnclickqr)
			{
				fragment = new S_Coupon_Issuing();
				replaceFragment(fragment);
			}
			
		}else{
			if (id == R.id.button2
				&& fragment instanceof S_Coupon_Management) {
			replaceFragment(new S_Coupon_Rules());
			
		}  if (id == R.id.button2
				&& fragment instanceof S_Coupon_Issuing) {
			replaceFragment(new S_Coupon_Rules());
			
		}  if (id == R.id.button1
				&& fragment instanceof S_Coupon_Rules) {
			replaceFragment(new S_Coupon_Management());
		}
		 if (id == R.id.button1
					&& fragment instanceof S_Coupon_Issuing) {
				replaceFragment(new S_Coupon_Management());
			}
		 if (id == R.id.btnclickqr
				 && fragment instanceof S_Coupon_Management) {
				replaceFragment(new S_Coupon_Issuing());}
		 if (id == R.id.btnclickqr
				 && fragment instanceof S_Coupon_Rules) {
				replaceFragment(new S_Coupon_Issuing());}
	}}

	private void replaceFragment(Fragment fragment) {
		this.getFragmentManager()
				.beginTransaction()				
				.setCustomAnimations(android.R.animator.fade_in,
						android.R.animator.fade_out)
				.replace(R.id.layout_container, fragment).addToBackStack(null)
				.commit();
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        
	        if (keyCode == KeyEvent.KEYCODE_BACK)
	        {
	            // Show home screen when pressing "back" button,
	            //  so that this app won't be closed accidentally
	        	Intent intent = new Intent();  
	    	    intent.setClass(Couponmain.this,S_Mainmenu.class);
	    	   startActivity(intent);    //Ä²µo´«­¶
	    	   finish();   //µ²§ô¥»­¶
	            
	            return true;
	        }
	        
	        return super.onKeyDown(keyCode, event);
	    }
}