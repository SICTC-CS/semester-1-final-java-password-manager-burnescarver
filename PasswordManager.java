import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class PasswordManager {
	private static ArrayList<User> users = new ArrayList<User>();
	private static Scanner ui = new Scanner(System.in);
	private static User system_user = null;

	public static void main(String[] args){
		//initializing FileWriter
		//help from Geeks for Geeks -> (https://www.geeksforgeeks.org/io-bufferedwriter-class-methods-java/)
		// FileWriter dataSave;
		// dataSave = new FileWriter("dataSave.txt");

		// Initializing BufferedWriter
		// BufferedWriter fileEdit = new BufferedWriter(dataSave);
		
		if (system_user == null) {
			System.out.println("No admin account found, create one now");
			system_user = createSystemUser();
		}

		if (!loginUser())
			System.exit(1);

		boolean should_continue = true;
		while (should_continue) {
			System.out.print("Account Options\n\t1. List\n\t2. Create\n\t3. Inspect\n\t4. Update\n\t5. Delete\n\t6. Exit\nChoice: ");
			int choiceAction = ui.nextInt();
			ui.nextLine();
			switch (choiceAction) {
				case 1:
					listUsers();
					break;
				case 2:
					users.add(createUser());
					break;
				case 3:
					inspectUser();
					break;
				case 4:
					updateUser();
					break;
				case 5:
					deleteUser();
					break;
				case 6:
					should_continue = false;
					break;
				default:
					System.out.println("Invalid action, please try again");
					break;
			}
		}
		ui.close();
	}

	public static void listUsers() {
		ArrayList<String> categories = new ArrayList<String>();

		for (User user: users)
			if (!categories.contains(user.category))
				categories.add(user.category);

		for (String category: categories) {
			System.out.println(category);
			for (User user: users)
				if (user.category == category)
					System.out.println(user);
		}
	}

	public static User createUser() {
		System.out.print("Account Name: ");
		String name = ui.nextLine().trim();

		System.out.print("Username: ");
		String username = ui.nextLine().trim();

		System.out.print("Category: ");
		String category = ui.nextLine().trim();

		String password;
		System.out.println("Password Requirements\n\t- At least one capital\n\t- At least one number\n\t- At least one special character (!@#$%&*?)\n\tAt least 8 characters long");
		do {
			System.out.print("Password (Empty for random): ");
			password = ui.nextLine().trim();

			if (password.isBlank()) {
				password = Password.generate(name, username, category);
				break;
			}
		} while (!Password.check(password));

		return new User(username, name, password, category, null);
	}

	public static User createSystemUser() {
		System.out.print("Username: ");
		String username = ui.nextLine().trim();

		String password;
		System.out.println("Password Requirements\n\t- At least one capital\n\t- At least one number\n\t- At least one special character (!@#$%&*?)\n\tAt least 8 characters long");
		do {
			System.out.print("Password: ");
			password = ui.nextLine().trim();
		} while (!Password.check(password));

		return new User(username, "Admin", password, "System", null);
	}

	public static void inspectUser() {
		int choice = userChoice();
		if (choice == -1)
			return;
		User chosen = users.get(choice);

		System.out.printf("%s (%s):\n\tUsername: %s\n\tPassword: %s\n", 
				chosen.name, chosen.category, 
				chosen.username, chosen.password);
	}

	public static boolean loginUser() {
		System.out.println("Log in");

		boolean found_user = false;
		for (int i = 0; i < 3; i++) {
			System.out.print("Username: ");
			String username = ui.nextLine().trim();

			if (system_user.username == username) {
				found_user = true;
				break;
			}
			
			System.out.println("Invalid username, try again");
		}

		if (!found_user) {
			System.out.println("Login failed");
			return false;
		}

		for (int i = 0; i < 3; i++) {
			System.out.print("Password: ");
			String password = ui.nextLine().trim();

			if (system_user.password == password) {
				System.out.println("Login successful");
				return true;
			}
		}

		System.out.println("Login failed");
		return false;
	}

	public static void updateUser(){
		System.out.println("UNFINISHED!!!!!");
	}

	public static void deleteUser(){
		int choice = userChoice();
		if (choice == -1)
			return;

		System.out.print("Confirm [y/N]: ");
		char input = Character.toLowerCase((char)ui.nextByte());

		if (input == 'y') {
			users.remove(choice);
			System.out.println("Delete successful");
		}
		else
			System.out.println("Delete failed");
	}

	public static int userChoice() {
		if (users.size() < 1) {
			System.out.println("No users to choose from");
			return -1;
		}
		for (int i = 0; i < users.size(); i++) {
			System.out.printf("%d. %s\n", i + 1, users.get(i));
		}
		System.out.print("User: ");

		int choice;
		do {
			choice = ui.nextInt();
			ui.nextLine();
		} while (choice < 1 || choice > users.size());

		return choice - 1;
	}

	public static int userChoice(String category) {
		System.out.println(category);
		ArrayList<User> category_users = new ArrayList<User>();

		for (User user: users)
			if (user.category == category)
				category_users.add(user);

		if (category_users.size() < 1) {
			System.out.println("No users to choose from");
			return -1;
		}

		for (int i = 0; i < category_users.size(); i++) {
			if (category_users.get(i).category != category) {
				i--;
				continue;
			}
			System.out.printf("%d. %s\n", i + 1, category_users.get(i));
		}
		System.out.print("User: ");

		int choice;
		do {
			choice = ui.nextInt();
			ui.nextLine();
		} while (choice < 1 || choice > category_users.size());

		return users.indexOf(category_users.get(choice - 1));
	}
}
