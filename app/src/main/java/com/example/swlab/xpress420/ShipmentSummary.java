package com.example.swlab.xpress420;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShipmentSummary extends AppCompatActivity {

    TextView sender_name_tv;
    TextView sender_address_tv;
    TextView sender_city_tv;

    TextView receiver_name_tv;
    TextView receiver_address_tv;
    TextView receiver_city_tv;

    RadioButton standard_rb;
    RadioButton express_rb;

    TextView shipment_charges_tv;
    TextView shipment_charges_cost_tv;

    TextView delivery_charges_tv;
    TextView delivery_charges_cost_tv;

    TextView fragile_charges_tv;
    TextView fragile_charges_cost_tv;

    TextView total_tv;
    TextView total_cost_tv;

    Button payment_btn;

    String pickup_address_sender_string;
    String pickup_address_house_string;
    String pickup_address_street_string;
    String pickup_address_area_string;
    String pickup_address_pincode_string;
    int from_index;

    String receiver_address_receiver_string;
    String receiver_address_house_string;
    String receiver_address_street_string;
    String receiver_address_area_string ;
    String receiver_address_pincode_string;
    int to_index;

    String weight_string;
    boolean fragile;

    String[] cities_array;

    double cost;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_summary);

        pickup_address_sender_string = getIntent().getStringExtra("pickup_address_sender_string");
        pickup_address_house_string = getIntent().getStringExtra("pickup_address_house_string");
        pickup_address_street_string = getIntent().getStringExtra("pickup_address_street_string");
        pickup_address_area_string = getIntent().getStringExtra("pickup_address_area_string");
        pickup_address_pincode_string = getIntent().getStringExtra("pickup_address_pincode_string");
        from_index = getIntent().getIntExtra("from_index",0);

        receiver_address_receiver_string = getIntent().getStringExtra("receiver_address_receiver_string");
        receiver_address_house_string = getIntent().getStringExtra("receiver_address_house_string");
        receiver_address_street_string = getIntent().getStringExtra("receiver_address_street_string");
        receiver_address_area_string = getIntent().getStringExtra("receiver_address_area_string");
        receiver_address_pincode_string = getIntent().getStringExtra("receiver_address_pincode_string");
        to_index = getIntent().getIntExtra("to_index",0);

        weight_string = getIntent().getStringExtra("weight_string");
        fragile = getIntent().getBooleanExtra("fragile",false);

        cities_array = getResources().getStringArray(R.array.cities_array);

        //Sender details
        sender_name_tv = (TextView) findViewById(R.id.sender_name_tv);
        sender_address_tv = (TextView) findViewById(R.id.sender_address_tv);
        sender_city_tv = (TextView) findViewById(R.id.sender_city_tv);

        //Receiver details
        receiver_name_tv = (TextView) findViewById(R.id.receiver_name_tv);
        receiver_address_tv = (TextView) findViewById(R.id.receiver_address_tv);
        receiver_city_tv = (TextView) findViewById(R.id.receiver_city_tv);

        //Set sender text
        sender_name_tv.setText(pickup_address_sender_string);
        sender_address_tv.setText(pickup_address_house_string + ", " + pickup_address_street_string + ", "+ pickup_address_area_string);
        sender_city_tv.setText(cities_array[from_index] + " - " + pickup_address_pincode_string + ".");

        //Set receiver text
        receiver_name_tv.setText(receiver_address_receiver_string);
        receiver_address_tv.setText(receiver_address_house_string + ", " + receiver_address_street_string + ", "+ receiver_address_area_string);
        receiver_city_tv.setText(cities_array[to_index] + " - " + receiver_address_pincode_string + ".");

        //Shipping method
        standard_rb = (RadioButton) findViewById(R.id.standard_rb);
        express_rb = (RadioButton) findViewById(R.id.express_rb);

        //Order details
        shipment_charges_tv = (TextView) findViewById(R.id.shipment_charges_tv);
        shipment_charges_cost_tv = (TextView) findViewById(R.id.shipment_charges_cost_tv);

        delivery_charges_tv = (TextView) findViewById(R.id.delivery_charges_tv);
        delivery_charges_cost_tv = (TextView) findViewById(R.id.delivery_charges_cost_tv);

        fragile_charges_tv = (TextView) findViewById(R.id.fragile_charges_tv);
        fragile_charges_cost_tv = (TextView) findViewById(R.id.fragile_charges_cost_tv);

        total_tv = (TextView) findViewById(R.id.total_tv);
        total_cost_tv = (TextView) findViewById(R.id.total_cost_tv);

        //Payment button
        payment_btn = (Button) findViewById(R.id.payment_btn);

        CalculateCost();

        if(fragile){
            fragile_charges_tv.setText("Fragile handling charges");
            fragile_charges_cost_tv.setText("Rs. 100");
        }

        standard_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivery_charges_tv.setText("Standard shipping charges");
                delivery_charges_cost_tv.setText("Rs. 0.0");
                CalculateCost();
            }
        });

        express_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivery_charges_tv.setText("Express shipping charges");
                delivery_charges_cost_tv.setText("Rs. 200.0");
                CalculateCost();
            }
        });

        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean express = express_rb.isChecked();

                Intent intent = new Intent(ShipmentSummary.this, PaymentActivity.class);

                intent.putExtra("pickup_address_sender_string", pickup_address_sender_string);
                intent.putExtra("pickup_address_house_string", pickup_address_house_string);
                intent.putExtra("pickup_address_street_string", pickup_address_street_string);
                intent.putExtra("pickup_address_area_string", pickup_address_area_string);
                intent.putExtra("pickup_address_pincode_string", pickup_address_pincode_string);
                intent.putExtra("from_index", from_index);

                intent.putExtra("receiver_address_receiver_string", receiver_address_receiver_string);
                intent.putExtra("receiver_address_house_string", receiver_address_house_string);
                intent.putExtra("receiver_address_street_string", receiver_address_street_string);
                intent.putExtra("receiver_address_area_string", receiver_address_area_string);
                intent.putExtra("receiver_address_pincode_string", receiver_address_pincode_string);
                intent.putExtra("to_index", to_index);

                intent.putExtra("weight_string", weight_string);
                intent.putExtra("fragile", fragile);

                intent.putExtra("cost",cost);

                intent.putExtra("express",express);

                startActivity(intent);
            }
        });


    }

    private  double get_distance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }

    private void CalculateCost(){
        int weight = Integer.parseInt(weight_string);

        double[] x = {-2,-1,-0.5,0};
        double[] y = {-0.5,-2.5,4,0};

        double x1,x2,y1,y2;
        double rate = 70.0;

        x1 = x[from_index];
        y1 = y[from_index];
        x2 = x[to_index];
        y2 = y[to_index];

        double distance = get_distance(x1,y1,x2,y2);

        if(distance != 0)
            cost = rate*distance*weight;
        else
            cost = 1.5*rate*weight;

        cost = Math.round(cost * 100D) / 100D;

        shipment_charges_tv.setText("Shipment charges ("+weight_string+"Kg)");

        shipment_charges_cost_tv.setText("Rs. "+cost);

        if(express_rb.isChecked())
            cost = cost + 200;

        if(fragile)
            cost = cost + 100;
        cost = Math.round(cost * 100D) / 100D;
        total_cost_tv.setText("Rs. " + cost);
    }
}
