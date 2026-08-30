package accounts;

import java.util.Calendar;

import exceptions.AccountClosedException;
import exceptions.CDMaturityDateException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import models.Bank;
import models.Depositor;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class CDAccount extends SavingsAccount {

    private Calendar maturityDate;

    public CDAccount() {
        super();
        maturityDate = Calendar.getInstance();
    }

    public CDAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance, String dateStr) {
        super(depositor, acctNum, acctType, acctStatus, balance);
        maturityDate = Calendar.getInstance();
        maturityDate.clear();
        String[] dateArray = dateStr.split("/");
        maturityDate.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1, Integer.parseInt(dateArray[1]));
    }

    public CDAccount(CDAccount account) {
        super(account);
        this.maturityDate = account.maturityDate;
    }

    public Calendar getMaturityDate() {
        return maturityDate;
    }

    public String getMaturityDateStr() {
        return String.format("%02d/%02d/%4d",
                maturityDate.get(Calendar.MONTH) + 1,
                maturityDate.get(Calendar.DAY_OF_MONTH),
                maturityDate.get(Calendar.YEAR));
    }

    public String toString() {
        String str = super.toString();
        str += String.format("%16s", getMaturityDateStr());
        return str;
    }

    public TransactionReceipt getBalance(TransactionTicket ticket) {
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance, getMaturityDateStr());
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
        if (ticket.getDateOfTransaction().before(maturityDate)) {
            throw new CDMaturityDateException("CD maturity date " + getMaturityDateStr() + " not reached");
        }

        maturityDate = Calendar.getInstance();
        maturityDate.add(Calendar.MONTH, ticket.getTermOfCD());
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance + ticket.getTransactionAmount(), getMaturityDateStr());
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
        if (ticket.getDateOfTransaction().before(maturityDate)) {
            throw new CDMaturityDateException("CD maturity date " + getMaturityDateStr() + " not reached");
        }

        maturityDate = Calendar.getInstance();
        maturityDate.add(Calendar.MONTH, ticket.getTermOfCD());
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount(), getMaturityDateStr());
        balance -= ticket.getTransactionAmount();
        addTransaction(receipt);
        Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
        return receipt;
    }
}
