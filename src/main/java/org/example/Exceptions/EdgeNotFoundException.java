package org.example.Exceptions;

public class EdgeNotFoundException extends IllegalArgumentException {

    public EdgeNotFoundException() {

    }

    public EdgeNotFoundException(String msg) {
        super(msg);
    }

}
