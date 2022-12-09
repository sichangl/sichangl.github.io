package com.CS571.myapplication.model.yelpApi;

import java.util.ArrayList;

public class yelpResponse {
    public ArrayList<Business> businesses;
    public int total;

    public yelpResponse() {

    }

    public yelpResponse(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }
}
