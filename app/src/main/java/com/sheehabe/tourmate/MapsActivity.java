package com.sheehabe.tourmate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public abstract class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private List<Address> addressess;
    FragmentManager fragmentManager;
    private TextView done;
    private AutoCompleteTextView search;
    String Search;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        search = findViewById( R.id.search );
        done = findViewById( R.id.done );
        geocoder = new Geocoder( this );
        initializeMap();
        getLocationPermission();

    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            initializeMap();
        } else {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1 );
            return;

        }
    }


    private void initializeMap() {
        fragmentManager = getSupportFragmentManager();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace( R.id.map, mapFragment );
        fragmentTransaction.commit();
        mapFragment.getMapAsync( this );

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng dhaka = new LatLng( 23.7567208, 90.4360883 );
        // mMap.addMarker( new MarkerOptions().position( sydney ).title( "Marker in Dhaka" ) );
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom( dhaka, 6 ) );
        mMap.getUiSettings().setMyLocationButtonEnabled( true );
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.getUiSettings().setZoomControlsEnabled( true );
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );

        mMap.setOnCameraIdleListener( new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                try {
                    addressess = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
                    search.setText( addressess.get( 0 ).getAddressLine( 0 ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } );

        mMap.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    addressess = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMap.addMarker( new MarkerOptions().position( latLng ).title( String.valueOf( latLng.latitude + "," + latLng.longitude ) )
                        .snippet( addressess.get( 0 ).getLocality() + "," + addressess.get( 0 ).getThoroughfare() ) );
            }
        } );
    }

    public void currentLocation(View view) {
        getMyLocation();
    }

    private void getMyLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( this );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener( new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location=task.getResult();
                    LatLng myLocation=new LatLng( location.getLatitude(),location.getLongitude() );
                    mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(myLocation,6 ) );
                }

            }
        } );
    }




}
