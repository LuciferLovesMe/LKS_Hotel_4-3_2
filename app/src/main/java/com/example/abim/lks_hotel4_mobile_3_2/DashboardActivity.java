package com.example.abim.lks_hotel4_mobile_3_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_order){
            startActivity(new Intent(getApplicationContext(), OrderActivity.class));
        }
        else if (id == R.id.card_cart){
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        else if (id == R.id.card_info){
            startActivity(new Intent(getApplicationContext(), InfoActvity.class));
        }
        else if (id == R.id.card_exit){
            AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).create();
            dialog.setTitle("Confirmation");
            dialog.setMessage("Are you sure?");
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
