package com.test.countrycapitalsmaps;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RequestQueue mRequestQueue;
    private ArrayList<MarkerData> markersArray = new ArrayList<>();

    private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mRequestQueue= Volley.newRequestQueue(this);


    }

    private void jsonparse() {
        String url="https://restcountries.eu/rest/v2/all";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int q=0;q<=200;q++){
                                JSONArray array=response.getJSONArray(q);
                            }















                            for(int i=0;i<=30;i++){
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String capital= jsonObject.getString("capital");
                                    String region= jsonObject.getString("region");
                                    long population=jsonObject.getInt("population");
                                    int lat,lng;
                                    JSONArray jsonArray=jsonObject.getJSONArray("latlng");
                                    lat=jsonArray.getInt(0);
                                    lng=jsonArray.getInt(1);
                                    MarkerData markerData=new MarkerData();
                                    markerData.setLatitude(lat);
                                    markerData.setLongitude(lng);
                                    markersArray.add(markerData);
                                }
                                showOnMap();
                                Log.v("asdasdasd", "asd......\n\n\n\n\n\n"+ "," + ".........\n\n\n\n\n\n......." + "..........\n\n\n\n\n\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error Communicating with Server",Toast.LENGTH_SHORT).show();
                error.printStackTrace();


            }
        });

        mRequestQueue.add(request);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        jsonparse();


    }

    private void showOnMap() {
        for(int i=0; i < markersArray.size() ; i++) {

            createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude());
        }
    }

    protected Marker createMarker(double latitude, double longitude) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));
    }

}
