package exceptions;

import static org.junit.Assert.assertThrows;

import java.util.Calendar;

import org.junit.Test;

import accounts.CDAccount;
import accounts.CheckingAccount;
import models.Account;
import models.Bank;
import models.Depositor;
import models.Name;
import transactions.TransactionTicket;

public class BankingExceptionsTest {

    @Test
    public void depositRejectsInvalidAmount() {
        Account account = new Account(new Depositor(new Name("Smith", "John"), "123-45-6789"), 1001, "Savings", "open", 500.0);
        TransactionTicket ticket = new TransactionTicket(1001, "08/30/2026", "Deposit", 0.0, 0);

        assertThrows(InvalidAmountException.class, () -> account.makeDeposit(ticket));
    }

    @Test
    public void withdrawalRejectsClosedAccount() {
        Account account = new Account(new Depositor(new Name("Smith", "John"), "123-45-6789"), 1001, "Savings", "closed", 500.0);
        TransactionTicket ticket = new TransactionTicket(1001, "08/30/2026", "Withdrawal", 100.0, 0);

        assertThrows(AccountClosedException.class, () -> account.makeWithdrawal(ticket));
    }

    @Test
    public void withdrawalRejectsInsufficientFunds() {
        Account account = new Account(new Depositor(new Name("Smith", "John"), "123-45-6789"), 1001, "Savings", "open", 50.0);
        TransactionTicket ticket = new TransactionTicket(1001, "08/30/2026", "Withdrawal", 100.0, 0);

        assertThrows(InsufficientFundsException.class, () -> account.makeWithdrawal(ticket));
    }

    @Test
    public void bankRejectsMissingAccount() {
        Bank bank = new Bank();
        TransactionTicket ticket = new TransactionTicket(9999, "08/30/2026", "Balance Inquiry", 0.0, 0);

        assertThrows(InvalidAccountException.class, () -> bank.getBalance(ticket));
    }

    @Test
    public void checkingCheckRejectsPostDatedCheck() {
        CheckingAccount account = new CheckingAccount(new Depositor(new Name("Doe", "Jane"), "987-65-4321"), 2001, "Checking", "open", 1200.0);
        modelCheck check = new modelCheck(2001, 100.0, "09/30/2027");

        assertThrows(PostDatedCheckException.class, () -> account.clearCheck(check));
    }

    @Test
    public void checkingCheckRejectsOldCheck() {
        CheckingAccount account = new CheckingAccount(new Depositor(new Name("Doe", "Jane"), "987-65-4321"), 2001, "Checking", "open", 1200.0);
        modelCheck check = new modelCheck(2001, 100.0, getPastDateString(7));

        assertThrows(CheckTooOldException.class, () -> account.clearCheck(check));
    }

    @Test
    public void cdRejectsWithdrawalBeforeMaturity() {
        CDAccount account = new CDAccount(new Depositor(new Name("Lee", "Sam"), "111-22-3333"), 3001, "CD", "open", 500.0, "12/31/2027");
        TransactionTicket ticket = new TransactionTicket(3001, "08/30/2026", "Withdrawal", 100.0, 0);

        assertThrows(CDMaturityDateException.class, () -> account.makeWithdrawal(ticket));
    }

    private static String getPastDateString(int monthsAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthsAgo);
        return String.format("%02d/%02d/%4d",
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.YEAR));
    }

    private static class modelCheck extends models.Check {
        public modelCheck(int acctNum, double amount, String dateStr) {
            super(acctNum, amount, dateStr);
        }
    }
}
