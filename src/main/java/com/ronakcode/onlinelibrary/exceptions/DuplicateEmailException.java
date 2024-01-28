package com.ronakcode.onlinelibrary.exceptions;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
