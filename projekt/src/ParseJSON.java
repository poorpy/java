package com.company;

import  org.json.*;

import java.sql.*;
import java.text.DecimalFormat;

public class ParseJSON {
    public static com.company.Data parse(String str) {
        JSONObject obj = new JSONObject(str);
        String name = obj.getString("name");

        int id = obj.getInt("id");
        float lon = obj.getJSONObject("coord").getFloat("lon");
        float lat = obj.getJSONObject("coord").getFloat("lat");

        DecimalFormat df = new DecimalFormat("#.##");
        String strTemp = df.format(obj.getJSONObject("main").getFloat("temp")-273.15).replace(',','.');

        float temp = Float.valueOf(strTemp);

        System.out.println("temp"+temp);

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:temp.db"))
        {
            System.out.println("Connection established");
            Statement statement = conn.createStatement();
            String initDB = "CREATE TABLE IF NOT EXISTS temps(\n"
                    + " id INTEGER PRIMARY KEY    AUTOINCREMENT,\n"
                    + " city           TEXT    NOT NULL,\n"
                    + " temp           FLOAT    NOT NULL,\n"
                    + " temp_time      TEXT    NOT NULL);";
            statement.execute(initDB);
            statement.execute("insert into temps(city, temp, temp_time) values ('"+name+"','"+temp+"',  datetime('now', 'localtime'))");

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return new com.company.Data(name, id, lon, lat, temp);
    }

}
