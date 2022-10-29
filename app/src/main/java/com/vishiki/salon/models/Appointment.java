package com.vishiki.salon.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Appointment {
    private String username;
    private ArrayList<Services> servicesList;
    private ArrayList<HashMap<String,Object>> stringHashMap;
    private String total;
    private String appointmentDate;

    public Appointment() {
    }

    public Appointment(String username, ArrayList<Services> servicesList, String total, String appointmentDate) {
        this.username = username;
        this.servicesList = servicesList;
        this.total = total;
        this.appointmentDate = appointmentDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Services> getServicesList() {
        return servicesList;
    }

    public void setServicesList(ArrayList<Services> servicesList) {
        this.servicesList = servicesList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public ArrayList<HashMap<String,Object>> getStringHashMap() {
        return stringHashMap;
    }

    public void setStringHashMap(ArrayList<HashMap<String,Object>> stringHashMap) {
        this.stringHashMap = stringHashMap;
    }
}
