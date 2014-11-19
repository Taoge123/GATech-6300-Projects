package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.CoffeeCartLocation;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CoffeeCartLocation coffeeCartLocation;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.v("Main Activity - onCreated: ", "onCreate Call");
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
                R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (!isConnectedToNetwork()) {
            Toast.makeText(this, "No Network - Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }
        if( mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeFragmentDrawer();
        }
        loadCoffeeCartLocation();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (position == 0) {
            fragmentManager.beginTransaction().add(R.id.container, VIPCustomerManagementFragment.newInstance())
                    .addToBackStack("fragBack").commit();

        } else if (position == 1) {
            fragmentManager.beginTransaction().add(R.id.container, PreOrderFragment.newInstance())
                    .addToBackStack("fragBack").commit();

        } else if (position == 2) {
            fragmentManager.beginTransaction().add(R.id.container, PurchaseFragment.newInstance())
                    .addToBackStack("fragBack").commit();

        } else if (position == 3) {
            fragmentManager.beginTransaction().add(R.id.container, SelectCoffeeCartLocationFragment.newInstance())
                    .addToBackStack("fragBack").commit();

        } else if (position == 4) {
            fragmentManager.beginTransaction().add(R.id.container, GenerateReportFragment.newInstance())
                    .addToBackStack("fragBack").commit();

        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
        case 1:
            mTitle = getString(R.string.title_section1);
            restoreActionBar();
            break;
        case 2:
            mTitle = getString(R.string.title_section2);
            restoreActionBar();
            break;
        case 3:
            mTitle = getString(R.string.title_section3);
            restoreActionBar();
            break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag("fragBack") != null) {

        } else {
            super.onBackPressed();
            return;
        }
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            Fragment frag = getFragmentManager().findFragmentByTag("fragBack");
            getFragmentManager().beginTransaction().remove(frag).commit();
        }

    }

    public Boolean isConnectedToNetwork() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // mobile
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        // wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        return (mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED
                || mobile == NetworkInfo.State.CONNECTING || wifi == NetworkInfo.State.CONNECTING);
    }

    public CoffeeCartLocation getCoffeeCartLocation() {
        return this.coffeeCartLocation;
    }

    public void setCoffeeCartLocation(CoffeeCartLocation location) {
        this.coffeeCartLocation = location;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("coffeeCartLocation", this.coffeeCartLocation.getId());
        editor.commit();
        Toast.makeText(this, "New Location Set To " + this.coffeeCartLocation.getLocation(), Toast.LENGTH_SHORT).show();
    }

    public void loadCoffeeCartLocation() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String location = sharedPref.getString("coffeeCartLocation", null);
        if (location == null) {

            ParseQuery<CoffeeCartLocation> getLoc = new ParseQuery(CoffeeCartLocation.class);
            try {
                this.setCoffeeCartLocation(getLoc.getFirst());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Set Your Coffee Cart Location", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.container, SelectCoffeeCartLocationFragment.newInstance()).commit(); 
            
        } else {

            ParseQuery<CoffeeCartLocation> locationQuery = ParseQuery.getQuery(CoffeeCartLocation.class);
            try {
                this.coffeeCartLocation = locationQuery.get(location);
                Toast.makeText(this, "Current Location: " + this.coffeeCartLocation.getLocation(), Toast.LENGTH_SHORT)
                        .show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @Override public boolean onCreateOptionsMenu(Menu menu) { if (!mNavigationDrawerFragment.isDrawerOpen()) { //
     * Only show items in the action bar relevant to this screen // if the drawer is not showing. Otherwise, let the
     * drawer // decide what to show in the action bar. //getMenuInflater().inflate(R.menu.main, menu);
     * //restoreActionBar(); return true; } return super.onCreateOptionsMenu(menu); }
     */
    /*
     * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle action bar item clicks here. The action
     * bar will // automatically handle clicks on the Home/Up button, so long // as you specify a parent activity in
     * AndroidManifest.xml. int id = item.getItemId(); if (id == R.id.action_settings) { return true; } return
     * super.onOptionsItemSelected(item); }
     */

}
