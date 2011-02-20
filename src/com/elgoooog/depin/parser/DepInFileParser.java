package com.elgoooog.depin.parser;

import com.elgoooog.depin.Beans;

import java.io.InputStream;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:23 PM
 */
public interface DepInFileParser {
    void parseBeans(InputStream is, Beans beans);
}
