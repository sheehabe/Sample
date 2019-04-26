package com.sheehabe.tourmate.Retrofit;

import com.sheehabe.tourmate.Module.WeatherResult;
import com.sheehabe.tourmate.Module.WeatherResultForcast;
//import com.sheehabe.weather.Module.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface OpenWeatherMap {
    @GET()
    Call<WeatherResult> getWeatherResult(@Url String Url);

    @GET()
    Call<WeatherResultForcast> getWeatherResultForcast(@Url String Url);

}
