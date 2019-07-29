package com.Person.Tests;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.Person.PersonService.Person;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;


public class PersonRestServiceTest {

	private final String CONTEXT_PATH="/PersonService/rest/Person";
	
	//Some dummy data
	private final String USERNAME = "shroud";
	private final String NAME = "Chris";
	private final String EMAIL = "testmail@gmail.com";
	private final String PASSWORD = "!test123";
	
	@BeforeClass
    public static void init() {
       RestAssured.baseURI="http://localhost";
       RestAssured.port=8080;
    }


	@SuppressWarnings("unchecked")
	@Test
	public void testCrud() {
		
		/* Please notice if ONE of the CRUD tests fails ALL of them fail!
		 * 
		 * Unfortunately it is not easily possible to test all functions indepedent
		 * as they depend on each other and therefore need to be executed in an 
		 * specific order
		 */
		
		// Test creation of new Person
		
		JSONObject testPerson = new JSONObject();
		testPerson.put("username", USERNAME);
		testPerson.put("name", NAME);
		testPerson.put("email", EMAIL);
		testPerson.put("password", PASSWORD);
		
		given()
		.contentType("application/json")
		.accept("application/json")
		.body(testPerson)
		.when()
		.post(CONTEXT_PATH + "/register")
		.then()
		.assertThat()
		.statusCode(201);
	
		// Test getting person by username
		
		get(CONTEXT_PATH + "/get/" + USERNAME)
		.then()
	    .assertThat()
	    .statusCode(201)
	    .body("name", equalTo(NAME))
	    .body("email", equalTo(EMAIL))
	    .body("password", equalTo(PASSWORD));
		
		// Test login with credentials
		
		JSONObject testLogin = new JSONObject();
		testLogin.put("username", USERNAME);
		testLogin.put("password", PASSWORD);
		
		given()
		.contentType("application/json")
		.body(testLogin)
		.when()
		.post(CONTEXT_PATH + "/login")
		.then()
		.assertThat()
		.statusCode(201);
		
		// Test updating existing person
		
		String name = "Marvin";
		String email = "brandNewMail@gmail.com";
		String password = "test123";
		
		JSONObject updatedUserData = new JSONObject();
		updatedUserData.put("username", USERNAME);
		updatedUserData.put("name", name);
		updatedUserData.put("email", email);
		updatedUserData.put("password", password);
		
		given()
		.contentType("application/json")
		.body(updatedUserData)
		.when()
		.post(CONTEXT_PATH + "/update")
		.then()
		.assertThat()
		.statusCode(201);
		
		// Test deleting person by username
		
		given()
		.contentType("application/json")
		.delete(CONTEXT_PATH + "/delete/" + USERNAME)
		.then()
		.assertThat()
		.statusCode(201);
		
	}
	
	@Test
	public void testPersonCreation() {
		Person person = new Person(USERNAME, NAME, EMAIL, PASSWORD);
		
		assertEquals(person.getUsername(), USERNAME);
		assertEquals(person.getName(), NAME);
		assertEquals(person.getEmail(), EMAIL);
		assertEquals(person.getPassword(), PASSWORD);
		
	} 
} 
