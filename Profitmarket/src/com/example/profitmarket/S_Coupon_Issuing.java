package com.example.profitmarket;

import java.util.ArrayList;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import helper.RulesDbHandler;
import app.AppController;

public class S_Coupon_Issuing extends Fragment {
	
	private View v;
	EditText showw,showw1;
	Button btnaa;
	
	ArrayList<Integer> use = new ArrayList<Integer>();
	ArrayList<Integer> grant = new ArrayList<Integer>();
	
	private static final String DB_DBNAME = "rules.db",
                                DB_TBNAME = "rules";

	public static final String RID = "id";
	public static final String USEMONEY = "usemoney";
	public static final String GRANTMONEY = "grantmoney";
	
	int[] ciumy;
	int[] cigmy;

	String[] columns = {RID,USEMONEY,GRANTMONEY};

	private SQLiteDatabase MyrulesDb;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.s_coupon_issuing, container,false);
		
		RulesDbHandler rulesDbHandler = 
				new RulesDbHandler(getActivity().getApplicationContext(), DB_DBNAME, null, 1);
		MyrulesDb = rulesDbHandler.getWritableDatabase();
		
		showw = (EditText) v.findViewById(R.id.ciedta);
		showw1 = (EditText) v.findViewById(R.id.ciedtb);
		btnaa = (Button) v.findViewById(R.id.cibtna);
		
		Cursor cursor = MyrulesDb.rawQuery(
				   "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
						   DB_TBNAME + "'", null);
		
	/*	if (cursor != null){		
			if(cursor.getCount() == 0){	// 沒有資料表，要建立一個資料表。
				Toast.makeText(getActivity().getApplicationContext(), "沒有資料", Toast.LENGTH_SHORT).show();
	        }
		}else{
			Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}
		
		*/
			ABCC();
			showrules();
		
			
		
		return v;
	}
	
	public void ABCC()
    {
		Cursor c = MyrulesDb.query(true, DB_TBNAME, columns,null, null, null, null, null, null);
		
		if (c == null)
			return;

		if (c.getCount() == 0) {
			showw.setText("");
			Toast.makeText(getActivity(), "沒有資料", Toast.LENGTH_LONG).show();
		}
		else {
			c.moveToFirst();
			showw.setText(c.getString(0) + c.getString(1)  + c.getString(2));
			
			while (c.moveToNext())
				showw.append("\n" + c.getString(0) + c.getString(1)  + c.getString(2));
		}
		
		
    }
	
	public void showrules(){
		Cursor c = MyrulesDb.query(true, DB_TBNAME, columns,null, null, null, null, null, null);
		
		if (c == null)
			return;
		
		if (c.getCount() != 0) {
			while(c.moveToNext())
			{
				String umoney = c.getString(1);
				String gmoney = c.getString(2);
				int umoneys = Integer.valueOf(umoney);
				int gmoneys = Integer.valueOf(gmoney);
				
				use.add(umoneys);
				grant.add(gmoneys);
			}
			
			int cinum = use.size();
			ciumy = new int[cinum];
			cigmy = new int[cinum];
			
			for (int i = 0; i < cinum ; i++) {
				ciumy[i] = use.get(i);
				cigmy[i] = grant.get(i);
				
				showw1.append(""+ ciumy[i] + "&" + cigmy[i]);
			}
		}	
			
	}
}