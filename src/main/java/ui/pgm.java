package ui;

import java.io.*;
import java.util.Calendar;
import java.util.Scanner;
import models.Bank;
import models.Name;
import models.Account;
import models.Depositor;
import models.Check;
import accounts.CDAccount;
import accounts.SavingsAccount;
import accounts.CheckingAccount;
import transactions.TransactionTicket;
import transactions.TransactionReceipt;

/**
 * Main application entry point for the Banking System.
 * Provides user interface and transaction processing.
 */
public class pgm {

	public static void main(String[] args) throws IOException
	{

		//variable declarations
	    char choice;									               //menu item selected
	    boolean notDone = true;						                   //loop control flag

	    // open input test cases file
	    File testFile = resolveInputFile("myTestCases.txt");

        //create Scanner object
	    Scanner kybd = new Scanner(testFile);

	    //create the Bank object
	    Bank bankOfAmerica = new Bank();
	    readAccts(bankOfAmerica);

	    // open the output file
	    PrintWriter outFile = new PrintWriter(resolveOutputFile("pgmOutput.txt"));

	    /* print initial database */
	    printAccts(bankOfAmerica,outFile);

	    /* prompts for a transaction and then */
	    /* call functions to process the requested transaction */
	    do {
	        menu();
	        choice = kybd.next().charAt(0);
	        switch(choice)
	        {
	            case 'q':
	            case 'Q':
	                notDone = false;
	                printAccts(bankOfAmerica,outFile);
	                break;
	            case 'b':
	            case 'B':
	                balance(bankOfAmerica,outFile,kybd);
	                break;
	            case 'd':
	            case 'D':
	                deposit(bankOfAmerica,outFile,kybd);
	                break;
	            case 'w':
	            case 'W':
	                withdrawal(bankOfAmerica,outFile,kybd);
	                break;
	            case 'n':
	            case 'N':
	                newAcct(bankOfAmerica,outFile,kybd);
	                break;
	            case 'x':
	            case 'X':
	                deleteAcct(bankOfAmerica,outFile,kybd);
	                break;
	            case 'i':
	            case 'I':
	            	accountInfo(bankOfAmerica,outFile,kybd);
	                break;
	            case 'c':
	            case 'C':
	            	clearCheck(bankOfAmerica,outFile,kybd);
	                break;
	            case 'h':
	            case 'H':
	            	acctInfoHistory(bankOfAmerica,outFile,kybd);
	                break;
	            case 's':
	            case 'S':
	            	closeAcct(bankOfAmerica,outFile,kybd);
	                break;
	            case 'r':
	            case 'R':
	            	reopenAcct(bankOfAmerica,outFile,kybd);
	                break;
	            default:
	                outFile.println("Error: " + choice + " is an invalid selection -  try again");
	                outFile.println();
	                outFile.flush();
	                break;
	        }
	        // give user a chance to look at output before printing menu
	        //pause(kybd);
	    } while (notDone);

	    //close the output file
	    outFile.close();

	    //close the test cases input file
	    kybd.close();

	    System.out.println();
	    System.out.println("The program is terminating");
	}

