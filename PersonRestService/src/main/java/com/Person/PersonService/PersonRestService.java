package com.Person.PersonService;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

@Path("/Person")
public class PersonRestService {

	/* personList serves as temporary data storage
	 * data is stored as long as the server runs
	 * once the server restarts all data is lost
	 */
	
	private static List<Person> personList = new ArrayList<Person>();
	
	/* The service log can be found in the projects root directory: ServiceLog.log
	 * Please notice once the server runs all logging will be done by the server
	 * The serverlog file contains the JBoss logs aswell as the Service logs and can be found in the following directory:
	 * wildfly-17.0.1.Final\standalone\log\server
	 */
	
	private static Logger logger = Logger.getLogger(PersonRestService.class);
	
	@GET
	@Path("/get/{param}")
	@Produces("application/json")
	public Response getPerson(@PathParam("param") String username) {

		// Returns the Person object depending on the username
		
		logger.info("Daten für Benutzer " + username + " angefragt");
		
		String result = null;
		Person person = getPersonByUsername(username);
		
		if(person != null) {
			result = person.toString();
		
			logger.info("Daten für angefragten Benutzer " + username + " gesendet");
		
			return Response.status(201).entity(result).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response createNewPerson(JSONObject registrationData) {

		String email = registrationData.get("email").toString();
		
		if(emailValidation(email)) {
			Person person = new Person(registrationData.get("username").toString(), registrationData.get("name").toString(), registrationData.get("email").toString(), registrationData.get("password").toString());
			personList.add(person);
		
			logger.info("Neuer Benutzer registriert - ID: " + person.getUsername());
		
			return Response.status(201).build();		
		} else {
			
			logger.info("Invalide E-Mail Adresse");
			
			return Response.status(401).build();
		}
	}


	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response checkLogin(JSONObject credentials) {
		
		/* Login with username and password
		 * If the login is successful a cookie will be set
		 * And the Persondata will be loaded
		 */
		
		if(credentials.get("username") != null && credentials.get("password") != null) {
			String username = credentials.get("username").toString();
			String password = credentials.get("password").toString();
			
			if(checkCredentials(username, password)) {
				
				logger.info("Login des Benutzers: " + username + " erfolgreich");
				
				return Response.status(201).entity(true).build();		
			}
			
			logger.info("Benutzername oder Passwort ist falsch");
		}
		
		logger.info("Benutzername oder Passwort ist leer");
		
		return Response.status(404).build();		
	}
	
	@POST
	@Path("/update")
	@Consumes("application/json")
	public Response updateProfileData(JSONObject profileData) {
		
		logger.info("Benutzerdaten von " + profileData.get("username") + " werden aktualisiert");
		
		Person person = getPersonByUsername(profileData.get("username").toString());
		
		// if one field does not match its criteria the previous value will be stored an displayed
		
		String name = profileData.get("name").toString();
		if(!name.equals("") && name.length() > 2 && name.length() < 21) {
			person.setName(profileData.get("name").toString());
		}
			
		String email = profileData.get("email").toString();
		if(!email.equals("") && emailValidation(email)) {
			person.setEmail(email);
		}
		
		if(!profileData.get("password").toString().equals("")) {
			person.setPassword(profileData.get("password").toString());
		}
		
		logger.info("Benutzer " + person.getUsername() + " erfolgreich aktualisiert");
		
		return Response.status(201).build();
	}
	
	@DELETE
	@Path("/delete/{param}")
	public Response deletePersonByUsername(@PathParam("param") String username) {
		
		// Gets person to delete by its username and removes it from the personlist
		
		for(int i = 0; i < getPersonList().size(); i++) {
			if(getPersonList().get(i).getUsername().equals(username)) {
				personList.remove(i);

				logger.info("Benutzer: " + username + " wurde erfolgreich gelöscht");
				
				return Response.status(201).build();
			}
			
			logger.info("Benutzer: " + username + " konnte nicht gefunden werden");
			
			return Response.status(404).build();
		}
		
		logger.info("Es konnten keine Benutzer gefunden werden");
		
		return Response.status(401).build();
	}
	
	private Person getPersonByUsername(String username) {
		
		for(Person candidate : getPersonList()) {
			if(candidate.getUsername().equals(username)) {
				return candidate;
			}
		}
		
		return null;
	}
	
	private boolean checkCredentials(String username, String password) {
		
		Person person = getPersonByUsername(username);
		
		if(person != null && person.getPassword().equals(password)) {
			return true;
		} 
		
		return false;
	}
	
	private boolean emailValidation(String email) {
		   String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		   return email.matches(regex);
	}
	
	public static List<Person> getPersonList() {
		return personList;
	}


	public static void setPersonList(List<Person> personList) {
		PersonRestService.personList = personList;
	}
}
