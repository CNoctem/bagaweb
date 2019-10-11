package org.bla.bagaw.chart;

import org.bla.bagaw.data.TimeSeries;

import java.awt.Graphics2D;
import java.util.List;

public abstract class TimeSeriesPainter {

    protected TimeSeries timeSeries;
    protected List<Double> data;

    public TimeSeriesPainter(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        data = timeSeries.getValues();
    }

    public abstract void paintTimeSeries(Graphics2D g, int width, int height);

}
