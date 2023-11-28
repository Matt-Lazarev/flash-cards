package com.lazarev.flashcards.service.validation;

public interface Validator<T> {
    void validate(T arg);
}
