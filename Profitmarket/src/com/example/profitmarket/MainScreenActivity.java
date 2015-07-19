package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class MainScreenActivity extends Activity{
 
    Button btnViewProducts;
    Button btnNewProduct;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
     
      
    }
}