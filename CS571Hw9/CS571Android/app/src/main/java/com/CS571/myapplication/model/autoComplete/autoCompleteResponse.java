package com.CS571.myapplication.model.autoComplete;

import java.util.ArrayList;

public class autoCompleteResponse {
    public ArrayList<autoCategory> categories;
    public ArrayList<Object> businesses;
    public ArrayList<autoTerm> terms;

    public ArrayList<autoCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<autoCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<Object> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(ArrayList<Object> businesses) {
        this.businesses = businesses;
    }

    public ArrayList<autoTerm> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<autoTerm> terms) {
        this.terms = terms;
    }
}
