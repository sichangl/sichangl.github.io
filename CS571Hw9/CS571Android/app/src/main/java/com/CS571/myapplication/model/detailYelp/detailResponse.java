package com.CS571.myapplication.model.detailYelp;

import java.util.ArrayList;

public class detailResponse {
    public String id;
    public String alias;
    public String name;
    public String image_url;
    public boolean is_claimed;
    public boolean is_closed;
    public String url;
    public String phone;
    public String display_phone;
    public int review_count;
    public ArrayList<detailCategory> categories;
    public double rating;
    public detailLocation location;
    public detailCoordinates coordinates;
    public ArrayList<String> photos;
    public String price;
    public ArrayList<detailHour> hours;
    public ArrayList<String> transactions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isIs_claimed() {
        return is_claimed;
    }

    public void setIs_claimed(boolean is_claimed) {
        this.is_claimed = is_claimed;
    }

    public boolean isIs_closed() {
        return is_closed;
    }

    public void setIs_closed(boolean is_closed) {
        this.is_closed = is_closed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplay_phone() {
        return display_phone;
    }

    public void setDisplay_phone(String display_phone) {
        this.display_phone = display_phone;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public ArrayList<detailCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<detailCategory> categories) {
        this.categories = categories;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public detailLocation getLocation() {
        return location;
    }

    public void setLocation(detailLocation location) {
        this.location = location;
    }

    public detailCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(detailCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<detailHour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<detailHour> hours) {
        this.hours = hours;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }
}
