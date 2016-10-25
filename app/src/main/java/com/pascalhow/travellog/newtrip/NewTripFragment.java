package com.pascalhow.travellog.newtrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascalhow.travellog.HomeActivity;
import com.pascalhow.travellog.R;

import butterknife.ButterKnife;

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
//        homeActivity.getSupportActionBar().setBackgroundDrawable((new ColorDrawable(ContextCompat.getColor(getContext(), R.color.app_background))));

        return rootView;
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
