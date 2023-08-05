package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuhtInfo {
        String login;
        String password;
    }

    public static AuhtInfo getAuhtInfo() {
        return new AuhtInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode(AuhtInfo auhtInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String cardNumber;
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }
}
