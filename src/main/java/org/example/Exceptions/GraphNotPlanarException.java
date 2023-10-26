package org.example.Exceptions;

public class GraphNotPlanarException extends IllegalArgumentException {

    public GraphNotPlanarException() {

    }

    public GraphNotPlanarException(String msg) {
        super(msg);
    }

}
