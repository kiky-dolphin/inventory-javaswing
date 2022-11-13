/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import model.UserModel;

/**
 *
 * @author ahaidaralbaqir
 */
public class Session {
    private UserModel users; 
	
	public Session(UserModel users) {
		this.users = users;
	}

	public UserModel getUser() 
	{
		return this.users;
	}
}
