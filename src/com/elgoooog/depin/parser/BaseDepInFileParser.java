package com.elgoooog.depin.parser;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 5:10 PM
 */
public abstract class BaseDepInFileParser implements DepInFileParser {
    protected static final Set<String> validArgNames = new HashSet<String>();

    static {
        validArgNames.add("int");
        validArgNames.add("long");
        validArgNames.add("short");
        validArgNames.add("byte");
        validArgNames.add("float");
        validArgNames.add("double");
        validArgNames.add("boolean");
        validArgNames.add("char");
        validArgNames.add("string");
    }
}
