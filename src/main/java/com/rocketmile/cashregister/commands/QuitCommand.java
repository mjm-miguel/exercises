package com.rocketmile.cashregister.commands;

import com.rocketmile.cashregister.models.Bill;

import java.util.List;

public class QuitCommand implements Command {
    @Override
    public void execute(List<Bill> bills, int[] billInputs) {
        System.out.println("Bye");
        System.exit(1);
    }
}
