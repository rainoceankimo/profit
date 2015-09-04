package com.example.profitmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class CustomListAdapter2 extends ArrayAdapter<String> {
 
	private final Context context;
	private  String[] cost={};
	private  String[] howmuch={};
	private  String[] x={};
	private  String[] y={};
	private  String[] z={};
	public CustomListAdapter2(Context context,String[] z, String[] cost ,String[] x,String[] howmuch,String[] y) {
		super(context, R.layout.mylist, cost);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.cost=cost;
		this.howmuch=howmuch;
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.mylist2, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.b);
		TextView txt = (TextView) rowView.findViewById(R.id.c);
		TextView extratxt = (TextView) rowView.findViewById(R.id.d);
	
		TextView txt1 = (TextView) rowView.findViewById(R.id.e);
		TextView txt2 = (TextView) rowView.findViewById(R.id.a);
		txt2.setText(z[position]);
		txtTitle.setText(cost[position]);
		txt.setText(x[position]);
		extratxt.setText(howmuch[position]);
		txt1.setText(y[position]);
		return rowView;
		
	};
}