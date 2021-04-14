package com.rocketmile.cashregister.commands;

import com.rocketmile.cashregister.models.Bill;

import java.util.List;

public class ChangeBillsCommand implements Command {
    public void execute(List<Bill> bills, int[] billInputs) {
        for (int i=0; i<billInputs.length; i++) {
            Bill bill = bills.get(i);
            int currentPieces = bill.getPieces();
            bill.setPieces(currentPieces - billInputs[i]);
        }
    }
}
