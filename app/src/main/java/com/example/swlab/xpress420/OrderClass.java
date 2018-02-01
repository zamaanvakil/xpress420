package com.example.swlab.xpress420;

/**
 * Created by Shreyas on 03-11-2017.
 */

public class OrderClass {
    String order_id;
    String user_id;
    String order_date;
    String sender_name;
    String pickup_address;
    String receiver_name;
    String receiver_address;
    String weight;
    Boolean fragile;
    Boolean express;
    Boolean cop;
    double cost;

    public OrderClass() {
    }

    public OrderClass(String order_id, String user_id, String order_date, String sender_name, String pickup_address, String receiver_name, String receiver_address, String weight, Boolean fragile, Boolean express, Boolean cop, double cost) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.sender_name = sender_name;
        this.pickup_address = pickup_address;
        this.receiver_name = receiver_name;
        this.receiver_address = receiver_address;
        this.weight = weight;
        this.fragile = fragile;
        this.express = express;
        this.cop = cop;
        this.cost = cost;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getWeight() {
        return weight;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public Boolean getExpress() {
        return express;
    }

    public Boolean getcop() {
        return cop;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public void setExpress(Boolean express) {
        this.express = express;
    }

    public void setCop(Boolean cop) {
        this.cop = cop;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getOrder_date() {
        return order_date;
    }
}
