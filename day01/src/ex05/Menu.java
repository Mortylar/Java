

import java.util.Scanner;
import java.util.UUID;

import user.User;

import transaction.Transaction;
import transaction.TransactionType;

import exception.UserNotFoundException;
import exception.TransactionNotFoundException;
import exception.IllegalTransactionException;




public class Menu {

		private static final String devProfile_ = "--profile=dev";
    
		private Scanner scanner_;
		private TransactionService service_;
    private boolean devStatus_;


		{
        service_ = new TransactionService();
				scanner_ = new Scanner(System.in);
		}

		public Menu(String[] userStatus) {
        if (userStatus.length > 0) {
            devStatus_ = (devProfile_.equals(userStatus[0]) ? true : false);
				} else {
				    devStatus_ = false;
				}
    }

    public void run() {
		   AnswerCode answer = AnswerCode.DEFAULT_CODE;
			 while (answer != AnswerCode.FINISH_CODE) {
			     printMenu();
					 answer = readAnswer();

					 if (AnswerCode.ADD_USER_CODE == answer) {
					     addUser();
					 } else if (AnswerCode.USER_BALANCE_CODE == answer) {
               userBalance();
					 } else if (AnswerCode.TRANSFER_PERFORM_CODE == answer) {
					     transferPerform();
					 } else if (AnswerCode.ALL_TRANSACTIONS_CODE == answer) {
					     allTransactions();
           } else if (AnswerCode.REMOVE_TRANSACTION_CODE == answer) {
					     removeTransaction();
					 } else if (AnswerCode.TRANSACTION_VALIDATE_CODE == answer) {
					     transactionValidate();
					 } else if (AnswerCode.FINISH_CODE != answer) {
					     System.out.print("Unknown answer\n");
					 }
					 printSeparator();
			 }
		}
  
    private void printMenu() {
        System.out.print("1. Add a user\n");
        System.out.print("2. View user balances\n");
        System.out.print("3. Perform a transfer\n");
        System.out.print("4. View all transactions for specific user\n");
        System.out.print("5. DEV - remove a transfer bu ID\n");
        System.out.print("6. DEV - check transfer validity\n");
        System.out.print("7. Finish execution\n");
    }

		private AnswerCode readAnswer() {
		    int answer = ((scanner_.hasNextInt()) ? scanner_.nextInt() : AnswerCode.DEFAULT_CODE.ordinal());
				if (scanner_.hasNextLine()) scanner_.nextLine();
				while ((answer <= AnswerCode.DEFAULT_CODE.ordinal())
               || (answer > AnswerCode.FINISH_CODE.ordinal())) {
            System.out.print("Incorrect answer code - try again\n");
				    answer = (scanner_.hasNextInt()) ? scanner_.nextInt() : AnswerCode.DEFAULT_CODE.ordinal();;
						scanner_.nextLine();
				}
				return AnswerCode.values()[answer];
		}

    private void addUser() {
		    System.out.printf("Enter a user name and a balance\n");
				String input = scanner_.nextLine();

        Scanner s = new Scanner(input).useDelimiter(" ");
				if (s.hasNext()) {
				    String userName = s.next();
				    if (s.hasNextInt()) {
						    int userBalance = s.nextInt();
								User user = new User(userName, userBalance);
								service_.addUser(user);
                System.out.printf("User with id = %d is added %s\n", user.getID(), userName);
						} else {
						    addUserErrorMessage();
						}
				} else {
				    addUserErrorMessage();
				}
		}

		private void addUserErrorMessage() {
		    System.out.print("Incorrect data - try again\n");
				addUser();
		}

		private void userBalance() {
		    System.out.print("Enter a user ID\n");
				if (scanner_.hasNextInt()) {
				    int id = scanner_.nextInt();
						try {
                User user = service_.getUser(id);
						    System.out.printf("%s - %d\n", user.getName(), user.getBalance());
						} catch (UserNotFoundException e) {
						    System.out.printf("User with id = %d not found.\n", id);
						}
				} else {
				    System.out.printf("Incorrect data\n");
						if (scanner_.hasNextLine()) {
						    scanner_.nextLine();
						}
				}
		}

