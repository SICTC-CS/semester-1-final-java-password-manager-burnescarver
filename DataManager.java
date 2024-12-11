import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// https://www.w3schools.com/java/java_files_create.asp
// https://www.w3schools.com/java/java_files_read.asp

public class DataManager {
	private final static String fileName = "DATA.txt";

	public static void saveData(ArrayList<User> users) {
		File file = new File(fileName);
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			System.out.println("Failed to create data file");
			e.printStackTrace();
			return;
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		}
		catch (IOException e) {
			System.out.println("Failed to open filewriter");
			e.printStackTrace();
			return;
		}

		ArrayList<String> categories = new ArrayList<String>();

		for (User user : users)
			if (!categories.contains(user.category))
				categories.add(user.category);

		for (String category: categories) {
			try {
			writer.write(category + "\n");
			writer.write("-".repeat(category.length()) + "\n");
			}
			catch (IOException e) {
				System.out.println("Failed to save category: " + category);
				e.printStackTrace();
			}

			for (User user: users) {
				if (user.category != category)
					continue;
				try {
					writer.write("\t" + user.name + "\n");
					writer.write("\t\t" + user.username + "\n");
					writer.write("\t\t" + user.password + "\n");
				}
				catch (IOException e) {
					System.out.println("Failed to save user: " + user);
					e.printStackTrace();
				}
			}
		}

		try {
			writer.close();
		}
		catch (IOException e) {
			System.out.println("Failed to close filewriter");
			e.printStackTrace();
		}

	}

	public static ArrayList<User> loadData() {
		File file = new File(fileName);
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			System.out.println("Failed to create data file");
			e.printStackTrace();
			return new ArrayList<User>();
		}

		Scanner reader = null;
		try {
			reader = new Scanner(file);
		}
		catch (IOException e) {
			System.out.println("Failed to open reader");
			e.printStackTrace();
			return new ArrayList<User>();
		}

		// Convert the data into an indexable format
		ArrayList<String> data = new ArrayList<String>();
		while (reader.hasNextLine())
			data.add(reader.nextLine());

		reader.close();

		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < data.size(); i++) {
			String category = data.get(i);

			String header = "-".repeat(category.length());
			if (!data.get(i + 1).matches(header))
				continue;

			try {
				if (!data.get(i + 2).startsWith("\t"))
					continue;
			}
			catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				break;
			}

			int j;
			for (j = i + 2; j < data.size(); j += 3) {
				try {
					if (!data.get(j).startsWith("\t")) {
						j = 2;
						break;
					}
				}
				catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}

				String name = data.get(j);
				String username = data.get(j + 1);
				String password = data.get(j + 2);

				users.add(new User(username.trim(), name.trim(), password.trim(), category.trim(), null));
			}
			i += j - 1;
		}

		return users;
	}
}
