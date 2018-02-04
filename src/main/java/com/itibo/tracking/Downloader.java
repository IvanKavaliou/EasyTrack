package com.itibo.tracking;


import java.io.*;
import java.net.*;

public class Downloader {

    static public String getHTML(String urlGet) throws IOException {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuffer result = new StringBuffer();

            url = new URL(urlGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                    result.append(line.trim()).append('\n');
            }
            rd.close();

        return result.toString();
    }
}
