package config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ahaidaralbaqir
 */
public class Database {
	private String host;
	private int port;
	private String	username;
	private String password;
	private String dbName;

	public Database () {
		this.host = "localhost";
		this.port = 3306;
		this.username = "root";
		this.password = "password";	
		this.dbName = "inventaris";
	}

	public String getHost () {
		return this.host;
	}

	public int getPort () {
		return this.port;
	}

	public String getUsername () {
		return this.username;
	}

	public String getPassword () {
		return this.password;
	}
	
	public String getDatabaseName () {
		return this.dbName;
	}
}
