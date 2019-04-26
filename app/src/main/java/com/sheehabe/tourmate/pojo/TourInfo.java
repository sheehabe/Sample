package com.sheehabe.tourmate.pojo;

public class TourInfo {
    private String tourName, tourLocation, tourBudget, tourStartDate, tourEndDate, tourID;
    private int position;
    public int key;

    public TourInfo() {
    }



    public TourInfo(String tourName, String tourLocation, String tourBudget, String tourStartDate, String tourEndDate) {
        this.tourName = tourName;
        this.tourLocation = tourLocation;
        this.tourBudget = tourBudget;
        this.tourStartDate = tourStartDate;
        this.tourEndDate = tourEndDate;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }




    public String getTourName() {
        return tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public String getTourBudget() {
        return tourBudget;
    }

    public String getTourStartDate() {
        return tourStartDate;
    }

    public String getTourEndDate() {
        return tourEndDate;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }
}
