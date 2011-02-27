package com.elgoooog.depin;

import com.elgoooog.depin.inject.BeanInjector;
import com.elgoooog.depin.parser.DepInDomParser;
import org.junit.Before;

/**
 * @author Nicholas Hauschild
 *         Date: 2/20/11
 *         Time: 2:39 PM
 */
public class DomParserTest extends BaseDepInIntegrationTest {
    @Before
    public void initDomParser() throws Exception {
        depin = new DepIn(new DepInFileLoader(new DepInDomParser()), new BeanInjector());
    }
}
