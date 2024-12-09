public class User {
	public String username;
	public String name;
	public String password;
	public String category;
	public String hint;

	public User() {}
	public User(String username, String name, String password, String category, String hint) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.category  = category;
		this.hint = hint;
	}

	@Override
	public String toString() {
		return String.format("\t%s: %s\n", this.name, this.username);
	}
}
