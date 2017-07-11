package com.services.bean;

/**
 * Created by ripal on 5/3/2017.
 */

public class CityBean {
    String id,stateid,cityName;

    public CityBean(String id, String stateid, String cityName) {
        this.id = id;
        this.stateid = stateid;
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
