package com.rocketmile.cashregister.service.impl;

import com.rocketmile.cashregister.exceptions.*;
import com.rocketmile.cashregister.models.Bill;
import com.rocketmile.cashregister.service.CashRegisterService;
import com.rocketmile.cashregister.utils.CashRegisterConstants;
import com.rocketmile.cashregister.utils.CashRegisterUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CashRegisterServiceImpl implements CashRegisterService {

    public boolean hasEnoughBills(List<Bill> bills, int[] billInputs) {
        boolean hasEnoughBills = true;

        for (int i=0; i<billInputs.length; i++) {
            Bill bill = bills.get(i);
            int currentPieces = bill.getPieces();

            if ((currentPieces - billInputs[i]) < 0) {
                hasEnoughBills = false;
                break;
            }
        }

        return hasEnoughBills;
    }

    public int[] getBillsToProcess(String command, String billInputs, List<Bill> bills) throws
            InvalidCommandException, BillInputCountException, NotEnoughChangeException, NotEnoughBillsException,
            BillInputFormatException {
        int[] billsToProcess = new int[5];

        // return immediately to caller if command is show/quit
        if ((CashRegisterConstants.COMMAND_QUIT.equals(command) || CashRegisterConstants.COMMAND_SHOW.equals(command))) {
            return billsToProcess;
        }

        /**
         * for change, check first if it is possible to process
         * if yes, then return to caller to proceed processing
         */
        if (CashRegisterConstants.COMMAND_CHANGE.equals(command) && billInputs.split(CashRegisterConstants.DELIMITER).length == 1) {
            return prepareChangeBill(bills, Integer.parseInt(billInputs));
        }

        /**
         * checks whether commands and bill inputs are valid
         * if not, throw exception and ask user to retry
         */
        if (CashRegisterUtility.isEmpty(command) || CashRegisterUtility.isEmpty(billInputs) || !isValidCommand(command)) {
            throw new InvalidCommandException("invalid command");
        }

        /**
         * checks if the bill count is equal to the the number of bill denomination
         * we have in our cash register
         */
        String[] inputs = billInputs.split(CashRegisterConstants.DELIMITER);
        if (!isValidInputBillCount(inputs.length)) {
            throw new BillInputCountException("bill count is either less than or more than the required number");
        }

        /**
         * checks if the bill inputs are numeric
         */
        billsToProcess = convertStringBillToIntArray(billInputs);

        /**
         * for take, check first if the register has still enough bills
         * before continue processing
         */
        if (CashRegisterConstants.COMMAND_TAKE.equals(command) && !hasEnoughBills(bills, billsToProcess)) {
            throw new NotEnoughBillsException("not enough bill on cash register");
        }

        return billsToProcess;
    }

    public boolean isValidCommand(String command) {
        if (CashRegisterConstants.COMMAND_PUT.equals(command) ||
                CashRegisterConstants.COMMAND_TAKE.equals(command)) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param count
     * @return true if the bill count is equals to 5
     * else false
     */
    public boolean isValidInputBillCount(int count) {
        if (count == CashRegisterConstants.VALID_BILL_COUNT) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param bills
     * @return true if all denominations are numeric
     * else return false
     */
    public boolean isBillInputNumeric(String[] bills) {
        boolean result = true;

        try {
            for (int i=0; i<bills.length; i++) {
                Integer.parseInt(bills[i]);
            }
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }

    public Map<String, String> splitCommandAndBillCount(String input) {
        Map<String, String> inputMap = new HashMap<String, String>();

        if (input.split(CashRegisterConstants.DELIMITER).length == 1) {
            inputMap.put(CashRegisterConstants.KEY_COMMAND, input);
            inputMap.put(CashRegisterConstants.KEY_BILL_COUNT, "0 0 0 0 0");
        } else {
            inputMap.put(CashRegisterConstants.KEY_COMMAND, input.substring(0, input.indexOf(CashRegisterConstants.DELIMITER)));
            inputMap.put(CashRegisterConstants.KEY_BILL_COUNT, input.substring(input.indexOf(CashRegisterConstants.DELIMITER) + 1));
        }

        return inputMap;
    }

    public int[] convertStringBillToIntArray(String input) throws BillInputFormatException {
        int[] converted = new int[5];

        try {
            converted = Stream.of(input.split(CashRegisterConstants.DELIMITER))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            throw new BillInputFormatException("bill numbers should be whole numbers");
        }

        return converted;
    }

    @Override
    public int[] prepareChangeBill(List<Bill> bills, int change) throws NotEnoughChangeException {
        int[] deductBill = new int[bills.size()];
        int totalBillAmount = getTotalAmount(bills);

        if (change > totalBillAmount) {
            throw new NotEnoughChangeException("not enough change");
        }

        if (change == totalBillAmount) {
            return new int[] {
                    bills.get(0).getPieces(),
                    bills.get(1).getPieces(),
                    bills.get(2).getPieces(),
                    bills.get(3).getPieces(),
                    bills.get(4).getPieces()};
        }

        for (int i=0; i<bills.size(); i++) {
            Bill bill = bills.get(i);

            int billCount = change / bill.getValue();
            int remainder = change % bill.getValue();

            if (billCount > 0) {

                if (peek(bills, i+1, remainder) == 0) {
                    deductBill[i] = billCount;
                    change = remainder;
                } else {
                    while (billCount > 0) {
                        billCount--;
                        remainder = remainder + bill.getValue();
                        if (peek(bills, i+1, remainder) == 0) {
                            deductBill[i] = billCount;
                            change = remainder;
                        }
                    }
                }
            }

            if (change == 0) {
                break;
            }
        }

        if (change != 0) {
            throw new NotEnoughChangeException("not enough change");
        }

        return deductBill;
    }

    @Override
    public int getTotalAmount(List<Bill> bills) {
        int total = 0;

        for (int i=0; i<bills.size(); i++) {
            Bill bill = bills.get(i);
            total += (bill.getPieces() * bill.getValue());
        }

        return total;
    }

    private int peek(List<Bill> bills, int startIndex, int remainder) {
        int remaining = remainder;
        if (remaining == 0) {
            return remaining;
        }

        for (int i=startIndex; i<bills.size(); i++) {
            Bill bill = bills.get(i);

            if (bill.getValue() * bill.getPieces() >= remaining) {
                remaining = remainder % bill.getValue();
            }
        }

        return remaining;
    }

}
