package com.ic.smartpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button loginBtn;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="MyPreferences";
    private static final String TOKEN="TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginBtn=findViewById(R.id.loginBtn);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFormFied();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean validateEmail(){
        String _email=email.getText().toString();
        if(_email.isEmpty()){
            email.setError("Email can not be empty!");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String _password=password.getText().toString();
        if(_password.isEmpty()){
            password.setError("Password can not be empty!");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }

    public void processFormFied(){
        if(!validateEmail()||!validatePassword()){
            return;
        }
        Map<String,String> params=new HashMap<>();
        params.put("email",email.getText().toString());
        params.put("password",password.getText().toString());
        JSONObject requestData=new JSONObject(params);

        RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
        String url_home="http://192.168.1.228:8080/authenticate";
        String url_hotspot="http://192.168.16.153:8080/authenticate";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url_hotspot, requestData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if(jsonObject.getString("status").equalsIgnoreCase("succes")){
                        Toast.makeText(LoginActivity.this, "Login succesful!", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(TOKEN,jsonObject.getString("token"));
                        editor.apply();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i=new Intent(LoginActivity.this,HomePageActivity.class);
                                startActivity(i);
                                finish();
                            }
                        },500);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this, "Login not succesful!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}