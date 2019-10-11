package org.bla.bagaw.data;

public enum OHLCV {
    OPEN("1. open"), HIGH("2. high"), LOW("3. low"), CLOSE("4. close"), VOLUME("5. volume");

    OHLCV(String key) {
        this.key = key;
        candlestickKey = key.substring(3);
    }

    private String key, candlestickKey;

    public String getKey() {
        return key;
    }

    public String getCandlestickKey() {
        return candlestickKey;
    }
}