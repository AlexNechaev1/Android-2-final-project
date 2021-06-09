package com.example.android_2_final_project.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {

    @SerializedName("image_path")
    private String mImagePath;

    @SerializedName("car_model")
    private String mCarModel;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("manufacture_year")
    private int mManufactureYear;

    public Car(String mImagePath, String mCarModel, String mDescription, int mManufactureYear) {
        this.mImagePath = mImagePath;
        this.mCarModel = mCarModel;
        this.mDescription = mDescription;
        this.mManufactureYear = mManufactureYear;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        this.mImagePath = mImagePath;
    }

    public String getCarModel() {
        return mCarModel;
    }

    public void setCarModel(String carModel) {
        this.mCarModel = mCarModel;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = mDescription;
    }

    public int getManufactureYear() {
        return mManufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.mManufactureYear = mManufactureYear;
    }
}
