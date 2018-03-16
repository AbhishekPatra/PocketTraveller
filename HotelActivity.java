package com.example.android.remindme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotelActivity extends AppCompatActivity {
    PlaceAutocompleteFragment text1, text2;
    RequestQueue queue;
    JsonObjectRequest request;
    String source, destination;
    Button but;
    ProgressDialog dialog;
    double lat;
    double lng;
    ArrayList<String> hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog=new ProgressDialog(this);
        dialog.setTitle("Fetching...");
        dialog.setIndeterminate(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        queue = Volley.newRequestQueue(this);
        but=(Button) findViewById(R.id.but);
        text1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from);
        text2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to);
        text1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                source = place.getName().toString();
            }

            @Override
            public void onError(Status status) {

            }
        });
text2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
    @Override
    public void onPlaceSelected(Place place) {
        dialog.show();
        hotel = new ArrayList<String>();

        destination=place.getName().toString();
        LatLng latLng=place.getLatLng();
         lat=  latLng.latitude;
        lng=  latLng.longitude;

        Log.e("lati",+lat +"," +lng);
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +lat +"," +lng +"&radius=20000&keyword=hotels&key=AIzaSyCHmKT1Oe-2OGybj5pifV1z56xJrZ_NWtc";

        request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                Log.e("ty" ,response.toString());
                try {
                    JSONArray arry=response.getJSONArray("results");
                    int size=arry.length();
                    for(int i=0;i<size;i++){
                        JSONObject obj1=arry.getJSONObject(i);
                        String name=obj1.getString("name");
                        hotel.add(name);
                //        Toast.makeText(HotelActivity.this,name,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(HotelActivity.this,"ERROR TRY AGAIN",Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    @Override
    public void onError(Status status) {

    }
});

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelActivity.this,FlightActivity.class);
                intent.putStringArrayListExtra("hotel",hotel);
                Log.e("hotel",""+hotel.size());
                intent.putExtra("sorce",source);
                intent.putExtra("destination",destination);
                intent.putExtra("lat",lat);
                intent.putExtra("long",lng);
                startActivity(intent);
            }
        });

    }
}

