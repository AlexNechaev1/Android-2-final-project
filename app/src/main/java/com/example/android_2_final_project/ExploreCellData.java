package com.example.android_2_final_project;


public class ExploreCellData {

    private String mTitle;
    private String mFirstDescription;
    private String mSecondDescription;

    public ExploreCellData(String mTitle, String mFirstDescription, String mSecondDescription) {
        this.mTitle = mTitle;
        this.mFirstDescription = mFirstDescription;
        this.mSecondDescription = mSecondDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getFirstDescription() {
        return mFirstDescription;
    }

    public String getSecondDescription() {
        return mSecondDescription;
    }
}
