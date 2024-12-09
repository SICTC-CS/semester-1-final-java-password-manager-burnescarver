import java.util.Random;

public class Password {
	public static String generate(String name, String username, String category) {
		Random rand = new Random();

		char[] password = new char[50];
		for (char i = 0; i < 50; i++) {

			String sample = null;
			switch (rand.nextInt(3)) {
				case 0:
					sample = name;
					break;
				case 1:
					sample = username;
					break;
				case 2:
					sample = category;
					break;
				default:
					break;
			}

			char c = sample.charAt(rand.nextInt(sample.length()));
			password[i] = (char)(((c * i) % ('~' - '!')) + '!');
		}

		return new String(password);
	}

	public static boolean check(String password) {
		boolean hasCapital = false;
		boolean hasNumber = false;
		boolean hasSpecial = false;

		for (byte c: password.getBytes())
			if (Character.isUpperCase((char)c))
				hasCapital = true;
			else if (Character.isDigit((char)c))
				hasNumber = true;

		if (password.matches(".*[!@#$%&*?].*"))
			hasSpecial = true;
		
		boolean charLen = (password.length() >= 8);

		return (hasCapital && hasNumber && hasSpecial && charLen);
	}
}
