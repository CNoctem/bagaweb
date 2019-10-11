package org.bla.bagaw.data;

public class DataPoint {

    class Builder {
        private double open, high, low, close;
        public Builder open(double o) {
            open = o;
            return this;
        }
        public Builder close(double c) {
            close = c;
            return this;
        }
        public Builder high(double h) {
            high = h;
            return this;
        }
        public Builder low(double l) {
            low = l;
            return this;
        }
        public DataPoint build() {
            return new DataPoint(open, high, low, close);
        }
    }

    public final double open, high, low, close;

    private DataPoint(double open, double high, double low, double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

}
