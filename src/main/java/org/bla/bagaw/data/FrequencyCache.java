package org.bla.bagaw.data;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyCache {

    private static final String storeDir = "c:\\Users\\gergely.kovacs\\workspace\\bagaweb\\cache";

    private static Map<String, String> inmem = new HashMap<>();

    public static TimeSeries load(String symbol) throws IOException {
        Path p = Paths.get(storeDir + File.separator + symbol + ".json");
        JSONObject json;
        if (inmem.containsKey(symbol)) {
            System.out.println("Fetching " + symbol + " from inmem...");
            json = DataLoader.loadJsonFromRawData(inmem.get(symbol));
        } else if (p.toFile().exists()) {
            System.out.println("Fetching " + symbol + " from cache file " + p.toFile());
            json = DataLoader.loadJsonFromRawData(Files.lines(p).collect(Collectors.joining()));
        } else {
            System.out.println("Writing " + symbol + " to cache...");
            String rawData = DataLoader.loadRawData(symbol);
            Files.write(p, rawData.getBytes());

            inmem.put(symbol, rawData);
            json = DataLoader.loadJsonFromRawData(rawData);
        }
        return new TimeSeries(
                symbol,
                json,
                OHLCV.CLOSE);
    }

}
