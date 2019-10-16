package org.bla.bagaw.chart;

import org.bla.bagaw.data.DepTimeSeries;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;

public class AreaChart implements ChartPainter {

    @Override
    public void paintChart(Graphics2D g, Dimension size, DepTimeSeries timeSeries) {
        if (timeSeries.getData() == null) return;
        double height = size.getHeight();
        double width = size.getWidth();
        List<Double> data = timeSeries.getValues();
        g.setColor(Color.CYAN);
        double range = timeSeries.getDomain().range;
        double yStep = height / range;
        double min = timeSeries.getDomain().min;
        double N = timeSeries.getDomain().N;

        for (int n = 0; n < data.size() - 1; n++) {
            int x0 = (int) (n * width / N);
            int x1 = (int) ((n + 1) * width / N);
            double dataY = data.get(n);
            int y0 = (int) ((dataY - min) * yStep);
            int y1 = (int) ((data.get(n + 1) - min) * yStep);
            g.drawRect(x0, (int) (height - y0), 1, y0);
            drawTrapez(g, x0, x1, y0, y1, (int) height);
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
