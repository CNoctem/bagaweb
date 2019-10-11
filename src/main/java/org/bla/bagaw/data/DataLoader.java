package org.bla.bagaw.data;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class DataLoader {

    private static final String KEY = "OY1Z3KT43YF6WXW0";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static JSONObject loadJsonFromRawData(String rawData) {
        JSONObject obj = new JSONObject(rawData.toString());
        System.out.println(rawData);
        JSONObject tsd = obj.getJSONObject("Time Series (Daily)");
        return tsd;
    }

    public static JSONObject loadJsonBySymbol(String symbol) throws IOException {
        String content = loadRawData(symbol);
        return loadJsonFromRawData(content);
    }

    public static String loadRawData(String symbol) throws IOException {
        URL url = new URL(
                "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                        + symbol + "&apikey="
                        + KEY);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

}
