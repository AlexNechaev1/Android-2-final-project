package com.example.android_2_final_project.models;

import java.io.Serializable;

public class PostModel implements Serializable {

    private CarModel car;
    private String sellerId;

    public PostModel() {}

    public PostModel(CarModel car, String sellerId) {
        this.car = car;
        this.sellerId = sellerId;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
