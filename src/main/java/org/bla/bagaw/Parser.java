package org.bla.bagaw;

import org.bla.bagaw.data.OHLCV;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {

    private static final String KEY = "OY1Z3KT43YF6WXW0";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String symbol;

    public Parser(String symbol) {
        this.symbol = symbol;
    }

    public TreeMap<LocalDate, Double> getSingleTS(OHLCV ohlcv) throws IOException {
        JSONObject raw = getTimeSeries();
        TreeMap<LocalDate, Double> l = new TreeMap<>();
        for (String k : raw.keySet()) {
            JSONObject date = raw.getJSONObject(k);

            l.put(LocalDate.parse(k, FORMATTER),
                    date.getDouble(ohlcv.getKey()));
        }
        return l;
    }

    public String getCandleData() throws IOException {
        JSONObject tsd = getTimeSeries();
        JSONArray timeSeries = new JSONArray();

        for (String k : tsd.keySet()) {
            JSONObject oclh = tsd.getJSONObject(k);
            timeSeries.put(createDOHLC(oclh, k));
        }

        return timeSeries.toString();
    }

    private JSONObject timeSeries;

    private JSONObject getTimeSeries() throws IOException {
        if (timeSeries != null) return timeSeries;
        System.out.println("Loading time series for " + symbol);
        URL url = new URL(
                "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + KEY);

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

        try {
            JSONObject obj = new JSONObject(content.toString());
            JSONObject tsd = obj.getJSONObject("Time Series (Daily)");
            timeSeries = tsd;

            con.disconnect();

            return tsd;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject createDOHLC(JSONObject oclh, String date) {
        JSONObject jo = new JSONObject();
        jo.put("date", date);
        for (OHLCV oo : OHLCV.values()) {
            jo.put(oo.getCandlestickKey(), oclh.get(oo.getKey()));
        }
        return jo;
    }

}
