package com.sheehabe.tourmate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sheehabe.tourmate.Module.MyList;
import com.sheehabe.tourmate.Module.WeatherResult;
import com.sheehabe.tourmate.Module.WeatherResultForcast;
import com.sheehabe.tourmate.Retrofit.OpenWeatherMap;
import com.sheehabe.tourmate.Retrofit.RetrofitClass;
import com.sheehabe.tourmate.databinding.ActivityWeatherBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private ActivityWeatherBinding binding;

    private WeatherResult weatherResult;
    WeatherResultForcast weatherResultForcast;
    private double lat=23.7566001;
    private double lon=90.3894496;
    private String units ="metric";
    private List<MyList> weatherlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding= DataBindingUtil.setContentView( this,R.layout.activity_weather );
        /*binding.weatherRecylerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
        WeatherForcastAdapter weatherForcastAdapter=new WeatherForcastAdapter(this,weatherlist);
        binding.weatherRecylerView.setAdapter(weatherForcastAdapter);*/

        getWeatherInfo();
        getForcastWeatherInfo();
    }




    private void getWeatherInfo() {
        OpenWeatherMap service = RetrofitClass.getRetrofitInstance().create( OpenWeatherMap.class );
        String Url=String.format( "weather?lat=23.7566001&lon=90.3894496&units=metric&appid=c8ec88bd32e4febab50b194549e0d2e6"
                ,lat,lon,units,getResources().getString( R.string.weathe_api_key ) );
        Call<WeatherResult> weather_Response = service.getWeatherResult(Url);
        weather_Response.enqueue( new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.code() == 200) {
                    weatherResult=response.body();



                    binding.windTV.setText( String.valueOf(weatherResult.getWind().getSpeed())+ " hPa" );
                    binding.temparatureTV.setText( String.valueOf(weatherResult.getMain().getTemp()+"℃" ));
                    binding.humidityTV.setText( String.valueOf(weatherResult.getMain().getHumidity())+" %" );

                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                binding.temparatureTV.setText( t.getMessage() );

            }
        } );

    }

    private void getForcastWeatherInfo() {
        OpenWeatherMap service = RetrofitClass.getRetrofitInstance().create( OpenWeatherMap.class );
        String Url=String.format( "weather?lat=23.7566001&lon=90.3894496&units=metric&appid=c8ec88bd32e4febab50b194549e0d2e6"
                ,lat,lon,units,getResources().getString( R.string.weathe_api_key ) );
        Call<WeatherResultForcast> weather_Response_Forcast = service.getWeatherResultForcast(Url);
        weather_Response_Forcast.enqueue( new Callback<WeatherResultForcast>() {
            @Override
            public void onResponse(Call<WeatherResultForcast> call, Response<WeatherResultForcast> response) {
                if (response.code() == 200) {
                    weatherResultForcast=response.body();
                }
            }

            @Override
            public void onFailure(Call<WeatherResultForcast> call, Throwable t) {

            }
        } );

    }
}
