package com.pranjal.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "png", "pdf", "docx", "xlsx");


    public static final String  NAME_REGEX  = "^[A-Za-z]{2,50}$",
            PASSWORD_REGEX =  "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            EMAIL_REGEX =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            MOBILE_REGEX = "^(\\+91[\\s]?)?[6-9]\\d{9}$";

    public static final Integer TOKEN_EXPIRES_IN_MILLISECOND = 24*60*60   *1000; // 24 hours
}
