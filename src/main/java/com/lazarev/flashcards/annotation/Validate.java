package com.lazarev.flashcards.annotation;

import com.lazarev.flashcards.service.validation.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    Class<? extends Validator<?>>[] value();
}
