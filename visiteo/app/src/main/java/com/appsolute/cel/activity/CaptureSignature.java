package com.appsolute.cel.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.R;
import com.appsolute.cel.SignatureView;
import com.appsolute.cel.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class CaptureSignature extends BaseActivity {

	LinearLayout mContent;
	SignatureView mSignature;
	TextView mClear, mGetSign, mCancel;
	public static String tempDir;
	public int count = 1;
	public String current = null;
	private Bitmap mBitmap;
	View mView;
	File mypath;
	private String uniqueId;
	ImageView imageview;
    int min = 65;
    int max = 10000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signature);
		
		baseMethod();
		File directory = new File(Environment.getExternalStorageDirectory() + "/Android/data/"+mContext.getPackageName()+"/cache");
		if(!directory.exists())
			directory.mkdirs();

		prepareDirectory();
		uniqueId = Utils.getTodaysDate() + "_" + Utils.getCurrentTime() + "_"+ Math.random();
		current = uniqueId + ".png";
		mypath = new File(directory, current);

		mClear = (TextView) findViewById(R.id.clear);
		mGetSign = (TextView) findViewById(R.id.valider);
		mContent = (LinearLayout) findViewById(R.id.linearLayout);
		imageview = (ImageView)findViewById(R.id.imageview);
		mSignature  = new SignatureView(this, null);
		mContent.addView(mSignature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);		
		mView = mContent;

		mClear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Cleared");
				mSignature.clearSignature();
				//mGetSign.setEnabled(false);
	        	mContent.setVisibility(View.VISIBLE);
	        	imageview.setVisibility(View.GONE);
			}
		});

		mGetSign.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Saved");
				mView.setDrawingCacheEnabled(true);
				
				try {
					mSignature.setBackgroundColor(Color.WHITE);
					mSignature.setDrawingCacheEnabled(true);
	                mBitmap =  Bitmap.createBitmap (mSignature.getDrawingCache());
	                
	                Random r = new Random();
		            int values = r.nextInt(max - min + 1) + min;
		            File myDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/"+mContext.getPackageName()+"/cache/Signatures");
		           // file.createNewFile();
		            myDir.mkdirs();
		            if(myDir.exists()){
		                Log.d("Exist", "FOLDER EXISTS");
		            } else {	
		            	myDir.mkdir();	
		            }
		            String filename="sign"+String.valueOf(values)+".png";
		            File file = new File (myDir, filename);
		            FileOutputStream fOut = new FileOutputStream(file);
		            mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
		            fOut.flush();
		            fOut.close();
		            
		            if (mBitmap != null) {
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				Bundle b = new Bundle();
				b.putString("status", "done");
				Intent intent = new Intent();
				intent.putExtras(b);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		Log.w("GetSignature", "onDestory");
		super.onDestroy();
	}
	
	private boolean prepareDirectory() {
		try {
			if(makedirs())
				return true;
			else
				return false;			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, getString(R.string.singature_error), Toast.LENGTH_LONG).show();
			return false;
		}
	}

	private boolean makedirs() {
		if(tempDir!=null) {
			File tempdir = new File(tempDir);
			if (!tempdir.exists())
				tempdir.mkdirs();
	
			if (tempdir.isDirectory()) {
				File[] files = tempdir.listFiles();
				for (File file : files) {
					if (!file.delete()) {
						Log.d("Error", "Failed to delete " + file);
					}
				}
			}
			return (tempdir.isDirectory());
		}
		return false;
	}

}
