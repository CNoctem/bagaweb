package org.bla.bagaw.chart;

import org.bla.bagaw.data.DepTimeSeries;

import java.awt.Graphics2D;
import java.util.List;

public abstract class TimeSeriesPainter {

    protected DepTimeSeries timeSeries;
    protected List<Double> data;

    public TimeSeriesPainter(DepTimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        data = timeSeries.getValues();
    }

    public abstract void paintTimeSeries(Graphics2D g, int width, int height);

}
