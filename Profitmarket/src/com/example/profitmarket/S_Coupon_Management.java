package com.example.profitmarket;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class S_Coupon_Management extends ListFragment {
	class Month{
		  String chinese;
		  String english;
		   
		  Month(String c, String e){
		   chinese = c;
		   english = e;
		  }
		   
		  public String getChinese(){
		   return chinese; 
		  }
		  public String getEnglish(){
		   return english;  
		  }
		 }
		  
		  
		 Month[] months ={
		   new Month("250", "100"), 
		   new Month("150", "10"), 
		   new Month("100", "5"), 
		   new Month("75", "6"),
		   new Month("15", "7"), 
		   new Month("25", "10"), 
		   new Month("10", "9"), 
		   new Month("6", "1"),
		   new Month("25", "1"), 
		   new Month("20", "6"), 
		   new Month("3", "7"), 
		   new Month("50", "8")};
		  
		 @Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		   Bundle savedInstanceState) {
		  return inflater.inflate(R.layout.s_coupon_management, container, false);
		 }
		 
		 @Override
		 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		   
		  setListAdapter(new MyCustomAdapter(
		    getActivity(), 
		    R.layout.couponmanagementrow, 
		    months));
		 }
		  
		 public class MyCustomAdapter extends ArrayAdapter<Month>{
		 
		  public MyCustomAdapter(Context context, int textViewResourceId,
		    Month[] objects) {
		   super(context, textViewResourceId, objects);
		   // TODO Auto-generated constructor stub
		  }
		 
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		   // TODO Auto-generated method stub
		   LayoutInflater inflater=getActivity().getLayoutInflater();
		   View row=inflater.inflate(R.layout.couponmanagementrow, parent, false);
		   TextView text1 = (TextView)row.findViewById(R.id.text1);
		   TextView text2 = (TextView)row.findViewById(R.id.text3);
		    
		   text1.setText(months[position].getChinese());
		   text2.setText(months[position].getEnglish());
		 
		   return row;
		  }
		   
		 }
		 
	}
