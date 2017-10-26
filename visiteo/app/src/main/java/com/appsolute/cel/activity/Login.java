package com.appsolute.cel.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.BuildConfig;
import com.appsolute.cel.DAO.CEL_Actions_DAO;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Chauffage_DAO;
import com.appsolute.cel.DAO.CEL_Clefs_DAO;
import com.appsolute.cel.DAO.CEL_Compteurs_DAO;
import com.appsolute.cel.DAO.CEL_ECS_DAO;
import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.DAO.CEL_Elements_Obs_DAO;
import com.appsolute.cel.DAO.CEL_Etat_DAO;
import com.appsolute.cel.DAO.CEL_InfoCompteurs_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.DAO.ItemType_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.DAO.RoomItem_DAO;
import com.appsolute.cel.DAO.Room_DAO;
import com.appsolute.cel.PreferenceManager;
import com.appsolute.cel.R;
import com.appsolute.cel.models.CEL_Actions;
import com.appsolute.cel.models.CEL_Biens;
import com.appsolute.cel.models.CEL_Chauffage;
import com.appsolute.cel.models.CEL_Clefs;
import com.appsolute.cel.models.CEL_Compteurs;
import com.appsolute.cel.models.CEL_ECS;
import com.appsolute.cel.models.CEL_Elements;
import com.appsolute.cel.models.CEL_Elements_Obs;
import com.appsolute.cel.models.CEL_Etat;
import com.appsolute.cel.models.CEL_InfoCompteurs;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.models.OPERA_Photos;
import com.appsolute.cel.models.RoomItem;
import com.appsolute.cel.utils.InternetConnection;
import com.appsolute.cel.utils.XMLParser;
import com.crashlytics.android.Crashlytics;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appsolute.cel.R.layout.login;

public class Login extends BaseActivity {

    public static final String PREFS_NAME = "CellPrefsFile";
    static SharedPreferences settings;

    EditText log_licence, log_identifiant, log_pass;
    Button button_valider, button_editLicence;
    TextView textView_versionCode;
    public static String licence = "", identifiant = "", password = "";
    public static Boolean isLogin = true;
    private static boolean isEDL = false;
    private static String TYPE_CONNECTION = "2";
    public static String result;
    public static int statusCode;
    public static String successValue;
    private static String loadingString;
    private static String nomExpert;
    private static String prenomExpert;

    //Permission
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static final int REQUEST_CODE_LOCATION = 2;

