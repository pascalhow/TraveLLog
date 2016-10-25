package com.pascalhow.travellog;

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
        fab.setOnClickListener(view -> loadFragment(new NewTripFragment(), FRAGMENT_NEW_TRIP));

        getSupportFragmentManager().addOnBackStackChangedListener(
                () -> {
                    // Update your UI here.
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_content);
                    if (f != null) {
                        updateFragmentTitle(f);
                    }
                });

        loadFragment(new TravelMapFragment(), FRAGMENT_TRAVEL_MAP);
    }

    private void updateFragmentTitle(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(TravelMapFragment.class.getName())) {
            setTitle(getResources().getString(R.string.my_trips_fragment_title));
            showFloatingActionButton();
        } else if (fragClassName.equals(NewTripFragment.class.getName())) {
            setTitle(getResources().getString(R.string.new_trip_title));
        }
    }

    public void loadFragment(android.support.v4.app.Fragment fragment, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (tag) {

            //  Travel map fragment is the first fragment to be displayed so we don't addToBackStack()
            case FRAGMENT_TRAVEL_MAP:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment, tag)
                        .commit();
                break;

            case FRAGMENT_NEW_TRIP:
                fragmentManager.beginTransaction()
                        .add(R.id.main_content, fragment, FRAGMENT_NEW_TRIP)
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFloatingActionButton() {
        fab.show();
    }

    ;

    public void hideFloatingActionButton() {
        fab.hide();
    }

    ;
}
