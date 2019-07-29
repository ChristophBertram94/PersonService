package com.Person.PersonService;

import org.apache.log4j.Logger;

public class Person {

	private static Logger logger = Logger.getLogger(Person.class);
	
	protected String username;
	protected String name;
	protected String email;
	protected String password;
	
	
	public Person() {
        super();
    }
	
	public Person(String username, String name, String email, String password) {
		
		/* username serves as ID
		 * uniqueness is checked while registration
		 * if the getPersonByUsername method returns an result the username is already taken
		 */
		
		setUsername(username);
		
		// the name has to be between 3 and 20 characters long
		
		setName(name);
		setEmail(email);
		setPassword(password);
		
		logger.info("Neue Person angelegt");
	}

	public String getUsername() {
		return username;
	}


	protected void setUsername(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}


	protected void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	protected void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return new StringBuffer("{\"username\":").append("\"" + this.username + "\",")
                .append("\"name\":").append("\"" + this.name + "\",")
                .append("\"email\":").append("\"" + this.email + "\",")
                .append("\"password\":").append("\"" + this.password + "\"}").toString();
    }
}