	/* Method readAccts()
	 * Input:
	 *  array - reference to the temporary array
	 * Process:
	 *  Reads the initial database of accounts
	 * Output:
	 *  Fills in the initial bank account information into the temporary array and returns the number of active accounts
	 */
	public static void readAccts(Bank bank) throws IOException
	{
	    // open database input file
		//create File object
	    File dbFile = resolveInputFile("initAccounts.txt");

	    //create Scanner object
	    Scanner sc = new Scanner(dbFile);

                              //account number counter
	    String line;

	    while (sc.hasNext())
	    {
	    	line = sc.nextLine();                   //read the next line of data
	    	String[] tokens = line.split(" ");      //Tokens the line

	    	//extract the data from the line read
	    	Name name = new Name(tokens[1], tokens[0]);
	    	Depositor depositor = new Depositor(name, tokens[2]);
            switch (tokens[4]) {
                case "CD":
                    // adds CD account to array
                    {
                        CDAccount account = new CDAccount(depositor, Integer.parseInt(tokens[3]), tokens[4], tokens[5], Double.parseDouble(tokens[6]), tokens[7]);
                        bank.openNewAcct(account);
                        break;
                    }
                case "Savings":
                    // adds accounts with no maturity date to array
                    {
                        SavingsAccount account = new SavingsAccount(depositor, Integer.parseInt(tokens[3]), tokens[4], tokens[5], Double.parseDouble(tokens[6]));
                        bank.openNewAcct(account);
                        break;
                    }
                case "Checking":
                    {
                        CheckingAccount account = new CheckingAccount(depositor, Integer.parseInt(tokens[3]), tokens[4], tokens[5], Double.parseDouble(tokens[6]));
                        bank.openNewAcct(account);
                        break;
                    }
            }
	    }


	    //close the input file
	    sc.close();
	}

	/* Method printAccts:
	 * Input:
	 * bankOfAmerica - reference to the Bank object
	 * outFile - reference to the output file
	 * Process:
	 * Prints the database of bank account information
	 * Output:
	 * Prints the database of bank account information
	*/
	public static void printAccts(Bank bankOfAmerica,
								  PrintWriter outFile)
	{
	    outFile.println();
	    outFile.println("\t\tDatabase of Bank Accounts");
	    outFile.println();
	    outFile.println("LastName   FirstName         SSN   AcctNum   AcctType  Status   Balance   Maturity Date");
	    for (int index = 0; index < bankOfAmerica.getNumAccts(); index++)
	    {
	    	outFile.println((bankOfAmerica.getAcct(index).toString()));
	    }
	    outFile.println("Total Amount in Saving Accounts: " + String.format("$%.2f", Bank.getTotalAmountInSavingsAccts()));
	    outFile.println("Total Amount in Checking Accounts: " + String.format("$%.2f", Bank.getTotalAmountInCheckingAccts()));
	    outFile.println("Total Amount in CD Accounts: " + String.format("$%.2f", Bank.getTotalAmountInCDAccts()));
	    outFile.println("Total Amount in All Accounts: " + String.format("$%.2f", Bank.getTotalAmountInAllAccts()));
	    outFile.println();

	    //flush the output file
	    outFile.flush();
	}

	/* Method menu()
	 * Input:
	 *  none
	 * Process:
	 *  Prints the menu of transaction choices
	 * Output:
	 *  Prints the menu of transaction choices
	 */
	public static void menu()
	{
	    System.out.println();
	    System.out.println("Select one of the following transactions:");
	    System.out.println("\t****************************");
	    System.out.println("\t    List of Choices         ");
	    System.out.println("\t****************************");
	    System.out.println("\t     W -- Withdrawal");
	    System.out.println("\t     D -- Deposit");
	    System.out.println("\t     C -- Clear Check");
	    System.out.println("\t     N -- New Account");
	    System.out.println("\t     B -- Balance Inquiry");
	    System.out.println("\t     I -- Account Info");
	    System.out.println("\t     H -- Account Info and Transaction History");
	    System.out.println("\t     S -- Close Account");
	    System.out.println("\t     R -- Reopen Closed Account");
	    System.out.println("\t     X -- Delete Account");
	    System.out.println("\t     Q -- Quit");
	    System.out.println();
	    System.out.print("\tEnter your selection: ");
	}


