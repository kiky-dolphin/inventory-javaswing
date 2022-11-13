package usecase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import dto.LoginDTO;
import dto.RegisterDTO;
public class User {
	private Connection conn;
	public User(Connection conn) {
		this.conn = conn;
	}

	public void register(RegisterDTO payload) throws Exception {
		try {
			PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO `inventaris`.`users` (`username`, `password`, `level`) VALUES (?, ?, ?)");
			stmt.setString(1, payload.username);
			stmt.setString(2, payload.password);
			stmt.setInt(3, payload.level);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	public void login(LoginDTO payload) throws Exception {
		try {
			PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM users LEFT JOIN profile ON profile.id_users = users.id WHERE username = ? AND password = ?");
			stmt.setString(1, payload.getUsername());
			stmt.setString(2, payload.getPassword());
			ResultSet results =  stmt.executeQuery();
			while (results.next()) {
				
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
