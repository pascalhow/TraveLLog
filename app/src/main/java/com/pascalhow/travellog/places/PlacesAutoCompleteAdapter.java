package com.pascalhow.travellog.places;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by pascal on 24/11/2016.
 */


public class PlacesAutoCompleteAdapter
        extends ArrayAdapter<PlaceAutoComplete> implements Filterable {

    private static final String TAG = "PlaceAutoComplete";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    /**
     * Current results returned by this adapter.
     */
    private ArrayList<PlaceAutoComplete> resultList;

    /**
     * Handles autocomplete requests.
     */
    private GoogleApiClient googleApiClient;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private LatLngBounds bounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private AutocompleteFilter placeFilter;

    /**
     * Initializes with a resource for text rows and autocomplete query bounds.
     *
     * @see ArrayAdapter#ArrayAdapter(Context, int)
     */
    public PlacesAutoCompleteAdapter(Context context, int resource, GoogleApiClient googleApiClient,
                                     LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, resource);
        this.googleApiClient = googleApiClient;
        this.bounds = bounds;
        placeFilter = filter;
    }

    /**
     * Sets the bounds for all subsequent queries.
     */
    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return resultList.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public PlaceAutoComplete getItem(int position) {
        return resultList.get(position);
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    resultList = getAutocomplete(constraint);
                    if (resultList != null) {
                        // The API successfully returned results.
                        results.values = resultList;
                        results.count = resultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private ArrayList<PlaceAutoComplete> getAutocomplete(CharSequence constraint) {
        if (googleApiClient.isConnected()) {
            Log.i(TAG, "Starting autocomplete query for: " + constraint);

            // Submit the query to the autocomplete API and retrieve a PendingResult that will
            // contain the results when the query completes.
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(googleApiClient, constraint.toString(),
                                    bounds, placeFilter);

            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);

            // Confirm that the query completed successfully, otherwise return null
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Toast.makeText(getContext(), "Error contacting API: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");

            // Copy the results into our own data structure, because we can't hold onto the buffer.
            // AutocompletePrediction objects encapsulate the API response (place ID and description).
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                // Get the details of this prediction and copy it into a new PlaceAutoComplete object.
                resultList.add(new PlaceAutoComplete(prediction.getPlaceId(),
                        prediction.getFullText(STYLE_BOLD)));
            }

            // Release the buffer now that all data has been copied.
            autocompletePredictions.release();

            return resultList;
        }
        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }
}