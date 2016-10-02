package com.pascalhow.travellog.map;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pascalhow.travellog.R;

import java.util.List;

import timber.log.Timber;

/**
 * Created by pascal on 01/10/2016.
 */

public class TravelMapFragment extends SupportMapFragment {

    protected GoogleMap map;

    private static final float MAP_MARKER_ZOOM_LEVEL = 15.5f;
    private boolean mapIsReady, mapIsRendered;
    private List<LatLng> coordinates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_trip_map,
                container, false);

        View mapView = super.onCreateView(inflater, container, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(map == null) {
            map = getMap();
        } else {
            //  Get current location and display a blue dot
            map.setMyLocationEnabled(true);
        }

        final View viewfinalMapView = mapView;
        if (viewfinalMapView != null) {
            viewfinalMapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @SuppressLint("NewApi")
                @Override
                public void onGlobalLayout() {
                    mapIsReady = true;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        viewfinalMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        viewfinalMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    renderMap();

                }
            });
        }

        layout.addView(mapView, 0);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void renderMap() {

        // need to deal with multiple calls to this method

        if (!mapIsReady || mapIsRendered || map == null) {
            return;
        }

        map.clear();

//        PolylineOptions polylineOptions = TripHelper.getPolylineOptionsForLocations(
//                trip.getTripLocations(),
//                ContextCompat.getColor(getContext(), R.color.map_path_color),
//                getResources().getInteger(R.integer.map_path_weight));
//        if (polylineOptions != null) {
//            coordinates = polylineOptions.getPoints();
//        }

        if (coordinates == null || coordinates.isEmpty()) {
//            Timber.e("renderMap", getActivity(), "No coordinates found to render map");
        } else {
            Builder bounds = new LatLngBounds.Builder();

            for (LatLng pos : coordinates) {
                bounds.include(pos);
            }
            LatLngBounds latLngBounds = bounds.build();
//            map.addPolyline(polylineOptions);
            updateMapEventMarkers();

//            if (userSelectedEnhancedMessage != null) {
//                LatLngBounds boundaries = TripHelper.getBoundaries(userSelectedEnhancedMessage);
//                animateToLatLngBounds(boundaries);
//            } else if (userSelectedMessage != null) {
//                animateToLatLng(new LatLng(userSelectedMessage.getLatitude(), userSelectedMessage.getLongitude()));
//            } else {
//                animateToLatLngBounds(latLngBounds);
//            }

            mapIsRendered = true;
        }
    }

    private void animateToLatLng(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, MAP_MARKER_ZOOM_LEVEL);
        map.animateCamera(cameraUpdate);
    }

    private void animateToLatLngBounds(LatLngBounds latLngBounds) {

        int mapBoundaryPx = Math.round(getResources().getDimension(R.dimen.map_boundary));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, mapBoundaryPx);
        try {
            map.animateCamera(cameraUpdate);
        } catch (IllegalStateException ignored) {
        }
    }

    private void updateMapEventMarkers() {
        // Add the messages as markers

        if (coordinates != null && coordinates.size() > 0) {

            MarkerOptions routeStart = new MarkerOptions();
            routeStart.position(coordinates.get(0));
//            routeStart.title(getString(R.string.mapStartIconText));
//            routeStart.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_mapicon));
            map.addMarker(routeStart);

            MarkerOptions routeEnd = new MarkerOptions();
            routeEnd.position(coordinates.get(coordinates.size() - 1));
//            routeEnd.title(getString(R.string.mapEndIconText));
//            routeEnd.icon(BitmapDescriptorFactory.fromResource(R.drawable.stop_mapicon));
            map.addMarker(routeEnd);
        }

//        if (TripHelper.hasTripEvent(getContext(), trip)) {
//            addEnhancedTripMessageMarkersToMap();
//        } else {
//            addTripMessageMarkersToMap();
//        }
    }
}
