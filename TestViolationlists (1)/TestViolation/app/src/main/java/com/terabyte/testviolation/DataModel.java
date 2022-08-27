package com.terabyte.testviolation;

public class DataModel {
    Double latitude;
    Double longitude;
    long time;
    DataModel(){}
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DataModel(Double latitude, Double longitude, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }
}