	/* Method balance:
	 * Input:
	 *  bankOfAmerica - reference to array of accounts
	 *  outFile - reference to output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account
	 *  creates transaction ticket
	 *  calls the appropriate method within the bank class
	 *  If the account exists, the balance is printed
	 *  Otherwise, an error message is printed
	 * Output:
	 *  If the account exists, the balance is printed
	 *  Otherwise, an error message is printed
	 */
	public static void balance(Bank bankOfAmerica,
								PrintWriter outFile,
								Scanner kybd)
	{
	    Calendar date = Calendar.getInstance();
		String dateStr = String.format("%02d/%02d/%4d",				// sets up the date as String
				date.get(Calendar.MONTH) + 1,
				date.get(Calendar.DAY_OF_MONTH),
				date.get(Calendar.YEAR));

	    System.out.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    int requestedAccount = kybd.nextInt();						//read-in the account number

	    //create the transaction ticket
	    TransactionTicket ticket = new TransactionTicket(requestedAccount, dateStr, "Balance Inquiry", 0, 0);
	    TransactionReceipt reciept = bankOfAmerica.getBalance(ticket);

	    //output
        outFile.println(reciept);
	    outFile.println();

	    outFile.flush();				//flush the output buffer
	    }

	/* Method deposit:
	 * Input:
	 *  bankOfAmerica - reference to array of accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 *  Prompts for the requested account
	 *  creates transaction ticket
	 *  calls the appropriate method within the bank class
	 *  If the account exists, prompts for the amount to deposit
	 *  If the amount is valid, it makes the deposit and prints the new balance
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid deposit, the deposit transaction is printed
	 *  Otherwise, an error message is printed
	 */
	public static void deposit(Bank bankOfAmerica,
								PrintWriter outFile,
								Scanner kybd)
	{
		    Calendar date = Calendar.getInstance();
			String dateStr = String.format("%02d/%02d/%4d",				// sets up the date as String
					date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.YEAR));

		    System.out.println();
		    System.out.print("Enter the account number: ");			//prompt for the account number
		    int requestedAccount = kybd.nextInt();						//read-in the account number
		    System.out.print("Enter amount to Deposit: ");			//prompt for amount to withdrawal
		    double amountToDeposit = kybd.nextDouble();					//read-in the amount to withdrawal
		    System.out.print("Enter the next CD Term: ");			//prompt for the new CD Term
		    int newMaturityDate = kybd.nextInt();						//read-in the new CD Term

		    //create the transaction ticket
		    TransactionTicket ticket = new TransactionTicket(requestedAccount, dateStr, "Deposit", amountToDeposit, newMaturityDate);
		    TransactionReceipt reciept = bankOfAmerica.makeDeposit(ticket);

		    //output
	        outFile.println(reciept);
		    outFile.println();

