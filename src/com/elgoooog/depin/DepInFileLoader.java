package com.elgoooog.depin;

import com.elgoooog.depin.parser.DepInFileParser;
import com.elgoooog.depin.parser.DepInStaxParser;

import java.io.InputStream;

/**
 * DepInFileLoader is responsible for finding and loading the configuration file that is used by {@link DepIn} as
 * the applications context.  It then passes control over to the {@link DepInFileParser} to parse the loaded file.
 *
 * @author Nicholas Hauschild
 *         Date: 2/7/11
 *         Time: 11:51 PM
 */
public class DepInFileLoader {
    private DepInFileParser parser;

    DepInFileLoader(DepInFileParser p) {
        parser = p;
    }

    DepInFileLoader() {
        this(new DepInStaxParser());
    }

    protected void load(InputStream is, Beans beans) {
        parser.parseBeans(is, beans);
    }

    protected void setDepInFileParser(DepInFileParser fileParser) {
        parser = fileParser;
    }
}
