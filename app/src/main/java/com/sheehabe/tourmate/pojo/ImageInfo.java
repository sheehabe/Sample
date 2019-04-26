package com.sheehabe.tourmate.pojo;

import com.sheehabe.tourmate.Memory.ItemsActivity;

import java.util.List;

public class ImageInfo {
    public String imageName,location, imageID;
    public int position,key;


    public String imageURL;

    public ImageInfo() {

    }


    public ImageInfo(ItemsActivity itemsActivity, List<ImageInfo> imagelist) {
    }

    public ImageInfo(String imageName, String location, String imageURL) {
        this.imageName = imageName;
        this.location = location;
        this.imageURL = imageURL;
    }

    public ImageInfo(String imageName, String imageID, String location, String imageURL) {
        this.imageName = imageName;
        this.imageID = imageID;
        this.location = location;
        this.imageURL = imageURL;
    }

    public ImageInfo(String imageName, String imageURL) {
        this.imageName = imageName;
        this.imageURL = imageURL;
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


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



}
