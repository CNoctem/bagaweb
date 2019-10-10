package org.bla.bagaw.chart;

import org.bla.bagaw.TimeSeries;

import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class TimeSeriesPainter {

    protected TimeSeries timeSeries;
    protected List<Double> data;

    public TimeSeriesPainter(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        data = new ArrayList<>(timeSeries.getData().size());
        for (LocalDate k : timeSeries.getData().keySet()) {
            data.add(timeSeries.getData().get(k));
        }
    }

    public abstract void paintTimeSeries(Graphics2D g, int width, int height);

}
