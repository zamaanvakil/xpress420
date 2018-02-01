package com.example.swlab.xpress420;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zamaan on 27-10-2017.
 */


public class track_shipment extends Fragment {
    @Nullable

    FloatingActionButton send_shipment_fab;

    ListView orders_list_view;

    List<OrderClass> orders_list;

    private DatabaseReference orders_db;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    TextView no_shipments_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_shipment,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        orders_db = FirebaseDatabase.getInstance().getReference("orders");//.child(user.getUid());

        send_shipment_fab = (FloatingActionButton) view.findViewById(R.id.send_shipment_fab);

        orders_list_view = (ListView) view.findViewById(R.id.orders_list_view);
        no_shipments_tv = (TextView) view.findViewById(R.id.no_shipments_tv);

        orders_list = new ArrayList<>();

        send_shipment_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("fragment","send_shipment");
                startActivity(intent);
            }
        });

        orders_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrderClass order = orders_list.get(i);
                Intent intent = new Intent(getContext(), ShipmentDetails.class);
                intent.putExtra("order_id",order.getOrder_id());
                intent.putExtra("sender_name",order.getSender_name());
                intent.putExtra("pickup_address",order.getPickup_address());
                intent.putExtra("receiver_name",order.getReceiver_name());
                intent.putExtra("receiver_address",order.getReceiver_address());
                intent.putExtra("weight",order.getWeight());
                intent.putExtra("fragile",order.getFragile());
                intent.putExtra("express",order.getExpress());
                intent.putExtra("cost",order.getCost());
                intent.putExtra("cop",order.getcop());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        orders_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                orders_list.clear();
                for(DataSnapshot orderSnapshot: dataSnapshot.getChildren()){
                    OrderClass order = orderSnapshot.getValue(OrderClass.class);
                    if(order.getUser_id().equals(user.getUid()))
                        orders_list.add(order);
                }

                if(getActivity()!=null){
                    OrdersList adapter = new OrdersList(getActivity(),orders_list);
                    orders_list_view.setAdapter(adapter);
                    if(adapter.getCount() == 0){
                        //Toast.makeText(getContext(), "list is empty", Toast.LENGTH_SHORT).show();
                        no_shipments_tv.setText("No shipments to display");
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
