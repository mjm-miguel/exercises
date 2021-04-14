package com.rocketmile.cashregister.service;

import com.rocketmile.cashregister.exceptions.*;
import com.rocketmile.cashregister.models.Bill;

import java.util.List;
import java.util.Map;

public interface CashRegisterService {

    boolean hasEnoughBills(List<Bill> bills, int[] billInputs);

    boolean isValidCommand(String command);
    boolean isValidInputBillCount(int count);
    boolean isBillInputNumeric(String[] bills);

    Map<String, String> splitCommandAndBillCount(String input);

    int[] getBillsToProcess(String command, String billInputs, List<Bill> bills) throws
            InvalidCommandException, BillInputCountException, NotEnoughChangeException,
            NotEnoughBillsException, BillInputFormatException;
    int[] convertStringBillToIntArray(String input) throws BillInputFormatException;
    int[] prepareChangeBill(List<Bill> bills, int change) throws NotEnoughChangeException;

    int getTotalAmount(List<Bill> bills);
}
