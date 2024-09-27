package br.com.bcb.config.exceptions;

import lombok.Getter;

@Getter
public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException(String message) {
        super(message);
    }
}