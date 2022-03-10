package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils(){}

    public static int getCVV() {
        return getRandomInt(011, 999);
    }

    public static String getCardNumber() {
        String newCardNumber;
        newCardNumber = Integer.toString(getRandomInt(1000, 9999));
        newCardNumber += " " + getRandomInt(1000, 9999);
        newCardNumber += " " + getRandomInt(1000, 9999);
        newCardNumber += " " + getRandomInt(1000, 9999);
        return newCardNumber;
    }

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
