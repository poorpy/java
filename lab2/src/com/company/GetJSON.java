package com.company;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class GetJSON {
    public static String downloadJSON(String urlString) throws IOException {
        URL url = new URL(urlString);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        String str = readString(rbc, Charset.defaultCharset());
        rbc.close();
        return  str;
    }

    private static String readString(final ReadableByteChannel channel, final Charset charset) throws IOException {
        Scanner scanner = new Scanner(channel, charset.name());
        String text = scanner.useDelimiter("\\z").next();
        IOException e = scanner.ioException();
        if (e != null) {
            throw e;
        }
        return text;
    }

}
