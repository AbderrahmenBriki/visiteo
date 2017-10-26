package com.appsolute.cel.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.appsolute.cel.DAO.CEL_Database_DAO;
import com.appsolute.cel.R;

public class Utils {
	 static Context mContext;
	 
	 public static String getTodaysDate() {
		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));
	}

	 public static String getCurrentTime() {
		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)	+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.w("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));
	}
	 
	public static long generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}

	public static void showAlert(Context context, String message){
		Builder builder = new AlertDialog.Builder(context); 
		builder.setTitle(context.getResources().getString(R.string.app_name));
		builder.setMessage(message);
		builder.setCancelable(true);
        builder.setPositiveButton(context.getResources().getString(R.string.ok).toUpperCase(), null);
        AlertDialog dialog = builder.create();
       	dialog.show(); 
	}	
		
	public static boolean checkErrorCode(String error_code) {
		if(error_code.equals("+00X00000") || error_code.equals("+02F03000") || error_code.equals("+02D03000") 
				|| error_code.equals("+03F01000") || error_code.equals("+03C02000")) {
			return true;
		}
		return false;
	}
	
	public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi){
	    float[] resultArray = new float[99];
	    Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
	    return resultArray[0];
	}
	
	public static String getAddress(Context mContext, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }
	
	//--------------------------------
    // Filter out non-digit characters
    //--------------------------------
    private static String getDigitsOnly (String s) {
        StringBuffer digitsOnly = new StringBuffer ();
        char c;
        for (int i = 0; i < s.length (); i++) {
            c = s.charAt (i);
            if (Character.isDigit (c)) {
                digitsOnly.append (c);
            }
        }
        return digitsOnly.toString ();
    }
	
	public static boolean isCreditCardValid(String cardNumber) {
	    String digitsOnly = getDigitsOnly(cardNumber);
	    int sum = 0;
	    int digit = 0;
	    int addend = 0;
	    boolean timesTwo = false;

	    for (int i = digitsOnly.length() - 1; i >= 0; i--) {
	        digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
	        if (timesTwo) {
	            addend = digit * 2;
	            if (addend > 9) {
	                addend -= 9;
	            }
	        } else {
	            addend = digit;
	        }
	        sum += addend;
	        timesTwo = !timesTwo;
	    }

	    int modulus = sum % 10;
	    return modulus == 0;
	}

	public static boolean validateEmail(String email) {
		final Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
		try {
			return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
		} catch (NullPointerException exception) {
			return false;
		}
	}

	public static int getScreenWidth(Activity mActivity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.widthPixels;
	}

	public static int getScreenHeight(Activity mActivity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.heightPixels;

	}

	public static int getBilletsPagerMargin(Activity mActivity) {
		DisplayMetrics metrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			return -(int) (Utils.getScreenWidth(mActivity) / 3.6);
		case DisplayMetrics.DENSITY_MEDIUM:
			return -(int) (Utils.getScreenWidth(mActivity) / 3.2);
		case DisplayMetrics.DENSITY_HIGH:
			if (Utils.getScreenWidth(mActivity) > 500)
				return -(int) (Utils.getScreenWidth(mActivity) / 2.2);
			else
				return -(int) (Utils.getScreenWidth(mActivity) / 3.2);
		default:
			return -(int) (Utils.getScreenWidth(mActivity) / 2.2);
		}
	}
	
	public static int getBoutiquePagerMargin(Activity mActivity) {
		DisplayMetrics metrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			return -(int) (Utils.getScreenWidth(mActivity) / 3.6);
		case DisplayMetrics.DENSITY_MEDIUM:
			return -(int) (Utils.getScreenWidth(mActivity) / 2.5);
		case DisplayMetrics.DENSITY_HIGH:
			if (Utils.getScreenWidth(mActivity) > 500)
				return -(int) (Utils.getScreenWidth(mActivity) / 1.9);
			else
				return -(int) (Utils.getScreenWidth(mActivity) / 2.5);
		default:
			return -(int) (Utils.getScreenWidth(mActivity) / 1.9);
		}
	}

	public static String MD5(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSH1Hash(String str) {
		MessageDigest digest = null;
		byte[] input = null;

		try {
			digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			input = digest.digest(str.getBytes("UTF-8"));

		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return convertToHex(input).toUpperCase(Locale.FRANCE);
	}

	public static String getHash(byte[] data) {
		MessageDigest digest = null;
		byte[] input = null;

		try {
			digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			input = digest.digest(data);

		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return convertToHex(input);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}	

	public static void exportDatabaseFileTask(Context context) {
		mContext = context;
		
		class DatabaseFileTask extends AsyncTask<String, Void, Boolean> {	
	        @Override
	        public Boolean doInBackground(final String... args) {
	
	           File dbFile = new File(Environment.getDataDirectory() + "/data/"+mContext.getPackageName()+"/databases/"+ CEL_Database_DAO.NOM);
	
	           File exportDir = new File(Environment.getExternalStorageDirectory(), mContext.getString(R.string.app_name));
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
		
		new DatabaseFileTask().execute(new String[] { "" });
	}
	
	 /** 
     * Read bytes from a File into a byte[].
     * 
     * @param file The File to read.
     * @return A byte[] containing the contents of the File.
     * @throws IOException Thrown if the File is too long to read or couldn't be
     * read fully.
     */
    public static byte[] readBytesFromFile(File file) throws IOException {
    	InputStream is = new FileInputStream(file);
      
    	// Get the size of the file
    	long length = file.length();
  
    	// You cannot create an array using a long type.
    	// It needs to be an int type.
    	// Before converting to an int type, check
    	// to ensure that file is not larger than Integer.MAX_VALUE.
    	if (length > Integer.MAX_VALUE) {
    		throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
    	}
  
    	// Create the byte array to hold the data
    	byte[] bytes = new byte[(int)length];
  
    	// Read in the bytes
    	int offset = 0;
    	int numRead = 0;
    	while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
    		offset += numRead;
    	}
  
    	// Ensure all the bytes have been read in
    	if (offset < bytes.length) {
    		throw new IOException("Could not completely read file " + file.getName());
    	}
  
    	// Close the input stream and return bytes
    	is.close();
    	return bytes;
    }
    
    /**
     * Writes the specified byte[] to the specified File path.
     * 
     * @param theFile File Object representing the path to write to.
     * @param bytes The byte[] of data to write to the File.
     * @throws IOException Thrown if there is problem creating or writing the 
     * File.
     */
    public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
    	BufferedOutputStream bos = null;
      
    	try {
    		FileOutputStream fos = new FileOutputStream(theFile);
    		bos = new BufferedOutputStream(fos); 
    		bos.write(bytes);
    	}finally {
    		if(bos != null) {
    			try  {
          //	flush and close the BufferedOutputStream
    				bos.flush();
    				bos.close();
    			} catch(Exception e){}
    		}
    	}
    }
}
