package org.bla.bagaw;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Domain {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.00");

    public final double min;
    public final double max;
    public final LocalDate start;
    public final LocalDate end;
    public final double range;
    public final int N;

    public Domain(double min, double max, LocalDate start, LocalDate end, int N) {
        this.min = min;
        this.max = max;
        this.start = start;
        this.end = end;
        range = max - min;
        this.N = N;
    }

    @Override
    public String toString() {
        return start + " .. " + end + " | " + min + " .. " + max + " | range=" + FORMAT.format(range) + " | N=" + N;
    }
}
