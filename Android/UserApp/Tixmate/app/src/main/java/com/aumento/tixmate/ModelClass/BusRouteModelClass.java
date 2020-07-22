package com.aumento.tixmate.ModelClass;

public class BusRouteModelClass {

    String id;
    String stop_name;
    String stop_time;

    public BusRouteModelClass(String id, String stop_name, String stop_time) {
        this.id = id;
        this.stop_name = stop_name;
        this.stop_time = stop_time;
    }

    public String getId() {
        return id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public String getStop_time() {
        return stop_time;
    }
}
