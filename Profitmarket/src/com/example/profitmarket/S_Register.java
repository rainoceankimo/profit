package com.example.profitmarket;

import app.AppConfig_Stores;
import app.AppController;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
 
import java.util.HashMap;
import java.util.Map;
 
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
 
public class S_Register extends Activity {
    private static final String TAG = S_Register.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhone;
    private EditText inputAddress;
    private ProgressDialog pDialog;
    private SessionManager_Stores session;
    private SQLiteHandler_Stores db;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_register);
 
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPhone = (EditText) findViewById(R.id.s_phone);
        inputAddress = (EditText) findViewById(R.id.address);
        
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
 
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
 
        // Session manager
        session = new SessionManager_Stores(getApplicationContext());
 
        // SQLite database handler
        db = new SQLiteHandler_Stores(getApplicationContext());
 
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(S_Register.this,
            		S_Logout.class);
            startActivity(intent);
            finish();
        }
 
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String phone = inputPhone.getText().toString();
                String address = inputAddress.getText().toString();
 
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !address.isEmpty() ) {
                    registerUser(name, email, password, phone, address);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
 
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        S_Login.class);
                startActivity(i);
                finish();
            }
        });
 
    }
 
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email, final String password, final String phone,
            final String address) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
 
        pDialog.setMessage("Registering ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
        		AppConfig_Stores.URL_REGISTER, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                // User successfully stored in MySQL
                                // Now store the user in sqlite
                                String uid = jObj.getString("uid");
 
                                JSONObject user = jObj.getJSONObject("user");
                                String name = user.getString("name");
                                String email = user.getString("email");
                                String phone = user.getString("phone");
                                String address = user.getString("address");
                                String created_at = user
                                		.getString("created_at");
                                
 
                                // Inserting row in users table
                                db.addUser(name, email, phone, address, uid, created_at);
 
                                // Launch login activity
                                Intent intent = new Intent(
                                		S_Register.this,
                                		S_Login.class);
                                startActivity(intent);
                                finish();
                            } else {
 
                                // Error occurred in registration. Get the error
                                // message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Registration Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                params.put("address", address);
 
                return params;
            }
 
        };
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
 
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Register.this,S_Login.class);
    	    startActivity(intent);    //Ä²µo´«­¶
    	    finish();   //µ²§ô¥»­¶
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
