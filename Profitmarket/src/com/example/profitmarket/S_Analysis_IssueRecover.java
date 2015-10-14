package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
import org.achartengine.ChartFactory;  
import org.achartengine.GraphicalView;  
import org.achartengine.model.CategorySeries;  
import org.achartengine.model.SeriesSelection;  
import org.achartengine.renderer.DefaultRenderer;  
import org.achartengine.renderer.SimpleSeriesRenderer;  
  
import android.app.Activity;  
import android.graphics.Color;  
import android.os.Bundle;  
import android.view.View;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.LinearLayout;  
import android.widget.Toast;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;  


public class S_Analysis_IssueRecover extends Activity {
	public static   JSONParser jParser = new JSONParser();

	public static ArrayList<HashMap<String, String>> productsList;
	public static ArrayList<HashMap<String, String>> productsList1;		
	private float[] ydata[];
	private static final int SERIES_NR = 1;
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
	public static  String TAG_TIME = "time";
		// products JSONArray
	public static JSONArray products = null;
	private ProgressDialog pDialog;
	private static String url_all_products = "http://192.168.0.103/android_connect/get_issue.php";
	private static String url_all_products2 = "http://192.168.0.103/android_connect/get_issue2.php";
	private ArrayList<Map<String,String>> maps = new ArrayList<Map<String,String>>();
	private static int[] COLORS = new int[] { Color.YELLOW, Color.BLUE,Color.MAGENTA, Color.DKGRAY ,Color.BLACK,Color.GRAY,Color.LTGRAY,Color.RED,Color.WHITE,Color.rgb(221,160 ,221)};  
	//private static double[] VALUES = new double[] { 10, 11, 12, 13 };  
	 
