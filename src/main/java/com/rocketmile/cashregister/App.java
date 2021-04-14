package com.rocketmile.cashregister;

import com.rocketmile.cashregister.exceptions.*;
import com.rocketmile.cashregister.models.Bill;
import com.rocketmile.cashregister.service.CashRegisterService;
import com.rocketmile.cashregister.service.impl.CashRegisterServiceImpl;
import com.rocketmile.cashregister.utils.CashRegisterConstants;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        CashRegister cashRegister = new CashRegister();
        CashRegisterService cashRegisterService = new CashRegisterServiceImpl();

        Scanner scanner = new Scanner(System.in);
        boolean runCashRegister = true;

        System.out.println("ready");

        while(runCashRegister) {
            System.out.print("> ");
            String input = scanner.nextLine();

            Map<String, String> inputMap = cashRegisterService.splitCommandAndBillCount(input);
            String command = inputMap.get(CashRegisterConstants.KEY_COMMAND);
            String strBillInput = inputMap.get(CashRegisterConstants.KEY_BILL_COUNT);

            try {
                List<Bill> bills = cashRegister.getBills();
                int[] intBillInput = cashRegisterService.getBillsToProcess(command, strBillInput, bills);
                cashRegister.getCommands().get(command).execute(bills, intBillInput);
            } catch (InvalidCommandException | BillInputCountException |
                    NotEnoughChangeException | NotEnoughBillsException |
                    BillInputFormatException e) {
                System.out.println("Sorry, " + e.getMessage());
            }
        }
    }
}
