package org.bla.baga2.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    static class Builder {
        private String function, symbol, interval, datatype;

        public Builder function(String function) {
            this.function = function;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder interval(String interval) {
            this.interval = interval;
            return this;
        }

        public Builder dataType(String dt) {
            datatype = dt;
            return this;
        }

        DataLoader build() {
            return new DataLoader(function, symbol, interval, datatype);
        }

    }

    private final String function, symbol, interval, dataType;

    private DataLoader(String function, String symbol, String interval, String datatype) {
        this.function = function;
        this.symbol = symbol;
        this.interval = interval;
        this.dataType = datatype;
    }

    public List<String> loadData() throws IOException {
        URL url = new URL("https://www.alphavantage.co/query?" +
                "function=" + function +
                "&symbol=" + symbol +
                "&interval=" + interval +
                "&apikey=" + Configurator.INSTANCE.getProperty("vantage.api.key") +
                "&datatype=" + dataType);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", dataType.equals("json") ? "application/json" : "application/text");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        List<String> lines = new ArrayList<>();
        while ((inputLine = in.readLine()) != null) {
            lines.add(inputLine);
        }
        in.close();
        con.disconnect();
        return lines;
    }

}
