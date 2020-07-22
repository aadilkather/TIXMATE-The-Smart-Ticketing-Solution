package com.aumento.tixmate.ModelClass;

public class BookingListModelClass {

    String id;
    String bus_id;
    String user_id;
    String bus_name;
    String bus_no;
    String pickup_point;
    String dropoff_point;
    String pickup_time;
    String dropoff_time;
    String booking_date;
    String price;
    String seats;
    String status;

    public BookingListModelClass(String id, String bus_id, String user_id, String bus_name, String bus_no, String pickup_point, String dropoff_point, String pickup_time, String dropoff_time, String booking_date, String price, String seats, String status) {
        this.id = id;
        this.bus_id = bus_id;
        this.user_id = user_id;
        this.bus_name = bus_name;
        this.bus_no = bus_no;
        this.pickup_point = pickup_point;
        this.dropoff_point = dropoff_point;
        this.pickup_time = pickup_time;
        this.dropoff_time = dropoff_time;
        this.booking_date = booking_date;
        this.price = price;
        this.seats = seats;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public String getBus_no() {
        return bus_no;
    }

    public String getPickup_point() {
        return pickup_point;
    }

    public String getDropoff_point() {
        return dropoff_point;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public String getDropoff_time() {
        return dropoff_time;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getPrice() {
        return price;
    }

    public String getSeats() {
        return seats;
    }

    public String getStatus() {
        return status;
    }
}
