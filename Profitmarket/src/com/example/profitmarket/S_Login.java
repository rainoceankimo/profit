package com.example.profitmarket;

import app.AppConfig_Stores;
import app.AppController;
import helper.SessionManager_Stores;
import helper.SQLiteHandler_Stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.profitmarket.S_Tradedetail.updateuseQpon;
 
public class S_Login extends Activity {
    // LogCat tag
    private static final String TAG1 = S_Login.class.getSimpleName();
	
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager_Stores session;
    private SQLiteHandler_Stores db;
    //public String openstore;
    
    JSONParser jsonParser = new JSONParser();
    
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "users";
	private static final String TAG_OPENSTORE = "openstore";
	
	public String openstore; 
	public int checkopenstore = 0; 
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_login);
 
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
 
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
            Intent intent = new Intent(S_Login.this, S_Logout.class);
            startActivity(intent);
            finish();
        }
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	   
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                //String idnumber = "";
                //String phone = "";
                //String address = "";
                //String openstore = "";
                
                new Getstoremessage().execute();
                
                // Check for empty data in the form
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                     
                	checkLogin(email, password);
                
                    
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
            }
 
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        S_Register.class);
                startActivity(i);
                finish();
            }
        });
 
    }
 
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
 
        pDialog.setMessage("Logging in ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,AppConfig_Stores.URL_REGISTER, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG1, "Login Response: " + response.toString());
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
 
                            // Check for error node in json
                            if (!error) {
                            	
                                // user successfully logged in
                                // Create login session

                            	String uid = jObj.getString("uid");
                            	 
                                JSONObject user = jObj.getJSONObject("user");
                                String name = user.getString("name");
                                String email = user.getString("email");
                                String idnumber = user.getString("idnumber");
                                String phone = user.getString("phone");
                                String address = user.getString("address");
                                String created_at = user.getString("created_at");
                                //String openstore = user.getString("openstore");
 
                                // Inserting row in users table

                                
                                new Getstoremessage().execute();
                                
                                if (checkopenstore == 1){
                                	
                                	session.setLogin(true);
                                	db.addUser(name, email, idnumber, phone, address, uid, created_at);
                                	
                                	Intent intent = new Intent(S_Login.this,S_Logout.class);
                                    startActivity(intent);
                                    finish();
                                    
                                }else if(checkopenstore == 0)
                                {
                            	   Toast.makeText(S_Login.this, "貴店尚未開通!", Toast.LENGTH_SHORT).show();
                                } 

                                
                                //Toast.makeText(S_Login.this, "" + openstore, Toast.LENGTH_SHORT).show();
 
                                // Launch main activity
                              /*Intent intent = new Intent(S_Login.this,
                                		S_Logout.class);
                                startActivity(intent);
                                finish();  */
                                
                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG1, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);
                //params.put("idnumber", idnumber);
                //params.put("phone", phone);
                //params.put("address", address);
                //params.put("openstore", openstore);
                
 
                return params;
            }
 
        };
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    //-------------------------------------------
    
    // get store message
    class Getstoremessage extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			inputEmail = (EditText) findViewById(R.id.email);
			String email = inputEmail.getText().toString();

	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("email", email));
	
	        JSONObject gjson = jsonParser.makeHttpRequest(AppConfig_Stores.URL_GET_STOREMESSAGE, "POST", params);
	        Log.d("get message", gjson.toString());
	        
	        try {
     			int success = gjson.getInt(TAG_SUCCESS);

     			if (success == 1) {
     				
     				 JSONArray couponObj = gjson.getJSONArray("users");

					 JSONObject c = couponObj.getJSONObject(0);

					 openstore = c.getString("openstore");
					 checkopenstore = Integer.valueOf(openstore);
					 
				/*	 if (checkopenstore == 1){
                     	 Intent intent = new Intent(S_Login.this,S_Logout.class);
                         startActivity(intent);
                         finish();
                     }     */

     			} else {
     				// failed to update product
     			}
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}

			return null;
		}
		
		protected void onPostExecute(String file_url) 
		{
				// dismiss the dialog once done
			   pDialog.dismiss();
		}
    	
    }
    // -------------------------------------------
    
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
    	    intent.setClass(S_Login.this,MainActivity.class);
    	    startActivity(intent);    //觸發換頁
    	    finish();   //結束本頁
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    
}
