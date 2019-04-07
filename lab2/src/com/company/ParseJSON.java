package com.company;

import  org.json.*;

import java.text.DecimalFormat;

public class ParseJSON {
    public static Data parse(String str) {
        JSONObject obj = new JSONObject(str);
        String name = obj.getString("name");
        int id = obj.getInt("id");

        DecimalFormat df = new DecimalFormat("#.##");
        String strTemp = df.format(obj.getJSONObject("main").getFloat("temp")-273.15).replace(',','.');

        float temp = Float.valueOf(strTemp);
        float lon = obj.getJSONObject("coord").getFloat("lon");
        float lat = obj.getJSONObject("coord").getFloat("lat");
        return new Data(name, id, lon, lat, temp);
    }

}
