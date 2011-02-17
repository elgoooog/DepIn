package com.elgoooog.depin;

import com.elgoooog.depin.parser.DepInFileParser;
import com.elgoooog.depin.parser.DepInStaxParser;

import java.io.File;
import java.util.Map;

/**
 * DepInFileLoader is responsible for finding and loading the configuration file that is used by {@link DepIn} as
 * the applications context.  It then passes control over to the {@link DepInFileParser} to parse the loaded file.
 *
 * @author Nicholas Hauschild
 *         Date: 2/7/11
 *         Time: 11:51 PM
 */
public class DepInFileLoader {
    private DepInFileParser parser = new DepInStaxParser();

    DepInFileLoader() {

    }

    protected void load(String file, Map<String, Object> beans) {
        load(new File(file), beans);
    }

    protected void load(File file, Map<String, Object> beans) {
        parser.parseBeans(file, beans);
    }
}
