package demo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import pojos.GraphqlQuery;
import pojos.QueryVariable;

public class PojoCalls {
	
	@BeforeClass
	public void declareBaseURI() {
		baseURI = "https://countries.trevorblades.com/";
	}
	
	@Test
	public void testPojoForCountry() {
		
		GraphqlQuery graphqlQuery = new GraphqlQuery();
		QueryVariable queryVariable = new QueryVariable();
		queryVariable.setCountryCode("US");
		
		graphqlQuery.setQuery("query getCountry($countryCode: ID!){\r\n"
								+ "country(code: $countryCode) {\r\n"
								+ "		code\r\n"
								+ "		name\r\n"
								+ "		capital\r\n"
								+ "	  }\r\n"
								+ "}");
		graphqlQuery.setVariables(queryVariable);
		System.out.println(graphqlQuery);
		
		given()
			.contentType(ContentType.JSON)
			.body(graphqlQuery)
		.when()
			.post()
		.then()
			.assertThat()
			.statusCode(200)
			.body("data.country.code", equalTo(queryVariable.getCountryCode()))
			.log().all();
		
	}
}
