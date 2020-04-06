package ru.ed.microlib;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class RestClientApp {
    public static void main(String[] args) {
        //for (int i = 0; i < 10; i++) {
            new Thread(new Connection()).start();
        //}
    }

    private static class Connection implements Runnable{
        public void run() {
            //for (int i = 0; i < 100; i++) {
                connect();
            //}
        }
    }

    private static void connect(){
        try {
            URL url = new URL("http://192.168.0.106:8080/app-market-web/auth");

            URLConnection urlConnection = url.openConnection();

            urlConnection.setRequestProperty("Accept-Charset", "utf-8");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setDoOutput(true);
            OutputStream outputStream = urlConnection.getOutputStream();
            String body = "{\"username\":\"baran\", \"password\":\"baran\"}";
            outputStream.write(body.getBytes("utf-8"));

            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[255];

            while (inputStream.read(buffer) != -1){
                System.out.println(new String(buffer));
            }

            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headerFields.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
