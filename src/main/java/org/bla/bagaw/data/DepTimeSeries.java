package org.bla.bagaw.data;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepTimeSeries {

    private Domain domain;
    private TreeMap<LocalDate, Double> data = new TreeMap<>();
    private List<Double> values = new ArrayList<>();
    private String symbol;

    DepTimeSeries(String symbol, JSONObject rawData, OHLCV ohlcv) throws IOException {
        for (String k : rawData.keySet()) {
            JSONObject date = rawData.getJSONObject(k);
            double value = date.getDouble(ohlcv.getKey());
            data.put(LocalDate.parse(k, DepDataLoader.FORMATTER), value);
            values.add(value);
        }
        setMinMax();
        this.symbol = symbol;
    }

    private DepTimeSeries(String symbol, List<LocalDate> keys, List<Double> values) {
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

    public DepTimeSeries createSMA() {
        List<LocalDate> keys = new ArrayList<>(data.size());
        List<Double> v = new ArrayList<>(data.size());
        int i = 0;
        for (LocalDate k : data.keySet()) {
            keys.add(k);
            v.add(calculateWindowedSMA(i++, 4));
        }
        return new DepTimeSeries(symbol, keys, v);
    }

    private double calculateWindowedSMA(int index, int windowSize) {
        windowSize /= 2;
        int start = Math.max(index - windowSize, 0);
        int end = Math.min(index + windowSize, values.size() - 1);
        return calculateAverage(start, end);
    }

    private double calculateAverage(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= values.size()) {
            throw new IndexOutOfBoundsException();
        }
        double sum = 0;
        for (int n = startIndex; n <= endIndex; n++) {
            sum += values.get(n);
        }
        return sum / (double)(endIndex - startIndex);
    }

    private double getSMAValue(int index) {
        double sum = 0;
        for (int n = 0; n < index -1; n++) sum += values.get(n);
        sum /= index;
        return sum;
    }

}
