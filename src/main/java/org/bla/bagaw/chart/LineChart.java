package org.bla.bagaw.chart;

import org.bla.bagaw.data.TimeSeries;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class LineChart implements ChartPainter {
    @Override
    public void paintChart(Graphics2D g, Dimension size, TimeSeries timeSeries) {
        Color prevColor = g.getColor();
        g.setColor(Color.RED);
        int w = size.width;
        double xStep = size.getWidth() / timeSeries.getDomain().N;
        for (int n = 0; n < timeSeries.getDomain().N; n++) {
            int x = (int) (n * xStep);
            int y = timeSeries.getValues().get(n).intValue();
            g.drawRect(x, size.height - y, 3, 3);
        }
        g.setColor(prevColor);
    }
}
