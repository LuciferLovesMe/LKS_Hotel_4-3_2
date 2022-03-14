package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    Context ctx;
    Session s;
    RequestQueue queue;
    Spinner spin_room, spin_fd;
    Button btn_inc, btn_dec, btn_add;
    TextView tv_price, tv_total;
    EditText et_qty;
    List<String> room_number;
    List<String> fd_name;
    List<Integer> room_id;
    List<FD> fds;
    List<Cart> carts;
    int employeeId, fdId, roomId, totalPrice, qty, price;
    String fdName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setTitle("Order");

        ctx = this;
        s = new Session(ctx);
        queue = Volley.newRequestQueue(ctx);
        spin_fd = findViewById(R.id.spin_fd);
        spin_room = findViewById(R.id.spin_room);
        btn_add = findViewById(R.id.btn_add);
        btn_dec = findViewById(R.id.btn_dec);
        btn_inc = findViewById(R.id.btn_inc);
        tv_price = findViewById(R.id.tv_price);
        tv_total = findViewById(R.id.tv_total);
        et_qty = findViewById(R.id.et_qty);
        room_number = new ArrayList<>();
        room_id = new ArrayList<>();
        fds = new ArrayList<>();
        carts = new ArrayList<>();
        fd_name = new ArrayList<>();
        fd_name.clear();
        carts.clear();
        room_number.clear();
        room_id.clear();
        fds.clear();

        loadFd();
        loadRoom();

        qty = 0;

        btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.valueOf(et_qty.getText().toString());
                qty++;
                et_qty.setText(String.valueOf(qty));
                totalPrice = qty * price;
                tv_total.setText(String.valueOf(totalPrice));
            }
        });

        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.valueOf(et_qty.getText().toString());
                if (qty < 0){
                    qty = 0;
                }
                else {
                    qty--;
                    et_qty.setText(String.valueOf(qty));
                }

                totalPrice = qty * price;
                tv_total.setText(String.valueOf(totalPrice));
            }
        });

        spin_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomId = Integer.valueOf(room_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_fd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FD fd = fds.get(position);
                fdName = fd.getName();
                price = fd.getPrice();
                tv_price.setText(String.valueOf(price));
                tv_total.setText(String.valueOf(price * qty));
                fdId = fd.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_qty.getText().toString().equalsIgnoreCase("") || et_qty.getText().toString().equalsIgnoreCase("0")){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setMessage("Please insert quantity");
                    dialog.setTitle("Error");
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else {
                    add();
                }
            }
        });
    }

    void loadFd(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getFdURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        fds.add(new FD(obj.getInt("id"), obj.getInt("price"), obj.getString("name")));
                    }

                    for (FD fd : fds){
                        fd_name.add(fd.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.support_simple_spinner_dropdown_item, fd_name);
                    spin_fd.setAdapter(adapter);
                }
                catch (Exception e){
                    Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    void loadRoom(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getRoomURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);

                        room_number.add(obj.getString("RoomNumber"));
                        room_id.add(obj.getInt("id"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.support_simple_spinner_dropdown_item, room_number);
                    spin_room.setAdapter(adapter);
                }
                catch (Exception e){
                    Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    void add(){
        carts.add(new Cart(roomId, fdId, totalPrice, qty, s.getId(), fdName));
        SharedPreferences.Editor editor = getSharedPreferences("cart", MODE_PRIVATE).edit();
        editor.putInt("size", carts.size());
        editor.commit();

        for(int i = 0; i < carts.size(); i++){
            Cart cart = carts.get(i);
            SharedPreferences.Editor edit = getSharedPreferences("cart", MODE_PRIVATE).edit();
            edit.putInt("fdId"+i, cart.getFdId());
            edit.putInt("employeeId"+i, cart.getEmployeeId());
            edit.putInt("total"+i, cart.getTotalPrice());
            edit.putInt("qty"+i, cart.getQty());
            edit.putString("fdName"+i, cart.getFdName());
            edit.putInt("roomId"+i, cart.getRoom_id());
            edit.commit();
        }

        Toast.makeText(ctx, "Successfully added to cart!", Toast.LENGTH_SHORT).show();
    }
}