    private static int typeMission;
    private static String missionDate;
    private static boolean isfirstAttempt = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);
        baseMethod();

        settings = getSharedPreferences(PREFS_NAME, 0);
        checkInternet();

        allowpermission();

        loadingString = getString(R.string.loading);
        isLogin = true;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        actionBar.hide();
        log_licence = (EditText) findViewById(R.id.log_licence);
        log_identifiant = (EditText) findViewById(R.id.log_identifiant);
        log_pass = (EditText) findViewById(R.id.log_pass);
        button_valider = (Button) findViewById(R.id.button_valider);
        button_editLicence = (Button) findViewById(R.id.button_editLicence);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(log_licence.getWindowToken(), 0);
        textView_versionCode = (TextView) findViewById(R.id.textView_versionCode);
        PackageInfo pinfo;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionCode = "Version: " + String.valueOf(versionNumber);
            textView_versionCode.setText(versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getBoolean("isFirstTime", true)) {
            editor.putBoolean("isFirstTime", false);
            editor.apply();

        }
        log_licence.setText(settings.getString("codeLicence", ""));


        Message myMsg = bagroundHandler1.obtainMessage();
        bagroundHandler1.sendMessage(myMsg);

        /*if (!isLogin) {
            getEtatsCompteur();
            closeProgress();
            MissionsList.fragmentHandler.sendMessage(Message.obtain(MissionsList.fragmentHandler, MissionsList.MESSAGE_DOWNLOAD_COMPLETE));
        } else {
            mAQuery.ajax(Splash.BASE_URL + "rooms?timestamp=" + PreferenceManager.getRoomTimestamp(mContext),
                    JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json, AjaxStatus status) {
                            callParseRoomJson(json);
                        }
                    });
        }*/

        button_valider.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkInternet()) {

                    Message myMsg = bagroundHandler1.obtainMessage();
                    bagroundHandler1.sendMessage(myMsg);

                }
            }
        });

    }

    /**
     * set permission for >= 23 versions
     */
    private void allowpermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(Login.this,
                    Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.GET_ACCOUNTS)) {

                        ActivityCompat.requestPermissions(Login.this, new String[]{
                                Manifest.permission.VIBRATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.WAKE_LOCK
                        }, MY_PERMISSIONS_REQUEST);
                        /*this will execute when the user click deny*/

                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(Login.this, new String[]{
                                Manifest.permission.VIBRATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.WAKE_LOCK
                        }, MY_PERMISSIONS_REQUEST);
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission Granted", "Permission Granted");
                } else {
                    Log.d("Permission Denied", "Permission Denied");
                }
                return;
            }
            case REQUEST_CODE_LOCATION:
        }
    }

    private boolean checkInternet() {
        if (!InternetConnection.isConnected(this)) {
            return true;
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("noInternet", false);
            editor.apply();
            return false;
        }
    }



    public static void callParseRoomJson(JSONObject json) {
        if (json == null) {
            goToMissionListActivity();
            closeProgress();
        } else {
            try {
                if (json.getInt("Update") == 1 && json.has("Lien")) {
                    closeProgress();
                    PreferenceManager.setRoomTimestamp(Integer.parseInt(json.getString("Timestamp")), mContext);
                    DownloadTask downloadTask = new DownloadTask(mActivity);
                    showProgress(mContext, "Veuillez patienter une mis à jour est en cours. Cela peut ne prendre que quelques minutes.");
                    downloadTask.execute(json.getString("Lien"));
                } else {
                    goToMissionListActivity();
                    closeProgress();
                }
            } catch (Exception e) {
                e.printStackTrace();
                goToMissionListActivity();
                closeProgress();
            }
        }
    }

    public static class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://pdf.opera-groupe.fr/WSAndroide/Rooms.xml")
                    .get()
                    .build();

            try {

                Response response = client.newCall(request).execute();
                XMLParser.ParseXmlFile(mContext, response.body().byteStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "done";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mWakeLock.release();
            goToMissionListActivity();
            closeProgress();
        }
    }


    static Handler bagroundHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mAQuery.ajax(Splash.BASE_URL + "rooms?timestamp=" + PreferenceManager.getRoomTimestamp(mContext),
                    JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json, AjaxStatus status) {
                            callParseRoomJson(json);
                        }
                    });
        }
    };


    public static void alertDialogShow(int resId) {
        Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.app_name));
        builder.setMessage(mContext.getString(resId));
        builder.setPositiveButton(mContext.getString(R.string.ok), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void goToMissionListActivity() {
        final SharedPreferences.Editor editor = settings.edit();
         editor.putBoolean("isFirstTime", false);
        TYPE_CONNECTION = "1";
        editor.putBoolean("isIndependant", true);
        editor.putString("independant_login", "testweb");
        editor.commit();
        //mWakeLock.release();
        //getEtatsCompteur();
        closeProgress();
        isLogin = false;
        Intent missionListActivityIntent = new Intent(mActivity, MissionsList.class);
        missionListActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(missionListActivityIntent);
        ((Login) mContext).finish();
    }
    private static void getEtatsCompteur() {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                HttpURLConnection urlConnection = null;
                OutputStreamWriter writer = null;
                //DataOutputStream writer = null;
                BufferedReader reader = null;
                JSONObject json = new JSONObject();
                SharedPreferences.Editor editor = settings.edit();

                try {

                    URL url = new URL(Splash.BASE_URL + "etat");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setInstanceFollowRedirects(false);
                    urlConnection.connect();

                    writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    //writer = new DataOutputStream(urlConnection.getOutputStream());

                    writer.write(json.toString());
                    //writer.writeUTF(URLEncoder.encode(json.toString(), "UTF-8"));
                    writer.flush();
                    statusCode = urlConnection.getResponseCode();

                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        //Update isFirstTime to false
                        editor.putBoolean("isFirstTime", false);
                        editor.commit();

                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"), 8);
                        StringBuilder stringBuilder = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line + "\n");
                        }
                        String in = stringBuilder.toString();
                        JSONObject jsonObjectResponse = new JSONObject(in);
                        if (jsonObjectResponse.has("Etat")) {
                            CEL_Etat etat = null;
                            CEL_Etat_DAO etat_DAO = new CEL_Etat_DAO(mContext);
                            JSONArray jArray = new JSONArray();
                            if (jsonObjectResponse.toString().contains("Etat")) {
                                jArray = jsonObjectResponse.getJSONArray("Etat");

                                for (int i = 0; i < jArray.length(); i++) {
                                    etat = new CEL_Etat();
                                    JSONObject jsonObject = jArray.getJSONObject(i);
                                    if (jsonObject.getString("idEtat").length() > 0 && !jsonObject.getString("idEtat").equalsIgnoreCase("null"))
                                        etat.setIdEtat(Integer.parseInt(jsonObject.getString("idEtat")));
                                    else
                                        etat.setIdEtat(0);
                                    etat.setDescriptionEtat(jsonObject.getString("Description"));
                                    etat_DAO.addValue(etat);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        writer.close();
                    } catch (Exception e) {
                    }
                    try {
                        reader.close();
                    } catch (Exception e) {
                    }
                    try {
                        urlConnection.disconnect();
                    } catch (Exception e) {
                    }
                }
                Looper.loop();
            }
        };
        thread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mission_DAO != null) {
            mission_DAO.close();
        }
    }

    private boolean getCodeCel(String codeUser) {

        String keyCel;
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        int dayTime = day * month * year;
        int modulo = dayTime % 97;

        keyCel = String.valueOf(dayTime) + String.valueOf(modulo);

        return keyCel.equals(codeUser);

    }

    public static void sendPhotosToFTP() {
        OPERA_Photos_DAO dao_photos = new OPERA_Photos_DAO(mContext);
        ArrayList<OPERA_Photos> photos_data = dao_photos.selectAllPhotosFromMission(MissionsList.missionSelected.getIdMission());

        FTPClient ftp;
        int countPhoto = 0;
        int totalPhoto = 0;

        try {
            System.gc();

            deleteFiles(MissionsList.missionSelected.getCodeClientMission(),
                    String.valueOf(MissionsList.missionSelected.getNumeroRdvMission() + " - app").toLowerCase());


            ftp = new FTPClient();
            ftp.connect(mContext.getString(R.string.host_ftp));
            if (ftp.login(mContext.getString(R.string.id_ftp), mContext.getString(R.string.mdp_ftp))) {
                ftp.enterLocalPassiveMode();
                ftp.setFileType(FTP.BINARY_FILE_TYPE);

                if (!settings.getBoolean("isIndependant", false)) {

                    if (!ftp.changeWorkingDirectory(MissionsList.missionSelected.getCodeClientMission() + "/documentrattache")) {
                        ftp.makeDirectory(MissionsList.missionSelected.getCodeClientMission() + "/documentrattache");
                        ftp.changeWorkingDirectory(MissionsList.missionSelected.getCodeClientMission() + "/documentrattache");

                    }
                } else {
                    String codeLicence = settings.getString("codeLicence", "");
                    if (!ftp.changeWorkingDirectory(codeLicence + "/documentrattache")) {
                        ftp.makeDirectory(codeLicence + "/documentrattache");
                        ftp.changeWorkingDirectory(codeLicence + "/documentrattache");
                    }

                }

                totalPhoto = photos_data.size();
                for (OPERA_Photos photo : photos_data) {
                    ByteArrayInputStream in = new ByteArrayInputStream(photo.getPhoto());
                    ftp.storeFile(photo.getPhotoName() + ".png", in);
                    in.close();
                    countPhoto++;
                    Log.d("photo_uploaded", String.valueOf(countPhoto));
                }

                ftp.logout();
                ftp.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.log("Export FTP issue");
            Crashlytics.log(countPhoto + " pictures exported on " + totalPhoto);
            Crashlytics.logException(e);
        }

    }

    public static void deleteFiles(String codeClient, String prefix) {

        OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("codeClient", codeClient);
            jsonObject.put("prefix", prefix);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://pdf.opera-groupe.fr/WSAndroide/index.php/deletedocument")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("Reponse code delete API", String.valueOf(response.code()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPieceTypeName(String pieces) {
        if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_entree)))
            return mContext.getString(R.string.op_entree);
        else if (pieces.matches(".*\\bCuisine\\b.*"))
            return mContext.getString(R.string.op_cuisine);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_sejour)))
            return mContext.getString(R.string.op_sejour);
        else if (pieces.matches(".*\\bWC\\b.*"))
            return mContext.getString(R.string.op_wc);
        else if (pieces.matches(".*\\bSalle de bain\\b.*"))
            return mContext.getString(R.string.op_salle);
        else if (pieces.matches(".*\\bChambre\\b.*"))
            return mContext.getString(R.string.op_chambre);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_degage)))
            return mContext.getString(R.string.op_degage);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_garage)))
            return mContext.getString(R.string.op_garage);
        else if (pieces.matches(".*\\bGrenier\\b.*"))
            return mContext.getString(R.string.op_grenier);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_additif)))
            return mContext.getString(R.string.op_additif);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_facade)))
            return mContext.getString(R.string.op_facade);
        else if (pieces.matches(".*\\bSAS\\b.*"))
            return mContext.getString(R.string.op_sas);
        else if (pieces.matches(".*\\bLocal\\b.*"))
            return mContext.getString(R.string.op_local);
        else if (pieces.equalsIgnoreCase(mContext.getString(R.string.op_entrepot)))
            return mContext.getString(R.string.op_entrepot);
        else if (pieces.matches(".*\\bCirculation\\b.*"))
            return mContext.getString(R.string.op_circulation);
        else if (pieces.matches(".*\\bLocal informatique\\b.*"))
            return mContext.getString(R.string.op_local_info);
        else if (pieces.matches(".*\\bSanitaire\\b.*"))
            return mContext.getString(R.string.op_sanitaire);
        else if (pieces.matches(".*\\bAscenseur\\b.*"))
            return mContext.getString(R.string.op_ascenseur);
        else if (pieces.matches(".*\\bBureau\\b.*"))
            return mContext.getString(R.string.op_bureau);

        return mContext.getString(R.string.op_entree);
    }
}
