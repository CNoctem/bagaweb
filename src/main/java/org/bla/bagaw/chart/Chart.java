package org.bla.bagaw.chart;

import org.bla.bagaw.TimeSeries;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chart extends JPanel {

    private final TimeSeries timeSeries;
    private List<Double> data;

    public Chart(TimeSeries ts) {
        this.timeSeries = ts;
        data = new ArrayList<>(ts.getData().size());
        for (LocalDate k : ts.getData().keySet()) {
            data.add(ts.getData().get(k));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.CYAN);
        double range = timeSeries.getDomain().range;
        double yStep = (double) getHeight() / range;
        double min = timeSeries.getDomain().min;
        int N = timeSeries.getDomain().N;

        for (int n = 0; n < data.size() - 1; n++) {
            int x0 = (int) (n * (double) getWidth() / (double) timeSeries.getDomain().N);
            int x1 = (int) ((n + 1) * (double) getWidth() / (double) timeSeries.getDomain().N);
            double dataY = data.get(n);
            int y0 = (int) ((dataY - min) * yStep);
            int y1 = (int) ((data.get(n + 1) - min) * yStep);
            //g2.drawRect(x0, getHeight() - y0, 1, y0);
            drawTrapez(g2, x0, x1, y0, y1);
        }
    }

    private void drawTrapez(Graphics2D g, int x0, int x1, int y0, int y1) {
        double slope = (double)(y1 - y0) / (double)(x1 - x0);
        double y = y0;
        for (int x = x0; x < x1; x++) {
            y += slope;
            g.drawRect(x, getHeight() - (int)y, 1, (int)y);
        }
    }
}
