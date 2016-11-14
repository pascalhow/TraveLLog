package com.pascalhow.travellog.newtrip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pascalhow.travellog.HomeActivity;
import com.pascalhow.travellog.R;
import com.pascalhow.travellog.R2;
import com.pascalhow.travellog.utils.ImageHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pascal on 24/10/2016.
 * Reference: https://github.com/hanscappelle/SO-2169649
 */

public class NewTripFragment extends Fragment {

    private HomeActivity homeActivity;

    private static final int SELECT_SINGLE_PICTURE = 101;
    private static final int SELECT_MULTIPLE_PICTURE = 201;

    public static final String IMAGE_TYPE = "image/*";

    @BindView(R2.id.new_trip_cover_photo)
    ImageView coverPhoto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_trip, container, false);

        ButterKnife.bind(this, rootView);

        homeActivity = (HomeActivity) getActivity();
        homeActivity.setTitle(R.string.new_trip_title);
        homeActivity.hideFloatingActionButton();

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_save);
        item.setVisible(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                //  Save new trip
                Toast.makeText(getContext(), "Trip saved", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R2.id.new_trip_add_cover_photo)
    public void addCoverPhoto() {
        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.new_trip_select_cover_photo_title)), SELECT_SINGLE_PICTURE);

        Toast.makeText(getContext(), "Add cover photo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.hideFloatingActionButton();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                ImageHelper.setImage(getContext(), coverPhoto, selectedImageUri);
            } else if (requestCode == SELECT_MULTIPLE_PICTURE) {
                //And in the Result handling check for that parameter:
                if (Intent.ACTION_SEND_MULTIPLE.equals(data.getAction())
                        && data.hasExtra(Intent.EXTRA_STREAM)) {
                    // retrieve a collection of selected images
                    ArrayList<Parcelable> list = data.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                    // iterate over these images
                    if (list != null) {
                        for (Parcelable parcel : list) {
                            Uri uri = (Uri) parcel;
                            // handle the images one by one here
                        }
                    }

                    // for now just show the last picture
                    if (!list.isEmpty()) {
                        Uri imageUri = (Uri) list.get(list.size() - 1);

                        ImageHelper.setImage(getContext(), coverPhoto, imageUri);
                    }
                }
            }
        } else {
            // report failure
            Toast.makeText(getContext(), R.string.new_trip_msg_failed_to_get_picture, Toast.LENGTH_LONG).show();
            Log.d(HomeActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }
}
