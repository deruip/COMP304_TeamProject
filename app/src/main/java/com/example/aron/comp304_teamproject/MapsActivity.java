package com.example.aron.comp304_teamproject;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static LatLng place = new LatLng(43.6426, -79.3871);
    String PName;
    boolean street = true;

    private GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        final Geocoder coder = new Geocoder(getApplicationContext());
        String placeName = "M1B 3C6";
        try {
            List<Address> geocodeResults =
                    coder.getFromLocationName(placeName, 3);
            Iterator<Address> locations = geocodeResults.iterator();

            String locInfo = "Results:\n";
            double latitude = 43.6426f;
            double longitude = -79.3871f;
            while (locations.hasNext()) {
                Address loc = locations.next();
                locInfo += String.format("Location: %f, %f\n", loc.getLatitude(), loc.getLongitude());
                //if (loc.getCountryName().equals("Canada")) {
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
                place = new LatLng(latitude, longitude);
                // break;

                //code for address object
                String pName = loc.getLocality();
                String featureName = loc.getFeatureName();
                String country = loc.getCountryName();
                String road = loc.getThoroughfare();

                locInfo += String.format("\n[%s][%s][%s][%s]", pName, featureName, road, country);
                int addIdx = loc.getMaxAddressLineIndex();
                for (int idx = 0; idx <= addIdx; idx++) {
                    String addLine = loc.getAddressLine(idx);
                    locInfo += String.format("\nLine %d: %s", idx, addLine);
                }


                String address = loc.getAddressLine(10);


            }


            final String geoURI = String.format("geo:%f,%f", latitude, longitude);
            //


        } catch (IOException e) {
            Log.e("GeoAddress", "Failed to get location info", e);
        }


        gMap = googleMap;
        //gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Marker mark = gMap.addMarker(new MarkerOptions()
                .position(place)
                .title("Wynn Fitness Clubs ")
                .snippet("10 Milner Business Ct, Scarborough, ON M1B 3C6"));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10));

        // Zoom in, animating the camera.
        gMap.animateCamera(CameraUpdateFactory.zoomTo(500), 6000, null);

    }
}