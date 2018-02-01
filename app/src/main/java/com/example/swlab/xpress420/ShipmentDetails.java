package com.example.swlab.xpress420;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShipmentDetails extends AppCompatActivity {

    TextView order_id_tv;
    TextView sender_name_tv;
    TextView sender_address_tv;
    TextView receiver_name_tv;
    TextView receiver_address_tv;
    TextView shipping_method_tv;
    TextView weight_tv;
    TextView cost_tv;
    TextView pm_tv;

    Button track_btn;
    Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);

        final String order_id = getIntent().getStringExtra("order_id");
        String sender_name = getIntent().getStringExtra("sender_name");
        String pickup_address = getIntent().getStringExtra("pickup_address");
        String receiver_name = getIntent().getStringExtra("receiver_name");
        String receiver_address = getIntent().getStringExtra("receiver_address");
        String weight = getIntent().getStringExtra("weight");
        double cost = getIntent().getDoubleExtra("cost",0.0);
        Boolean fragile = getIntent().getBooleanExtra("fragile",false);
        Boolean cop = getIntent().getBooleanExtra("cop",true);
        Boolean express = getIntent().getBooleanExtra("express",false);

        order_id_tv = (TextView) findViewById(R.id.order_id_tv);
        sender_name_tv = (TextView) findViewById(R.id.sender_name_tv);
        sender_address_tv = (TextView) findViewById(R.id.sender_address_tv);
        receiver_name_tv = (TextView) findViewById(R.id.receiver_name_tv);
        receiver_address_tv = (TextView) findViewById(R.id.receiver_address_tv);
        shipping_method_tv = (TextView) findViewById(R.id.shipping_method_tv);
        weight_tv = (TextView) findViewById(R.id.weight_tv);
        cost_tv = (TextView) findViewById(R.id.cost_tv);
        pm_tv = (TextView) findViewById(R.id.pm_tv);
        track_btn = (Button) findViewById(R.id.track_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        order_id_tv.setText("Order ID: " + order_id);
        sender_name_tv.setText(sender_name);
        sender_address_tv.setText(pickup_address);
        receiver_name_tv.setText(receiver_name);
        receiver_address_tv.setText(receiver_address);

        if(express)
            shipping_method_tv.setText("Express");

        else{
            shipping_method_tv.setText("Standard");
            shipping_method_tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        if (fragile)
            weight_tv.setText(weight+" Kg (Fragile)");
        else
            weight_tv.setText(weight+" Kg");

        if(cop)
            pm_tv.setText("Cash On Pickup (COP)");
        else
            pm_tv.setText("Cash On Delivery (COD)");

        cost_tv.setText("Rs. "+cost);

        track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShipmentDetails.this, "The parcel is yet to be picked up.", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*deleteOrder(order_id);
                Intent intent = new Intent(ShipmentDetails.this, MainActivity.class);
                startActivity(intent);
                */
                AlertDialog.Builder cancel_alert_builder = new AlertDialog.Builder(ShipmentDetails.this);
                cancel_alert_builder.setMessage("Are you sure you want to cancel this order?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteOrder(order_id);
                                Intent intent = new Intent(ShipmentDetails.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog cancel_alert = cancel_alert_builder.create();
                cancel_alert.setTitle("Cancel Order");
                cancel_alert.show();

            }
        });

    }

    private void deleteOrder(String order_id) {
        DatabaseReference dorder = FirebaseDatabase.getInstance().getReference("orders").child(order_id);
        dorder.removeValue();
        Toast.makeText(this, "Order was cancelled successfully.", Toast.LENGTH_SHORT).show();
    }

}
