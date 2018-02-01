package com.example.swlab.xpress420;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shreyas on 03-11-2017.
 */

public class OrdersList extends ArrayAdapter<OrderClass> {

    private Activity context;
    private List<OrderClass> orderList;


    public OrdersList(Activity context, List<OrderClass> orderList){
        super(context, R.layout.orders_list_style,orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.orders_list_style,null,true);

        TextView receiver_name_tv = (TextView) listViewItem.findViewById(R.id.receiver_name_tv);
        TextView order_date_tv = (TextView) listViewItem.findViewById(R.id.order_date_tv);
        TextView shipping_method_tv = (TextView) listViewItem.findViewById(R.id.shipping_method_tv);
        TextView cost_tv = (TextView) listViewItem.findViewById(R.id.cost_tv);

        OrderClass order = orderList.get(position);

        receiver_name_tv.setText(order.getReceiver_name());
        //order_id_tv.setText(order.getOrder_id());
        if(order.getOrder_date()!= null)
            order_date_tv.setText(order.getOrder_date());

        if(order.getExpress())
            shipping_method_tv.setText("Express Delivery");
        else{
            shipping_method_tv.setText("Standard Delivery");
            shipping_method_tv.setTextColor(Color.rgb(21,35,105));
        }

        cost_tv.setText("Rs. " +  order.getCost());

        return listViewItem;
    }
}
