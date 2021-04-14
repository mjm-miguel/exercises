package com.rocketmile.cashregister.commands;

import com.rocketmile.cashregister.models.Bill;

import java.util.List;

public class ShowBillsCommand implements Command {
    public void execute(List<Bill> bills, int[] billInputs) {
        int total = 0;
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<bills.size(); i++) {
            Bill bill = bills.get(i);
            total += (bill.getPieces() * bill.getValue());
            sb.append(String.format("%8d", bill.getPieces()));
        }

        System.out.println(String.format("%10s%8s%8s%8s%8s%8s","Total Amt","$20","$10","$5","$2","$1"));
        System.out.println("==================================================");
        System.out.println(String.format("%10s", "$" + total) + sb.toString());
    }
}
