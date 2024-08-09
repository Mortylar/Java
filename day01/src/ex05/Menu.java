

import java.util.Scanner;

import user.User;

import transaction.Transaction;

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
            devStatus_ = (devProfile_.equals(userStatus) ? true : false);
				} else {
				    devStatus_ = false;
				}
    }

    public void run() {
		   AnswerCode answer = AnswerCode.DEFAULT_CODE;
			 while (answer != AnswerCode.FINISH_CODE) {
			     printMenu();
					 answer = AnswerCode.values()[scanner_.nextInt()]; //TODO
					 scanner_.nextLine();

					 if (answer == AnswerCode.ADD_USER_CODE) {
					     addUser();
					 }

			 }
		}
  
    private void printMenu(){}

    private void addUser() {
		    System.out.printf("Enter a user name and a balance\n");
				String input = scanner_.nextLine();
				char separator = ' ';
				int separatorIndex = input.lastIndexOf(separator);
        if ((separatorIndex > 0) && (separatorIndex < input.length())) {
				    String name = input.substring(0, separatorIndex);
				    int balance = Integer.parseInt(input.substring(separatorIndex + 1));


				    service_.addUser(new User(name, balance));
				    System.out.printf("User with id = %d is added %s\n", balance, name);
				} else {
            //TODO
        }
		}




		public enum AnswerCode {
		    DEFAULT_CODE,
				ADD_USER_CODE,
				USER_BALANCE_CODE,
				TRANSFER_PERFORM_CODE,
				ALL_TRANSACTION_CODE,
				REMOVE_TRANSACTION_CODE,
				TRANSACTION_VALIDATE_CODE,
				FINISH_CODE;
		}

}
