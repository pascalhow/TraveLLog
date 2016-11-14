package com.pascalhow.travellog.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pascal on 14/11/2016.
 */

public class ImageHelper {

    private static final int IMAGE_HEIGHT = 250;
    private static final int IMAGE_WIDTH = 500;
    /**
     * Calls the Picasso library
     * @param context       Context
     * @param imageView     The ImageView to display
     * @param uri     The image file path
     */
    public static void setImage(Context context, ImageView imageView, Uri uri) {

        //  Handle the image with the Picasso library
        Picasso.with(context)
                .load(uri)
//                .centerInside()
                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                .centerCrop()
//                .onlyScaleDown()
                .into(imageView);
    }
}
