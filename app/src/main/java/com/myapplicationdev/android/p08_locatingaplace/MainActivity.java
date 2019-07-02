package com.myapplicationdev.android.p08_locatingaplace;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;
    private GoogleMap map;
    private Marker ad;
    private Marker tamp;
    private Marker orch;
    private LatLng poi_tamp;
    private LatLng poi_admiralty;
    private LatLng poi_orchard;
    private LatLng poi_CausewayPoint;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }


                 poi_CausewayPoint = new LatLng(1.290270, 103.851959);

                poi_admiralty = new LatLng(1.4513263, 103.7842251);
                ad = map.addMarker(new
                        MarkerOptions()
                        .position(poi_admiralty )
                        .title("HQ-North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 ")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));

                poi_orchard = new LatLng(1.2976615, 103.8452969);
                 orch = map.addMarker(new
                        MarkerOptions()
                        .position(poi_orchard)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                poi_tamp = new LatLng(1.3497301, 103.9322365);
                 tamp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_tamp)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788  ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_CausewayPoint, 11));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

            }
        });

        spinner = findViewById(R.id.spinner);;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (map != null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_CausewayPoint, 16));
                        }
                        break;
                    case 1:
                        if (map != null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_admiralty, 10));
                        }
                        break;
                    case 2:
                        if (map != null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_orchard, 10));
                        }
                        break;
                    case 3:
                        if (map != null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_tamp, 10));
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
