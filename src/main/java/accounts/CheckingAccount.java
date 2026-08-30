package accounts;

import java.util.Calendar;

import exceptions.AccountClosedException;
import exceptions.CheckTooOldException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.PostDatedCheckException;
import models.Account;
import models.Bank;
import models.Check;
import models.Depositor;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super();
    }

    public CheckingAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance) {
        super(depositor, acctNum, acctType, acctStatus, balance);
    }

    public CheckingAccount(CheckingAccount account) {
        super(account);
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

        if (balance < 2500) {
            TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - (ticket.getTransactionAmount() + 1.50));
            balance -= ticket.getTransactionAmount() + 1.50;
            addTransaction(receipt);
            Bank.subToStaticAmount(ticket.getTransactionAmount() + 1.50, acctType);
            return receipt;
        }

        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount());
        balance -= ticket.getTransactionAmount();
        addTransaction(receipt);
        Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
        return receipt;
    }

    public TransactionReceipt clearCheck(Check check) {
        Calendar today = Calendar.getInstance();
        Calendar sixMonthsAgo = (Calendar) today.clone();
        sixMonthsAgo.add(Calendar.MONTH, -6);
        TransactionTicket ticket = new TransactionTicket(check.getAcctNum(), check.getDateOfCheckStr(), "Clear Check", check.getCheckAmount(), 0);

        if (acctStatus.equals("closed")) {
            throw new AccountClosedException("Account is closed");
        }
        if (check.getCheckAmount() <= 0) {
            throw new InvalidAmountException("Invalid Check Amount");
        }
        if (check.getDateOfCheck().after(today)) {
            throw new PostDatedCheckException("Check not cleared - Post-dated check: " + check.getDateOfCheckStr());
        }
        if (check.getDateOfCheck().before(sixMonthsAgo)) {
            throw new CheckTooOldException("Check is older than six months");
        }
        if (check.getCheckAmount() > balance) {
            balance -= 2.50;
            Bank.subToStaticAmount(2.50, acctType);
            throw new InsufficientFundsException("Insufficient Funds Available - Bounce Fee ($2.50) Charged");
        }

        if (balance < 2500) {
            balance -= check.getCheckAmount() + 1.50;
            TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance + check.getCheckAmount() + 1.50, balance);
            addTransaction(receipt);
            Bank.subToStaticAmount(check.getCheckAmount() + 1.50, acctType);
            return receipt;
        }

        balance -= check.getCheckAmount();
        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance + check.getCheckAmount(), balance);
        addTransaction(receipt);
        Bank.subToStaticAmount(check.getCheckAmount(), acctType);
        return receipt;
    }
}
