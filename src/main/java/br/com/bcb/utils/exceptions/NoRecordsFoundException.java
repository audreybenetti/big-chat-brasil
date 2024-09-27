package br.com.bcb.utils.exceptions;

import lombok.Getter;

@Getter
public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException(String message) {
        super(message);
    }
}