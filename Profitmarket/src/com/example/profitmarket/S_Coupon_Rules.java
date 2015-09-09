package com.example.profitmarket;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class S_Coupon_Rules extends Fragment {
	CustomListAdapter2 adapter;
	EditText input;
	ListView list;
	Spinner spinner;
	Button add, clear, save;
	String[] z = new String[100];
	String[] cost = new String[100];
	String[] x = new String[100];
	String[] howmuch = new String[100];
	String[] y = new String[100];
	int k=0;
	 private View v;
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	   
	     
	  }
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.s_coupon_rules, container,
				false);
		
	     input= (EditText) v.findViewById(R.id.abc);
	     
	     spinner=(Spinner)v.findViewById(R.id.spinner1);
	     adapter = new CustomListAdapter2(getActivity(), z,cost, x, howmuch, y);
			list = (ListView) v.findViewById(R.id.jkl);
			list.setAdapter(adapter);
			add =(Button) v.findViewById(R.id.def);
			clear = (Button) v.findViewById(R.id.ghi);
			save = (Button) v.findViewById(R.id.mno);
			add.setOnClickListener(addOnClickListener);
		return v;
		
	}
	private Button.OnClickListener addOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			String moneyinput = input.getText().toString();
			
			try {
				if (input.getText().toString().startsWith("0")) {
					input.setText("");
					
					Toast.makeText(getActivity(), "���ର�s", Toast.LENGTH_SHORT).show();
				} 
				
				else if (moneyinput.isEmpty()){
					Toast.makeText(getActivity(), "���ର�ť�", Toast.LENGTH_SHORT).show();
				}
				

					else {

						cost[k] = input.getText().toString();
						howmuch[k] = input.getText().toString();

						list.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					
						z[k]="���O�F";
						x[k] = "�o������";
						y[k] = "��";
						k++;
						
						input.setText("");
					
					
						Toast.makeText(getActivity(), "�s�W���\", Toast.LENGTH_SHORT).show();
					
					
				}
			} catch (NumberFormatException e) {
				Toast.makeText(getActivity(), "��줣�ର��", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getActivity(), "����W�L�Q�ӿﶵ��", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
}