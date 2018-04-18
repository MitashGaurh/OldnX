package com.oldandx.oldnx.utils;

import android.support.annotation.NonNull;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data validation
 */
public final class ValidationUtils {

    private final static String INPUT_ENG_A = "[ a-zA-Z]+";
    private final static String INPUT_HIN_A = "^[\\u0900-\\u0965 \\u0970-\\u097F\\s]+$";
    private final static String INPUT_TEL_A = "^[\\u0C00-\\u0C65 \\u0C70-\\u0C7F\\s]+$";
    private final static String INPUT_KAN_A = "^[\\u0C80-\\u0CE5 \\u0CF0-\\u0CFF\\s]+$";
    private final static String INPUT_TAM_A = "^[\\u0B80-\\u0BE5 \\u0BF0-\\u0BFF\\s]+$";
    private final static String INPUT_PERSON = "/^[a-zA-Z0-9 .&()_'’-]*$/";
    private final static Pattern PATTERN_ENG_A = Pattern.compile(INPUT_ENG_A);
    private final static Pattern PATTERN_HIN_A = Pattern.compile(INPUT_HIN_A);
    private final static Pattern PATTERN_TEL_A = Pattern.compile(INPUT_TEL_A);
    private final static Pattern PATTERN_KAN_A = Pattern.compile(INPUT_KAN_A);
    private final static Pattern PATTERN_TAM_A = Pattern.compile(INPUT_TAM_A);
    private final static Pattern PATTERN_PERSON = Pattern.compile(INPUT_PERSON);

    /**
     * Don't let anyone instantiate this class.
     */
    private ValidationUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static boolean isEmptyOrWhiteSpaces(@NonNull EditText editText) {
        return editText.getText().toString().trim().equalsIgnoreCase("") || editText.getText()
                .toString().length() == 0;
    }

    public static boolean isHavingOnlyAlphabetCharacter(@NonNull EditText editText) {
        boolean flag;
        String chars = editText.getText().toString();
        Matcher matcherEng = PATTERN_ENG_A.matcher(chars);
        Matcher matcherHin = PATTERN_HIN_A.matcher(chars);
        Matcher matcherTel = PATTERN_TEL_A.matcher(chars);
        Matcher matcherKan = PATTERN_KAN_A.matcher(chars);
        Matcher matcherTam = PATTERN_TAM_A.matcher(chars);
        flag = matcherEng.matches() || matcherHin.matches() || matcherTel.matches() || matcherKan
                .matches() || matcherTam.matches();
        return flag;
    }

    public static boolean isValidEmail(@NonNull EditText editText) {
        String stringForValidation = editText.getText().toString().trim();
        return Patterns.EMAIL_ADDRESS.matcher(stringForValidation).matches();
    }

    public static boolean isValidPinCode(EditText etPinCode) {
        boolean flag = false;
        String stringForValidation = etPinCode.getText().toString().trim();
        if (stringForValidation.length() == 6 && !stringForValidation.startsWith("0")) {
            flag = true;
        }
        return flag;
    }

    public static boolean isValidLandline(EditText etLlNumber) {
        boolean flag = false;
        String stringForValidation = etLlNumber.getText().toString().trim();
        if (stringForValidation.length() >= 6 && stringForValidation.length() <= 11) {
            flag = true;
        }
        return flag;
    }

    public static boolean isValidMobileNo(EditText editText) {
        String chars = editText.getText().toString();
        boolean flag = false;
        if (chars.length() == 10) {
            if (chars.startsWith("9") || chars.startsWith("8") || chars.startsWith("7") || chars.startsWith("6")) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isValidPersonName(EditText editText)
    {
        String chars = editText.getText().toString().trim();
        return chars.matches("/^[a-zA-Z0-9 .&()_'’-]*$/"); // PATTERN_PERSON.matcher(chars).matches();
    }

    /**
     * Is valid email or not
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Email verification
     *
     * @param data
     * @return
     */
    public static boolean isEmail2(String data) {
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return data.matches(expr);
    }

    /**
     * Mobile phone number verification
     *
     * @param data
     * @return
     */
    public static boolean isMobileNumber(String data) {
        //String expr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String expr = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6} ";
        return data.matches(expr);
        //return Patterns.PHONE.matcher(data).matches();
    }

    /**
     * Does the string contain only letters and numbers
     *
     * @param data
     * @return
     */
    public static boolean isValidPersonName(String data) {
        String expr = "^[A-Za-z0-9 .&()_-]+$";
        return data.matches(expr);
    }

    /**
     * Does the string contain only numbers
     *
     * @param data
     * @return
     */
    public static boolean isNumber(String data) {
        String expr = "^[0-9]+$";
        return data.matches(expr);
    }

    /**
     * Does the string contain only letters
     *
     * @param data
     * @return
     */
    public static boolean isLetter(String data) {
        String expr = "^[A-Za-z]+$";
        return data.matches(expr);
    }

    /**
     * ID number verification
     *
     * @param data
     * @return
     */
    public static boolean isCard(String data) {
        String expr = "^[0-9]{17}[0-9xX]$";
        return data.matches(expr);
    }

    /**
     * Zip code verification
     *
     * @param data
     * @return
     */
    public static boolean isPostCode(String data) {
        String expr = "^[0-9]{6,10}";
        return data.matches(expr);
    }

    /**
     * Length validation
     *
     * @param data
     * @param length
     * @return
     */
    public static boolean isLength(String data, int length) {

        return data != null && data.length() == length;
    }


}
