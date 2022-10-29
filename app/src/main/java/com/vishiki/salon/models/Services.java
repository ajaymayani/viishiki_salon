package com.vishiki.salon.models;

public class Services {
    private String serviceName;
    private int servicePrice;
    private int position;

    public Services() {
    }

    public Services(String serviceName, int servicePrice, int position) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.position = position;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
