package com.example.android.sunshine.app;

/**
 * Created by thy on 08/09/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {
    private  final int VIEW_TYPE_TODAY=0;
    private  final int VIEW_TYPE_FUTURE_DAY=1;
    private  final int VIEW_TYPE_COUNT=2;
    String CLEAR="clear";
    String CLOUDS="clouds";
    String FOG="fog";
    String RAIN="rain";
    String STORM="storm";
    String SNOW="snow";
    String CLOUDY="cloudy";


    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        boolean isMetric = Utility.isMetric(mContext);
        String highLowStr = Utility.formatTemperature(mContext,high, isMetric) + "/" + Utility.formatTemperature(mContext,low, isMetric);
        return highLowStr;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return VIEW_TYPE_TODAY;
        }
       else{
            return VIEW_TYPE_FUTURE_DAY;

        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /*
            This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
            string.
         */
    private String convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor
       // int idx_max_temp = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP);
       // int idx_min_temp = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP);
       // int idx_date = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE);
        //int idx_short_desc = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC);

        String highAndLow = formatHighLows(
                cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
                cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));

        return Utility.formatDate(cursor.getLong(ForecastFragment.COL_WEATHER_DATE) )+
                " - " + cursor.getString(ForecastFragment.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType= getItemViewType(cursor.getPosition());
        int  LayoutId=-1;
        if(viewType==VIEW_TYPE_TODAY){
            LayoutId=R.layout.list_item_forecast_today;
        }else if(viewType==VIEW_TYPE_FUTURE_DAY){
            LayoutId=R.layout.list_item_forecast;
        }
        View view = LayoutInflater.from(context).inflate(LayoutId, parent, false);
       ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

       // TextView tv = (TextView)view.findViewById(R.id.list_item_forecast_textview);
       // tv.setText(convertCursorRowToUXFormat(cursor));
        // icon
        int weatherId= cursor.getInt(ForecastFragment.COL_WEATHER_ID);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());
              switch (viewType) {
                       case VIEW_TYPE_TODAY: {
                             // Get weather icon
                                       viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
                                                      cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                               break;
                           }
                        case VIEW_TYPE_FUTURE_DAY: {
                               // Get weather icon
                                       viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
                                                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                              break;
                          }
                  }
       //ImageView iconView=(ImageView)view.findViewById(R.id.list_item_icon);
       // iconView.setImageResource(R.drawable.ic_launcher);
      //  viewHolder.iconView.setImageResource(R.drawable.ic_launcher);
        //  date
        long date=cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        //TextView dateView=(TextView)view.findViewById(R.id.list_item_date_textview);
       // dateView.setText(Utility.getFriendlyDayString(context,date));
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context,date));
        //weather description
        String description=cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        //  TextView descView=(TextView)view.findViewById(R.id.list_item_forecast_textview);
      //  descView.setText(weatherDesc);
        viewHolder.descriptionView.setText(description);
        // weather high
        // For accessibility, add a content description to the icon field
          viewHolder.iconView.setContentDescription(description);

      boolean isMetric = Utility.isMetric(context);
        Double high=cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        //TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
       // highView.setText(Utility.formatTemperature(high, isMetric));
        viewHolder.highTempView.setText(Utility.formatTemperature(context,high, isMetric));
        // weather LOW

        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
          // TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
          // lowView.setText(Utility.formatTemperature(low, isMetric));
        viewHolder.lowTempView.setText(Utility.formatTemperature(context,low, isMetric));

    }
    //     VIEW HOLDER CLASS
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }
}
