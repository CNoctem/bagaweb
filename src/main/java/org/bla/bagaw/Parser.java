package org.bla.bagaw;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class Parser {

    public static void main(String[] args) throws IOException {
        String j = new String(Files.readAllBytes(Paths.get("c:\\Users\\gergely.kovacs\\workspace\\bagaweb\\src\\main\\resources\\daily.json")));

        InputStream in = new FileInputStream("c:\\Users\\gergely.kovacs\\workspace\\bagaweb\\src\\main\\resources\\daily.json");
        JSONObject obj = new JSONObject(j);

        JSONObject tsd = obj.getJSONObject("Time Series (Daily)");

        JSONArray timeSeries = new JSONArray();

        for (String k : tsd.keySet()) {
            JSONObject oclh = tsd.getJSONObject(k);

            timeSeries.put(createDOHLC(oclh, k));

            System.out.println(createDOHLC(oclh, k));
        }

        System.out.println(timeSeries);

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
