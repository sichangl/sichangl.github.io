package com.CS571.myapplication.model.detailYelp;

import java.util.ArrayList;

public class detailHour {
    public ArrayList<detailOpen> myopen;
    public String hours_type;
    public boolean is_open_now;

    public ArrayList<detailOpen> getMyopen() {
        return myopen;
    }

    public void setMyopen(ArrayList<detailOpen> myopen) {
        this.myopen = myopen;
    }

    public String getHours_type() {
        return hours_type;
    }

    public void setHours_type(String hours_type) {
        this.hours_type = hours_type;
    }

    public boolean isIs_open_now() {
        return is_open_now;
    }

    public void setIs_open_now(boolean is_open_now) {
        this.is_open_now = is_open_now;
    }
}
