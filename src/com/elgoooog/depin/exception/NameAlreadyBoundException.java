package com.elgoooog.depin.exception;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 12:29 AM
 */
public class NameAlreadyBoundException extends RuntimeException {
    public NameAlreadyBoundException(String message) {
        super(message);
    }
}
