package dto;

public class RegisterDTO {
	public String username;
	public String password;
	public int level;
	public RegisterDTO(String username, String password, int level) {
		this.username = username;
		this.password = password;
		this.level = level;
	}
}
