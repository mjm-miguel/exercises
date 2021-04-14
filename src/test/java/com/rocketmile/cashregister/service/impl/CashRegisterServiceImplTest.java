package com.rocketmile.cashregister.service.impl;

import com.rocketmile.cashregister.CashRegister;
import com.rocketmile.cashregister.models.*;
import com.rocketmile.cashregister.service.CashRegisterService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CashRegisterServiceImplTest {

    private CashRegister cashRegister = new CashRegister();
    private CashRegisterService cashRegisterService = new CashRegisterServiceImpl();

    private List<Bill> initializeBills(int $1, int $2, int $3, int $4, int $5) {
        List<Bill> bills = new ArrayList<>(5);
        TwentyBill twenty = new TwentyBill();
        TenBill ten = new TenBill();
        FiveBill five = new FiveBill();
        TwoBill two = new TwoBill();
        OneBill one = new OneBill();

        twenty.setPieces($1);
        ten.setPieces($2);
        five.setPieces($3);
        two.setPieces($4);
        one.setPieces($5);

        bills.add(twenty);
        bills.add(ten);
        bills.add(five);
        bills.add(two);
        bills.add(one);

        return bills;
    }

    @Test
    public void test_hasEnoughBills_when_cashRegisterContent_isLessThan_inputBillsToTake_expect_false() {
        Assert.assertFalse(cashRegisterService.hasEnoughBills(cashRegister.getBills(), new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    public void test_isValidCommand_when_command_Isshow_expect_false() {
        Assert.assertFalse(cashRegisterService.isValidCommand("show"));
    }

    @Test
    public void test_isValidCommand_when_command_Isput_expect_true() {
        Assert.assertTrue(cashRegisterService.isValidCommand("put"));
    }

    @Test
    public void test_isValidCommand_when_command_Istake_expect_true() {
        Assert.assertTrue(cashRegisterService.isValidCommand("take"));
    }

    @Test
    public void test_isValidCommand_when_command_Ischange_expect_false() {
        Assert.assertFalse(cashRegisterService.isValidCommand("change"));
    }

    @Test
    public void test_isValidCommand_when_command_Isquit_expect_false() {
        Assert.assertFalse(cashRegisterService.isValidCommand("quit"));
    }

    @Test
    public void test_isValidCommand_when_command_IsNotInTheChoices_expect_false() {
        Assert.assertFalse(cashRegisterService.isValidCommand("try"));
    }

    @Test
    public void test_isValidInputBillCount_when_input_isEqualTo5_expect_true() {
        Assert.assertTrue(cashRegisterService.isValidInputBillCount(5));
    }

    @Test
    public void test_isValidInputBillCount_when_input_isEqualTo1_expect_fale() {
        Assert.assertFalse(cashRegisterService.isValidInputBillCount(1));
    }

    @Test
    public void test_isBillInputNumeric_when_input_isAllNumeric_expect_true() {
        Assert.assertTrue(cashRegisterService.isBillInputNumeric(new String[] {"1", "1" ,"1", "1", "1"}));
    }

    @Test
    public void test_isBillInputNumeric_when_input_isNonNumeric_expect_false() {
        Assert.assertFalse(cashRegisterService.isBillInputNumeric(new String[] {"1", "a", "a", "a", "1"}));
    }

    @Test
    public void test_prepareChangeBill_when_input_is58_expect_Exception() throws Exception {
        int[] deduct = cashRegisterService.prepareChangeBill(initializeBills(3, 3, 3, 3, 3), 58);

        Assert.assertEquals(Arrays.toString(new int[]{2, 1, 1, 1, 1}), Arrays.toString(deduct));
    }

    @Test
    public void test_prepareChangeBill_when_input_is60_expect_assert_isTrue() throws Exception {
        int[] deduct = cashRegisterService.prepareChangeBill(initializeBills(3, 3, 3, 3, 3), 60);

        Assert.assertEquals(Arrays.toString(new int[]{3, 0, 0, 0, 0}), Arrays.toString(deduct));
    }
}