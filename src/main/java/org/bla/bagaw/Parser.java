package org.bla.bagaw;

import com.bulenkov.darcula.DarculaLaf;
import org.bla.bagaw.chart.AreaChart;
import org.bla.bagaw.chart.Chart;
import org.bla.bagaw.chart.TSPainterFactory;
import org.bla.bagaw.chart.TimeSeriesPainter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {

    private static final String KEY = "OY1Z3KT43YF6WXW0";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String symbol;

    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new DarculaLaf());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);


        JPanel r1 = createRow(true,
                TSPainterFactory.createAreaChart(new TimeSeries("BAC")),
                TSPainterFactory.createAreaChart(new TimeSeries("AAPL")),
                TSPainterFactory.createAreaChart(new TimeSeries("RGSE")));

        JPanel r2 = createRow(true,
                TSPainterFactory.createAreaChart(new TimeSeries("GOOG")),
                TSPainterFactory.createAreaChart(new TimeSeries("GT")));

        frame.setSize(700, 600);


        JPanel page = new JPanel();
        BoxLayout l = new BoxLayout(page, BoxLayout.PAGE_AXIS);
        page.setLayout(l);

        page.add(r1);
        page.add(Box.createRigidArea(new Dimension(0, 20)));
        page.add(r2);

        frame.setContentPane(page);

        frame.setVisible(true);


    }

    private static JPanel createRow(boolean isRow, JPanel... charts) {
        JPanel row = new JPanel();
        BoxLayout bl = new BoxLayout(row, isRow ? BoxLayout.LINE_AXIS : BoxLayout.PAGE_AXIS);
        row.setLayout(bl);

        for (int i = 0; i < charts.length; i++) {
            row.add(charts[i]);
            if (i < charts.length - 1)
                row.add(Box.createRigidArea(new Dimension(isRow ? 20 : 0, isRow ? 0 : 20)));
        }
        row.add(Box.createGlue());
        return row;
    }

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
