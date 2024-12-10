import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// https://www.w3schools.com/java/java_files_create.asp
// https://www.w3schools.com/java/java_files_read.asp

public class DataManager {
	private final static String fileName = "DATA.txt";

	public static void saveData(User system_user, ArrayList<User> users) {
		File file = new File(fileName);
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			System.out.println("Failed to create data file");
			e.printStackTrace();
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		}
		catch (IOException e) {
			System.out.println("Failed to open filewriter");
			e.printStackTrace();
		}
		if (writer == null)
			return;

		try {
			writer.write(system_user.category + "\n");
			writer.write("-".repeat(system_user.category.length()) + "\n");
			writer.write("\t" + system_user.name + "\n");
			writer.write("\t\t" + system_user.username + "\n");
			writer.write("\t\t" + system_user.password + "\n");
		}
		catch (IOException e) {
			System.out.println("Failed to save system user");
			e.printStackTrace();
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

	public static ArrayList<User> loadData(User system_user) {
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<String> data = new ArrayList<String>();

		File file = new File(fileName);
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		}
		catch (IOException e) {
			System.out.println("Failed to open reader");
			e.printStackTrace();
		}
		if (reader == null)
			return null;

		// Convert the data into an indexable format
		for (String line = null; reader.hasNextLine(); line = reader.nextLine())
			data.add(line);

		reader.close();

		for (int i = 1; i < data.size(); i++) {
			String category = data.get(i);

			String header = "-".repeat(category.length());
			if (!data.get(i + 1).matches(header))
				continue;

			int j;
			for (j = i + 2; j < data.size(); j++) {
				String name = data.get(j);
				if (!name.startsWith("\t") || name.startsWith("\t\t"))
					continue;

				String username = data.get(j + 1);
				int username_offset;
				for (username_offset = 1; !username.startsWith("\t\t"); username_offset++)
					username = data.get(j + username_offset);
				
				String password = data.get(j + username_offset);
				for (int k = username_offset; !password.startsWith("\t\t"); k++)
					password = data.get(j + k);

				users.add(new User(username, name, password, category, null));
			}
			i += j;
		}

		return users;
	}
}
