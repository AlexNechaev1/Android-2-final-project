package com.example.android_2_final_project.models;

import java.io.Serializable;

public class CarModel implements Serializable {

    private String carModel;
    private String description;
    private String imagePath;
    private int manufactureYear;

    public CarModel() {
    }

    public CarModel(String carModel, String description, String imagePath, int manufactureYear) {
        this.carModel = carModel;
        this.description = description;
        this.imagePath = imagePath;
        this.manufactureYear = manufactureYear;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }
}
