package com.example.profitmarket;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import app.AppConfig_Stores;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import helper.SQLiteHandler;
import helper.SQLiteHandler_Stores;
import helper.SessionManager;
import helper.SessionManager_Stores;

public class S_Analysis_Whereabouts extends Activity {
	
	public static JSONParser jParser = new JSONParser();
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	public static ArrayList<HashMap<String, String>> productsList;
	public  ArrayList arr2= new ArrayList();
	private int[] ydata[];
	private static final int SERIES_NR = 1;
    
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
	public static  String TAG_YEAR = "year";
	public static  String TAG_MONTH = "month";
	// products JSONArray
	public static JSONArray products = null;
	private ProgressDialog pDialog;
	
	//private static String url_all_products = "http://192.168.0.102/analysis/get_all_issue.php";

	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.s_analysis_whereabouts);
		new LoadAllProducts().execute();
		productsList = new ArrayList<HashMap<String, String>>();	  
	}
	 /* public static void main(String[] args) {
		     //初始化ArrayList
		     ArrayList<String> myList = new ArrayList<String>();
		     myList.add(productsList.get(0).get("receive"));
		     myList.add("Apple");
		     myList.add("Design");
		     myList.add("Center");
		 	String r = productsList.get(0).get("SUM(howp)");
		     //將排序前的資料顯示出來
		     System.out.println("排序前：");
		     for (String str : myList) {
		     System.out.println(str);
		     }

		     //進行排序
		     Collections.sort(myList);
		     System.out.println("\n");

		     //將排序後的資料顯示出來
		     System.out.println("排序後：");
		     for (String str : myList) {
		     System.out.println(str);
		     }
		    }*/
		
	protected void onTime(){
		
		
		
		
		
		
		
	}
	
	
	protected void onTry() {
		/*pDialog = ProgressDialog.show(S_Analysis_Revenue.this,
		        "讀取中", "請等待3秒...",true);
		new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try{
		            Thread.sleep(6000);
		        }
		        catch(Exception e){
		            e.printStackTrace();
		        }
		        finally{
		            pDialog.dismiss();
		        }
		    } 
		}).start();*/
		XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
		Intent intent = ChartFactory.getBarChartIntent ( this , getBarDemoDataset(), renderer, Type. DEFAULT );
		startActivity(intent);
		finish();
		}

		private XYMultipleSeriesDataset getBarDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		 Intent intime = getIntent();
		

		for ( int i = 0; i < SERIES_NR ; i++) {
		CategorySeries series = new CategorySeries(  "折價卷張數"  );
		
		for ( int k = 0; k <productsList.size(); k++) {
			 String  timeY =  intime.getStringExtra("year");
			  Map<Integer, String> treeMap = new TreeMap<Integer, String>(  
		                new Comparator<Integer>() {  
		  
		                    @Override  
		                    public int compare(Integer o1, Integer o2) {  
		                        return o2.compareTo(o1);//降序  
		                    }  
		                });   
			 String s1 =productsList.get(k).get("SUM(howp)");
			 int is = Integer.parseInt(s1);
			 String r1 = productsList.get(k).get("receive_store");
			// int r2 = Integer.parseInt(r1);
 			 treeMap.put(is, r1);
			
 	         List<Entry<Integer, String>> arrayList = new ArrayList<Entry<Integer, String>>(treeMap.entrySet());  
			
 	         Iterator<Integer> iter = treeMap.keySet().iterator();  
 	     
			 Double timeYD=Double.parseDouble(timeY);
			 
			 String  timey = productsList.get(k).get("YEAR(created_date)");
			 Double timeyr=Double.parseDouble(timey);
			 //int timeyear = Integer.parseInt(time);
			 
			 String  time2 = intime.getStringExtra("month");
			 Double timeMH=Double.parseDouble(time2);
			
			 String  timem = productsList.get(k).get("MONTH(created_date)");
	
			 Double timemh =Double.parseDouble(timem);
			 if( (timeYD-timeyr==0&&timeMH-timemh==0)){
				  while (iter.hasNext()) {
					  Integer key = iter.next();  
			 	      String qaw =  treeMap.get(key);
				         String s = String.valueOf(qaw);      
				         series.add(key);
				          Log.d("All Products23: ", s );
			 	      }
	 
			}
			  else{
				  Toast.makeText(this, "本月份尚未有資料", Toast.LENGTH_LONG).show();
				  break;
			  }
		}
		finish();
		dataset.addSeries(series.toXYSeries());
		}
		
		return dataset;
		}                

		public XYMultipleSeriesRenderer getBarDemoRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setPanEnabled(false, true);
		
	 for(int i=0;i<productsList.size();i++){
				// String name2=productsList.get(i).get(TAG_NAME);
				// if(username.equals(name2)){
				// renderer.addXTextLabel(i+1, productsList.get(i).get("receive_store"));
				// }	
		 Map<Integer, String> treeMap = new TreeMap<Integer, String>(  
	                new Comparator<Integer>() {  
	  
	                    @Override  
	                    public int compare(Integer o1, Integer o2) {  
	                        return o2.compareTo(o1);//降序  
	                    }  
	                });   
		 String s1 =productsList.get(i).get("SUM(howp)");
		 int is = Integer.parseInt(s1);
		 String r1 = productsList.get(i).get("receive_store");
		// int r2 = Integer.parseInt(r1);
		 treeMap.put(is, r1);
		
      List<Entry<Integer, String>> arrayList = new ArrayList<Entry<Integer, String>>(treeMap.entrySet());  
		
     Iterator<Integer> iter = treeMap.keySet().iterator();  
			//String rrr =(String)arr2.get(i);
			//Double rd2=Double.parseDouble(rd);
			//String rrr = productsList.get(i).get("receive_store");
		// for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			//   Map.Entry mapEntry = (Map.Entry) it.next();
      while (iter.hasNext()) {
		  Integer key = iter.next();  
 	      String qaw =  treeMap.get(key);
	         String s = String.valueOf(qaw); 
	       //  int is3 = Integer.parseInt(qaw);
	       //  int is5 = Integer.parseInt(key);
	         renderer.addXTextLabel(i+1,qaw);
	         //series.add(qaw);
	          Log.d("All Products234: ", s );
 	      }
			 
	         // Log.d("All Products23: ", mapper.getKey()  );
	              
			  // String qe=  ( String) mapper.getKey();
		       // String s = String.valueOf(qe);
		         
		
		  //}
			 }
		   /*for(int i=0;i<6;i++){
			renderer.addXTextLabel(i, d[i]);
		}*/
		  /* renderer.addTextLabel(1,"文沃的店");
		   renderer.addTextLabel(2,"浩克的店");
		   renderer.addTextLabel(3,"帥哥的店");
		   renderer.addTextLabel(4,"帥哥的店2"); 
		*/
		renderer.setMargins(new int[] {40, 50, 25, 25}); 
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.CYAN);
		renderer.setXLabelsAlign(Align.CENTER);            
		renderer.setYLabelsAlign(Align.CENTER); 
		renderer.setLabelsTextSize(10); 
		renderer.setZoomEnabled(false,false);
		renderer.setXLabels(0); 
		renderer.setShowGrid(true);
		renderer.setShowGridY(true);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setGridColor(Color.BLUE);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setLegendTextSize(20);
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.RED );

		renderer.addSeriesRenderer(r);
		        
		//r = new SimpleSeriesRenderer();

		//r.setColor(Color. RED );

		//renderer.addSeriesRenderer(r);
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(20);
		setChartSettings(renderer);
		return renderer;
		}
		private void setChartSettings(XYMultipleSeriesRenderer renderer) {
		renderer.setChartTitle( "折價券使用店家分析" );
		renderer.setXTitle( "使用店家 " );
		renderer.setChartTitleTextSize(40);
		renderer.setPanEnabled(true, false);
		renderer.setYTitle("折價券張數  ");
		renderer.setAxisTitleTextSize(25);
		renderer.setBarSpacing(0.5);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(productsList.size());
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(15);
		}
		 public boolean onKeyDown(int keyCode, KeyEvent event) {
		        
		        if (keyCode == KeyEvent.KEYCODE_BACK)
		        {
		            // Show home screen when pressing "back" button,
		            //  so that this app won't be closed accidentally
		        
		    	   finish();   //結束本頁
		         
		            return true;
		        }
		        
		        return super.onKeyDown(keyCode, event);
		    }
    class LoadAllProducts extends AsyncTask <String, String, String> {
   	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	
        	db = new SQLiteHandler_Stores(getApplicationContext());
	        // session manager
	        session = new SessionManager_Stores(getApplicationContext());
	        HashMap<String, String> user = db.getUserDetails();
	        String issue_store = user.get("name");
	        
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("issue_store", issue_store));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(AppConfig_Stores.url_get_recordtoAW, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products_ISSUE: ", json.toString());
            

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray("issueqpon");
                   
                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                      
                        // Storing each json item in variable
                       // String id = c.getString(TAG_PID);
                      //  String name = c.getString(TAG_NAME);
                      //  String price = c.getString(TAG_PRICE);
	                   String[] check =  new String[products.length()];
	                   check[i] = String.valueOf(c.getString("receive_store"));
	                  
                        String YEARcreated_date = c.getString("YEAR(created_date)");
                        String MONTHcreated_date = c.getString("MONTH(created_date)");
                        String receive_store = c.getString("receive_store");
                        String howp = c.getString("SUM(howp)");
                        // creating new HashMap
                        
						
                        Double howps=Double.parseDouble(howp);
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                      //  map.put(TAG_PID, id);
                      //  map.put(TAG_NAME, name);
                       // map.put(TAG_PRICE, price);
                        if (howps!=0){
                        map.put("YEAR(created_date)", YEARcreated_date);
                        map.put("MONTH(created_date)", MONTHcreated_date);
                        map.put("receive_store", receive_store);
                        map.put("SUM(howp)",howp);
                        // adding HashList to ArrayList
                        productsList.add(map);
                        
	                   }
                  
				}
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
    		super.onPostExecute(result);
    		onTry();
    		
    }
      
}
    
    
    
    class myComparator implements Comparator<Map.Entry<Integer, String>>{  
    	  
        public int compare(Entry<Integer, String> o1,  
                Entry<Integer, String> o2) {  
            return o1.getKey()-o2.getKey();  
        }  
    }
}