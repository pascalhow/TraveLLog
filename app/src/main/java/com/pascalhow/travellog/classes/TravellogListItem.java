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

    public TravellogListItem(String title, String description, String imagePath) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }
}
