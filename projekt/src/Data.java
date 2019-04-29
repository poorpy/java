package com.company;

public class Data {
    public String name;
    public int id;
    public Coords coords;
    public float temp;

    Data(String name, int id,float lon, float lat, float temp) {
        this.name = name;
        this.id = id;
        this.coords = new Coords(lon, lat);
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Name: " + name + '\n' + "Id: " + id + '\n' + coords.toString() + '\n' + "Temp: " + temp;
    }

}

class Coords {
    public float lon = 0;
    public float lat = 0;

    Coords(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return  "Lon: " + lon + '\n' + "Lat: " + lat;
    }
}
