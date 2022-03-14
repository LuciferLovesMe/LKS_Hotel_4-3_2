package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Context ctx;
    RequestQueue queue;
    EditText et_user, et_pass;
    Button btn;
    Session s;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        et_user = findViewById(R.id.et_user);
        et_pass = findViewById(R.id.et_pass);
        btn = findViewById(R.id.btn_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_user.getText().toString().equalsIgnoreCase("") || et_pass.getText().toString().equalsIgnoreCase("")){
                    dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Error");
                    dialog.setMessage("All fields must be filled");
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    doLogin();
                }
            }
        });
    }

    void doLogin(){
        final String username = et_user.getText().toString(), pass = et_pass.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, MyRequest.getLoginURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    s = new Session(ctx);
                    s.setUser(object.getInt("id"), object.getString("name"));

                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                }
                catch(Exception e){
                    dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Error");
                    dialog.setMessage("Can't Find User");
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error");
                dialog.setMessage("Can't Find User");
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", pass);
                return params;
            }
        };

        queue.add(request);
    }
}
