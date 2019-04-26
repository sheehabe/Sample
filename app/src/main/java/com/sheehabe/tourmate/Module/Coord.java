package com.sheehabe.tourmate.Module;

public class Coord {
    private double lon ;
    private double lat ;

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
//for build model
    @Override
    public String toString() {
        return new StringBuilder( "[" ).append( this.lat ).append( ',' ).append( this.lon ).append( ']' ).toString();
    }
}
