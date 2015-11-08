package com.example.android.sunshine.app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sunshine.app.data.WeatherContract;
import com.example.android.sunshine.app.service.SunshineService;

/**
 * Created by thy on 11/08/2015.
 */
public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
     //ArrayAdapter<String> mforecast;
    ForecastAdapter mforecast;
     ListView myListView;
     private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private static final int FORECAST_LOADER = 0;
    private static final String[] FORECAST_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_COORD_LAT,
            WeatherContract.LocationEntry.COLUMN_COORD_LONG
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;
    static final int COL_LOCATION_SETTING = 5;
    static final int COL_WEATHER_CONDITION_ID = 6;
    static final int COL_COORD_LAT = 7;
    static final int COL_COORD_LONG = 8;
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }
    public ForecastFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        mforecast = new ForecastAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        myListView=(ListView)rootView.findViewById(R.id.listview_forecast);
        myListView.setAdapter(mforecast);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    String locationSetting = Utility.getPreferredLocation(getActivity());
                    ((Callback) getActivity())
                         .onItemSelected(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
                            ));
                    mPosition = position;
                }

            }
        });
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
                 // The listview probably hasn't even been populated yet.  Actually perform the
                         // swapout in onLoadFinished.
                           mPosition = savedInstanceState.getInt(SELECTED_KEY);
             }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
                     outState.putInt(SELECTED_KEY, mPosition);
                  }
              super.onSaveInstanceState(outState);    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.  forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.action_refresh){
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateWeather(){
      /*  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location=  prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mforecast);*/
       // FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity());
        String location = Utility.getPreferredLocation(getActivity());
        Intent intent = new Intent(getActivity(), SunshineService.class);
              intent.putExtra(SunshineService.LOCATION_QUERY_EXTRA,
               location);
            getActivity().startService(intent);
      //  weatherTask.execute(location);
       /* Intent intent=new Intent(getActivity(), SunshineService.class);
        intent.putExtra(SunshineService.LOCATION_QUERY_EXTRA,location);
        getActivity().startService(intent);*/
      /*  Intent alarmIntent = new Intent(getActivity(),SunshineService.AlarmReceive.class);
        alarmIntent.putExtra(SunshineService.LOCATION_QUERY_EXTRA,location);

                //Wrap in a pending intent which only fires once.
        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);//getBroadcast(context, 0, i, 0);

        AlarmManager am=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

                  //Set the AlarmManager to wake up the system.
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);*/
    }
  /*  @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }*/

   /* public class FetchWeatherTask extends AsyncTask<String,Void,String[] >{
    final String LOG_TAG=FetchWeatherTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[]forecast=new String[7];

// Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format="json";
            String units="metric";
            int days=7;
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                String barse_url = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                String query_params="q";
                String format_params="mode";
                String units_params="units";
                String days_params="cnt";
                Uri buildUri=Uri.parse(barse_url).buildUpon()
                        .appendQueryParameter(query_params,params[0])
                        .appendQueryParameter(format_params,format)
                        .appendQueryParameter(units_params,units)
                        .appendQueryParameter(days_params,Integer.toString(days)).build();
                URL url=new URL(buildUri.toString());
                Log.v("url",buildUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            try {
                forecast= getWeatherDataFromJson(forecastJsonStr,7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;
        }

        @Override
        protected void onPostExecute(String[] strings) {
           // super.onPostExecute(strings);
            if( strings!=null){
                mforecast.clear();
               for(String forecastString:strings){
                   mforecast.add(forecastString);
               }

            }
           // mforecast=new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast,R.id.list_item_forecast_textview,strings);
           // myListView.setAdapter(mforecast);
        }


        /* The date/time conversion code is going to be moved outside the asynctask later,
              * so for convenience we're breaking it out into its own method now.
              */
     /*   private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
      /*  private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String units=  prefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_metric));
            if(units.equals(getString(R.string.pref_units_imperial))){
                high=(high*1.8)+32;
                low=(low*1.8)+32;}
                else if(!units.equals(getString(R.string.pref_units_metric))){
                Log.d("unit","undefine_units");

            }
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String locationSetting = Utility.getPreferredLocation(getActivity());
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

     return new CursorLoader(getActivity(),weatherForLocationUri,
             FORECAST_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
               mforecast.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
                     // If we don't need to restart the loader, and there's a desired position to restore
                           // to, do so now.
                                    myListView.smoothScrollToPosition(mPosition);
                  }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mforecast.swapCursor(null);

    }

    /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
      /*  private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

              Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();


        /*    String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);
                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;

        }

    }*/
    void onLocationChanged( ) {
            updateWeather();
         getLoaderManager().restartLoader(FORECAST_LOADER, null, this);
          }

}
