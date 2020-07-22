package com.aumento.tixmate.ModelClass;

public class BusDetailsModelClass {
    private String bus_id;
    private String bus_name;
    private String start_time;
    private String end_time;
    private String seats;
    private String travel_time;
    private String start_place;
    private String dest_place;
    private String route_id;

    public BusDetailsModelClass(String bus_id, String bus_name, String start_time, String end_time, String seats, String travel_time, String start_place, String dest_place, String route_id) {
        this.bus_id = bus_id;
        this.bus_name = bus_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.seats = seats;
        this.travel_time = travel_time;
        this.start_place = start_place;
        this.dest_place = dest_place;
        this.route_id = route_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getSeats() {
        return seats;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public String getStart_place() {
        return start_place;
    }

    public String getDest_place() {
        return dest_place;
    }

    public String getRoute_id() {
        return route_id;
    }
}
