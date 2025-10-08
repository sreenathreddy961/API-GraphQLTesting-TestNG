package demo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SampleTesting {
	
//	baseURI = "https://reqres.in/api/users?page=2";

	@Test
	public void test1() {
		Response response = RestAssured.get("https://reqres.in/api/users?page=2");
		System.out.println(response.asPrettyString());
	}
	
	@Test
	public void testGet() {
		given()
			.contentType(ContentType.JSON)
		.when()
			.get("https://reqres.in/api/users?page=2")
		.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("data.first_name", hasItems("Michael","Lindsay","Tobias"))
			.body("data.first_name", hasSize(6))
			.log().status();
	}
	
	@Test
	public void testPost() {
		
		baseURI = "https://petstore.swagger.io/v2/";
		given()
			.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "  \"id\": 0,\r\n"
					+ "  \"category\": {\r\n"
					+ "    \"id\": 0,\r\n"
					+ "    \"name\": \"string\"\r\n"
					+ "  },\r\n"
					+ "  \"name\": \"Tiger\",\r\n"
					+ "  \"photoUrls\": [\r\n"
					+ "    \"string\"\r\n"
					+ "  ],\r\n"
					+ "  \"tags\": [\r\n"
					+ "    {\r\n"
					+ "      \"id\": 0,\r\n"
					+ "      \"name\": \"string\"\r\n"
					+ "    }\r\n"
					+ "  ],\r\n"
					+ "  \"status\": \"available\"\r\n"
					+ "}")
		.when()
			.post("/pet")
		.then()
			.statusCode(200)
			.log().all();
		
	}
}
