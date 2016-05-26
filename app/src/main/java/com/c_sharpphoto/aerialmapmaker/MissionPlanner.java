package com.c_sharpphoto.aerialmapmaker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;

import com.google.maps.ElevationApi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class MissionPlanner extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, OnMarkerDragListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Location mOldLocation;
    private LocationRequest mLocationRequest;
    private String dat;

    private TextView mLatitude;
    private TextView mLongitude;
    private EditText mAltitude;

    private static final String TAG = "MEDIA";


    private double myLat, myLong, polyLatOffset, polyLongOffset;
    public int routeSize;

    public double planLat, planLong;
    public String planText;
    public String planAlt;
    public double[] RouteLat;
    public double[] RouteLong;
    public int[] RouteAction;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_planner);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mLatitude = (TextView) findViewById(R.id.planLat);
        mLongitude = (TextView) findViewById(R.id.planLong);
        mAltitude = (EditText) findViewById(R.id.planAlt);

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
        mMap.setOnMarkerDragListener(this);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void SavePlan(View view) {
    }

    @Override
    public void onConnected(Bundle bundle) {



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            return;
        }
        mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
        dat = DateFormat.getTimeInstance().format(new Date()).toString();
        myLat = mLastLocation.getLatitude();
        myLong = mLastLocation.getLongitude();
        LatLng currLoc = new LatLng(myLat, myLong);
        mMap.addMarker(new MarkerOptions().position(currLoc).title("Start point").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc, (float) 19.0));
        /*Polygon initialPolygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(myLat, myLong),
                        new LatLng(myLat, myLong-polyLongOffset),
                        new LatLng(myLat-polyLatOffset, myLong-polyLongOffset),
                        new LatLng(myLat-polyLatOffset, myLong))
                .strokeColor(Color.RED));*/

//        createLocationRequest();
//        if (mLocationRequest != null) {
//            startLocationUpdates();
//        }

    }

//    private void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
//    }
//
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(20000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }

    @Override
    public void onConnectionSuspended(int i) {

    }

//    @Override
//    public void onLocationChanged(Location location) {
//        mLastLocation = location;
//        newLat = mLastLocation.getLatitude();
//        newLong = mLastLocation.getLongitude();
//
//
//        mLastUpdate.setText(dat);
//
//        PolylineOptions trackOptions = new PolylineOptions()
//                .add(new LatLng(oldLat, oldLong))
//                .add(new LatLng(newLat, newLong)) ;
//        Polyline polyline = mMap.addPolyline(trackOptions);
//        LatLng newLoc = new LatLng(newLat, newLong);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
//        oldLat = newLat;
//        oldLong = newLong;
//
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MissionPlanner Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.c_sharpphoto.aerialmapmaker/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MissionPlanner Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.c_sharpphoto.aerialmapmaker/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    public void createLowRoad(double la, double lo) {
        routeSize = 54;
        double latOffset = 200 * Utils.ONE_FOOT_OFFSET;
        double longOffset = 267 * Utils.calcLongitudeFeetOffset(myLat);
        RouteAction = new int[]{1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 2, 2, 3};
        RouteLat = new double[]{
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset, la - 7 * latOffset, la - 8 * latOffset,
                la - 8 * latOffset, la - 7 * latOffset, la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la,
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset, la - 7 * latOffset, la - 8 * latOffset,
                la - 8 * latOffset, la - 7 * latOffset, la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la,
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset, la - 7 * latOffset, la - 8 * latOffset,
                la - 8 * latOffset, la - 7 * latOffset, la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la};
        RouteLong = new double[]{
                lo, lo, lo, lo, lo, lo, lo, lo, lo,
                lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset,
                lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset,
                lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset,
                lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset,
                lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset};


    }

    public void createHighRoad(double la, double lo) {
        routeSize = 35;
        double latOffset = 300 * Utils.ONE_FOOT_OFFSET;
        double longOffset = 360 * Utils.calcLongitudeFeetOffset(myLat);
        RouteAction = new int[]{1, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 3, 1, 2, 2, 2, 2, 2, 3};
        RouteLat = new double[]{
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset,
                la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la,
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset,
                la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la,
                la, la - latOffset, la - 2 * latOffset, la - 3 * latOffset, la - 4 * latOffset, la - 5 * latOffset, la - 6 * latOffset,
                la - 6 * latOffset, la - 5 * latOffset, la - 4 * latOffset, la - 3 * latOffset, la - 2 * latOffset, la - latOffset, la};
        RouteLong = new double[]{
                lo, lo, lo, lo, lo, lo, lo,
                lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset, lo - longOffset,
                lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset, lo - 2 * longOffset,
                lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset, lo - 3 * longOffset,
                lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset, lo - 4 * longOffset,
                lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset, lo - 5 * longOffset};


    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        planLat = marker.getPosition().latitude;
        planLong = marker.getPosition().longitude;
        mLatitude.setText(String.valueOf(planLat));
        mLongitude.setText(String.valueOf(planLong));

    }

    public void savePlan(View view) {
        String planAltitude = mAltitude.getText().toString();
        int alt = Integer.parseInt(planAltitude);
        if (alt>400)
            planAltitude = "400";
        if (alt<200)
            planAltitude = "200";
        planText = "Plan:"+String.valueOf(planLat)+","+String.valueOf(planLong)+","+planAltitude+",0;";
        String format = "yyyyMMddHHmmss";
        String dat = DateFormat.getDateTimeInstance().format(new Date());
        String filename = dat+"plan.txt";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "******* File not found.");
        }
        try {
            fos.write(planText.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "******* IOException");
        }

    }
};

