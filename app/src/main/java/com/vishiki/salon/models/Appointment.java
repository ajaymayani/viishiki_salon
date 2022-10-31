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
    private boolean isCompleted;
    private String name;
    private String phoneNumber;

    public Appointment() {
    }

    public Appointment(String username, ArrayList<Services> servicesList, String total, String appointmentDate,boolean isCompleted,String name,String phoneNumber) {
        this.username = username;
        this.servicesList = servicesList;
        this.total = total;
        this.appointmentDate = appointmentDate;
        this.isCompleted = isCompleted;
        this.name = name;
        this.phoneNumber= phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
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
