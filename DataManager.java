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

		String current_category = "System";
		for (String data = null; reader.hasNextLine(); data = reader.nextLine()) {
			if (data == null)
				data = reader.nextLine();

			
		}

		reader.close();
		return users;
	}
}
