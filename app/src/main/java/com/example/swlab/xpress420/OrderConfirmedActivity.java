package com.example.swlab.xpress420;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderConfirmedActivity extends AppCompatActivity {

    TextView order_id_tv;
    TextView delivery_message;
    TextView view_all_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        order_id_tv = (TextView) findViewById(R.id.order_id_tv);
        delivery_message = (TextView) findViewById(R.id.delivery_message);
        view_all_orders = (TextView) findViewById(R.id.view_all_orders);

        order_id_tv.setText(getIntent().getStringExtra("order_id"));

        if(getIntent().getBooleanExtra("express",false)){
            delivery_message.setText(getResources().getText(R.string.express_delivery_message));
        }

        view_all_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderConfirmedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderConfirmedActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
