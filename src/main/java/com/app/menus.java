package com.app;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.pojo.account;
import com.pojo.customer;
import com.service.accountService;
import com.service.customerService;

public class menus {
	
	private static Scanner scanner = new Scanner(System.in);
	private static customerService cService = new customerService();
	private static accountService aService = new accountService();
	private static customer customerTemp = new customer();
	
	
	public static void loginPrompt() {
		customerTemp = new customer();
		System.out.println(" ______________________");
		System.out.println("|      Login Menu      |\n"
						 + "|______________________|\n\n"
				+ "1. Login\n"
				+ "2. Create account\n"
				+ "3. Employee login\n"
				+ "4. Exit\n"
				+ "\nPlease select a menu option: ");
		int option = 0;
		
		try {
			option = Integer.parseInt(scanner.nextLine());
			if ((option <= 0) || (option > 3)) {
				System.out.println("Invalid input - please try another number");
				loginPrompt();
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input - please try another number");
			loginPrompt();
		}
		
		switch(option) {
		case 1:
			loginScreen();
			break;
		case 2: 
			createUser();
			break;
		case 3:
			employeeScreen();
			break;
		case 4:
			System.out.println("Thanks for visiting your virtual bank! :)");
			System.exit(1);
			break;
			
		}
	}

		
		
//////////////Implement Login Screen////////////////////////
	
	static void loginScreen() {
		System.out.println("Enter your Username:");
		String userName = scanner.nextLine();
		System.out.println("Enter your Password:");
		String password = scanner.nextLine();
		
		customer c = new customer();
		
		if(cService.findOne(userName) != null) {
			c = cService.findOne(userName);
			if(c.getUser_Password().equals(password)) {
				customerTemp = c;
				accountsScreen();
			} else {
				System.out.println("Incorrect username or password");
				loginPrompt();
			}
		}	
		else
		{
			System.out.println("Incorrect username or password");
			loginPrompt();
		}
	}
	
	
	static void accountsScreen() {
		if(!aService.getAll().contains(null)) {
			LinkedList<account> temp = new LinkedList<account>();
			int counter = 5;
			int option = 0;
			System.out.print("______Accounts Menu______\n"
					+ "1. Add account\n"
					+ "2. View account\n"
					+ "3. Remove account\n"
					+ "4. Logout\n"
					+ "5. Exit\n");
			
			List<account> accountsList = aService.getAll();
			
			for(account acc : accountsList) {			
				if(customerTemp.getUserId() == acc.getCustomer_id()) {
					System.out.print(counter + ". " + acc.getAccount_type() + " account " + acc.getAccount_Name() +"\n");
					acc.setCounter(counter);
					temp.add(acc);
					counter++;
				}
			}
				
			try {
				option = Integer.parseInt(scanner.nextLine());
				if ((option <= 0) || (option > counter)) {
					System.out.println("Invalid input - please try another number");
					accountsScreen();
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input - please try another number");
				accountsScreen();
			}
			
			if (option > counter) {
				System.out.println("Invalid input - please try another number");
				accountsScreen();
				
			} else if(option == 1) {
				createAccount();
				
			} else if(option == 2) {
				viewAccount();
				
			} else if(option == 3) {
				temp = deleteAccount(temp);
				accountsScreen();
			}
			
			else if(option == 4) {
				loginPrompt();
			}
			
			else if(option == 5) {
				System.out.println("Thanks for visiting your virtual bank! :)");
				System.exit(1);
			
			} else {
				for(account act : temp) {
					if(act.getCounter() == option) {
						accountOptions(act);
						break;
					}
				}
			}
		}
		else {
			createAccount();
		}
	}
	
	static LinkedList<account> deleteAccount(LinkedList<account> obj) {
		int counter = 2;
		int option = 0;
		System.out.println("_____Remove_____\n"
							+ "1. Back");
		for(account acc : obj) {
			System.out.print(counter + ". " + acc.getAccount_type() + " account " + acc.getAccount_Name() +"\n");
			acc.setCounter(counter);
			counter++;
		}
		
		try {
			option = Integer.parseInt(scanner.nextLine());
			if ((option <= 0) || (option > counter)) {
				System.out.println("Invalid input - please try another number");
				deleteAccount(obj);
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid input - please try another number");
			deleteAccount(obj);
		}
		
		
		if(option == 1) {
			accountsScreen();
		} else {
			for(account act : obj) {
				if(act.getCounter() == option) {
					aService.delete(act);
					break;
				}
			}
		}
		
		
		
		return obj;
	}
	
	static void accountOptions(account obj) {
		System.out.println("______Menu______\n"
				+ "1. Withdraw\n"
				+ "2. Deposit\n"
				+ "3. View Balance\n"
				+ "4. Back to Accounts Menu\n"
				+ "5. Logout\n"
				+ "6. Exit");
		int option = 0;
		
		try {
			option = Integer.parseInt(scanner.nextLine());
			if ((option <= 0) || (option > 6)) {
				System.out.println("Invalid input - please try another number\n");
				accountOptions(obj);
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input - please try another number\n");
			accountOptions(obj);
		}
		
		if(option <= 6) {
			double amount = 0;
			switch(option)
			{ 
			case 1: 
				System.out.println("Please enter amount: ");
				try {
					String input = scanner.nextLine();
					try {
						if (input.contains("+")) {
							throw new myException();
						}
						
					} catch (myException e) {
						System.out.println("Invalid input\n");
						accountOptions(obj);
					}
					
					
					amount = Double.parseDouble(input);
					
					if(amount <= 0) {
						System.out.println("Invalid input\n");
						accountOptions(obj);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input\n");
					accountOptions(obj);
				}
				
				if(amount < obj.getBalance()) {
					obj.setBalance(obj.getBalance() - amount);
					
				
				} else if (amount > obj.getBalance()) {
					System.out.println("The amount you've entered is greater than account total. Avoid debt - please enter another number:");
					accountOptions(obj);
				}
				
				aService.update(obj);
				
				accountOptions(obj);
				
				break;
				
				
			case 2: 
				System.out.println("Please enter amount:");
				try {
					amount = Double.parseDouble(scanner.nextLine());
					if (amount < 0) {
						System.out.println("Invalid input\n");
						accountOptions(obj);
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input\n");
					accountOptions(obj);
				}
				
				obj.setBalance(obj.getBalance() + amount);
				
				aService.update(obj);
				
				accountOptions(obj);
				break;
				
			case 3:
				System.out.println(obj.getBalance() + "\n");
				accountOptions(obj);
				break;
			case 4:
				accountsScreen();
				break;
			case 5:
				customerTemp = null;
				loginPrompt();
				break;
			case 6:
				System.out.println("Thanks for visiting your virtual bank! :)");
				System.exit(1);
				break;
			}
		} else {
			System.out.println("Invalid input - please enter another option");
		}
		
	}
	
	
///////////////////////////Implement Creating a User////////////////////////////////////	
	static void createUser()	
	{
		customer cus = new customer();
		Boolean check = true;
		System.out.println("Enter your First Name:");		
		String firstname = scanner.nextLine();		
		
		if (badLength(firstname)) {
			System.out.println("You've entered too many characters, please shorten");
			createUser();
		}
		
		cus.setFirstName(firstname);		
		
		System.out.println("Enter your Last Name:");
		String lastname = scanner.nextLine();
		
		if (badLength(lastname)) {
			System.out.println("You've entered too many characters, please shorten");
			createUser();
		}
		
		cus.setLastName(lastname);
				
		do {
			System.out.println("Create Username: ");
			String username = scanner.nextLine();
			
			if (badLength(username)) 
			{
				System.out.println("You're Username is too long, please shorten");
				createUser();
			} else if(cService.findOne(username) == null) {
				
				cus.setUser_Username(username);
				check = false;
			} else {
				System.out.println("You're Username is not unique, please try again");
			}
		
		} while(check == true);
				
		
		System.out.println("Create Password: ");
		String password = scanner.nextLine();
		
		if (badLength(password)) {
			System.out.println("You're password is too long, please shorten");
			createUser();
		}
		
		cus.setUser_Password(password);
		
		cService.save(cus);
		customerTemp = cus;
		accountsScreen();
	}
	
	private static Boolean badLength(String obj) {
		if (obj.length() > 50) {
			return true;
		}
		
		return false;
	}
	static void viewAccount() {
		createAccount();
	}
	static void createAccount() {
		account acc = new account();
		
		System.out.println("Enter account name:");
		String accountName = scanner.nextLine();
		
		if (badLength(accountName)) {
			System.out.println("You're Account Name is too long, please shorten");
			createAccount();
		}
		
		acc.setAccount_Name(accountName);
		
		System.out.println("______Choose Account Type_____\n"
				+ "1. Checking\n"
				+ "2. Savings\n"
				+ "3. Loans");
		
		int option = 0;
		String accountType;
		try {
			option = Integer.parseInt(scanner.nextLine());
			if ((option <= 0) || (option > 3)) {
				System.out.println("Invalid input please try another number");
				createAccount();
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid input - please try another number");
			createAccount();
		}
		
		switch(option) {
		case 1:
			accountType = "Checking";
			acc.setAccount_type(accountType);
			break;
		case 2:
			accountType = "Savings";
			acc.setAccount_type(accountType);
			break;
		case 3:
			accountType = "Loans";
			acc.setAccount_type(accountType);
			break;		
		}
		
		acc.setCustomer_id(customerTemp.getUserId());
		
		aService.save(acc);
		accountOptions(acc);
	}
	
///////////Implement Employee Login//////////////////
static void employeeScreen() {
	System.out.println("Enter your Username: ");
	String userName = scanner.nextLine();
	System.out.println("Enter your Password: ");
	String password = scanner.nextLine();
	
	if (userName.equals("Stephen") && password.equals("password")) {	
		System.out.println("______Employee Menu______\n"
				+ "1. View all Customer Accounts\n"
				+ "2. Approve or Reject an account\n"
				+ "3. View Transaction Logs\n"
				+ "4. Logout\n"
				+ "5. Exit System"
				);
		} else {
			System.out.println("You have entered an incorrect Username and Password");
		}
	List<account> accountsList = aService.getAll();
	if(!aService.getAll().contains(null)) {
		LinkedList<account> temp = new LinkedList<account>();
		int counter = 5;
		int option = 0;
	for(account acc : accountsList) {			
		if(customerTemp.getUserId() == acc.getCustomer_id()) {
			System.out.print(counter + ". " + acc.getAccount_type() + " account " + acc.getAccount_Name() +"\n");
			acc.setCounter(counter);
			temp.add(acc);
			counter++;
			}
		}		
	try {
		option = Integer.parseInt(scanner.nextLine());
		if ((option <= 0) || (option > counter)) {
			System.out.println("Invalid input - please try another number");
			accountsScreen();
		}
	} catch (NumberFormatException e) {
		System.out.println("Invalid input - please try another number");
		accountsScreen();
	}
	
	if (option > counter) {
		System.out.println("Invalid input - please try another number");
		accountsScreen();
		
	} else if(option == 1) {
		createAccount();
		
	} else if(option == 2) {
		viewAccount();
		
	} else if(option == 3) {
		accountsScreen();
	}
	
	else if(option == 4) {
		loginPrompt();
	}
	
	else if(option == 5) {
		System.out.println("Thanks for visiting your virtual bank! :)");
		System.exit(1);
	
	} else {
		for(account act : temp) {
			if(act.getCounter() == option) {
				accountOptions(act);
				break;
			}
		}
	}
}
else {
	createAccount();
}
}

}
