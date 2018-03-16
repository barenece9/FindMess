package com.lnsel.findmess.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lnsel.findmess.R;
import com.lnsel.findmess.appconroller.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {

    String address="",name="",latitute="22.6735936",longitude="88.387";//22.6735936, 88.387
    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        address=intent.getStringExtra("address");
        name=intent.getStringExtra("name");
       /* Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapActivity.this,FindActivity.class));
                finish();
            }
        });*/

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        getlatlong(address);
        //afteraddmarker();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
		/*LatLng sydney = new LatLng(22.6735936, 88.387);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in SRFoods"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lnsel.findmess/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lnsel.findmess/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    void getlatlong(final String zipcode)
    {
        String  tag_string_req = "string_req";
        //String zip="700091";
        String url ="http://maps.googleapis.com/maps/api/geocode/json?address="+zipcode+"&sensor=false%22";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        //pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parselatlong(response);
                Log.d("", response.toString());
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void parselatlong(String response)
    {
        try {

            JSONObject parentObj = new JSONObject(response);

            JSONArray jArray = parentObj.getJSONArray("results");


            JSONObject innerObj = jArray.optJSONObject(0);
            JSONObject geometry = innerObj.optJSONObject("geometry");
            JSONObject location = geometry.optJSONObject("location");

            latitute=location.optString("lat");
            longitude=location.optString("lng");

            afteraddmarker();


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void afteraddmarker(){
        // Creating MarkerOptions
       // mMap.clear();
        LatLng point = new LatLng(Double.valueOf(latitute), Double.valueOf(longitude));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(10).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        MarkerOptions options = new MarkerOptions();
        // Setting the position of the marker
        options.position(point);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        options.title(name);
        //options.snippet("A-312 Crystal plaza,Opp infinity mall,New link road,Andheri West,Mumbai-400053");
        // Add new marker to the Google Map Android API V2
        mMap.addMarker(options);

        /*double radious=0;
        if(distanceunit.equalsIgnoreCase("Miles")){
            radious= Integer.valueOf(Integer.valueOf(chefKithenEditDeliveryzonebyradiuskmmiles.getText().toString()))*1000*1.60934;
        }else if(distanceunit.equalsIgnoreCase("Km")){
            radious= Integer.valueOf(Integer.valueOf(chefKithenEditDeliveryzonebyradiuskmmiles.getText().toString()))*1000;
        }*/

        /*Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.valueOf(latitute), Double.valueOf(longitude)))
                .radius(radious)
                .strokeColor(Color.RED)
                .strokeWidth(1)
                .fillColor(Color.BLUE));*/
    }
}
