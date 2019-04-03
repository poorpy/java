package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        try {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Warsaw&APPID=" + readFile("src/secret.txt", Charset.defaultCharset());
            Data data = ParseJSON.parse(GetJSON.downloadJSON(url));
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}


