package org.bla.bagaw.chart;

import org.bla.bagaw.data.TimeSeries;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TSPainterFactory {

    public static JPanel createAreaChart(TimeSeries timeSeries) {
        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                new AreaHeavyChart(timeSeries).paintTimeSeries((Graphics2D) g, getWidth(), getHeight());
                new LineChart().paintChart((Graphics2D) g, getSize(), timeSeries.createSMA());
            }
        };

        JPanel titlePane = new JPanel(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder(timeSeries.getSymbol() + " : " + timeSeries.getDomain());
        border.setTitleColor(Color.CYAN);
        titlePane.setBorder(border);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        wrapper.add(chart, BorderLayout.CENTER);
        titlePane.add(wrapper, BorderLayout.CENTER);
        return titlePane;
    }

}
