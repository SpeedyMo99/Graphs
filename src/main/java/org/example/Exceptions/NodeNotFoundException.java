package org.example.Exceptions;

public class NodeNotFoundException extends IllegalArgumentException {

    public NodeNotFoundException() {

    }

    public NodeNotFoundException(String msg) {
        super(msg);
    }

}
