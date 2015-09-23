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
public class S_Analysis_Whereabouts extends Activity {
	
	public static   JSONParser jParser = new JSONParser();
	  
	 public static ArrayList<HashMap<String, String>> productsList;
		
	private float[] ydata[];
	private static final int SERIES_NR = 1;

	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
	// products JSONArray
	public static JSONArray products = null;
	private ProgressDialog pDialog;
	private static String url_all_products = "http://10.3.204.2/android_connect/get_all_products.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.s_analysis_whereabouts);
		new LoadAllProducts().execute();
		productsList = new ArrayList<HashMap<String, String>>(10);	  
	}
	protected void onTry() {
		/*pDialog = ProgressDialog.show(S_Analysis_Revenue.this,
		        "弄い", "叫单3...",true);
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

		//final int nr = 1;
	    //String t = productsList.get(1).get(TAG_PRICE);
		//Double t1=Double.parseDouble(t);
		//String q = productsList.get(2).get(TAG_PRICE);
		//Double q1=Double.parseDouble(q);
		for ( int i = 0; i < SERIES_NR ; i++) {
		CategorySeries series = new CategorySeries( "ч基ㄩ眎计"  );
		for ( int k = 0; k < productsList.size(); k++) {
			String r = productsList.get(k).get(TAG_PRICE);
			Double r1=Double.parseDouble(r);
			series.add( r1 );
		}
		/*
		for ( int z = 0; z < 1; z++) {
		series.add(t1);
		}
		for ( int w = 0; w < nr; w++) {
		series.add(q1);
		}
		*/
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
			/*String[] d =new String[10];
			 d[1]="1231";
			 d[2]="21";
			 d[3]="123";
			 d[4]="12321";
			 d[5]="12ww1";
			 d[6]="ww";*/
			 for(int i=0;i<products.length();i++){
				 renderer.addXTextLabel(i+1, productsList.get(i).get(TAG_NAME));
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
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
        }

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
                        
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_PRICE, price);
                        
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