package com.lazarev.flashcards.service.validation;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Map;
import java.util.regex.Pattern;

@UtilityClass
public class ValidationConstants {
    public Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z\\d_-]{3,15}$");
    public Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d-+=:;%$#@!?*&^`~_<>'.,/(){}\\[\\]\"\\\\]{5,15}$");
    public Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,256}$)[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$");

    public Map<String, String> ERRORS = Map.of(
            "username", "Username must contain from 3 to 15 symbols, consists only of latin letters, digits, underscores and hyphens",
            "password", "Password must contain from 5 to 15 symbols, consists only of latin letters, digits, special symbols, at least one digit and capital letter",
            "email", "Email format is invalid",
            "name", "Name should be up to 20 symbols",
            "description", "Description should be up to 40 symbols"
    );

    public static Map<String, String> isValidUsername(String username) {
        if (USERNAME_PATTERN.matcher(username).matches()){
            return Map.of();
        }
        return Map.of("username", ERRORS.get("username"));
    }

    public static Map<String, String> isValidEmail(String email) {
        if (EMAIL_PATTERN.matcher(email).matches()){
            return Map.of();
        }
        return Map.of("email", ERRORS.get("email"));
    }

    public static Map<String, String> isValidPassword(char[] password) {
        StringBuilder passwordWrapper = new StringBuilder().append(password);

        if (PASSWORD_PATTERN.matcher(passwordWrapper).matches()){
            return Map.of();
        }
        return Map.of("password", ERRORS.get("password"));
    }

    public static Map<String, String> isValidElementName(String elementName) {
        if (elementName.length() < 20){
            return Map.of();
        }
        return Map.of("name", ERRORS.get("name"));
    }


    public static Map<String, String> isValidElementDescription(String elementDescription) {
        if (elementDescription == null || elementDescription.length() < 40){
            return Map.of();
        }
        return Map.of("description", ERRORS.get("description"));
    }
}
