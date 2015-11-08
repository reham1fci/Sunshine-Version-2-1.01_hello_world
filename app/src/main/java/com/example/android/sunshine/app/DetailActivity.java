package com.example.android.sunshine.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.weather_detail_container, fragment
                    )
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

            return super.onOptionsItemSelected(item);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
  /*  public static class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
        private static final String LOG_TAG=DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASHTAG="#SunshineAPP";
        String forecast;

          private ShareActionProvider mShareActionProvider;
          private String mForecast;
         private static final int DETAIL_LOADER = 0;

        private static final String[] FORECAST_COLUMNS = {
                WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
                WeatherContract. WeatherEntry.COLUMN_DATE,
                             WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
                              WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
                              WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
                WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
                WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
                WeatherContract.WeatherEntry.COLUMN_PRESSURE,

        };

                     // these constants correspond to the projection defined above, and must change if the
                    // projection changes
        private static final int COL_WEATHER_ID = 0;
        private static final int COL_WEATHER_DATE = 1;
        private static final int COL_WEATHER_DESC = 2;
        private static final int COL_WEATHER_MAX_TEMP = 3;
        private static final int COL_WEATHER_MIN_TEMP = 4;
        private static final int COL_WEATHER_HUMIDITY = 5;
        private static final int COL_WEATHER_WIND = 6;
        private static final int COL_WEATHER_PRESSURE = 7;




        public DetailFragment() {
                setHasOptionsMenu(true);

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent =getActivity().getIntent();
            if (intent != null) {
                forecast = intent.getDataString();
            }
            if(forecast!=null) {
                TextView forecastDetail = (TextView) rootView.findViewById(R.id.forecastDetail_tv);
                forecastDetail.setText(forecast);
            }
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.detailfragment,menu);
           MenuItem menuItem = menu.findItem(R.id.menu_item_share);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            //ShareActionProvider mShareActionProvider =
            //  (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if (mForecast != null ) {
                 mShareActionProvider.setShareIntent(createShareForecastIntent());
                       } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareForecastIntent() {
                   Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                   shareIntent.setType("text/plain");
                  shareIntent.putExtra(Intent.EXTRA_TEXT,
                           mForecast + FORECAST_SHARE_HASHTAG);
                    return shareIntent;
                }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Intent intent=getActivity().getIntent();
            if(intent==null){
                return null;
            }
            return new CursorLoader(getActivity(),intent.getData(),FORECAST_COLUMNS,null,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
          if(!data.moveToFirst()){return;}
            String dateString = Utility.formatDate( data.getLong(COL_WEATHER_DATE));
            String weatherDescription = data.getString(COL_WEATHER_DESC);
            boolean isMetric = Utility.isMetric(getActivity());
            String high = Utility.formatTemperature(getActivity(),data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);
            String low = Utility.formatTemperature( getActivity(),data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);
            String humidity=data.getString(COL_WEATHER_HUMIDITY);
            String wind=Utility.getFormattedWind(getActivity(),data.getFloat(COL_WEATHER_WIND),);
            String pressure =data.getString(COL_WEATHER_PRESSURE);
            mForecast = String.format("%s - %s - %s/%s", dateString, weatherDescription, high, low);
            TextView detailTextView = (TextView)getView().findViewById(R.id.forecastDetail_tv);
             detailTextView.setText(mForecast);
             // If onCreateOptionsMenu has already happened, we need to update the share intent now.
                           if (mShareActionProvider != null) {
                       mShareActionProvider.setShareIntent(createShareForecastIntent());
                    }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }*/
}