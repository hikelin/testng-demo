package com.github.hikelin.testng.common.provider.rest;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class RestException extends Exception implements Iterable<Throwable> {

    private static final long serialVersionUID = 2021244094396331484L;

    /**
     * @serial
     */
    private Integer httpStatus;

    /**
     * @serial
     */
    private volatile RestException next;

    private static final AtomicReferenceFieldUpdater<RestException,RestException> nextUpdater =
            AtomicReferenceFieldUpdater.newUpdater(RestException.class,RestException.class,"next");


    public RestException(String reason, int httpStatus) {
        super(reason);
        this.httpStatus = httpStatus;
    }

    public RestException(String reason) {
        super(reason);
        this.httpStatus = null;
    }

    public RestException() {
        super();
        this.httpStatus = null;
    }

    public RestException(Throwable cause) {
        super(cause);
    }

    public RestException(String reason, Throwable cause) {
        super(reason,cause);
    }

    public RestException(String reason, int httpStatus, Throwable cause) {
        super(reason,cause);

        this.httpStatus = httpStatus;
    }

    public Integer getHttpStatus() {
        return (httpStatus);
    }

    public RestException getNextException() {
        return (next);
    }


    public void setNextException(RestException ex) {

        RestException current = this;
        for(;;) {
            RestException next=current.next;
            if (next != null) {
                current = next;
                continue;
            }

            if (nextUpdater.compareAndSet(current,null,ex)) {
                return;
            }
            current=current.next;
        }
    }

    public Iterator<Throwable> iterator() {

        return new Iterator<Throwable>() {

            RestException firstException = RestException.this;
            RestException nextException = firstException.getNextException();
            Throwable cause = firstException.getCause();

            public boolean hasNext() {
                if(firstException != null || nextException != null || cause != null)
                    return true;
                return false;
            }

            public Throwable next() {
                Throwable throwable = null;
                if(firstException != null){
                    throwable = firstException;
                    firstException = null;
                }
                else if(cause != null){
                    throwable = cause;
                    cause = cause.getCause();
                }
                else if(nextException != null){
                    throwable = nextException;
                    cause = nextException.getCause();
                    nextException = nextException.getNextException();
                }
                else
                    throw new NoSuchElementException();
                return throwable;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        };

    }

}
