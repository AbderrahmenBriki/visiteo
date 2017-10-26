package com.appsolute.cel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.appsolute.cel.models.ApplicationData;
import com.appsolute.cel.R;

@SuppressLint("HandlerLeak")
public abstract class BaseFragment extends Fragment {
	protected Activity mActivity;
	protected static Context mContext;
    public ProgressDialog processDialog;
    Thread mBackground;
	public static Typeface menu_medium, dinpro_black, dinpro_bold, dinpro_medium, dinpro_regular, dinpro_light, disis_medium;
	protected ApplicationData applicationdata;
    public static Typeface bold;
	public Typeface normal;
    
    public void init() {
		mActivity = getActivity();
		mContext = getActivity(); 
		applicationdata = (ApplicationData)mActivity.getApplicationContext(); 
    	
    	normal = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Regular.ttf"); 
    	bold = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Bold.ttf"); 
    	
    	callHiddenKeyboard();
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
	
	 public void callAlerter(String message) {
		Builder builder = new AlertDialog.Builder(getActivity()); 
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.ok), null);
        AlertDialog dialog = builder.create();
        dialog.show();
	}
	     
    public static boolean checkNetworkConnection(Context mContext) {	
    	ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    	
    	NetworkInfo[] info = cm.getAllNetworkInfo();
    	if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	}
    
}
