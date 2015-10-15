package com.example.profitmarket;
import java.lang.reflect.Field;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class S_Analysis extends Activity {
 private DatePicker mDatepicker;
 private static TextView ss;
 String s;
 Button  button1,button2, button3, button4;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_analysis_view);
	
	    button1 = (Button)findViewById(R.id.button1);
	    button3 = (Button)findViewById(R.id.button3);
	    button4 = (Button)findViewById(R.id.button4);
	    button2 = (Button)findViewById(R.id.button2);
	    
		button2.setOnClickListener(new OnClickListener() {     
			
            public void onClick(View v) {  
                final DatePicker datePicker = new DatePicker(S_Analysis.this);  
                datePicker.setCalendarViewShown(false);  
   
                //
                try {  
                    Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");  
                    daySpinner.setAccessible(true);  
                    ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);  
                } catch (NoSuchFieldException e) {  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();  
                }  
   
               // Calendar minCalendar = Calendar.getInstance();  
               // minCalendar.get(Calendar.HOUR_OF_DAY);  
              //  minCalendar.get(Calendar.MINUTE);  
              //  minCalendar.get(Calendar.SECOND);  
               // datePicker.setMinDate(minCalendar.getTimeInMillis());  
   
               // Calendar	maxCalendar = Calendar.getInstance();  
               // maxCalendar.add(Calendar.YEAR,10);  
               // maxCalendar.add(Calendar.MONTH,12);  
               // datePicker.setMaxDate(maxCalendar.getTimeInMillis());  
   
                final Calendar	curCalendar = Calendar.getInstance();  
                datePicker.init(curCalendar.get(Calendar.YEAR),  
                curCalendar.get(Calendar.MONTH),  
                curCalendar.get(Calendar.DAY_OF_MONTH),null);  
   
                AlertDialog.Builder	builder = new AlertDialog.Builder(S_Analysis.this);  
                builder.setView(datePicker);  
                builder.setTitle("�п�ܤ��");  
                builder.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int id) {
    	        		Intent intime = new Intent();
    	    			intime.setClass(S_Analysis.this,S_Analysis_Sources.class);
    	    			int year =datePicker.getYear();
    	    			String years = Integer.toString(year); 
    	    			int month = (datePicker.getMonth()+1);
    	    			String months = Integer.toString(month); 
    	    			intime.putExtra("year", years);
    	    			intime.putExtra("month", months);

    	    			startActivity(intime);    //Ĳ�o����
    	        		//ss.setText("�A�]�w������O" +
    	        		//years+"�~" +
    	        		//months + "��" );
    	        		
    	        	}
    	        });
                
                
                AlertDialog	dialog = builder.create();  
                dialog.setCanceledOnTouchOutside(true);  
                dialog.show();  
                
                
                
                
                
                
              //  ss.setText("�A�]�w������O" +
                	//	datePicker.getYear()+"�~"+
                		//(datePicker.getMonth()+1)+"��");
                
            }  
		});
       button3.setOnClickListener(new OnClickListener() {     
			
            public void onClick(View v) {  
                final DatePicker datePicker = new DatePicker(S_Analysis.this);  
                datePicker.setCalendarViewShown(false);  
   
                //
                try {  
                    Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");  
                    daySpinner.setAccessible(true);  
                    ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);  
                } catch (NoSuchFieldException e) {  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();  
                }  
   
               // Calendar minCalendar = Calendar.getInstance();  
               // minCalendar.get(Calendar.HOUR_OF_DAY);  
              //  minCalendar.get(Calendar.MINUTE);  
              //  minCalendar.get(Calendar.SECOND);  
               // datePicker.setMinDate(minCalendar.getTimeInMillis());  
   
               // Calendar	maxCalendar = Calendar.getInstance();  
               // maxCalendar.add(Calendar.YEAR,10);  
               // maxCalendar.add(Calendar.MONTH,12);  
               // datePicker.setMaxDate(maxCalendar.getTimeInMillis());  
   
                final Calendar	curCalendar = Calendar.getInstance();  
                datePicker.init(curCalendar.get(Calendar.YEAR),  
                curCalendar.get(Calendar.MONTH),  
                curCalendar.get(Calendar.DAY_OF_MONTH),null);  
   
                AlertDialog.Builder	builder = new AlertDialog.Builder(S_Analysis.this);  
                builder.setView(datePicker);  
                builder.setTitle("�п�ܤ��");  
                builder.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int id) {
    	        		Intent intime = new Intent();
    	    			intime.setClass(S_Analysis.this,S_Analysis_Whereabouts.class);
    	    			int year =datePicker.getYear();
    	    			String years = Integer.toString(year); 
    	    			int month = (datePicker.getMonth()+1);
    	    			String months = Integer.toString(month); 
    	    			intime.putExtra("year", years);
    	    			intime.putExtra("month", months);

    	    			startActivity(intime);    //Ĳ�o����
    	        		//ss.setText("�A�]�w������O" +
    	        		//years+"�~" +
    	        		//months + "��" );
    	        		
    	        	}
    	        });
                
                
                AlertDialog	dialog = builder.create();  
                dialog.setCanceledOnTouchOutside(true);  
                dialog.show();  
                
                
                
                
                
                
              //  ss.setText("�A�]�w������O" +
                	//	datePicker.getYear()+"�~"+
                		//(datePicker.getMonth()+1)+"��");
                
            }  
		});
       button1.setOnClickListener(new OnClickListener() {     
			
           public void onClick(View v) {  
               final DatePicker datePicker = new DatePicker(S_Analysis.this);  
               datePicker.setCalendarViewShown(false);  
  
               //
               try {  
                   Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");  
                   daySpinner.setAccessible(true);  
                   ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);  
               } catch (NoSuchFieldException e) {  
                   e.printStackTrace();  
               } catch (IllegalArgumentException e) {  
                   e.printStackTrace();  
               } catch (IllegalAccessException e) {  
                   e.printStackTrace();  
               }  
  
            
  
               final Calendar	curCalendar = Calendar.getInstance();  
               datePicker.init(curCalendar.get(Calendar.YEAR),  
               curCalendar.get(Calendar.MONTH),  
               curCalendar.get(Calendar.DAY_OF_MONTH),null);  
  
               AlertDialog.Builder	builder = new AlertDialog.Builder(S_Analysis.this);  
               builder.setView(datePicker);  
               builder.setTitle("�п�ܤ��");  
               builder.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
   	        	public void onClick(DialogInterface dialog, int id) {
   	        		Intent intime = new Intent();
   	    			intime.setClass(S_Analysis.this,S_Analysis_IssueRecover.class);
   	    			int year =datePicker.getYear();
   	    			String years = Integer.toString(year); 
   	    			int month = (datePicker.getMonth()+1);
   	    			String months = Integer.toString(month); 
   	    			intime.putExtra("year", years);
   	    			intime.putExtra("month", months);

   	    			startActivity(intime);    //Ĳ�o����
   	        	
   	        		
   	        	}
   	        });
               
               
               AlertDialog	dialog = builder.create();  
               dialog.setCanceledOnTouchOutside(true);  
               dialog.show();  
               
               
               
               
               
               
             //  ss.setText("�A�]�w������O" +
               	//	datePicker.getYear()+"�~"+
               		//(datePicker.getMonth()+1)+"��");
               
           }  
		});
       button4.setOnClickListener(new OnClickListener() {     
			
           public void onClick(View v) {  
               final DatePicker datePicker = new DatePicker(S_Analysis.this);  
               datePicker.setCalendarViewShown(false);  
  
               //
               try {  
                   Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");  
                   daySpinner.setAccessible(true);  
                   ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);  
               } catch (NoSuchFieldException e) {  
                   e.printStackTrace();  
               } catch (IllegalArgumentException e) {  
                   e.printStackTrace();  
               } catch (IllegalAccessException e) {  
                   e.printStackTrace();  
               }  
  
              // Calendar minCalendar = Calendar.getInstance();  
              // minCalendar.get(Calendar.HOUR_OF_DAY);  
             //  minCalendar.get(Calendar.MINUTE);  
             //  minCalendar.get(Calendar.SECOND);  
              // datePicker.setMinDate(minCalendar.getTimeInMillis());  
  
              // Calendar	maxCalendar = Calendar.getInstance();  
              // maxCalendar.add(Calendar.YEAR,10);  
              // maxCalendar.add(Calendar.MONTH,12);  
              // datePicker.setMaxDate(maxCalendar.getTimeInMillis());  
  
               final Calendar	curCalendar = Calendar.getInstance();  
               datePicker.init(curCalendar.get(Calendar.YEAR),  
               curCalendar.get(Calendar.MONTH),  
               curCalendar.get(Calendar.DAY_OF_MONTH),null);  
  
               AlertDialog.Builder	builder = new AlertDialog.Builder(S_Analysis.this);  
               builder.setView(datePicker);  
               builder.setTitle("�п�ܤ��");  
               builder.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
   	        	public void onClick(DialogInterface dialog, int id) {
   	        		Intent intime = new Intent();
   	    			intime.setClass(S_Analysis.this,S_Analysis_Revenue.class);
   	    			int year =datePicker.getYear();
   	    			String years = Integer.toString(year); 
   	    			int month = (datePicker.getMonth()+1);
   	    			String months = Integer.toString(month); 
   	    			intime.putExtra("year", years);
   	    			intime.putExtra("month", months);

   	    			startActivity(intime);    //Ĳ�o����
   	        		//ss.setText("�A�]�w������O" +
   	        		//years+"�~" +
   	        		//months + "��" );
   	        		
   	        	}
   	        });
               
               
               AlertDialog	dialog = builder.create();  
               dialog.setCanceledOnTouchOutside(true);  
               dialog.show();  
               
               
               
               
               
               
             //  ss.setText("�A�]�w������O" +
               	//	datePicker.getYear()+"�~"+
               		//(datePicker.getMonth()+1)+"��");
               
           }  
		});
    }    


		//Calendar now = Calendar.getInstance();
		/*DatePickerDialog datePickerDlg = new DatePickerDialog(S_Analysis.this,datePickerDlgOnDateSet,
				   now.get(Calendar.YEAR),
				   now.get(Calendar.MONTH),
				   now.get(Calendar.DAY_OF_MONTH));
		datePickerDlg.setTitle("��ܤ��");
		datePickerDlg.setMessage("�п�ܭn���R���~���Τ��");*/

	/* private Button.OnClickListener btnDatePickerDlgOnClick = new Button.OnClickListener() {
			public void onClick(View v) {
				
				//Calendar now = Calendar.getInstance();
                
				DatePickerDialog datePic			Dlg = new DatePickerDialog();
				                          
				   try {  
	                    Field daySpinner = datePicDlg.getClass().getDeclaredField("mDaySpinner");  
	                    daySpinner.setAccessible(true);  
	                    ((View)daySpinner.get( datePicDlg)).setVisibility(View.GONE);  
	                } catch (NoSuchFieldException e) {  
	                    e.printStackTrace();  
	                } catch (IllegalArgumentException e) {  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    e.printStackTrace();  
	                }  
				                
				datePicDlg.setTitle("��ܤ��");
				datePicDlg.setMessage("�п�ܾA�X�z�����");
				datePicDlg.setIcon(android.R.drawable.ic_dialog_info);
				datePicDlg.setCancelable(false);
				datePicDlg.show();
			}
		};*/
	/*private DatePicker.OnDateSetListener datePickerDlgOnDateSet= new DatePicker.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			Intent intime = new Intent();
			intime.setClass(S_Analysis.this,S_Analysis_Sources.class);
			intime.putExtra("year", year);
			intime.putExtra("month", monthOfYear);
			intime.putExtra("date", dayOfMonth);
			startActivity(intime);    //Ĳ�o����
			ss.setText("�A�]�w������O" +
					Integer.toString(year) + "�~" +
					Integer.toString(monthOfYear + 1) + "��" );
			
		}
	};*/
	
	  

	private OnClickListener newOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__analysis, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*public void s_goIssueRecover_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Analysis.this,S_Analysis_IssueRecover.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}*/
	
/*	public void s_goSources_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Analysis.this,S_Analysis_Sources.class);
		startActivity(intent);    //Ĳ�o����
		   //��������
	}*/
	
	/*public void s_goWhereabouts_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Analysis.this,S_Analysis_Whereabouts.class);
		startActivity(intent);    //Ĳ�o����
		 //��������
	}*/
	
	
	/*public void s_goRevenue_onClick(View v){
		Intent intent = new Intent();
		intent.setClass(S_Analysis.this,S_Analysis_Revenue.class);
		startActivity(intent);    //Ĳ�o����
		finish();   //��������
	}*/
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Analysis.this,S_Records.class);
    	   startActivity(intent);    //Ĳ�o����
    	   finish();   //��������
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
