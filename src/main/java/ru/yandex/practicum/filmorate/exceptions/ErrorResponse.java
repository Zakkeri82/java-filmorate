package ru.yandex.practicum.filmorate.exceptions;

public class ErrorResponse {

    String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
