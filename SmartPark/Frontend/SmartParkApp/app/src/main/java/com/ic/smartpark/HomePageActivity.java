package com.ic.smartpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="MyPreferences";
    private static final String TOKEN="TOKEN";
    Button codeButton ;

    ArrayList<ImageView> parkingSpaces=new ArrayList<>();
    int[] id ={R.id.A1,R.id.A2,R.id.A3,R.id.A4,R.id.A5,R.id.A6,R.id.A7,R.id.A8,R.id.A9,R.id.A10,
              R.id.B1,R.id.B2,R.id.B3,R.id.B4,R.id.B5,R.id.B6,R.id.B7,R.id.B8,R.id.B9,R.id.B10,
              R.id.C1,R.id.C2,R.id.C3,R.id.C4,R.id.C5,R.id.C6,R.id.C7,R.id.C8,R.id.C9,R.id.C10,
              R.id.D1,R.id.D2,R.id.D3,R.id.D4,R.id.D5,R.id.D6,R.id.D7,R.id.D8,R.id.D9,R.id.D10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        codeButton=findViewById(R.id.getCode);
        for(int i=0;i<40;i++){
            parkingSpaces.add(findViewById(id[i]));
        }
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue= Volley.newRequestQueue(HomePageActivity.this);
                String url_home="http://192.168.1.228:8080/getAvailableParkingSpot";
                String url_hotspot="http://192.168.16.153:8080/getAvailableParkingSpot";
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url_hotspot, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()) {
                           Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i=new Intent(HomePageActivity.this,BarCodeActivity.class);
                                    i.putExtra(BarCodeActivity.BARCODE,response);
                                    startActivity(i);
                                    finish();
                                }
                            },500);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {}
                });
                queue.add(stringRequest);
            }
        });
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getParkingStatus();
    }
     public void logout(View view){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(TOKEN);
        editor.apply();
         Intent i=new Intent(HomePageActivity.this,MainActivity.class);
         startActivity(i);
         finish();
     }

     public void getParkingStatus(){
         RequestQueue queue= Volley.newRequestQueue(HomePageActivity.this);
         String url_home="http://192.168.1.228:8080/getParkingInfo";
         String url_hotspot="http://192.168.16.153:8080/getParkingInfo";
         JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url_hotspot,null, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray jsonArray) {
                 try {
                     System.out.println(jsonArray);
                     for(int i=0;i<jsonArray.length();i++){
                         if(jsonArray.getJSONObject(i).getString("emptyParkingSpot").equalsIgnoreCase(Boolean.toString(false))){
                             parkingSpaces.get(i).setImageDrawable(getResources().getDrawable(R.drawable.taken_parking_spot));
                         }else if(jsonArray.getJSONObject(i).getString("bookedParkingSpot").equalsIgnoreCase(Boolean.toString(true))){
                             parkingSpaces.get(i).setImageDrawable(getResources().getDrawable(R.drawable.booked_parking_spot));
                         }
                     }
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError volleyError) {
             }
         });
         queue.add(jsonArrayRequest);
     }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 15*1000; //Delay for 5 seconds.  One second = 1000 milliseconds.


    @Override
    protected void onResume() {
        //start handler as activity become visible
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                getParkingStatus();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity
    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
}