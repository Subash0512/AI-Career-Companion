package com.example.AI.Resume.Maker.exception;

public class InvalidResumeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidResumeException(String message) {
        super(message);
    }
}