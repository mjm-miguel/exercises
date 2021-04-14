package com.rocketmile.cashregister;

import com.rocketmile.cashregister.commands.*;
import com.rocketmile.cashregister.models.*;
import com.rocketmile.cashregister.utils.CashRegisterConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashRegister {

    private Map<String, Command> commands;
    private List<Bill> bills;

    public CashRegister() {
        initializeBills();
        initializeCommands();
    }

    public List<Bill> getBills() {
        return bills;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void initializeBills() {
        bills = new ArrayList<Bill>();

        bills.add(new TwentyBill());
        bills.add(new TenBill());
        bills.add(new FiveBill());
        bills.add(new TwoBill());
        bills.add(new OneBill());
    }

    public void initializeCommands() {
        commands = new HashMap<String, Command>();

        commands.put(CashRegisterConstants.COMMAND_SHOW, new ShowBillsCommand());
        commands.put(CashRegisterConstants.COMMAND_PUT, new PutBillsCommand());
        commands.put(CashRegisterConstants.COMMAND_TAKE, new TakeBillsCommand());
        commands.put(CashRegisterConstants.COMMAND_CHANGE, new ChangeBillsCommand());
        commands.put(CashRegisterConstants.COMMAND_QUIT, new QuitCommand());
    }
}
