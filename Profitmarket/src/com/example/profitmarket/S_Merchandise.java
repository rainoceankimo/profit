package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class S_Merchandise extends Activity {

    Button btnViewProducts;
    Button btnNewProduct;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_merchandise);
		 btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);
	     // view products click event
	     btnNewProduct.setOnClickListener(new View.OnClickListener() {

	         @Override
	         public void onClick(View view) {
	             // Launching create new product activity
	             Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
	             startActivity(i);

	         }
	     });
	
	      
		 // Buttons
        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
      
 
        // view products click event
        btnViewProducts.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(i);
 
            }
        });
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__stroes_merchandise, menu);
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
	
	
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Merchandise.this,S_Stores_Center.class);
    	   startActivity(intent);    //Ä²µo´«­¶
    	   finish();   //µ²§ô¥»­¶
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	
}
