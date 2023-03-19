package com.br.authentication.enums;

public enum UserStatusType {
    ACTIVE(0),
    IN_ACTIVE(1),
    NOT_VERIFY(2);

    private final int value;

    UserStatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
