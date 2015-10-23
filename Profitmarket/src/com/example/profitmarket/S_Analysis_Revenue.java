package com.example.profitmarket;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppConfig_Stores;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.NameValuePair;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import app.AppController;
import helper.SQLiteHandler_Stores;
import helper.SessionManager_Stores;
import helper.TradedetailDbHandler;  



public class S_Analysis_Revenue extends Activity {
	public static   JSONParser jParser = new JSONParser();
	 String  timeY ;
	 String  time2;
  public	 	int as=0;
  public				int getas;	
  public	 int MONEY; 
  public	int MONEY2;
  public			int as2=0;
  public       	int getas2;
  
  
	private static final String DB_FILE = "tradedetail.db",
			                    DB_TABLE = "tradedetail";

	public static final String TID = "id";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String CONSUMPTION = "consumption";
    public static final String DISCOUNT = "discount";
    public static final String GRANT = "grant";
    public static final String GRANTDENOMINATIONS = "grantdenominations";
    public static final String MEMNAME = "memname";
    public static final String QPONUSE = "Qponuse";
    public static final String QPONNO = "QponNo";
    public static final String USEDENOMINATIONS = "usedenominations";
    public static final String COUNTCONSUMPTION = "countconsumption";
	public static final String TRADETTMONEY = "tradettmoney";
	
	String[] columns = 
		{TID,TYPE,DATE,CONSUMPTION,DISCOUNT,GRANT,GRANTDENOMINATIONS,MEMNAME,QPONUSE,QPONNO,USEDENOMINATIONS,COUNTCONSUMPTION,TRADETTMONEY};
	
	private SQLiteDatabase mtradeDb;
	
	
	
	//ArrayList<Integer> id = new ArrayList<Integer>();
	private SQLiteHandler_Stores db;
	private SessionManager_Stores session;
	public static ArrayList<HashMap<String, String>> productsList;
			
	

	ArrayList<Integer> id = new ArrayList<Integer>();
	ArrayList<Integer> name = new ArrayList<Integer>();

	List<Map<String, Object>> mList;
	public static  String TAG_SUCCESS = "success";
	public static  String TAG_PRODUCTS = "products";
	public static String TAG_PID = "pid";
	public static  String TAG_NAME = "name";
	public static  String TAG_PRICE = "price";
		// products JSONArray
	public static JSONArray products = null;
	//private static String url_all_products = "http://192.168.0.103/android_connect/get_all_products.php";
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
		setContentView(R.layout.s_analysis_revenue);

		 Intent intime = getIntent();
		  timeY =  intime.getStringExtra("year");
		  Double timeYD=Double.parseDouble(timeY);
		time2 = intime.getStringExtra("month");
		 Double timeMH=Double.parseDouble(time2);
		
		AppController globalVariable = (AppController)getApplicationContext();
		Bundle extras = getIntent().getExtras();
		
		TradedetailDbHandler tradedetailDbHandler = 
				new TradedetailDbHandler(getApplicationContext(), DB_FILE, null, 1);
		mtradeDb = tradedetailDbHandler.getWritableDatabase();
		
		//mList = new ArrayList<Map<String,Object>>();
	    Cursor cursor = mtradeDb.rawQuery(
	    		"select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
	    		DB_TABLE + "'", null);
	    
	    if(cursor != null) {
	        if(cursor.getCount() == 0){	// ⊿Τ戈璶ミ戈
	        	mtradeDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
	        			          TID + " INTEGER PRIMARY KEY," +
	        			          TYPE + " TEXT NOT NULL," +
	        			          DATE + " TEXT," +
	        			          CONSUMPTION + " TEXT," +
	        			          DISCOUNT + " TEXT," +
	        			          GRANT + " TEXT," +
	        			          GRANTDENOMINATIONS + " TEXT," +
	        			          MEMNAME + " TEXT," +
	        			          QPONUSE + " TEXT," +
	        			          QPONNO + " TEXT," +
	        			          USEDENOMINATIONS + " TEXT," +
	        			          COUNTCONSUMPTION + " TEXT," +
	        			          TRADETTMONEY + " TEXT);");
	        	
	        	Toast.makeText(this, "穝糤Θ", Toast.LENGTH_SHORT).show();
		        
	        }else
	        {
	        	//Toast.makeText(S_Tradedetail.this, "穝糤ア毖", Toast.LENGTH_SHORT).show();
	        }

