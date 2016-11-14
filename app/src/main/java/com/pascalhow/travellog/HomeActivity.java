package com.pascalhow.travellog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pascalhow.travellog.map.TravelMapFragment;
import com.pascalhow.travellog.newtrip.NewTripFragment;

public class HomeActivity extends AppCompatActivity {

    private static final String FRAGMENT_TRAVEL_MAP = "travel_map";
    private static final String FRAGMENT_NEW_TRIP = "new_trip";

    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.addNewTrip);

        setOnBackStackListener();

        loadFragment(new TravelMapFragment(), FRAGMENT_TRAVEL_MAP);
    }

    /**
     * Handle screen display when navigating between fragment
     * @param fragment The current fragment
     */
    private void updateFragmentTitle(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(TravelMapFragment.class.getName())) {
            setTitle(getResources().getString(R.string.my_trips_fragment_title));
            showFloatingActionButton();
        } else if (fragClassName.equals(NewTripFragment.class.getName())) {
            setTitle(getResources().getString(R.string.new_trip_title));
        }
    }

    /**
     * Replaces or adds a new fragment on top of the current fragment
     * @param fragment The new fragment
     * @param tag A tag relating to the new fragment
     */
    public void loadFragment(android.support.v4.app.Fragment fragment, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (tag) {

            //  Travel map fragment is the first fragment to be displayed so we don't addToBackStack()
            case FRAGMENT_TRAVEL_MAP:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment, tag)
                        .commit();

                fab.setOnClickListener(view -> loadFragment(new NewTripFragment(), FRAGMENT_NEW_TRIP));
                break;

            case FRAGMENT_NEW_TRIP:
                fragmentManager.beginTransaction()
                        .add(R.id.main_content, fragment, tag)
                        .addToBackStack(FRAGMENT_NEW_TRIP)
                        .commitAllowingStateLoss();
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void showFloatingActionButton() {
        fab.show();
    }

    public void hideFloatingActionButton() {
        fab.hide();
    }

    /**
     * Handles backStackListener when user navigates between fragments
     */
    private void setOnBackStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(
                () -> {
                    // Update your UI here.
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
                    if (fragment != null) {
                        updateFragmentTitle(fragment);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
