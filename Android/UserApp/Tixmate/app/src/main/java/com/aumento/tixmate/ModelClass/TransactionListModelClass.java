package com.aumento.tixmate.ModelClass;

public class TransactionListModelClass {

    String id;
    String trans_num;
    String bus_name;
    String stop_name;
    String amount;
    String tdate;
    String status;

    public TransactionListModelClass(String id, String trans_num, String bus_name, String stop_name, String amount, String tdate, String status) {
        this.id = id;
        this.trans_num = trans_num;
        this.bus_name = bus_name;
        this.stop_name = stop_name;
        this.amount = amount;
        this.tdate = tdate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTrans_num() {
        return trans_num;
    }

    public String getBus_name() {
        return bus_name;
    }

    public String getStop_name() {
        return stop_name;
    }

    public String getAmount() {
        return amount;
    }

    public String getTdate() {
        return tdate;
    }

    public String getStatus() {
        return status;
    }
}
