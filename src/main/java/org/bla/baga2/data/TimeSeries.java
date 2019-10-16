package org.bla.baga2.data;

import java.time.LocalDate;
import java.util.List;

public class TimeSeries {

    private long[] time;
    private float[] dataPoints;

    private float min = Float.MAX_VALUE, max = Float.MIN_VALUE;

    public TimeSeries(List<String> dataLines, String columnName) {
        int colIdx = getColumnIndex(dataLines.get(0), columnName);
        time = new long[dataLines.size() - 1];
        dataPoints = new float[dataLines.size() - 1];
        for (int j = 1; j < dataLines.size(); j++) {
            String[] parts = dataLines.get(j).split(",");
            time[j-1] = LocalDate.parse(parts[0]).toEpochDay();
            float dp = Float.parseFloat(parts[colIdx]);
            dataPoints[j-1] = dp;
            if (dp < min) min = dp;
            if (dp > max) max = dp;
        }
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public int size() {
        return dataPoints.length;
    }

    public float getByIndex(int i) {
        return dataPoints[i];
    }

    public float getByTime(long t) {
        if (t < time[0] || t > time[time.length - 1]) return -1;
        for (int i = 0; i < time.length; i++) if (time[i] >= t) return dataPoints[i];
        throw new IllegalStateException("Must not get here.");
    }

    private int getColumnIndex(String header, String colName) {
        String[] colNames = header.split(",");
        for (int i = 0; i < colNames.length; i++) {
            if (colNames[i].equals(colName)) return i;
        }
        return -1;
    }

}
