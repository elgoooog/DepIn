package com.elgoooog.depin;

import com.elgoooog.depin.parser.model.Beans;

/**
 * DepIn is the main class with which one can access the beans created by the configuration file.
 *
 * @author Nicholas Hauschild
 *         Date: 2/7/11
 *         Time: 11:56 PM
 */
public class DepIn {
    private static DepIn instance = new DepIn();

    private DepIn() {
        DepInFileLoader fileLoader = new DepInFileLoader();
        fileLoader.load("src/depin.xml");
    }

    public static DepIn getInstance() {
        return instance;
    }

    public Object get(String id) {
        return Beans.getBean(id).getInstance();
    }
}
