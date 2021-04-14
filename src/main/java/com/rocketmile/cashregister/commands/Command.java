package com.rocketmile.cashregister.commands;

import com.rocketmile.cashregister.models.Bill;

import java.util.List;

public interface Command {

    void execute(List<Bill> bills, int[] billInputs);
}
