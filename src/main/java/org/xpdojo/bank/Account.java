package org.xpdojo.bank;

import java.io.StringWriter;

import static org.xpdojo.bank.Result.failure;
import static org.xpdojo.bank.Result.success;

public class Account {

    private Money balance;

    public static Account emptyAccount() {
        return accountWithBalance(Money.ZERO);
    }

    public Money balance() {
        return balance;
    }

    public void deposit(Money amount) {
        balance = balance.plus(amount);
    }

    public Result withdraw(Money amount) {
        if (balance.isLessThan(amount)) {
            return failure();
        }
        balance = balance.minus(amount);
        return success();
    }

    public void transfer(Money amount, Account receiver) {
        Result withdrawal = withdraw(amount);
        if (withdrawal.succeeded()) {
            receiver.deposit(amount);
        }
    }


    private Account(Money balance) {
        this.balance = balance;
    }

    static Account accountWithBalance(Money balance) {
        return new Account(balance);
    }

    public String generate(Statement statement) {
        StringWriter writer = new StringWriter();
        statement.writeTo(writer);
        return writer.toString();
    }
}
