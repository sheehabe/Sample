
package com.sheehabe.tourmate.Module;

import java.util.List;

public class WeatherResultForcast {

    public String cod ;
    public double message ;
    public int cnt ;
    public List<MyList> list; ;
    public City city;

    public WeatherResultForcast() {
    }

    public WeatherResultForcast(String cod, double message, int cnt, City city) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
