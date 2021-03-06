package com.cameronstanley.javatetris.server;

import java.io.*;
import java.util.ArrayList;

public class RegistrationManager {

	private ArrayList<User> registeredUsers;
	
	private static final String DATAFILEDIRECTORY = "data";
	private static final String DATAFILENAME = "users";
	
	public RegistrationManager() {
		registeredUsers = new ArrayList<User>();
		
		try {
			// Make sure the date file directory exists; if not, create it
			File dataDirectory = new File(DATAFILEDIRECTORY);
			if (!dataDirectory.isDirectory()) {
				dataDirectory.mkdir();
			}
			
			// Make sure the data file exists; if not, create it
			File dataFile = new File(DATAFILEDIRECTORY + "/" + DATAFILENAME);
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public boolean isUserRegistered(User user) {
		return registeredUsers.contains(user);
	}
	
	public boolean registerUser(User user) {
		if (!isUserRegistered(user)) {
			registeredUsers.add(user);
			saveUsers();
			return true;
		} else {
			return false;
		}
	}
	
	public void loadUsers() {
		registeredUsers.clear();
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(DATAFILEDIRECTORY + "/" + DATAFILENAME));
			try {
				String currentLine;
				while ((currentLine = bufferedReader.readLine()) != null) {
					String[] fields = currentLine.split(",");
					
					User user = new User();
					user.setUsername(fields[0]);
					user.setPassword(fields[1]);
					registeredUsers.add(user);
				}
			} finally {
				bufferedReader.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	public void saveUsers() {
		try {
			Writer writer = new BufferedWriter(new FileWriter(DATAFILEDIRECTORY + "/" + DATAFILENAME));
			try {
				for (User user : registeredUsers) {
					writer.write(user.getUsername() + "," + user.getPassword() + "\n");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				writer.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
