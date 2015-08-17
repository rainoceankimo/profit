package com.example.profitmarket;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomListAdapter extends ArrayAdapter<String> {
 
	private final Activity context;
	private  String[] itemname={};
	private  String[] imgid={};
	private  String[] x={};
	private  String[] y={};
	public CustomListAdapter(Activity context, String[] itemname ,String[] x,String[] imgid,String[] y) {
		super(context, R.layout.mylist, itemname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.itemname=itemname;
		this.imgid=imgid;
		this.x=x;
		this.y=y;
		
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.mylist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		TextView txt = (TextView) rowView.findViewById(R.id.icon);
		TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
	
		TextView txt1 = (TextView) rowView.findViewById(R.id.icon1);
		txtTitle.setText(itemname[position]);
		txt.setText(x[position]);
		extratxt.setText(imgid[position]);
		txt1.setText(y[position]);
		return rowView;
		
	};
}