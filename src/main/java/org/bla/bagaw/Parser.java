package org.bla.bagaw;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class Parser {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&apikey=OY1Z3KT43YF6WXW0");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        System.out.println(con.getResponseCode());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject obj = new JSONObject(content.toString());

        JSONObject tsd = obj.getJSONObject("Time Series (Daily)");

        JSONArray timeSeries = new JSONArray();

        for (String k : tsd.keySet()) {
            JSONObject oclh = tsd.getJSONObject(k);

            timeSeries.put(createDOHLC(oclh, k));

            System.out.println(createDOHLC(oclh, k));
        }

        System.out.println(timeSeries);

        con.disconnect();
    }

    private static JSONObject createDOHLC(JSONObject oclh, String date) {
        JSONObject jo = new JSONObject();
        jo.put("date", date);
        for (OHLCV oo : OHLCV.values()) {
            jo.put(oo.getCandlestickKey(), oclh.get(oo.getKey()));
        }
        return jo;
    }

    enum OHLCV {
        OPEN("1. open"), HIGH("2. high"), LOW("3. low"), CLOSE("4. close"), VOLUME("5. volume");

        OHLCV(String key) {
            this.key = key;
            candlestickKey = key.substring(3);
        }

        private String key, candlestickKey;

        public String getKey() {
            return key;
        }

        public String getCandlestickKey() {
            return candlestickKey;
        }
    }

}
