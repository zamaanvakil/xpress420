package com.example.swlab.xpress420;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Zamaan on 27-10-2017.
 */

public class send_shipment extends Fragment {
    @Nullable
    //Pickup Address
    EditText pickup_address_sender;
    EditText pickup_address_house;
    EditText pickup_address_street;
    EditText pickup_address_area;
    EditText pickup_address_pincode;
    Spinner from_spinner;

    //Receiver Address
    EditText receiver_address_receiver;
    EditText receiver_address_house;
    EditText receiver_address_street;
    EditText receiver_address_area;
    EditText receiver_address_pincode;
    Spinner to_spinner;

    //Parcel Details
    EditText weight_et;
    CheckBox fragile_cb;

    Button next_btn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_shipment,container,false);

        pickup_address_sender = (EditText) view.findViewById(R.id.pickup_address_sender);
        pickup_address_house = (EditText) view.findViewById(R.id.pickup_address_house);
        pickup_address_street = (EditText) view.findViewById(R.id.pickup_address_street);
        pickup_address_area = (EditText) view.findViewById(R.id.pickup_address_area);
        pickup_address_pincode = (EditText) view.findViewById(R.id.pickup_address_pincode);
        from_spinner = (Spinner) view.findViewById(R.id.from_spinner);

        receiver_address_receiver = (EditText) view.findViewById(R.id.receiver_address_receiver);
        receiver_address_house = (EditText) view.findViewById(R.id.receiver_address_house);
        receiver_address_street = (EditText) view.findViewById(R.id.receiver_address_street);
        receiver_address_area = (EditText) view.findViewById(R.id.receiver_address_area);
        receiver_address_pincode = (EditText) view.findViewById(R.id.receiver_address_pincode);
        to_spinner = (Spinner) view.findViewById(R.id.to_spinner);

        weight_et = (EditText) view.findViewById(R.id.weight_et);
        fragile_cb = (CheckBox) view.findViewById(R.id.fragile_cb);

        next_btn = (Button) view.findViewById(R.id.next_btn);


        //pickup_address_sender.setText(currentDateTimeString);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pickup_address_sender_string = pickup_address_sender.getText().toString().trim();
                String pickup_address_house_string = pickup_address_house.getText().toString().trim();
                String pickup_address_street_string = pickup_address_street.getText().toString().trim();
                String pickup_address_area_string = pickup_address_area.getText().toString().trim();
                String pickup_address_pincode_string = pickup_address_pincode.getText().toString().trim();
                int from_index = from_spinner.getSelectedItemPosition();

                String receiver_address_receiver_string = receiver_address_receiver.getText().toString().trim();
                String receiver_address_house_string = receiver_address_house.getText().toString().trim();
                String receiver_address_street_string = receiver_address_street.getText().toString().trim();
                String receiver_address_area_string = receiver_address_area.getText().toString().trim();
                String receiver_address_pincode_string = receiver_address_pincode.getText().toString().trim();
                int to_index = to_spinner.getSelectedItemPosition();

                String weight_string = weight_et.getText().toString();
                double  weight = 0;
                if (!weight_string.matches("")) {
                    weight = Integer.parseInt(weight_string);
                }
                boolean fragile = fragile_cb.isChecked();

                if(weight>100)
                    Toast.makeText(getContext(),"Enter a weight less than 100Kg",Toast.LENGTH_SHORT).show();
                else if(weight <= 0)
                    Toast.makeText(getContext(),"Enter a valid weight",Toast.LENGTH_SHORT).show();
                else if ((pickup_address_sender_string.length() != 0) && (pickup_address_house_string.length() != 0) &&
                        (pickup_address_street_string.length() != 0) && (pickup_address_area_string.length() != 0) &&
                        (pickup_address_pincode_string.length() != 0) && (receiver_address_receiver_string.length() != 0) &&
                        (receiver_address_house_string.length() != 0) && (receiver_address_street_string.length() != 0) &&
                        (receiver_address_area_string.length() != 0) && (receiver_address_pincode_string.length() != 0)){

                    Intent start_summary = new Intent(getActivity(), ShipmentSummary.class);

                    start_summary.putExtra("pickup_address_sender_string", pickup_address_sender_string);
                    start_summary.putExtra("pickup_address_house_string", pickup_address_house_string);
                    start_summary.putExtra("pickup_address_street_string", pickup_address_street_string);
                    start_summary.putExtra("pickup_address_area_string", pickup_address_area_string);
                    start_summary.putExtra("pickup_address_pincode_string", pickup_address_pincode_string);
                    start_summary.putExtra("from_index", from_index);

                    start_summary.putExtra("receiver_address_receiver_string", receiver_address_receiver_string);
                    start_summary.putExtra("receiver_address_house_string", receiver_address_house_string);
                    start_summary.putExtra("receiver_address_street_string", receiver_address_street_string);
                    start_summary.putExtra("receiver_address_area_string", receiver_address_area_string);
                    start_summary.putExtra("receiver_address_pincode_string", receiver_address_pincode_string);
                    start_summary.putExtra("to_index", to_index);

                    start_summary.putExtra("weight_string", weight_string);
                    start_summary.putExtra("fragile", fragile);

                    getActivity().startActivity(start_summary);
                }
                else{
                    Toast.makeText(getContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
