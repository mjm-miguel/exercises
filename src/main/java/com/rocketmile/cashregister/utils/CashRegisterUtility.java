package com.rocketmile.cashregister.utils;

public class CashRegisterUtility {

    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        } else if (str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