		    outFile.flush();				//flush the output buffer

	}

	/* Method Withdrawal:
	 * Input:
	 *  bankAccount - reference to array of bank accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 *  Prompts for the requested account
	 *  creates transaction ticket
	 *  calls the appropriate method within the bank class
	 *  If the account exists, prompts for the amount to withdrawal
	 *  If the amount is valid, it makes the withdrawal and prints the new balance
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid withdrawal, the withdrawal transaction is printed
	 *  Otherwise, an error message is printed
	 */
	public static void withdrawal(Bank bankOfAmerica,
									PrintWriter outFile,
									Scanner kybd)
{
		    Calendar date = Calendar.getInstance();
			String dateStr = String.format("%02d/%02d/%4d",				// sets up the date as String
					date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.YEAR));

		    System.out.println();
		    System.out.print("Enter the account number: ");			//prompt for the account number
		    int requestedAccount = kybd.nextInt();						//read-in the account number
		    System.out.print("Enter amount to Withdrawal: ");		//prompt for amount to withdrawal
	        double amountToWithdrawal = kybd.nextDouble();					//read-in the amount to withdrawal
	        System.out.print("Enter the next CD Term: ");			//prompt for the new CD Term
		    int newMaturityDate = kybd.nextInt();						//read-in the new CD Term

		    //create the transaction ticket
		    TransactionTicket ticket = new TransactionTicket(requestedAccount, dateStr, "Withdrawal", amountToWithdrawal, newMaturityDate);
		    TransactionReceipt reciept = bankOfAmerica.makeWithdrawal(ticket);

		    //output
	        outFile.println(reciept);
		    outFile.println();

		    outFile.flush();				//flush the output buffer
	}

	/* Method clearCheck:
	 * Input:
	 *  bankAccount - reference to array of bank accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 *  Prompts for the requested account, amount to check and date of check
	 *  creates Check
	 *  calls the appropriate method within the bank class
	 *  If there were no errors between the method, prints the new balance
	 *  Otherwise, an error message is printed
	 * Output:
	 *  If there were no errors between the method, prints the new balance
	 *  Otherwise, an error message is printed
	 */
	public static void clearCheck(Bank bankOfAmerica,
									PrintWriter outFile,
									Scanner kybd)
{
		    System.out.println();
		    System.out.print("Enter the account number: ");			//prompt for the account number
		    int requestedAccount = kybd.nextInt();						//read-in the account number
		    System.out.print("Enter amount in the Check: ");		//prompt for amount to withdrawal
	        double amountToWithdrawal = kybd.nextDouble();					//read-in the amount to withdrawal
	        System.out.print("Enter the Check Date: ");				//prompt for the new CD Term
		    String dateStr = kybd.next();										//read-in the new CD Term

		    //create the transaction ticket
		    Check check = new Check(requestedAccount, amountToWithdrawal, dateStr);
		    TransactionReceipt reciept = bankOfAmerica.clearCheck(check);

		    //output
		    outFile.println(reciept);
		    outFile.println();

		    outFile.flush();				//flush the output buffer
	}

	/*Method newAcct
	 * Input:
	 *  bankAccount - reference to array of bank accounts
	 *  numAccts - number of active accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 * The method then prompts the user to enter the new depositor’s first name, last name,
social security number, account type, initial opening deposit and CD term
	 * then method will create a new account with the new account number and information provided
	 * calls the approbate method within Bank class
	 * If there were no errors between the method, prints the new account number and balance
	 * Otherwise, an error message is printed
	 * output:
	 * If there were no errors between the method, prints the new account number and balance
	 * Otherwise, an error message is printed
	 */

	public static void newAcct(Bank bankOfAmerica,
								PrintWriter outFile,
								Scanner kybd)
{
		String dateStr = "01/01/1970";
		System.out.println();
	    System.out.print("Enter the account number: ");				//prompt for a new account number
	    int requestedAccount = kybd.nextInt();							//read-in the account number
	    System.out.print("Enter the First Name: ");					//prompt for the new depositor’s first name
	    String first = kybd.next();									//read-in the first name
	    System.out.print("Enter the Last Name: ");					//prompt for the new depositor’s last name
	    String last = kybd.next();									//read-in the last name
	    System.out.print("Enter the Social Security Number: ");		//prompt for the new depositor’s SSN
	    String ssn = kybd.next();									//read-in the SSN
	    System.out.print("Enter the Account Type: ");				//prompt for the new depositor’s account type
	    String type = kybd.next();									//read-in the first name
	    System.out.print("Enter the Deposit Amount: ");				//prompt for the new depositor’s account type
	    double bal = kybd.nextDouble();								//read-in the balance
	    Name name = new Name(last, first);
	    Depositor depositor = new Depositor(name, ssn);
	    if (type.equals("CD"))										//checks if CD
		{
	    	System.out.print("Enter new CD Term: ");				//prompt for the CD term
	    	int term = kybd.nextInt();								//read-in the CD term
	    	Calendar maturityDate = Calendar.getInstance();
	    	maturityDate.add(Calendar.MONTH, term);
	    	dateStr = String.format("%02d/%02d/%4d",				//converts to string format
	    			maturityDate.get(Calendar.MONTH) + 1,
	    			maturityDate.get(Calendar.DAY_OF_MONTH),
	    			maturityDate.get(Calendar.YEAR)
						);
		}

	    //creates new account
	    Account acct = new Account();
	    if (type.equals("CD")) {
	        // adds CD account to array
	        acct = new CDAccount(depositor, requestedAccount, type, "open", bal, dateStr);
	    } else if (type.equals("Savings")) {
	        acct = new SavingsAccount(depositor, requestedAccount, type, "open", bal);
	    } else if (type.equals("Checking")) {
	        acct = new CheckingAccount(depositor, requestedAccount, type, "open", bal);
	    }

	    // calls the new account method in Bank class
	    TransactionReceipt reciept = bankOfAmerica.openNewAcct(acct);

	    //output
	    outFile.println(reciept);
	    outFile.println();

	    outFile.flush();				//flush the output buffer
	}


	/*Method deleteAcct
	 * Input:
	 *  bankAccount - reference to array of bank accounts
	 *  numAccts - number of active accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 * prompt for account number
	 * creates transaction ticket
	 * calls the appropriate method within the bank class
	 * If error was detected, method prints out an error message
	 * otherwise prints out that it was successful
	 * output:
	 * If error was detected, method prints out an error message
	 * otherwise prints out that it was successful
	 */
	public static void deleteAcct(Bank bankOfAmerica,
								PrintWriter outFile,
								Scanner kybd)
	{
		System.out.println();
	    System.out.print("Enter the account number: ");					//prompt for a new account number
	    int requestedAccount = kybd.nextInt();								//read-in the account number

	  //create the transaction ticket
	  TransactionTicket ticket = new TransactionTicket(requestedAccount, "01/01/1970", "Delete Account", 0, 0);
	  TransactionReceipt reciept = bankOfAmerica.deleteAcct(ticket);

	  //output
	    outFile.println(reciept);
	    outFile.println();

	    outFile.flush();				//flush the output buffer
	}

	/*Method accountInfo
	 * Input:
	 *  bankAccount - reference to array of bank accounts
	 *  numAccts - number of active accounts
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:
	 * prompts for SSN
	 * creates transaction ticket
	 * calls the appropriate method within the bank class
	 * If error was detected, method prints out an error message
	 * otherwise, prints the complete account information for all of the accounts of the SSN
	 * output:
	 * If error was detected, method prints out an error message
	 * otherwise, prints the complete account information for all of the accounts of the SSN
	 *
	 */
	public static void accountInfo(Bank bankOfAmerica,
									PrintWriter outFile,
									Scanner kybd)
	{
		System.out.println();
	    System.out.print("Enter the Social Security Number: "); 			//
	    String ssn = kybd.next();											//

	    // Create a transaction ticket
	    TransactionTicket ticket = new TransactionTicket(ssn, "Account Info");
	    TransactionReceipt receipt = new TransactionReceipt(bankOfAmerica.acctInfo(ticket));

	    //output
	    outFile.println(receipt);
	    outFile.println();
	    outFile.flush();
		}

	/**
	 * Method acctInfoHistory
	 * Input:
	 *  bankOfAmerica - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the Social Security Number (SSN) of the account holder.
	 *  Creates a transaction ticket to request account information and transaction history.
	 *  Calls the appropriate method within the Bank class to retrieve the accounts associated with the SSN.
	 *  If accounts exist, prints a formatted table with account details.
	 *  Also retrieves and prints the transaction history for each account.
	 * Output:
	 *  If the SSN exists in the database, prints the full account information along with transaction history.
	 *  Otherwise, prints an error message.
	 */
	public static void acctInfoHistory(Bank bankOfAmerica,
										PrintWriter outFile,
										Scanner kybd)
	{
		System.out.println();
	    System.out.print("Enter the Social Security Number: ");
	    String ssn = kybd.next();

	    // Create a transaction ticket
	    TransactionTicket ticket = new TransactionTicket(ssn, "Account Info With Transaction History");
	    TransactionReceipt receipt = new TransactionReceipt(bankOfAmerica.acctInfo(ticket));

	    //output
	    outFile.println(receipt);
	    outFile.println();
	    outFile.flush();
	}

	/**
	 * Method closeAcct
	 * Input:
	 *  bankOfAmerica - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account number.
	 *  Creates a transaction ticket with the "Close Account" request.
	 *  Calls the appropriate method within the Bank class to close the account.
	 *  If the account exists and is open, updates its status to closed.
	 *  If the account does not exist or is already closed, an error message is printed.
	 * Output:
	 *  If successful, prints a confirmation that the account has been closed.
	 *  Otherwise, prints an error message explaining why the account could not be closed.
	 */
	public static void closeAcct(Bank bankOfAmerica,
								PrintWriter outFile,
								Scanner kybd)
	{
	    Calendar date = Calendar.getInstance();
		String dateStr = String.format("%02d/%02d/%4d",				// sets up the date as String
				date.get(Calendar.MONTH) + 1,
				date.get(Calendar.DAY_OF_MONTH),
				date.get(Calendar.YEAR));

	    System.out.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    int requestedAccount = kybd.nextInt();						//read-in the account number

	    //create the transaction ticket
	    TransactionTicket ticket = new TransactionTicket(requestedAccount, dateStr, "Close Account", 0, 0);
	    TransactionReceipt reciept = bankOfAmerica.closeAcct(ticket);

	    //output
	    outFile.println(reciept);
	    outFile.println();

	    outFile.flush();				//flush the output buffer
	}

	/**
	 * Method reopenAcct
	 * Input:
	 *  bankOfAmerica - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account number.
	 *  Creates a transaction ticket with the "Reopen Account" request.
	 *  Calls the appropriate method within the Bank class to reopen the account.
	 *  If the account exists and is closed, updates its status to open.
	 *  If the account does not exist or is already open, an error message is printed.
	 * Output:
	 *  If successful, prints a confirmation that the account has been reopened.
	 *  Otherwise, prints an error message explaining why the account could not be reopened.
	 */
	public static void reopenAcct(Bank bankOfAmerica,
									PrintWriter outFile,
									Scanner kybd)
	{
	    Calendar date = Calendar.getInstance();
		String dateStr = String.format("%02d/%02d/%4d",				// sets up the date as String
				date.get(Calendar.MONTH) + 1,
				date.get(Calendar.DAY_OF_MONTH),
				date.get(Calendar.YEAR));

	    System.out.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    int requestedAccount = kybd.nextInt();						//read-in the account number

	    //create the transaction ticket
	    TransactionTicket ticket = new TransactionTicket(requestedAccount, dateStr, "Reopen Account", 0, 0);
	    TransactionReceipt reciept = bankOfAmerica.reopenAcct(ticket);

	    //output
	    outFile.println(reciept);
	    outFile.println();

	    outFile.flush();				//flush the output buffer
	}


	/* Method pause() */
	public static void pause(Scanner keyboard)
	{
		String tempstr;
		System.out.println();
		System.out.print("press ENTER to continue");
		tempstr = keyboard.nextLine();		//flush previous ENTER
		tempstr = keyboard.nextLine();		//wait for ENTER
	}

	private static File resolveInputFile(String fileName) throws FileNotFoundException
	{
		File localFile = new File(fileName);
		if (localFile.exists())
		{
			return localFile;
		}

		File resourcesFile = new File("src/main/java/resources", fileName);
		if (resourcesFile.exists())
		{
			return resourcesFile;
		}

		throw new FileNotFoundException("Could not locate " + fileName + " in the working directory or src/main/java/resources/");
	}

	private static File resolveOutputFile(String fileName)
	{
		File resourcesDir = new File("src/main/java/resources");
		if (resourcesDir.isDirectory())
		{
			return new File(resourcesDir, fileName);
		}
		return new File(fileName);
	}

}
