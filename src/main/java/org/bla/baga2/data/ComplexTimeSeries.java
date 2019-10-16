package org.bla.baga2.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ComplexTimeSeries {

    private List<DataPoint> dataPoints;
    private List<Float> highs, lows, opens, closes;

    public ComplexTimeSeries(List<String> dataLines) {
        dataPoints = new ArrayList<>(dataLines.size() - 1);
        highs = new ArrayList<>(dataLines.size() - 1);
        lows = new ArrayList<>(dataLines.size() - 1);
        opens = new ArrayList<>(dataLines.size() - 1);
        closes = new ArrayList<>(dataLines.size() - 1);
        for (int i = 1; i < dataLines.size(); i++) {
            DataPoint dp = new DataPoint(dataLines.get(i));
            dataPoints.add(dp);
            highs.add(dp.high);
            lows.add(dp.low);
            opens.add(dp.open);
            closes.add(dp.close);
        }
    }

}

class DataPoint {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final LocalDate timeStamp;
    public final float open, high, low, close;
    public final long volume;

    DataPoint(String csvRow) {
        StringTokenizer tok = new StringTokenizer(csvRow, ",");
        timeStamp = LocalDate.parse(tok.nextToken(), FORMATTER);
        open = Float.parseFloat(tok.nextToken());
        high = Float.parseFloat(tok.nextToken());
        low = Float.parseFloat(tok.nextToken());
        close = Float.parseFloat(tok.nextToken());
        volume = Long.parseLong(tok.nextToken());
    }

}
