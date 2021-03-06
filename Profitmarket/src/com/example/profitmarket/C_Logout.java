package com.example.profitmarket;

import helper.SQLiteHandler;
import helper.SessionManager;
 
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class C_Logout extends Activity {
 
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
 
    private SQLiteHandler db;
    private SessionManager session;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
 
        txtName = (TextView) findViewById(R.id.c_loname);
        txtEmail = (TextView) findViewById(R.id.c_loemail);
        btnLogout = (Button) findViewById(R.id.btnLogout);
 
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
 
        // session manager
        session = new SessionManager(getApplicationContext());
 
        if (!session.isLoggedIn()) {
            logoutUser();
        }
 
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
 
        String name = user.get("name");
        String email = user.get("email");
 
        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
 
        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
 
    
    
    private void logoutUser() {
        session.setLogin(false);
 
        db.deleteUsers();
 
        // Launching the login activity
        Intent intent = new Intent(C_Logout.this, C_Login.class);
        startActivity(intent);
        finish();
    }
    
    public void c_gomemview11_onClick(View v) {
	    Intent intent = new Intent();  
	    intent.setClass(C_Logout.this,C_mem_view.class);
	   startActivity(intent);    //Ĳ�o����
	   finish();   //��������
	}
}
