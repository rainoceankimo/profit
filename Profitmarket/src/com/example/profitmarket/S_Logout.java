package com.example.profitmarket;

import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
 
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class S_Logout extends Activity {
 
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
 
    private SQLiteHandler_Stores db;
    private SessionManager_Stores session;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_logout);
 
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
 
        // SqLite database handler
        db = new SQLiteHandler_Stores(getApplicationContext());
 
        // session manager
        session = new SessionManager_Stores(getApplicationContext());
 
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
 
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);
 
        db.deleteUsers();
 
        // Launching the login activity
        Intent intent = new Intent(S_Logout.this, S_Login.class);
        startActivity(intent);
        finish();
    }
    
    public void s_signin_onClick(View v){
		Intent intent = new Intent(); 
		intent.setClass(S_Logout.this,S_Mainmenu.class);
		startActivity(intent);    //Ä²µo´«­¶
		finish();   //µ²§ô¥»­¶
	}
}
