package com.pascalhow.travellog.newtrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pascalhow.travellog.HomeActivity;
import com.pascalhow.travellog.R;
import com.pascalhow.travellog.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pascal on 24/10/2016.
 */

public class NewTripFragment extends Fragment {

    private HomeActivity homeActivity;



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
}