	        cursor.close();
	    }else
	    {
	    	Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
	    }
	    
		//productsList = new ArrayList<HashMap<String, String>>(10);
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
		mRenderer.setChartTitle("ч基ㄩ犁Μ癪膍参璸");
		mRenderer.setChartTitleTextSize(30);
		SHOWLIST2();
		drawpie();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.s__analysis__revenue, menu);
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
		Toast.makeText(S_Analysis_Revenue.this,"No chart element was clicked",Toast.LENGTH_SHORT).show();  
		} else {  
		Toast.makeText(S_Analysis_Revenue.this,"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();  
		}  
		}  
		});  
		  
		mChartView.setOnLongClickListener(new View.OnLongClickListener() {  
		@Override  
		public boolean onLongClick(View v) {  
		SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
		if (seriesSelection == null) {  
		Toast.makeText(S_Analysis_Revenue.this,"No chart element was long pressed", Toast.LENGTH_SHORT);  
		return false;   
		} else {  
		Toast.makeText(S_Analysis_Revenue.this,"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);  
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
			  timeY =  intime.getStringExtra("year");
			  Double timeYD=Double.parseDouble(timeY);
			time2 = intime.getStringExtra("month");
			 Double timeMH=Double.parseDouble(time2);
			
			 
			//String t = productsList.get(0).get(TAG_PID);
			//Double t1=Double.parseDouble(t);
			//String q = productsList.get(1).get(TAG_PID);
			//Double q1=Double.parseDouble(q);
			//String a = productsList.get(2).get(TAG_PID);
			//Double a1=Double.parseDouble(a);
			//String b = productsList.get(3).get(TAG_PID);
			
			//Double b1=Double.parseDouble(b);
			int[] VALUES = new int[4];
			VALUES[0]=getas;
			VALUES[1]=getas2;
			//VALUES[2]=a1;
			//VALUES[3]=b1;
			
			//Double  all=t1+q1+a1+b1;
			
			//String[] NAME_LIST = new String[] { t, q, };
				
			//String sc23= productsList.get(0).get(TAG_NAME);
			//String d = productsList.get(1).get(TAG_NAME);
			//String e = productsList.get(2).get(TAG_NAME);
			//String r = productsList.get(3).get(TAG_NAME);
			String[] NAME_LIST=new String[4];
			NAME_LIST[0]="ㄏノ";
			NAME_LIST[1]="ゼㄏノ";
			//NAME_LIST[2]=e;
			//NAME_LIST[3]=r;
			
			//String  NAME_LIST[] = (String[]) productsList.toArray(new String[0]);
			//Double  VALUES[] = (Double[]) productsList.toArray(new Double[0]);
			for (int i = 0; i <2; i++) { 

			mSeries.add(NAME_LIST[i]+(VALUES[i]) , VALUES[i]); 

			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer(); 

			//renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);

			renderer.setColor(COLORS[i % COLORS.length]);

			mRenderer.addSeriesRenderer(renderer);  
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
    	    intent.setClass(S_Analysis_Revenue.this,S_Analysis.class);
    	   startActivity(intent);    //牟祇传
    	   finish();   //挡セ
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
 
public void SHOWLIST2(){

	
		Cursor c1 = mtradeDb.query(true, DB_TABLE, columns, null, null, null, null, null, null);
	//Cursor c = mtradeDb.rawQuery("select * from "+DB_TABLE+" where strftime('%m', date) = '10'",null);
	//Cursor c = mtradeDb.query(true, DB_TABLE, columns, null, null, null, null, null, null);
	//String strSQL = String.format("SELECT * FROM DB_TABLE WHERE strftime('%m', date)", DB_TABLE, time2 ); 
	//Cursor c = mtradeDb.rawQuery("select * from "+DB_TABLE+" where date like= %"+timeY+"-"+time2+"%",null);
	//Cursor c = mtradeDb.rawQuery("selcet sum(consumption) [mmm] form (select * from "+DB_TABLE+" where date like '%"+timeY+"-"+time2+"%'"),null);
	//Cursor c = mtradeDb.rawQuery(strSQL ,null);
		
		if (c1 == null)
	        return;

	  	  while(c1.moveToNext())
		  {
	  		
			  String pname = c1.getString(3);
			// if(pname.indexOf(timeY)){}
				 int  pname2 = Integer.parseInt(pname);
			  String nousedollar = c1.getString(12);
			 int  nousedollar2 = Integer.parseInt(nousedollar);
			  //  HashMap<String, String> map = new HashMap<String, String>();
			 //   HashMap<String, String> map2 = new HashMap<String, String>();
			    
			 //   map.put("pname",pname);
			    
			  //  map2.put("nousedollar",nousedollar);
			    
			    name.add(pname2);
			    id.add(nousedollar2);
			    // mtradeDb(DB_TABLE,"SELECT * FROM DB_TABLE;");
	  		
		  }

	//	int a = name.size();
	//	String a2= String.valueOf(a);
		
	//	Log.d("size", a2);
	  	int recodeNum = id.size();
	  	int recodeNum2 = name.size();
		String[] pid = new String[recodeNum];
		String[] names = new String[recodeNum2];
		
		
	
		for (int i = 0; i < recodeNum ; i++) {
			MONEY = id.get(i);
			// String pagess = name.get(i).get("pname");
			 // int page233 =  Integer.parseInt(pagess);
			  as=as+MONEY;

			
		}
	
		 getas=as;
		
		for (int k = 0; k < recodeNum2 ; k++) {
			//String nousemoney = name.get(k).get("nousedollar");
			 // int nousemoney23 =  Integer.parseInt(nousemoney);
			MONEY2 = name.get(k);
            as2=as2+MONEY2;
			
		}
		
		 getas2=as2;
	
		
		 
		 
	}
   /*public void SHOWLIST(){
	
		Cursor c = mtradeDb.query(true, DB_TABLE, columns, null, null, null, null, null, null);
		
		if (c == null)
	        return;

      
	  	  while(c.moveToNext())
		  {
			
			  String pid = c.getString(0);
			  String pname = c.getString(2);
			  id.add(pid);
			  name.add(pname);
		  }

		
		int recodeNum = name.size();
		String[] pid = new String[recodeNum];
		String[] names = new String[recodeNum];
		
		
		for (int i = 0; i < recodeNum ; i++) {

			pid[i] = id.get(i);
			names[i] = name.get(i);
			
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("pid", pid[i]);
			item.put("pname", names[i]);
			
			mList.add(item);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, mList,
    		R.layout.s_list_trade,
    		new String[] {"pid","pname" },
    		new int[] {R.id.ttV1,R.id.ttV2 });

		setListAdapter(adapter);
		
		
	}
*/
    
    
}
