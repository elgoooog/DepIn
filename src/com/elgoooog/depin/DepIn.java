package com.elgoooog.depin;

import java.util.HashMap;
import java.util.Map;

/**
 * DepIn is the main class with which one can access the beans created by the configuration file.
 *
 * @author Nicholas Hauschild
 * Date: 2/7/11
 * Time: 11:56 PM
 */
public class DepIn {
    private static DepIn instance = new DepIn();

    private Map<String, Object> beans = new HashMap<String, Object>();

    private DepIn() {
        DepInFileLoader fileLoader = new DepInFileLoader();
        fileLoader.load("src/depin.xml", beans);
    }

    public static DepIn getInstance() {
        return instance;
    }

    public Object get(String id) {
        return beans.get(id);
    }
}
