package com.example.profitmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.C_discount_use.DownloadQponData;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import helper.RulesDbHandler;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import app.AppConfig;
import app.AppConfig_Stores;
import app.AppController;

public class S_Coupon_Issuing extends ListFragment {
	
	private SQLiteHandler_Stores db;
    private SessionManager_Stores session;
    
    private ProgressDialog pDialog;
    
    JSONParser jsonParser = new JSONParser();
    
    private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISSUE = "issueqpon";
	private static final String TAG_DEADLINE = "deadline";
	private static final String TAG_MONEY = "money";
	private static final String TAG_YESORNO = "yesorno";
	private static final String TAG_COUPONID = "couponid";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_CREATED_DATE = "created_date";
	
	ArrayList<HashMap<String, String>> couponsList;
	JSONArray coupons = null;
	
	private View v;
	EditText showw;
	Button btnaa;
	
	ArrayList<Integer> use = new ArrayList<Integer>();
	ArrayList<Integer> grant = new ArrayList<Integer>();
	
	int[] ciumy;
	int[] cigmy;
	
	//rules DB
	private static final String DB_DBNAME = "rules.db",
                                DB_TBNAME = "rules";

	public static final String RID = "id";
	public static final String USEMONEY = "usemoney";
	public static final String GRANTMONEY = "grantmoney";
	
	String[] columns = {RID,USEMONEY,GRANTMONEY};
	
	private SQLiteDatabase MyrulesDb;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.s_coupon_issuing, container,false);
		

		showw = (EditText) v.findViewById(R.id.ciedta);
       
		couponsList = new ArrayList<HashMap<String, String>>();
		
		RulesDbHandler rulesDbHandler = 
				new RulesDbHandler(getActivity().getApplicationContext(), DB_DBNAME, null, 1);
		MyrulesDb = rulesDbHandler.getWritableDatabase();
		
		// �ˬd��ƪ�O�_�w�g�s�b�A�p�G���s�b�A�N�إߤ@�ӡC
		Cursor cursor = MyrulesDb.rawQuery(
			   "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
					   DB_TBNAME + "'", null);
				
	    //Toast.makeText(getActivity().getApplicationContext(), cursor+"", Toast.LENGTH_SHORT).show();
				
		if(cursor != null) {
			 if(cursor.getCount() == 0){	// �S����ƪ�A�n�إߤ@�Ӹ�ƪ�C
			       MyrulesDb.execSQL("CREATE TABLE " + DB_TBNAME + " (" +
				                      RID + " INTEGER PRIMARY KEY," +
				        		      USEMONEY + " TEXT NOT NULL," +
				        		      GRANTMONEY + " TEXT);");
			        	
			        //Toast.makeText(getActivity().getApplicationContext(), "�s�W���\", Toast.LENGTH_SHORT).show();
			        
			 }else
			 {
			        	//Toast.makeText(getActivity().getApplicationContext(), "�s�W����", Toast.LENGTH_SHORT).show();
			 }

			    cursor.close();
		}else
		{
			  Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}

		showall();
		new DownloadQponData().execute();

		return v;
	}
	
	// show rulers
	public void showall(){
		Cursor c = MyrulesDb.query(true, DB_TBNAME, columns,null, null, null, null, null, null);
		
		if (c == null)
			return;

		if (c.getCount() == 0) {
			//showw.setText("");
			Toast.makeText(getActivity(), "�Х��]�w�����o��W�h��!", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(); 
			intent.setClass(getActivity(),S_Mainmenu.class);
			startActivity(intent);    //Ĳ�o����
			
		}
		else {
			c.moveToFirst();
			showw.setText("���O�F"+" " + c.getString(1) + " " + "���o������"+" "+ c.getString(2)+" " +"��");
			
			while (c.moveToNext())
				showw.append("\n" + "���O�F"+" " + c.getString(1) + " " + "���o������"+" "+ c.getString(2)+" " +"��");
		}
    }  
	// -----------------------------------------------
	
	class DownloadQponData extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			// SqLite database handler
			db = new SQLiteHandler_Stores(getActivity().getApplicationContext());
	        session = new SessionManager_Stores(getActivity().getApplicationContext());
	        
			HashMap<String, String> user = db.getUserDetails();
			String issue_store = user.get("name");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("issue_store", issue_store));
			
			
			JSONObject json = jsonParser.makeHttpRequest(AppConfig_Stores.url_get_coupontoissuing, "GET", params);
			Log.d("Get Qpon Message", json.toString());
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					coupons = json.getJSONArray(TAG_ISSUE);

					for (int i = 0; i < coupons.length(); i++) {
						JSONObject c = coupons.getJSONObject(i);

						int[] check = new int[coupons.length()];

						check[i] = Integer.valueOf(c.getString(TAG_YESORNO));

						if (check[i] != 1) {
							
							String deadline = "�����G" + c.getString(TAG_DEADLINE);
							String couponid = "�Ǹ��G" + c.getString(TAG_COUPONID);
							String money = "���B�G" + c.getString(TAG_MONEY) + "��";
							String username = "�|���G" + c.getString(TAG_USERNAME);
							String created_date = "�o�����G" + c.getString(TAG_CREATED_DATE);

							HashMap<String, String> map = new HashMap<String, String>();

							map.put(TAG_DEADLINE, deadline);
							map.put(TAG_COUPONID, couponid);
							map.put(TAG_MONEY, money);
							map.put(TAG_USERNAME, username);
							map.put(TAG_CREATED_DATE, created_date);

							couponsList.add(map);
							
						} else {

						}
					}
				}else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();

			// updating UI from Background Thread
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 */
					ListAdapter adapter = new SimpleAdapter(getActivity(), couponsList,
							R.layout.s_coupon_issuinglist,
							new String[] { TAG_DEADLINE, TAG_COUPONID,TAG_MONEY,TAG_USERNAME,TAG_CREATED_DATE},
							new int[] { R.id.s_cilttv1, R.id.s_cilttv2, R.id.s_cilttv3, R.id.s_cilttv4,R.id.s_cilttv5 });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}
		
	}
	
	
}