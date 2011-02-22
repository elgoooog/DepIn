package com.elgoooog.depin.exception;

/**
 * @author Nicholas Hauschild
 *         Date: 2/21/11
 *         Time: 11:12 PM
 */
public class CycleException extends RuntimeException {
    public CycleException(String message) {
        super(message);
    }
}
