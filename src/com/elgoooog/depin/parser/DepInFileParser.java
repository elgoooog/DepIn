package com.elgoooog.depin.parser;

import java.io.File;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:23 PM
 */
public interface DepInFileParser {
    void parseBeans(File file, Map<String, Object> beans);
}
