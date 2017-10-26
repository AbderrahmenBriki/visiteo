package com.appsolute.cel.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.appsolute.cel.BuildConfig;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.models.ApplicationData;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.utils.InternetConnection;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import io.fabric.sdk.android.Fabric;

public class Splash extends Activity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, 
LocationListener {
	
	LocationRequest mLocationRequest;
	GoogleApiClient mGoogleApiClient;
	Location mCurrentLocation = null;
	public static final int UPDATE_INTERVAL_IN_SECONDS = 1;
	private static final long UPDATE_INTERVAL = 1000 * UPDATE_INTERVAL_IN_SECONDS;
	private ApplicationData applicationdata;
	boolean m_bSplashActive = true;
	Context mContext;
	ProgressBar progressBar;
	int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	LinearLayout linearLayout3;	
	protected static AQuery mAQuery;
	public static String BASE_URL = "http://pdf.opera-groupe.fr/WSAndroide/index.php/";

	//Permission
	private static final int MY_PERMISSIONS_REQUEST = 1;
	private static final int REQUEST_CODE_LOCATION = 2;
    static SharedPreferences settings;
    public static final String PREFS_NAME = "CellPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		if(!BuildConfig.DEBUG)
			Fabric.with(this, new Crashlytics());

		mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();
		 
		
		mContext = this;

        //new ExportDatabaseFileTask();

		mAQuery = new AQuery(mContext);  	        
		progressBar = (ProgressBar)findViewById(R.id.progressBar);  
		linearLayout3 = (LinearLayout)findViewById(R.id.linearLayout3);
		applicationdata = (ApplicationData)getApplicationContext();    

		if(InternetConnection.isConnected(mContext))  {
			linearLayout3.setVisibility(View.VISIBLE);			
		} else  {
			linearLayout3.setVisibility(View.INVISIBLE);
		}

		try {     			
			Thread splashTimer = new Thread() {
				public void run() {
					long l1 = System.currentTimeMillis();
					long l2 = l1;

					while (progressBarStatus < 10) {
						progressBarStatus = progressBarStatus+1;
						try {

							Thread.sleep(75);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						progressBarHandler.post(new Runnable() {
							public void run() {
								progressBar.setProgress(progressBarStatus);
							}
						});
					}
                    //settings = getSharedPreferences(PREFS_NAME, 0);
                    //if (settings.getBoolean("isFirstTime", true)) {
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                    //}else{
                     //   Intent intent = new Intent(Splash.this, MissionsList.class);
                      //  startActivity(intent);
                    //}

				}
			};
			splashTimer.start();
		} catch (Exception e) {
			Log.d("Exception", e.toString());
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//if we get any key, clear the splash screen
		super.onKeyDown(keyCode, event);
		m_bSplashActive = false;
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		// check whether we are connected to GooglePlayServices
		// before setting location functionality

		if (servicesConnected()) {
			mGoogleApiClient.connect();
			mLocationRequest = LocationRequest.create();

			// set update parameters
			mLocationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
			mLocationRequest.setInterval(UPDATE_INTERVAL);
			// mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
			mGoogleApiClient.connect();
		}

	}

	@Override
	protected void onDestroy() {
		try {
			if (mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
				mGoogleApiClient.disconnect();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onStop();

	}

	// find user's location
	private boolean servicesConnected() {
		GoogleApiAvailability api = GoogleApiAvailability.getInstance();
		int resultCode = api.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d("Location Updates", "Google Play services is available.");
			return true;

		} else {
			Log.d("Location Updates", "Google Play services is not available.");
		}
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		mCurrentLocation = location;
		applicationdata.setLatitude(mCurrentLocation.getLatitude());
		applicationdata.setLongitude(mCurrentLocation.getLongitude());
		Log.d("Location", "===="+applicationdata.getLatitude()+"  "+applicationdata.getLongitude());
	}

	@Override
	public void onConnected(Bundle arg0) {
		try {
			Log.v ("Location", "Connected");
			mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.v("Location", "Connection failed");
	}


	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}


    private class ExportDatabaseFileTask extends AsyncTask<String, Void, Boolean> {
		private final ProgressDialog dialog = new ProgressDialog(mContext);

		// can use UI thread here
		protected void onPreExecute() {
			this.dialog.setMessage("Exporting database...");
			this.dialog.show();
		}

		// automatically done on worker thread (separate from UI thread)
		protected Boolean doInBackground(final String... args) {

			File dbFile =
					new File(Environment.getDataDirectory() + "/data/com.appsolute.cel/databases/cel.db");

			File exportDir = new File(Environment.getExternalStorageDirectory(), "");
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
			File file = new File(exportDir, dbFile.getName());

			try {
				file.createNewFile();
				this.copyFile(dbFile, file);
				return true;
			} catch (IOException e) {
				Log.e("mypck", e.getMessage(), e);
				return false;
			}
		}

		// can use UI thread here
		protected void onPostExecute(final Boolean success) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if (success) {
				Toast.makeText(mContext, "Export successful!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "Export failed", Toast.LENGTH_SHORT).show();
			}
		}

		void copyFile(File src, File dst) throws IOException {
			FileChannel inChannel = new FileInputStream(src).getChannel();
			FileChannel outChannel = new FileOutputStream(dst).getChannel();
			try {
				inChannel.transferTo(0, inChannel.size(), outChannel);
			} finally {
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			}
		}

	}
}
