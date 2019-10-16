package org.bla.baga2.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public enum Configurator {

    INSTANCE;

    Properties props = new Properties();
    Configurator() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("baga.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String name) {
        return props.getProperty(name);
    }

}
