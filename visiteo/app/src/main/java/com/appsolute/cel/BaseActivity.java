package com.appsolute.cel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.models.ApplicationData;
import com.appsolute.cel.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

public abstract class BaseActivity extends FragmentActivity {
	public static Context mContext;
	public static Activity mActivity;
	protected ActionBar actionBar;
    public static ProgressDialog processDialog;
    public Typeface bold, normal;
	public static CEL_Mission_DAO mission_DAO;
	protected ApplicationData applicationdata;
	protected static AQuery mAQuery;
	static boolean flag=true;
	public static byte[] data=null;
	    
    public void baseMethod() {
    	mContext = this;
    	mActivity = this;
        actionBar = getActionBar();
        mAQuery = new AQuery(mActivity); 
    	normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"); 
    	bold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"); 
		applicationdata = (ApplicationData)mContext.getApplicationContext();  
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	/*try {
	        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	        if(imm!=null && this.getCurrentFocus().getWindowToken()!=null)
	        	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}*/
        return true;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	baseMethod();
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	        case android.R.id.home:
	        	onBackPressed();
	        	break;
    	}
		return super.onOptionsItemSelected(item);    	
    }
    
    public static void showProgress(Context context, String message) {
    	processDialog = ProgressDialog.show(context, "", message, true);
    	processDialog.setCancelable(false);
    }
    
    public static void closeProgress() {
    	if(processDialog!=null && processDialog.isShowing())
    		processDialog.dismiss();
    }    
       
    public void callErrorTost(String message) {
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    
    public void callAlerter(String message) {
		Builder builder = new AlertDialog.Builder(mContext); 
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.ok), null);
        AlertDialog dialog = builder.create();
        dialog.show();
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(mission_DAO!=null)
    		mission_DAO.close();
    }
    
    public void callHiddenKeyboard() {
    	try {
	        InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
	        if(imm!=null && mActivity.getCurrentFocus()!=null)
	        	imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void callHiddenKeyboardDialog(Dialog dialog) {
    	try {
    		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    
    public String postByteUrl(String url, byte[] data) throws Exception {
        try {
            String fileName = Utils.getTodaysDate() + "_" + Utils.getCurrentTime() + "_"+ Math.random()+".png" ;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            ByteArrayBody bab = new ByteArrayBody(data, fileName);
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("photo", bab);
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
 
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
            if(s!=null && s.length()!=0) {
				JSONObject obj = new JSONObject(s.toString());
				if(obj.has("url")) {
					return obj.getString("url");
				}
			}
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage());
        }
		return "";
    }
    
}
