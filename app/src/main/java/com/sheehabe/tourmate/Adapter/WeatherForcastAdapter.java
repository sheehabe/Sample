package com.sheehabe.tourmate.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sheehabe.tourmate.Common.Common;
import com.sheehabe.tourmate.Module.MyList;
import com.sheehabe.tourmate.Module.WeatherResult;
import com.sheehabe.tourmate.Module.WeatherResultForcast;
import com.sheehabe.tourmate.databinding.TourItemViewBinding;
import com.sheehabe.tourmate.databinding.WeatherItemViewBinding;

import java.util.List;

public class WeatherForcastAdapter extends RecyclerView.Adapter<WeatherForcastAdapter.ViewHolder> {
    private TourItemViewBinding binding;
    private Context context;
    private WeatherResult weatherResult;
    private WeatherResultForcast weatherResultForcast;
    private List<MyList> weatherlist;

    public WeatherForcastAdapter(Context context, List<MyList> weatherlist) {
        this.context = context;
        this.weatherlist = weatherlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from( viewGroup.getContext() );
        WeatherItemViewBinding weatherItemViewBinding = WeatherItemViewBinding.inflate( layoutInflater,viewGroup,false ) ;

        return new ViewHolder( weatherItemViewBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyList CurrentUser= weatherlist.get( i );
        viewHolder.bidning.wdateTV.setText(new StringBuilder( Common.convertUnixTimeToDate( weatherResultForcast.list.get( i ).dt ) ));
        viewHolder.bidning.fhumidity.setText(new StringBuilder(String.valueOf(weatherResultForcast.list.get( i ).main.getHumidity())));
        viewHolder.bidning.ftemp.setText(new StringBuilder(String.valueOf(weatherResultForcast.list.get( i ).main.getTemp())));

    }

    @Override
    public int getItemCount() {
        return weatherlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private WeatherItemViewBinding bidning;
        public ViewHolder(WeatherItemViewBinding weatherItemViewBinding) {
            super(weatherItemViewBinding.getRoot());
            bidning=weatherItemViewBinding;
        }
    }
}
