package com.pascalhow.travellog.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pascalhow.travellog.R;
import com.pascalhow.travellog.classes.TravellogListItem;

import java.util.ArrayList;

/**
 * Created by pascal on 01/10/2016.
 */

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.ViewHolder> {

    private final String JPG_exifTag_imageDescription = "UserComment";
    private final String tag_selectedImage = "SelectedImage";

    private ArrayList<TravellogListItem> itemList;
    private Context context;
    private Activity activity;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        int itemType;
        CardView cardView;
        TextView title;
        ImageView image;
        TextView imageDescription;

        public ViewHolder(final View itemView, int ViewType) {
            super(itemView);
            this.itemType = ViewType;
//            cardView = (CardView) itemView.findViewById(R.id.gallery_adapter_item_titleallery_cardView);
//            title = (TextView) itemView.findViewById(R.id.gallery_adapter_item_title);
//            image = (ImageView) itemView.findViewById(R.id.gallery_adapter_item_image);
//            imageDescription = (TextView) itemView.findViewById(R.id.gallery_adapter_item_image_description);
        }
    }

    public MultiAdapter(Context context) {
        this.itemList = new ArrayList<>();
        this.context = context;
    }

    public void setItemList(ArrayList<TravellogListItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public MultiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;

//        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrips_adapter_item, parent, false);
        return new ViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(final MultiAdapter.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
