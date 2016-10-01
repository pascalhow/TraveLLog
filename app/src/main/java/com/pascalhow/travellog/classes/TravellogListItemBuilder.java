package com.pascalhow.travellog.classes;

/**
 * Created by pascal on 01/10/2016.
 */

public class TravellogListItemBuilder {

    private String title = "";
    private String description = "";
    private String imagePath = "";
    private int type = -1;

    public TravellogListItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TravellogListItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TravellogListItemBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public TravellogListItemBuilder setType(int type) {
        this.type = type;
        return this;
    }

    /**
     * Build the Travellog List Item object
     * @return TravellogListItem object
     */
    public TravellogListItem build() {
        return new TravellogListItem(this.title, this.description, this.imagePath, this.type);
    }
}
