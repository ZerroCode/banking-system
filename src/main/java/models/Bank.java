package models;

import java.util.ArrayList;
import java.util.Calendar;

import accounts.CDAccount;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import exceptions.InvalidAccountException;
import exceptions.InvalidAmountException;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class Bank {

    private final ArrayList<Account> bank;
    private static double totalAmountInSavingsAccts = 0;
    private static double totalAmountInCheckingAccts = 0;
    private static double totalAmountInCDAccts = 0;
    private static double totalAmountInAllAccts = 0;

    public Bank() {
        this.bank = new ArrayList<>();
    }

    public ArrayList<Account> getAccounts() {
        return bank;
    }

    public Account getAcct(int index) {
        Account acct = new Account();
        if (bank.get(index).getAcctType().equals("CD")) {
            acct = new CDAccount((CDAccount) bank.get(index));
        }
        if (bank.get(index).getAcctType().equals("Savings")) {
            acct = new SavingsAccount((SavingsAccount) bank.get(index));
        }
        if (bank.get(index).getAcctType().equals("Checking")) {
            acct = new CheckingAccount((CheckingAccount) bank.get(index));
        }
        return acct;
    }

    public int getNumAccts() {
        return bank.size();
    }

    public static double getTotalAmountInSavingsAccts() {
        return totalAmountInSavingsAccts;
    }

    public static double getTotalAmountInCheckingAccts() {
        return totalAmountInCheckingAccts;
    }

    public static double getTotalAmountInCDAccts() {
        return totalAmountInCDAccts;
    }

    public static double getTotalAmountInAllAccts() {
        return totalAmountInAllAccts;
    }

    public static void addToStaticAmount(double amount, String acctType) {
        switch (acctType) {
            case "Savings":
                totalAmountInSavingsAccts += amount;
                break;
            case "Checking":
                totalAmountInCheckingAccts += amount;
                break;
            case "CD":
                totalAmountInCDAccts += amount;
                break;
        }
        totalAmountInAllAccts += amount;
    }

    public static void subToStaticAmount(double amount, String acctType) {
        switch (acctType) {
            case "Savings":
                totalAmountInSavingsAccts -= amount;
                break;
            case "Checking":
                totalAmountInCheckingAccts -= amount;
                break;
            case "CD":
                totalAmountInCDAccts -= amount;
                break;
        }
        totalAmountInAllAccts -= amount;
    }

    private int findAcct(int requestedAccount) {
        for (int index = 0; index < bank.size(); index++) {
            if (bank.get(index).getAcctNum() == requestedAccount) {
                return index;
            }
        }
        return -1;
    }

    public TransactionReceipt getBalance(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        return bank.get(index).getBalance(ticket);
    }

    public TransactionReceipt makeDeposit(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        return bank.get(index).makeDeposit(ticket);
    }

    public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        return bank.get(index).makeWithdrawal(ticket);
    }

    public TransactionReceipt clearCheck(Check check) {
        int index = findAcct(check.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + check.getAcctNum() + " does not exist");
        }
        if (!bank.get(index).getAcctType().equals("Checking")) {
            throw new InvalidAccountException("Cannot clear check from non-Checking account");
        }
        CheckingAccount account = (CheckingAccount) bank.get(index);
        return account.clearCheck(check);
    }

    public TransactionReceipt openNewAcct(Account account) {
        Calendar today = Calendar.getInstance();
        String todayStr = String.format("%02d/%02d/%4d",
                today.get(Calendar.MONTH) + 1,
                today.get(Calendar.DAY_OF_MONTH),
                today.get(Calendar.YEAR));
        TransactionTicket ticket = new TransactionTicket(account.getAcctNum(), todayStr, "Open New Account", account.getBalance(), 0);

        for (Account value : bank) {
            if (value.equals(account)) {
                throw new InvalidAccountException("Account number " + account.getAcctNum() + " already exists");
            }
        }

        bank.add(account);
        if (account.getAcctType().equals("CD")) {
            CDAccount cdAccount = (CDAccount) account;
            TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", cdAccount.getAcctType(), 0, cdAccount.getBalance(), cdAccount.getMaturityDateStr());
            bank.get(bank.size() - 1).addTransaction(receipt);
            addToStaticAmount(cdAccount.getBalance(), cdAccount.getAcctType());
            return receipt;
        }

        TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", account.getAcctType(), 0, account.getBalance());
        bank.get(bank.size() - 1).addTransaction(receipt);
        addToStaticAmount(account.getBalance(), account.getAcctType());
        return receipt;
    }

    public TransactionReceipt deleteAcct(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());

        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        if (bank.get(index).getBalance() != 0) {
            throw new InvalidAmountException("Account number " + ticket.getAcctNum() + " has a non-zero balance");
        }

        bank.remove(index);
        return new TransactionReceipt(ticket, true, "", "", 0, 0);
    }

    public TransactionReceipt acctInfo(TransactionTicket ticket) {
        int count = 0;

        for (Account value : bank) {
            if (value.getDepositor().getSSN().equals(ticket.getAcctSSN())) {
                count++;
            }
        }
        if (count == 0) {
            return new TransactionReceipt(ticket, false, "No accounts found for SSN " + ticket.getAcctSSN(), null);
        }

        Account[] matchingAccounts = new Account[count];
        int index = 0;
        for (Account account : bank) {
            if (account.getDepositor().getSSN().equals(ticket.getAcctSSN())) {
                if (account.getAcctType().equals("CD")) {
                    matchingAccounts[index++] = new CDAccount((CDAccount) account);
                } else if (account.getAcctType().equals("Savings")) {
                    matchingAccounts[index++] = new SavingsAccount((SavingsAccount) account);
                } else if (account.getAcctType().equals("Checking")) {
                    matchingAccounts[index++] = new CheckingAccount((CheckingAccount) account);
                }
            }
        }
        return new TransactionReceipt(ticket, true, "", matchingAccounts);
    }

    public TransactionReceipt reopenAcct(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        return bank.get(index).reopenAcct(ticket);
    }

    public TransactionReceipt closeAcct(TransactionTicket ticket) {
        int index = findAcct(ticket.getAcctNum());
        if (index == -1) {
            throw new InvalidAccountException("Account number " + ticket.getAcctNum() + " does not exist");
        }
        return bank.get(index).closeAcct(ticket);
    }
}
