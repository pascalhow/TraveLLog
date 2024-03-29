package com.pascalhow.travellog.map;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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

public class TravelMapFragment extends Fragment{

    protected GoogleMap map;
    MapView mapView;

    private static final float MAP_MARKER_ZOOM_LEVEL = 15.5f;
    private boolean mapIsReady, mapIsRendered;
    private List<LatLng> coordinates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(mMap -> {
            map = mMap;

            MapStartLocation londonStartLocation = new MapStartLocation(51.508620, -0.126172);
            animateToLatLng(londonStartLocation);

            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return;
            // For showing a move to my location button
            map.setMyLocationEnabled(true);

            //  TODO: Restore that once we have set app permissions
//            animateToLatLng(londonStartLocation);
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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

    private void animateToLatLng(MapStartLocation location) {
        // For dropping a marker at a point on the Map
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(position).title("London").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
