package models;

import java.util.ArrayList;

import exceptions.AccountClosedException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import generators.genAccount;
import models.Depositor;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class Account extends genAccount {

    public Account() {
        super();
    }

    public Account(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance) {
        super(depositor, acctNum, acctType, acctStatus, balance);
    }

    public Account(Account account) {
        super(account);
    }

    public String toString() {
        return String.format("%10s  %8d  %9s %7s  $%7.2f",
                depositor,
                acctNum,
                acctType,
                acctStatus,
                balance
        );
    }

    public boolean equals(Account account) {
        return depositor.equals(account.depositor) && acctNum == account.acctNum;
    }

    public void addTransaction(TransactionReceipt receipt) {
        acctHistory.add(receipt);
    }

    public TransactionReceipt getBalance(TransactionTicket ticket) {
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
        addTransaction(receipt);
        return receipt;
    }

    public TransactionReceipt makeDeposit(TransactionTicket ticket) {
        if (acctStatus.equals("closed")) {
            throw new AccountClosedException("Account is closed");
        }
        if (ticket.getTransactionAmount() <= 0) {
            throw new InvalidAmountException("Invalid Deposit Amount");
        }

        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance + ticket.getTransactionAmount());
        balance += ticket.getTransactionAmount();
        addTransaction(receipt);
        Bank.addToStaticAmount(ticket.getTransactionAmount(), acctType);
        return receipt;
    }

    public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
        if (acctStatus.equals("closed")) {
            throw new AccountClosedException("Account is closed");
        }
        if (ticket.getTransactionAmount() <= 0) {
            throw new InvalidAmountException("Invalid Withdrawal Amount");
        }
        if (ticket.getTransactionAmount() > balance) {
            throw new InsufficientFundsException("account does not contain sufficient funds");
        }

        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount());
        balance -= ticket.getTransactionAmount();
        addTransaction(receipt);
        Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
        return receipt;
    }

    public TransactionReceipt reopenAcct(TransactionTicket ticket) {
        if (acctStatus.equals("open")) {
            TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is already open", acctType, balance, balance);
            addTransaction(receipt);
            return receipt;
        }
        acctStatus = "open";
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
        addTransaction(receipt);
        return receipt;
    }

    public TransactionReceipt closeAcct(TransactionTicket ticket) {
        if (acctStatus.equals("closed")) {
            TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is already closed", acctType, balance, balance);
            addTransaction(receipt);
            return receipt;
        }
        acctStatus = "closed";
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
        addTransaction(receipt);
        return receipt;
    }

    public ArrayList<TransactionReceipt> getTransactionHistory(TransactionTicket ticket) {
        ArrayList<TransactionReceipt> history = new ArrayList<>();
        for (TransactionReceipt receipt : acctHistory) {
            if (!receipt.getTransactionTicket().getDateOfTransaction().after(ticket.getDateOfTransaction())) {
                history.add(receipt);
            }
        }
        return history;
    }
}
