package com.CS571.myapplication.model.reviewYelp;

import java.util.ArrayList;

public class reviewResponse {
    public ArrayList<Review> reviews;
    public int total;
    public ArrayList<String> possible_languages;

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<String> getPossible_languages() {
        return possible_languages;
    }

    public void setPossible_languages(ArrayList<String> possible_languages) {
        this.possible_languages = possible_languages;
    }
}
