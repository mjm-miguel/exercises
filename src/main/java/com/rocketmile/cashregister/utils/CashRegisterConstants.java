package com.rocketmile.cashregister.utils;

public class CashRegisterConstants {

    public static final String TWENTY_BILL = "TWENTY_BILL";
    public static final String TEN_BILL = "TEN_BILL";
    public static final String FIVE_BILL = "FIVE_BILL";
    public static final String TWO_BILL = "TWO_BILL";
    public static final String ONE_BILL = "ONE_BILL";

    public static final String COMMAND_SHOW = "show";
    public static final String COMMAND_PUT = "put";
    public static final String COMMAND_TAKE = "take";
    public static final String COMMAND_CHANGE = "change";
    public static final String COMMAND_QUIT = "quit";

    public static final String KEY_COMMAND = "COMMAND";
    public static final String KEY_BILL_COUNT = "BILL_COUNT";

    public static final String DELIMITER = " ";

    public static final int VALID_BILL_COUNT = 5;

    private CashRegisterConstants() {
        //
    }
}
