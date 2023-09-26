package day1;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

//static packages for direct use of static methods
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.ObjectOutputStream;
import java.util.HashMap;

/*
 * We user given() for content type, setting cookies , add auth , add param , set headers etc
 * for when()  we use get(), post(), put (), delete
 * for then () , we validate, status code, extract response, extract headers , cookies response bodies
 */
public class HTTPRequest {
	
	private int id;
	@Test(priority = 4)
	void getUsers() {
		given()
		
		.when()
			.get("https://reqres.in/api/users?page=2")
		
		.then()
			.statusCode(200)
			.body("per_page",equalTo(6))
			.log().all();
	}
	
	@Test(priority = 1)
	void postUser() {
		/**
		 * We are using this method to post user using REST
		 * Assured, for now I am using Hashmap.
		 */
		/*
		 * For testing purpose we are using Hashmap
		 * otherwise we don't hard code the data
		 */
		HashMap map = new HashMap();
		map.put("name", "AkhilTest");
		map.put("job","Supreme Leader Kim-Jon-Un");

		
		String response = given()
			.contentType("application/json")
			.body(map)
			
		.when()
			.post("https://reqres.in/api/users")
		.then()
			.statusCode(201)
			.log().all()
			.extract().response().asString();
		JsonPath jsonPath = new JsonPath(response);
		id = jsonPath.getInt("id");
		System.out.println("The id of the new user is :"+id);
	}
	
	@Test(priority = 2)
	void updateUser() {
		HashMap<String,String> map = new HashMap();
		map.put("name","SupremeLeaderAkhil");
		map.put("job","Saviour of North Korea");
		
		given()
			.contentType("application/json")
			.body(map);
		when()
			.put("https://reqres.in/api/users/"+id)
		
		.then()
			.statusCode(200)
			.log().all();
		
	}
	
	@Test(priority = 3)
	void getSingleUser() {
		given()
		
		.when()
			.get("https://reqres.in/api/users/"+id)
		.then()
			.statusCode(200)
			.log().all();
	}
	
	
}
