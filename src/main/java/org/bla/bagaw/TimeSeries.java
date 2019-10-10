package org.bla.bagaw;

import java.io.IOException;
import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TimeSeries {

    private Domain domain;
    private TreeMap<LocalDate, Double> data;
    private String symbol;

    public TimeSeries(String symbol) throws IOException {
        data = new Parser(symbol).getSingleTS(Parser.OHLCV.CLOSE);
        setMinMax();
        this.symbol =symbol;
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

}
