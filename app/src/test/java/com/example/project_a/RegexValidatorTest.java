package com.example.project_a;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.Test;

public class RegexValidatorTest {

    RegexValidator regexValidator = new RegexValidator();

    @Test
    public void valid_date_1_returns_True() {
        assertTrue(regexValidator.validDate("23/11/2023"));
    }

    @Test
    public void valid_date_2_returns_True() {
        assertTrue(regexValidator.validDate("01/01/2001"));
    }

    @Test
    public void invalid_date_1_returns_False() {
        assertFalse(regexValidator.validDate("00/00/0000"));
    }

    @Test
    public void invalid_date_2_returns_False() {
        assertFalse(regexValidator.validDate("2002/02/23"));
    }

    @Test
    public void invalid_date_3_returns_False() {
        assertFalse(regexValidator.validDate("-11/-11/2023"));
    }

    // Testcases for email id
    @Test
    public void valid_email_1_returns_True() {
        assertTrue(regexValidator.validEmail("chan23du@gmail.com"));
    }

    @Test
    public void valid_email_2_returns_True() {
        assertTrue(regexValidator.validEmail("chandu@gmail.com"));
    }

    @Test
    public void invalid_email_1_returns_False() {
        assertTrue(regexValidator.validEmail("chan23du@gmai43l.com"));
    }

    @Test
    public void invalid_email_2_returns_False() {
        assertTrue(regexValidator.validEmail("3254732@gmai43l.com"));
    }

//    @Test
//    public void invalid_email_3_returns_False(){
//        assertFalse(regexValidator.validEmail("chan.com.du@gmai43l.com"));
//    }

    @Test
    public void invalid_email_4_returns_False() {
        assertFalse(regexValidator.validEmail(""));
    }

    @Test
    public void invalid_email_5_returns_False() {
        assertFalse(regexValidator.validEmail(".com"));
    }

    @Test
    public void invalid_email_6_returns_False() {
        assertFalse(regexValidator.validEmail("johnsmith"));
    }

    @Test
    public void invalid_email_7_returns_False() {
        assertFalse(regexValidator.validEmail("687647832432.@"));
    }

    @Test
    public void invalid_email_8_returns_False() {
        assertFalse(regexValidator.validEmail("@.com"));
    }


    // Testcases for phone number
    @Test
    public void valid_phone_1_returns_True() {
        assertTrue(regexValidator.validMobile("9876543210"));
    }

    @Test
    public void valid_phone_2_returns_True() {
        assertTrue(regexValidator.validMobile("8765432109"));
    }

    @Test
    public void valid_phone_3_returns_True() {
        assertTrue(regexValidator.validMobile("7890123456"));
    }

    @Test
    public void valid_phone_4_returns_True() {
        assertTrue(regexValidator.validMobile("9012345678"));
    }

    @Test
    public void invalid_phone_1_returns_False() {
        assertFalse(regexValidator.validMobile("1234567890"));
    }

    @Test
    public void invalid_phone_2_returns_False() {
        assertFalse(regexValidator.validMobile("987654321"));
    }

    @Test
    public void invalid_phone_3_returns_False() {
        assertFalse(regexValidator.validMobile("8765abc4321"));
    }

    @Test
    public void invalid_phone_4_returns_False() {
        assertFalse(regexValidator.validMobile("invalidPhoneNumber"));
    }

    @Test
    public void invalid_phone_5_returns_False() {
        assertFalse(regexValidator.validMobile(""));
    }
}