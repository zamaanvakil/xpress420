package com.example.swlab.xpress420;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PaymentActivity extends AppCompatActivity {

    RadioButton COP_rb;
    RadioButton COD_rb;

    Button order_brn;

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

    double cost;
    boolean express;

    String[] cities_array;

    private DatabaseReference orders_db;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        orders_db = FirebaseDatabase.getInstance().getReference("orders");

        COP_rb = (RadioButton) findViewById(R.id.COP_rb);
        COD_rb = (RadioButton) findViewById(R.id.COD_rb);
        order_brn = (Button) findViewById(R.id.order_btn);

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
        express = getIntent().getBooleanExtra("express",false);

        cost = getIntent().getDoubleExtra("cost",0.0);

        cities_array = getResources().getStringArray(R.array.cities_array);

        order_brn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                boolean COP = COP_rb.isChecked();

                String user_id = user.getUid();
                String order_id = orders_db.push().getKey();
                //String order_id = "00";

                String pickup_address = pickup_address_house_string + ", " + pickup_address_street_string + ", " + pickup_address_area_string
                        + ", " +cities_array[from_index]+ " - " + pickup_address_pincode_string + ".";

                String receiver_address = receiver_address_house_string + ", " + receiver_address_street_string + ", " + receiver_address_area_string
                        + ", " +cities_array[to_index]+ " - " + receiver_address_pincode_string + ".";

                String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

                OrderClass order = new OrderClass(order_id,user_id,currentDateTimeString,pickup_address_sender_string,pickup_address,receiver_address_receiver_string,
                        receiver_address,weight_string,fragile,express,COP,cost);

                orders_db.child(order_id).setValue(order);



                Intent intent = new Intent(PaymentActivity.this, OrderConfirmedActivity.class);

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

                intent.putExtra("COP",COP);

                intent.putExtra("order_id",order_id);

                startActivity(intent)
                ;

            }
        });
    }
}
