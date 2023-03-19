package com.br.common.enums;

public enum UserStatus {
        ACTIVE(0),
        IN_ACTIVE(1),
        NOT_VERIFY(2);

        private final int value;

    UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
}
