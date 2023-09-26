package day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
//static packages for direct use of static methods
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * This class demonstrates multiple ways to create a POST request for the API.
 * <p>
 * We will be using the following methods:
 * </p>
 * <ol>
 * <li>Using HashMap</li>
 * <li>Using org.json</li>
 * <li>Using POJO (Plain Old Java Object)</li>
 * <li>Using external JSON files</li>
 * </ol>
 */
public class CreatingPOST {

	/*
	 * Using the HashMap Method First
	 */

	// @Test(priority = 1)
	public void testPOSTusingHashmap() {
		HashMap data = new HashMap();
		data.put("id", 5);
		data.put("name", "Number5");
		data.put("location", "CheckJson");
		data.put("phone", "64354684354654");
		String courseArr[] = { "C#", "ASP.NET" };
		data.put("courses", courseArr);

		given()
				.contentType("application/json")
				.body(data)
				.when()
				.post("http://localhost:3000/students")
				.then()
				.statusCode(201)
				.header("Content-Type", "application/json; charset=utf-8")
				.body("name", equalTo("Number5"))
				.body("location", equalTo("CheckJson"))
				.log().all();
	}

	// @Test
	public void deleteUser() {
		given()
				.when().delete("http://localhost:3000/students/7")
				.then()
				.statusCode(200)
				.log().all();
	}

	/**
	 * Method 2: now using org.json library for the POST method
	 * of our API we will use rest assured to test our POST method this time 
	 * 
	 */

	@Test
	public void testPostUsingJson() {

		JSONObject data = new JSONObject();

		data.put("name", "Chris Gayle");
		data.put("location", "West Indies");
		data.put("phone", "653468435");
		String coursesArr[] = { "Twenty", "Hitter" };
		data.put("courses", coursesArr);

		given()
			.contentType(ContentType.JSON)
				.body(data.toString()) 
				/*
				 * adding toString is necessary as I think API is unable to parse JSON
				 * directly here.
				 */
		.when()
				.post("http://localhost:3000/students")
		.then()
				.statusCode(201)
				.header("Content-Type", "application/json; charset=utf-8")
				.body("name", equalTo("Chris Gayle"))
				.body("location", equalTo("West Indies"))
				.log().all();
	}
	
	/**
	 * Method 3 : Let's try the POST with POJO classes
	 */
	
	//@Test
	void testPojo() {
		PojoPOST data = new PojoPOST();
		data.setName("Gautam");
		data.setLocation("DelhiDareDevils");
		data.setPhone("542316831685");
		String courses[] = {"Ranji","IPL-Winner"};
		data.setCourses(courses);
		
		given()
		.contentType("application/json")
		.body(data)
		.when()
			.put("http://localhost:3000/students/14")
		.then()
		.statusCode(201)
		.log().all();
	}
	
	/**
	 * Method 4 : POST Implementation using Json File
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test(priority = 4)
	void createPOSTusingJsonFile() throws FileNotFoundException {
		File file = new File("C:\\Users\\AKHIL\\eclipse-workspace\\RestAssuredTraining\\body.json");
		FileReader fileReader = new FileReader(file);
		JSONTokener tocken = new JSONTokener(fileReader);
		JSONObject data = new JSONObject(tocken);
		
		given()
			.contentType("application/json")
			.body(data.toString())
		.when()
			.post("http://localhost:3000/students")
        .then()
        	.statusCode(201)
        	.body("name", equalTo("John Cena"))
        	.body("location", equalTo("WWE"))
        	.body("phone", equalTo("68453146843"))
        	.log().all();
		
	}
	
	

}
