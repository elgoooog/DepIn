package com.elgoooog.depin;

import com.elgoooog.depin.parser.DepInStaxParser;
import org.junit.Before;

/**
 * @author Nicholas Hauschild
 *         Date: 2/20/11
 *         Time: 2:37 PM
 */
public class StaxParserTest extends BaseDepInIntegrationTest {
    @Before
    public void initStaxParser() throws Exception {
        depin = new DepIn(new DepInFileLoader(new DepInStaxParser()));
        depin.loadConfiguration();
    }
}
