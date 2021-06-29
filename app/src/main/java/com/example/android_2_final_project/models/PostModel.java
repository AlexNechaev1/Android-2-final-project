package com.example.android_2_final_project.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class PostModel implements Serializable {

    private CarModel car;
    private String sellerId;
    private String id;

    public PostModel() {
    }

    public PostModel(CarModel car, String sellerId, String id) {
        this.car = car;
        this.sellerId = sellerId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof PostModel)) return false;

        PostModel post = (PostModel) obj;

        return this.id.equals(post.id);
    }
}
