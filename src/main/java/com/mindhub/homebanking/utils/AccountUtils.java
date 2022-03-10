package com.mindhub.homebanking.utils;

public final class AccountUtils {

    private AccountUtils(){}

    public static String getAccountNumber() {
        String newAccountNumber;
        newAccountNumber = "VIN-" + getRandomInt(10000000, 99999999);
        return newAccountNumber;
    }

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
