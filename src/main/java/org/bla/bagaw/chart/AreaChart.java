package org.bla.bagaw.chart;

import org.bla.bagaw.TimeSeries;

import java.awt.Color;
import java.awt.Graphics2D;

public class AreaChart extends TimeSeriesPainter {

    public AreaChart(TimeSeries timeSeries) {
        super(timeSeries);
    }

    @Override
    public void paintTimeSeries(Graphics2D g, int width, int height) {
        if (timeSeries.getData() == null) return;
        g.setColor(Color.CYAN);
        double range = timeSeries.getDomain().range;
        double yStep = (double) height / range;
        double min = timeSeries.getDomain().min;
        int N = timeSeries.getDomain().N;

        for (int n = 0; n < data.size() - 1; n++) {
            int x0 = (int) (n * (double) width / (double) timeSeries.getDomain().N);
            int x1 = (int) ((n + 1) * (double) width / (double) timeSeries.getDomain().N);
            double dataY = data.get(n);
            int y0 = (int) ((dataY - min) * yStep);
            int y1 = (int) ((data.get(n + 1) - min) * yStep);
            //g2.drawRect(x0, getHeight() - y0, 1, y0);
            drawTrapez(g, x0, x1, y0, y1, height);
        }
    }

    private void drawTrapez(Graphics2D g, int x0, int x1, int y0, int y1, int height) {
        double slope = (double)(y1 - y0) / (double)(x1 - x0);
        double y = y0;
        for (int x = x0; x < x1; x++) {
            y += slope;
            g.drawRect(x, height - (int)y, 1, (int)y);
        }
    }

}
