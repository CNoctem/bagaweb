package org.bla.bagaw;

import com.bulenkov.darcula.DarculaLaf;
import org.bla.bagaw.chart.TSPainterFactory;
import org.bla.bagaw.data.DataLoader;
import org.bla.bagaw.data.FrequencyCache;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Dimension;
import java.io.IOException;

public class Launcher {

    private static final Dimension ROW_RIGID = new Dimension(20, 0);
    private static final Dimension PAGE_RIGID = new Dimension(0, 20);

    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new DarculaLaf());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);


        JPanel r1 = createRow(true,
                TSPainterFactory.createAreaChart(FrequencyCache.load("BAC")),
                TSPainterFactory.createAreaChart(FrequencyCache.load("AAPL")),
                TSPainterFactory.createAreaChart(FrequencyCache.load("RGSE")));

        JPanel r2 = createRow(true,
                TSPainterFactory.createAreaChart(FrequencyCache.load("GOOG")));

        frame.setSize(700, 600);


        JPanel page = createRow(false, r1, r2, TSPainterFactory.createAreaChart(FrequencyCache.load("BAC")));

        frame.setContentPane(page);

        frame.setVisible(true);
    }

    private static JPanel createRow(boolean isRow, JPanel... charts) {
        JPanel row = new JPanel();
        BoxLayout bl = new BoxLayout(row, isRow ? BoxLayout.LINE_AXIS : BoxLayout.PAGE_AXIS);
        row.setLayout(bl);

        for (int i = 0; i < charts.length; i++) {
            row.add(charts[i]);
            if (i < charts.length - 1)
                row.add(Box.createRigidArea(isRow ? ROW_RIGID : PAGE_RIGID));
        }
        row.add(Box.createGlue());
        return row;
    }

}
