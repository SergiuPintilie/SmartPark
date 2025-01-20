package com.ic.smartpark;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName,lastName,email,password,confirmPassword;
    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
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

    public boolean validateFirstName(){
        String first_name=firstName.getText().toString();
        if(first_name.isEmpty()){
            firstName.setError("First name can not be empty!");
            return false;
        }else{
            firstName.setError(null);
            return true;
        }
    }
    public boolean validateLastName(){
        String last_name=lastName.getText().toString();
        if(last_name.isEmpty()){
            lastName.setError("Last name can not be empty!");
            return false;
        }else{
            lastName.setError(null);
            return true;
        }
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
        String confirm_password=confirmPassword.getText().toString();
        if(_password.isEmpty()){
            password.setError("Password can not be empty!");
            return false;
        }else{
            password.setError(null);
        }
        if(confirm_password.isEmpty()){
            confirmPassword.setError("Password confirmation can not be empty!");
            return false;
        }else{
            confirmPassword.setError(null);
        }
        if(!_password.equals(confirm_password)){
            confirmPassword.setError("Passwords do not match!");
            return false;
        }else{
            confirmPassword.setError(null);
        }
        return true;
    }

    public void processFormFied(){
        if(!validateFirstName()||!validateLastName()||!validateEmail()||!validatePassword()){
            return;
        }
        RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
        String url_home="http://192.168.1.228:8080/register";
        String url_hotspot="http://192.168.16.153:8080/register";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_hotspot, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("succes")) {
                    Toast.makeText(RegisterActivity.this, "Register succesful!", Toast.LENGTH_LONG).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },500);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterActivity.this, "Register not succesful!", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("firstName",firstName.getText().toString());
                params.put("lastName",lastName.getText().toString());
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}