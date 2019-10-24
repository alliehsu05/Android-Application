package com.example.calorietrackerr;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.MapQuest;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import java.util.PrimitiveIterator;

public class MapActivity extends AppCompatActivity {
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private final LatLng Home = new LatLng(-37.873041, 145.0417503);
    private final LatLng Park = new LatLng(-37.8768297, 145.0394658);

    private TextView tvMap;

    private static final String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final Integer LOCATION_PERMISSION_REQUEST_CODE =1234;

    private Boolean mLocationPermissionsGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_map);




        tvMap = (TextView) findViewById(R.id.maptext);
        Intent intent = getIntent();
        String postcode = ((Intent) intent).getStringExtra("address");
        tvMap.setText(tvMap.getText()+postcode);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 14));
                addMarker(mapboxMap);
            }
        });
    }



    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Home);
        markerOptions.title("HOME");
        markerOptions.snippet("21 Bates St");
        mapboxMap.addMarker(markerOptions);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(Park);
        markerOptions2.title("PARK");
        markerOptions2.snippet("Caulfield Racecourse");
        mapboxMap.addMarker(markerOptions2);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

}

//  AIzaSyAkAJUTwYmqWrBWDjRAp0bRy8MSG2BHuXU
