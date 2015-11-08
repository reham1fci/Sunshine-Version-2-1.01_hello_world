package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements ForecastFragment.Callback{
    private String mLocation;
    boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = Utility.getPreferredLocation(this);
        setContentView(R.layout.activity_main);
       if(findViewById(R.id.weather_detail_container)!=null){
           mTwoPane=true;
           if(savedInstanceState == null){
               getSupportFragmentManager().beginTransaction()
               .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                       .commit();}
       }
        else{
           mTwoPane=false;
           getSupportActionBar().setElevation(0f);

       }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_forecast);
            if (null != ff) {
                ff.onLocationChanged();
            }
            DetailFragment df = (DetailFragment) getSupportFragmentManager()
                    .findFragmentByTag(DETAILFRAGMENT_TAG);
            if (null != df) {
                df.onLocationChanged(location);
            }

            mLocation = location;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in
        //
        //
        //
        //
        //AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection Simplif.iableIfStatement
        if (id == R.id.action_settings) {
            Intent intent= new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.preferred_location) {

            openPreferredLocationOnMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void openPreferredLocationOnMap(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String location=  prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        Uri uri=Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Log.d("couldn't call","activity to show");
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }

    }


    /**
     * A placeholder fragment containing a simple view.
     */


