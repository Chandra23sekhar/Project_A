package com.example.project_a;

public class RentalDB {

    String vehicle_id, date, username, vehicle_owner, source, destination, est_cost;

    public RentalDB(String vehicle_id, String date, String username, String vehicle_owner, String source, String destination, String est_cost) {
        this.vehicle_id = vehicle_id;
        this.date = date;
        this.username = username;
        this.vehicle_owner = vehicle_owner;
        this.source = source;
        this.destination = destination;
        this.est_cost = est_cost;
    }

    public RentalDB() {

    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicle_owner() {
        return vehicle_owner;
    }

    public void setVehicle_owner(String vehicle_owner) {
        this.vehicle_owner = vehicle_owner;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEst_cost() {
        return est_cost;
    }

    public void setEst_cost(String est_cost) {
        this.est_cost = est_cost;
    }
}
