package com.example.tictactoe.exception;

public class TicTacToeServiceException extends Exception {

    public ErrorType errorType;

    public TicTacToeServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public TicTacToeServiceException(String message, Throwable cause, ErrorType errorType) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}
