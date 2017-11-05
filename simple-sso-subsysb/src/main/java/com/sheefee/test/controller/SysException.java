package com.sheefee.test.controller;

/**
 * Created by zorke on 2017/11/5.
 */
public class SysException extends RuntimeException {
    /**
     * Instantiates a new Sys exception.
     */
    public SysException() {
        super();
    }

    /**
     * Instantiates a new Sys exception.
     *
     * @param msg the msg
     */
    public SysException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Sys exception.
     *
     * @param ex the ex
     */
    public SysException(Throwable ex) {
        super(ex);
    }

    /**
     * Instantiates a new Sys exception.
     *
     * @param msg the msg
     * @param ex  the ex
     */
    public SysException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
