package org.bla.bagaw.data;

import org.bla.bagaw.Parser;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TimeSeries {

    private Domain domain;
    private TreeMap<LocalDate, Double> data = new TreeMap<>();
    private List<Double> values = new ArrayList<>();
    private String symbol;

    TimeSeries(String symbol, JSONObject rawData, OHLCV ohlcv) throws IOException {
        for (String k : rawData.keySet()) {
            JSONObject date = rawData.getJSONObject(k);
            double value = date.getDouble(ohlcv.getKey());
            data.put(LocalDate.parse(k, DataLoader.FORMATTER), value);
            values.add(value);
        }
        setMinMax();
        this.symbol = symbol;
    }

    private TimeSeries(String symbol, List<LocalDate> keys, List<Double> values) {
        for (int i = 0; i < keys.size(); i++) data.put(keys.get(i), values.get(i));
        this.values = values;
        setMinMax();
        this.symbol = symbol;
    }

    public List<Double> getValues() {
        return values;
    }

    public String getSymbol() {
        return symbol;
    }

    public Domain getDomain() {
        return domain;
    }

    public TreeMap<LocalDate, Double> getData() {
        return data;
    }

    private void setMinMax() {
        DoubleSummaryStatistics stats =
                data.values().stream()
                .collect(Collectors.summarizingDouble(Double::doubleValue));
        domain = new Domain(stats.getMin(), stats.getMax(), data.firstKey(), data.lastKey(), data.size());
    }

    public TimeSeries createSMA() {
        List<LocalDate> keys = new ArrayList<>(data.size());
        List<Double> v = new ArrayList<>(data.size());
        int i = 0;
        for (LocalDate k : data.keySet()) {
            keys.add(k);
            v.add(getSMAValue(i++));
        }
        return new TimeSeries(symbol, keys, v);
    }

    private double getSMAValue(int index) {
        double sum = 0;
        for (int n = 0; n < index -1; n++) sum += values.get(n);
        sum /= index;
        return sum;
    }

}
