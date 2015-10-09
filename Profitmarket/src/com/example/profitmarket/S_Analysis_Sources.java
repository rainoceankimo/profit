package com.example.profitmarket;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.profitmarket.S_Analysis_Revenue.LoadAllProducts;

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
import helper.SQLiteHandler;
import helper.SessionManager;
public class S_Analysis_Sources extends Activity {
	public static   JSONParser jParser = new JSONParser();
	private SQLiteHandler db;
    private SessionManager session;
	 public static ArrayList<HashMap<String, String>> productsList;
		
	private float[] ydata[];
	private static final int SERIES_NR = 1;
    String time,time2,time3;
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
	public static  String TAG_YEAR = "year";
	public static  String TAG_MONTH = "month";
	public static  String TAG_DAY = "day";
	// products JSONArray
	
	public static JSONArray products = null;
	private ProgressDialog pDialog;
	private static String url_all_products = "http://10.3.204.2/android_connect/get_all_products.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.s_analysis_sources);
		   Intent intime = getIntent();
		   time = intime.getStringExtra(TAG_YEAR);
		   time2 = intime.getStringExtra(TAG_MONTH);
		
		new LoadAllProducts().execute();
		productsList = new ArrayList<HashMap<String, String>>(10);	 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__analysis__sources, menu);
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
	protected void onTry() {
		
		XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
		Intent intent = ChartFactory.getBarChartIntent ( this , getBarDemoDataset(), renderer, Type. DEFAULT );
		startActivity(intent);
		finish();
		
		}
	private XYMultipleSeriesDataset getBarDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				for ( int i = 0; i < SERIES_NR ; i++) {
					CategorySeries series = new CategorySeries( "ч基ㄩ眎计"  );
					for ( int k = 0; k < productsList.size(); k++) {
						if( productsList.get(k).get(TAG_YEAR) ==time && productsList.get(k).get(TAG_MONTH)==time2)
					{
						String r = productsList.get(k).get(TAG_PRICE);
						Double r1=Double.parseDouble(r);
						series.add( r1 );
					}		   
		       }
		dataset.addSeries(series.toXYSeries());
		   }
		
		return dataset;
		}      

		public XYMultipleSeriesRenderer getBarDemoRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		 //int PID= Integer.valueOf(TAG_PID);
		 //int[] yo =new int[PID];
		
		int qw=5;
		double ts=1;
		  renderer.setPanEnabled(false, true);
		    db = new SQLiteHandler(getApplicationContext());
	        // session manager
	        session = new SessionManager(getApplicationContext());
	        HashMap<String, String> user = db.getUserDetails();
	        String username = user.get("name");
			 for(int i=0;i<products.length();i++){
				 if(username==productsList.get(i).get(TAG_NAME))
				 {
				 renderer.addXTextLabel(i+1, productsList.get(i).get(TAG_NAME));}
			 }
		   /*for(int i=0;i<6;i++){
			renderer.addXTextLabel(i, d[i]);
		}*/
		  /* renderer.addTextLabel(1,"ゅ║┍");
		   renderer.addTextLabel(2,"疎┍");
		   renderer.addTextLabel(3,"┍");
		   renderer.addTextLabel(4,"┍2"); 
		*/
		renderer.setMargins(new int[] {40, 50, 15, 0}); 
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.CYAN);
		renderer.setXLabelsAlign(Align.CENTER);            
		renderer.setYLabelsAlign(Align.CENTER); 
		renderer.setLabelsTextSize(20); 
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
		renderer.setChartTitle( "ч基ㄩㄏノ┍產だ猂" );
		renderer.setXTitle( "ㄏノ┍產 " );
		renderer.setChartTitleTextSize(40);
		renderer.setPanEnabled(false, false);
		renderer.setYTitle("ч基ㄩㄏノ秖  ");
		renderer.setAxisTitleTextSize(25);
		renderer.setBarSpacing(0.1);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(10.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(1000);
		}
		 public boolean onKeyDown(int keyCode, KeyEvent event) {
		        
		        if (keyCode == KeyEvent.KEYCODE_BACK)
		        {
		            // Show home screen when pressing "back" button,
		            //  so that this app won't be closed accidentally
		        
		    	   finish();   //挡セ
		         
		            return true;
		        }
		        
		        return super.onKeyDown(keyCode, event);
		    }
    
		 class LoadAllProducts extends AsyncTask <String, String, String> {
		   	 
		        /**
		         * Before starting background thread Show Progress Dialog
		         * */
		       // @Override
		       /* protected void onPreExecute() {
		            super.onPreExecute();
		           
		        }*/
		       
		        /**
		         * getting All products from url
		         * */
		        protected String doInBackground(String... args) {
		            // Building Parameters
		            List<NameValuePair> params = new ArrayList<NameValuePair>();
		            // getting JSON string from URL
		            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

		            // Check your log cat for JSON reponse
		            Log.d("All Products: ", json.toString());

		            try {
		                // Checking for SUCCESS TAG
		                int success = json.getInt(TAG_SUCCESS);
		                 
		                if (success == 1) {
		                    // products found
		                    // Getting Array of Products
		                    products = json.getJSONArray(TAG_PRODUCTS);

		                    // looping through All Products
		                    for (int i = 0; i < products.length(); i++) {
		                        JSONObject c = products.getJSONObject(i);

		                        // Storing each json item in variable
		                        String id = c.getString(TAG_PID);
		                        String name = c.getString(TAG_NAME);
		                        String price = c.getString(TAG_PRICE);
		                        String year = c.getString(TAG_YEAR);
		                        // creating new HashMap
		                        HashMap<String, String> map = new HashMap<String, String>();

		                        // adding each child node to HashMap key => value
		                        map.put(TAG_PID, id);
		                        map.put(TAG_NAME, name);
		                        map.put(TAG_PRICE, price);
		                        map.put(TAG_YEAR, year);
		                        // adding HashList to ArrayList
		                        productsList.add(map);
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
}
