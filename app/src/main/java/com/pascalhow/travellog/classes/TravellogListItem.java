package com.pascalhow.travellog.classes;

/**
 * Created by pascal on 01/10/2016.
 */

public class TravellogListItem {
    public static final int ITEM_TYPE_ALBUM = 100;
    public static final int ITEM_TYPE_TIMELINE = 101;
    public static final int ITEM_TYPE_IMAGE = 102;

    private String title;
    private String description;
    private String imagePath;
    private int type;

    public TravellogListItem(String title, String description, String imagePath, int type) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {

        String s = "Title: " + this.title;
        s += "Description: " + this.description;
        s += "Image Path: " + this.imagePath;
        s += "Type: " + String.valueOf(this.type);

        return s;
    }
}
