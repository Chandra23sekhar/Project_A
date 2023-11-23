package com.example.project_a;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator {

    // code to validate date, phone number, email, etc

    public boolean validDate(String date) {
        // TODO: need to validate format and value
        if (date == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$");
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();

    }

    public boolean validEmail(String email) {
        if (email == null) {
            return false;
        }

        // TODO: Improve regex

//        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean validMobile(String mobNo) {
        if (mobNo == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[789]\\d{9}$");
        Matcher matcher = pattern.matcher(mobNo);

        return matcher.matches();
    }
}
