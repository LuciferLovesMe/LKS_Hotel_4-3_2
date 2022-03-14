package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Adapter adapter;
    RequestQueue queue;
    Session s;
    Context ctx;
    RecyclerView rv;
    Button btn;
    List<Cart> carts;
    AlertDialog dialog;
    private int room_id, fdId, totalPrice, qty, employeeId;
    private String fdName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setTitle("Cart");

        ctx = this;
        s = new Session(ctx);
        carts = new ArrayList<>();
        queue = Volley.newRequestQueue(ctx);
        rv = findViewById(R.id.rv);
        btn = findViewById(R.id.btn_checkout);

        getdata();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSharedPreferences("cart", MODE_PRIVATE).getInt("size", 0) == 0){
                    btn.setEnabled(false);
                }
                else{
                    dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Confirmation");
                    dialog.setMessage("Are you sure to check out?");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkOut();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    void getdata(){
        if (getSharedPreferences("cart", MODE_PRIVATE).getInt("size", 0) == 0){
            rv.setVisibility(View.GONE);
        }
        else {
            rv.setVisibility(View.VISIBLE);
            int size = getSharedPreferences("cart", MODE_PRIVATE).getInt("size", 0);

            for(int i = 0; i < size; i++){
                room_id = getSharedPreferences("cart", MODE_PRIVATE).getInt("roomId"+i, 0);
                fdId = getSharedPreferences("cart", MODE_PRIVATE).getInt("fdId"+i, 0);
                employeeId = getSharedPreferences("cart", MODE_PRIVATE).getInt("employeeId"+i, 0);
                totalPrice = getSharedPreferences("cart", MODE_PRIVATE).getInt("total"+i, 0);
                qty = getSharedPreferences("cart", MODE_PRIVATE).getInt("qty"+i, 0);
                fdName = getSharedPreferences("cart", MODE_PRIVATE).getString("fdName"+i, "");

                carts.add(new Cart(room_id, fdId, totalPrice, qty, employeeId, fdName));
            }

            rv.setLayoutManager(new LinearLayoutManager(ctx));
            adapter = new Adapter(carts, ctx);
            rv.setAdapter(adapter);
        }
    }

    void checkOut(){
        int size = getSharedPreferences("cart", MODE_PRIVATE).getInt("size", 0);
        for (int i = 0; i < size; i++){
            Cart cart = carts.get(i);
            try {
                JSONObject obj = new JSONObject();
                obj.put("reservationRoomId", getSharedPreferences("cart", MODE_PRIVATE).getInt("roomId"+i, 0));
                obj.put("fdId", getSharedPreferences("cart", MODE_PRIVATE).getInt("fdId"+i, 0));
                obj.put("qty", getSharedPreferences("cart", MODE_PRIVATE).getInt("qty"+i, 0));
                obj.put("price", getSharedPreferences("cart", MODE_PRIVATE).getInt("total"+i, 0));
                obj.put("employeeId", getSharedPreferences("cart", MODE_PRIVATE).getInt("employeeId"+i, 0));

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyRequest.getCheckoutURL(), obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog = new AlertDialog.Builder(ctx).create();
                        dialog.setTitle("Information");
                        dialog.setMessage("Success");
                        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        SharedPreferences.Editor editor = getSharedPreferences("cart", MODE_PRIVATE).edit();
                        editor.putInt("size", 0);
                        editor.commit();
                        getdata();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog = new AlertDialog.Builder(ctx).create();
                        dialog.setTitle("Information");
                        dialog.setMessage("Success");
                        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        SharedPreferences.Editor editor = getSharedPreferences("cart", MODE_PRIVATE).edit();
                        editor.putInt("size", 0);
                        editor.commit();
                        getdata();
                    }
                });

                queue.add(request);
            }
            catch (Exception e){
                dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Information");
                dialog.setMessage("Success");
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                SharedPreferences.Editor editor = getSharedPreferences("cart", MODE_PRIVATE).edit();
                editor.putInt("size", 0);
                editor.commit();
                getdata();
            }
        }
    }
}