	private CategorySeries mSeries = new CategorySeries("");  
	private DefaultRenderer mRenderer = new DefaultRenderer();  
	private GraphicalView mChartView;  
	//private static String[] NAME_LIST = new String[] { "A", "B", "C", "D" }; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s_analysis_issue_recover);
		new LoadAllProducts().execute();
		new LoadAllProducts2().execute();
		productsList = new ArrayList<HashMap<String, String>>(10);
		productsList1 = new ArrayList<HashMap<String, String>>(10);
		mRenderer.setApplyBackgroundColor(true);  
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));  
		mRenderer.setChartTitleTextSize(20);  
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLabelsColor(Color.RED);
		mRenderer.setLegendTextSize(15);  
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });  
		mRenderer.setZoomButtonsVisible(true);  
		mRenderer.setStartAngle(90);  
		//mRenderer.setChartTitle("ч基ㄩㄓ方┍產参璸");
		mRenderer.setChartTitle("ч基ㄩㄏノ眎计参璸");
		mRenderer.setChartTitleTextSize(30);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__analysis__issue_recover, menu);
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
	protected void onResume() {  
		super.onResume();  
		if (mChartView == null) {  
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);  
		mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);  
		mRenderer.setClickEnabled(true);  
		mRenderer.setSelectableBuffer(10);  
		mRenderer.setPanEnabled(false);  
		mRenderer.setZoomEnabled(false);
		mRenderer.setBackgroundColor(Color.CYAN);
		mRenderer.setLegendTextSize(30);




		mChartView.setOnClickListener(new View.OnClickListener() {  
		@Override  
		public void onClick(View v) {  
		SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
		  
		if (seriesSelection == null) {  
		Toast.makeText(S_Analysis_IssueRecover.this,"No chart element was clicked",Toast.LENGTH_SHORT).show();  
		} else {  
		Toast.makeText(S_Analysis_IssueRecover.this,  " 眎计="+ seriesSelection.getValue()+"眎", Toast.LENGTH_SHORT).show();  
		}  
		}  
		});  
		  
		mChartView.setOnLongClickListener(new View.OnLongClickListener() {  
		@Override  
		public boolean onLongClick(View v) {  
		SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
		if (seriesSelection == null) {  
		Toast.makeText(S_Analysis_IssueRecover.this,"No chart element was long pressed", Toast.LENGTH_SHORT);  
		return false;   
		} else {  
		Toast.makeText(S_Analysis_IssueRecover.this,"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);  
		return true;         
		}  
		}  
		});  
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
		}  
		else {  
		mChartView.repaint();  
		} 
		}


		private void drawpie(){
			 Intent intime = getIntent();
			 String  timeY =  intime.getStringExtra("year");
			
			
			String t = productsList.get(0).get("SUM(howp)");
			Double t1=Double.parseDouble(t);
			String q = productsList.get(1).get("SUM(howp)");
			Double q1=Double.parseDouble(q);
			String a = productsList.get(2).get("SUM(howp)");
			Double a1=Double.parseDouble(a);
			String b = productsList.get(3).get("SUM(howp)");
             Double timeYD=Double.parseDouble(timeY);
             Double b1=Double.parseDouble(b); 
             
             
             double[] VALUES2 = new double[productsList1.size()];
			 double as=0;
            double size1 =productsList.size();
            double size2 =productsList1.size();
             double size3= size2/size1;
             String page = productsList1.get(0).get("SUM(howp)");
			 Double page2 = Double.parseDouble(page);
			 for(int i=0;i<productsList.size();i++)
			 {
				  String pagess = productsList.get(i).get("SUM(howp)");
				  Double page233 = Double.parseDouble(pagess);
				 as=as+page233;
							 
				 
			 }
			 
			 double getas;
			 getas=as;
			double all=getas-page2;
			 
			double[] VALUES = new double[2];
			
			
			//Double  all=t1+q1+a1+b1;
			
			//String[] NAME_LIST = new String[] { t, q, };
				
			String c = productsList.get(0).get("month");
			String d = productsList.get(1).get("month");
			String e = productsList.get(2).get("month");
			String r = productsList.get(3).get("month");
			String[] NAME_LIST=new String[4];
			NAME_LIST[0]="ゼㄏノ";
			NAME_LIST[1]="ㄏノ";
			NAME_LIST[2]=e;
			NAME_LIST[3]=r;
			VALUES[0]=all;
			VALUES[1]=page2;
			
		
			//String  NAME_LIST[] = (String[]) productsList.toArray(new String[0]);
			//Double  VALUES[] = (Double[]) productsList.toArray(new Double[0]);
			for (int i = 0; i <2; i++) { 
				 String  timey = productsList.get(i).get("year");
				 Double timeyr=Double.parseDouble(timey);
				 //int timeyear = Integer.parseInt(time);
				 String  time2 = intime.getStringExtra("month");
				 Double timeMH=Double.parseDouble(time2);
				 String  timem = productsList.get(i).get("month");
				 Double timemh =Double.parseDouble(timem);
				if( (timeYD-timeyr==0&&timeMH-timemh==0)){
					
			mSeries.add(NAME_LIST[i]+(VALUES[i]), VALUES[i]); 

			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer(); 

			//renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);

			renderer.setColor(COLORS[i % COLORS.length]);

			mRenderer.addSeriesRenderer(renderer);  
				}
			}  
			  
			if (mChartView != null) {  
			mChartView.repaint();  
			}  
		}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
        	Intent intent = new Intent();  
    	    intent.setClass(S_Analysis_IssueRecover.this,S_Analysis.class);
    	   startActivity(intent);    //牟祇传
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
        	db = new SQLiteHandler_Stores(getApplicationContext());
            // session manager
            session = new SessionManager_Stores(getApplicationContext());
            HashMap<String, String> user = db.getUserDetails();
            String issue_store = user.get("name");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("issue_store", issue_store));
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
                        String year = c.getString("year");
                        String month = c.getString("month");
                        String yesorno = c.getString("yesorno");
                        String howp = c.getString("SUM(howp)");
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                    
                        // adding each child node to HashMap key => value
                        map.put("year", year);
                        map.put("month",month);
                        map.put("yesorno",  yesorno);
                        map.put("SUM(howp)", howp);
                        
                        // adding HashList to ArrayList
                        productsList.add(map);
                       // Double yesorno1=Double.parseDouble(yesorno);
                      //  if(yesorno1==1)
                        //{
                       // 	 HashMap<String, String> map1 = new HashMap<String, String>();
                       //    map1.put("year", year);
                       //     map1.put("month",month);
                       //     map1.put("yesorno",  yesorno);
                       //     map1.put("SUM(howp)", howp);
                        	
                       //     productsList1.add(map1);
                       // }
                        
                        
                    }
                } 
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }  
    }
    
    
    class LoadAllProducts2 extends AsyncTask <String, String, String> {
      	 
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
            JSONObject json = jParser.makeHttpRequest(url_all_products2, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products2: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray("products2");

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String year = c.getString("year");
                        String month = c.getString("month");
                        String yesorno = c.getString("yesorno");
                        String howp = c.getString("SUM(howp)");
                        // creating new HashMap
                        HashMap<String, String> map1 = new HashMap<String, String>();
                    
                        // adding each child node to HashMap key => value
                        map1.put("year", year);
                        map1.put("month",month);
                        map1.put("yesorno",  yesorno);
                        map1.put("SUM(howp)", howp);
                        
                        // adding HashList to ArrayList
                        productsList1.add(map1);
                    
                     
                     
                        
                        
                    }
                } 
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		super.onPostExecute(result);
    		drawpie();
    	}
    }
 
}