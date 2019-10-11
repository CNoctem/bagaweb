package org.bla.bagaw.chart;

import org.bla.bagaw.data.TimeSeries;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface ChartPainter {

    void paintChart(Graphics2D g, Dimension size, TimeSeries timeSeries);

}
