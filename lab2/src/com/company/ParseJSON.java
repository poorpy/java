package com.company;

import  org.json.*;

public class ParseJSON {
    public static Data parse(String str) {
        JSONObject obj = new JSONObject(str);
        String name = obj.getString("name");
        int id = obj.getInt("id");
        float temp = obj.getJSONObject("main").getFloat("temp");
        float lon = obj.getJSONObject("coord").getFloat("lon");
        float lat = obj.getJSONObject("coord").getFloat("lat");
        return new Data(name, id, lon, lat, temp);
    }

}
