package com.Treino.QuestBoard.Exceptions;

public class UserAlreadyExsistsException extends RuntimeException {
    public UserAlreadyExsistsException(String message) {
        super(message);
    }
}
