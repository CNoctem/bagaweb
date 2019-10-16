package org.bla.baga2.data;

import java.io.IOException;
import java.util.List;

public class FCache {

    private static final String KEY = "OY1Z3KT43YF6WXW0";

    public static void main(String[] args) throws IOException {
        DataLoader dtl = new DataLoader.Builder()
                .function("TIME_SERIES_MONTHLY")
                .symbol("AAPL")
                .dataType("csv").build();

        List<String> dataPoints = dtl.loadData();
        System.out.println(dataPoints);

        TimeSeries ts = new TimeSeries(dataPoints, "close");

    }

}
