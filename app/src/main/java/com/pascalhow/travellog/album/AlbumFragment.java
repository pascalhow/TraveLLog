package com.pascalhow.travellog.album;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascalhow.travellog.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragment extends Fragment {

    public AlbumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_map, container, false);
    }
}
