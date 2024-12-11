import java.util.Scanner;
import java.util.ArrayList;

public class PasswordManager {
	private static ArrayList<User> users = new ArrayList<User>();
	private static Scanner ui = new Scanner(System.in);

	public static void main(String[] args){
		users = DataManager.loadData();

		User system_user = null;
		for (User user: users)
			if (user.category.equals("System"))
				system_user = user;

		if (system_user == null) {
			System.out.println("No admin account found, create one now");
			system_user = createSystemUser();
			users.add(system_user);
		} 
		else if (!loginUser()) 
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
					DataManager.saveData(users);
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
			System.out.println("-".repeat(category.length()));
			for (User user: users)
				if (user.category.equals(category))
					System.out.printf("\t%s: %s\n", user.name, user.username);
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
		System.out.println("Password Requirements");
		System.out.println("\t- At least one capital");
		System.out.println("\t- At least one number");
		System.out.println("\t- At least one special character (!@#$%&*?)\n");
		System.out.println("\t- At least 8 characters long");
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
		System.out.println("Password Requirements");
		System.out.println("\t- At least one capital");
		System.out.println("\t- At least one number");
		System.out.println("\t- At least one special character (!@#$%&*?)\n");
		System.out.println("\t- At least 8 characters long");
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

		User system_user = new User();
		for (User user: users)
			if (user.category.equals("System")) {
				system_user = user;
				break;
			}

		boolean found_user = false;
		for (int i = 0; i < 3; i++) {
			System.out.print("Username: ");
			String username = ui.nextLine().trim();

			if (system_user.username.equals(username)) {
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

			if (system_user.password.equals(password)) {
				System.out.println("Login successful");
				return true;
			}
			System.out.println("Invalid password, try again");
		}

		System.out.println("Login failed");
		return false;
	}

	public static void updateUser(){
		int choice = userChoice();
		if (choice == -1)
			return;

		User user = users.get(choice);
		
		boolean choosing = true;
		do {
			System.out.println("Edit Property: ");
			System.out.println("\t1. Name: " + user.name);
			System.out.println("\t2. Username: " + user.username);
			System.out.println("\t3. Category: " + user.category);
			System.out.println("\t4. Password: " + user.password);
			System.out.println("\t5. Done");

			System.out.print("Choice: ");
			int prop_choice = ui.nextInt();
			ui.nextLine();
			
			switch (prop_choice) {
				case 1:
					System.out.print("Enter new name: ");
					user.name = ui.nextLine().trim();
					break;
				case 2:
					System.out.print("Enter new username: ");
					user.username = ui.nextLine().trim();
					break;
				case 3:
					System.out.print("Enter new category: ");
					user.category = ui.nextLine().trim();
					break;
				case 4:
					String password;
					System.out.println("Password Requirements");
					System.out.println("\t- At least one capital");
					System.out.println("\t- At least one number");
					System.out.println("\t- At least one special character (!@#$%&*?)\n");
					System.out.println("\t- At least 8 characters long");
					do {
						System.out.print("Enter new password (Empty for random): ");
						password = ui.nextLine().trim();

						if (password.isBlank()) {
							password = Password.generate(user.name, user.username, user.category);
							break;
						}
					} while (!Password.check(password));
					user.password = password;
					break;
				case 5:
					choosing = false;
					break;
				default:
					System.out.println("Invalid Property, try again");
					break;
			}
		} while (choosing);
	}

	public static void deleteUser(){
		int choice = userChoice();
		if (choice == -1)
			return;

		System.out.print("Confirm [y/N]: ");
		char input = Character.toLowerCase(ui.nextLine().trim().charAt(0));

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

		System.out.println("\t#. Name (Category): Username");
		System.out.println("\t----------------------------");

		for (int i = 0; i < users.size(); i++) {
			System.out.printf("\t%d. %s\n", i + 1, users.get(i));
		}
		System.out.print("Choice: ");

		int choice;
		do {
			choice = ui.nextInt();
			ui.nextLine();
		} while (choice < 1 || choice > users.size());

		return choice - 1;
	}
}