    private void transferPerform() {
		    System.out.print("Enter a sender ID, a recipient ID, and a transfer amount\n");
				String input = scanner_.nextLine();
				Scanner scanner = new Scanner(input).useDelimiter(" ");
				if (scanner.hasNextInt()) {
				    int senderID = scanner.nextInt();
						if (scanner.hasNextInt()) {
						    int recepientID = scanner.nextInt();
								    if (scanner.hasNextInt()) {
										    int transferAmount = scanner.nextInt();
												try {
												    service_.transactionPerform(recepientID, senderID, transferAmount);
														System.out.print("The transfer is completed\n");
														return;
												} catch (UserNotFoundException e) {
												    System.out.printf("User with id = %d or %d not found\n", senderID, recepientID);
														return;
												} catch (IllegalTransactionException e) {
												    System.out.printf("Illegal transaction\n");
														return;
												}
										}
						}
				}
				System.out.printf("Incorrect data\n");
		}

		private void allTransactions() {
		    System.out.print("Enter a user ID\n");
				if (scanner_.hasNextInt()) {
				    int userID = scanner_.nextInt();
						scanner_.nextLine();
						try {
               User user = service_.getUser(userID);
							 user.getTransactionList().print();
						} catch (UserNotFoundException e) {
						    System.out.printf("User with id = %d not found\n", userID);
						}
				} else {
				    System.out.print("Incorrect data\n");
            if (scanner_.hasNextLine()) {
						    scanner_.nextLine();
						}
				}
		}

		private void removeTransaction() {
		    if (!devStatus_) {
				    System.out.print("Operation not permitted.\n");
						return;
				}
				System.out.print("Enter a user ID and a transfer ID\n");

				String input = scanner_.nextLine();
        Scanner scanner = new Scanner(input).useDelimiter(" ");
       
			  if (scanner.hasNextInt()) {
				    int userID = scanner.nextInt();
						if (scanner.hasNext()) {
                String uuid = scanner.next();
						    UUID transactionID = UUID.fromString(uuid);
								try {
                    Transaction tr = ((service_.getUser(userID)).getTransactionList()).get(transactionID);
								    service_.removeTransaction(transactionID, userID);
										if (tr.getTransactionType() == TransactionType.DEBIT) {
										    System.out.printf("Transfer from %s(id = %d) %d removed\n", tr.getSender().getName(),
                                           tr.getSender().getID(), tr.getTransferAmount());
										} else {
										    System.out.printf("Transfer from %s(id = %d) %d removed\n", tr.getSender().getName(),
                                           tr.getSender().getID(), tr.getTransferAmount());
										}
										return;
								} catch (UserNotFoundException e) {
								    System.out.printf("User with id = %d not found\n", userID);
										return;
								} catch (TransactionNotFoundException e) {
								    System.out.printf("Transaction with id = %s not found\n", transactionID.toString());
										return;
								}
						}
				}
				System.out.print("Incorect data\n");
		}


    private void transactionValidate() {
		    System.out.printf("Check result:\n");
				Transaction[] array = service_.validate();
				if (array.length == 0) {
				    System.out.print("Correct\n");
				} else {
				    for (int i = 0; i < array.length; ++i) {
                User recepient = array[i].getRecepient();
								User sender = array[i].getSender();
						    System.out.printf("%s(id = %d) has an unaknowledged transfer id = %s from %s(id = %d)\n",
                                   recepient.getName(), recepient.getID(), array[i].getID(),
                                   sender.getName(), sender.getID());
						}
				}
    
		}


    private void printSeparator() {
		    System.out.print("--------------------------------------\n");
		}

		public enum AnswerCode {
		    DEFAULT_CODE,
				ADD_USER_CODE,
				USER_BALANCE_CODE,
				TRANSFER_PERFORM_CODE,
				ALL_TRANSACTIONS_CODE,
				REMOVE_TRANSACTION_CODE,
				TRANSACTION_VALIDATE_CODE,
				FINISH_CODE;
		}

}
