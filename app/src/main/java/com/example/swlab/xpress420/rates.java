package com.example.swlab.xpress420;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Shreyas on 27-10-2017.
 */



public class rates extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rates,container,false);

        final Spinner from_spinner = (Spinner) view.findViewById(R.id.from_spinner);
        final Spinner to_spinner = (Spinner) view.findViewById(R.id.to_spinner);
        final EditText weight = (EditText) view.findViewById(R.id.weight);
        RadioButton standard_rb = (RadioButton) view.findViewById(R.id.standard_rb);
        final RadioButton express_rb = (RadioButton) view.findViewById(R.id.express_rb);
        Button get_estimate_btn = (Button) view.findViewById(R.id.get_estimate_btn);
        final TextView display_tv = (TextView) view.findViewById(R.id.display_tv);

        get_estimate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String from_string = from_spinner.getSelectedItem().toString();
               // String to_string = to_spinner.getSelectedItem().toString();

                int from_index = from_spinner.getSelectedItemPosition();
                int to_index = to_spinner.getSelectedItemPosition();

                String weight_string = weight.getText().toString();

                int weight = 0;

                if (!weight_string.matches("")) {
                    weight = Integer.parseInt(weight_string);
                }


                double[] x = {-2,-1,-0.5,0};
                double[] y = {-0.5,-2.5,4,0};

                double x1,x2,y1,y2;
                double rate = 70.0;

                x1 = x[from_index];
                y1 = y[from_index];
                x2 = x[to_index];
                y2 = y[to_index];

                double distance = get_distance(x1,y1,x2,y2);

                double cost;
                if(distance != 0)
                    cost = rate*distance*weight;
                else
                    cost = 1.5*rate*weight;

                if(express_rb.isChecked())
                    cost = cost + 200;

                if(weight>100)
                    Toast.makeText(getContext(),"Enter a weight less than 100Kg",Toast.LENGTH_SHORT).show();
                else if(weight>0){
                    String display_text = "The estimated cost of shipping is Rs."+ Math.round(cost * 100D) / 100D +" only.";
                    display_tv.setText(display_text);
                }
                else{
                    Toast.makeText(getContext(),"Enter a valid weight",Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

    private  double get_distance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onClick(View view) {

    }
}
