package com.example.android.sunshine.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thy on 04/08/2015.
 */
public class adaptr extends RecyclerView.Adapter<adaptr.forecastViewHolder> {
    List<String>forecastList;

    public adaptr(List<String> forecastList) {
        this.forecastList = forecastList;
    }

    @Override
    public adaptr.forecastViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        forecastViewHolder vh=new forecastViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(adaptr.forecastViewHolder forecastViewHolder, int i) {
     forecastViewHolder.tv.setText(forecastList.get(i));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
    public static class forecastViewHolder extends RecyclerView.ViewHolder{
          CardView cv;
          TextView tv;
        public forecastViewHolder(View itemView) {
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.cv);
            tv=(TextView)itemView.findViewById(R.id.forecast_day);

        }
    }
}
