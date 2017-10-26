package com.appsolute.cel.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.Manifest;
import com.appsolute.cel.R;
import com.appsolute.cel.models.CEL_Biens;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private CEL_Biens bien;
	private String map_key_debug = "AIzaSyBA8huBtL_bCj3tXKHmlS9l6pvxtJdn7Uk";
    private final static int MY_PERMISSIONS_REQUEST_MAPS = 0;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

	@Override
	public void onMapReady(GoogleMap map) {

        this.map = map;

		CEL_Biens_DAO biens_DAO = new CEL_Biens_DAO(this);
		bien = biens_DAO.select(MissionsList.missionSelected.getIdBien());

        LatLng latestLocation = new LatLng(45.761837, 4.835843);

		try {
			String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
					URLEncoder.encode(bien.getAdresseBiens() + ", " + bien.getVilleBiens() + ", " + bien.getCodePostalBiens(), "UTF-8") +
					"&key=" + map_key_debug;


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);

                JSONArray Jarray = Jobject.getJSONArray("results");

                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);

                    JSONObject geometryObject = object.getJSONObject("geometry");

                    JSONObject locationObject = object.getJSONObject("location");

                    LatLng bienPosition = new LatLng(locationObject.getDouble("lat"), locationObject.getDouble("lng"));

                    latestLocation = bienPosition;

                    map.addMarker(new MarkerOptions()
                            .title(bien.getMandatBiens())
                            .snippet(bien.getAdresseBiens() + ", " + bien.getVilleBiens() + ", " + bien.getCodePostalBiens())
                            .position(bienPosition));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.MAPS_RECEIVE)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.MAPS_RECEIVE},
                    MY_PERMISSIONS_REQUEST_MAPS);
        }
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latestLocation, 13));

	}

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MAPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
            }
        }
    }
}
