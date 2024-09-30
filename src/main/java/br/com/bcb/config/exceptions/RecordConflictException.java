package br.com.bcb.config.exceptions;

public class RecordConflictException extends RuntimeException {

    public RecordConflictException(String message) {
        super(message);
    }
}