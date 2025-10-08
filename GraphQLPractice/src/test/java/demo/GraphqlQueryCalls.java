package demo;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class GraphqlQueryCalls {
	
	@BeforeClass
	public void declareBaseURI() {
		baseURI = "https://countries.trevorblades.com/";
	}
	
	@DataProvider(name = "countryData")
	public Object[] sendData() {
		return new Object[] {"IN", "US"};
	}
	
	@DataProvider(name = "excelData")
	public Object [][] sendXLData() throws IOException {
		
		String filePath = "C:\\Users\\MangalamadakaSreenat\\eclipse-workspace\\GraphQLPractice\\Countries.xlsx";
		FileInputStream file = new FileInputStream(filePath);
		
		XSSFWorkbook workBook = new XSSFWorkbook(file);
		XSSFSheet countryDetails = workBook.getSheet("countryDetails");
		
		int rowCount = countryDetails.getLastRowNum();
		int colCount = countryDetails.getRow(0).getLastCellNum();
		
		Object[][] fileData = new Object[rowCount][colCount];
		
		for (int i = 0; i < rowCount; i++) {
			XSSFRow currentRow = countryDetails.getRow(i+1);
			for (int j = 0; j < colCount; j++) {
				XSSFCell currentCell = currentRow.getCell(j);
				fileData[i][j] = currentCell.getStringCellValue();
				/*
				 * switch(currentCell.getCellType()) { case STRING: fileData[i][j] =
				 * currentCell.getStringCellValue(); }
				 */
				
			}
		}		
		return fileData;
	}

	@Test(dataProvider = "countryData")
	public void testWithVariables(String countryCode) {
		
		String query = "{\"query\":\"query getCountry($countryCode: ID!){\\n    country(code: $countryCode) {\\n        name\\n        capital\\n    }\\n}\\n\",\"variables\":{\"countryCode\":\""+countryCode+"\"},\"operationName\":\"getCountry\"}";
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(query)
			.log().all()
		.when()
			.post()
		.then()
			.log().all();
	}
	
	@Test(dataProvider = "excelData")
	public void testXLData(String countryCode, String expectedName) {
		
		String query = "{\"query\":\"query getCountry($countryCode: ID!){\\n    country(code: $countryCode) {\\n        name\\n        capital\\n    }\\n}\\n\",\"variables\":{\"countryCode\":\""+countryCode+"\"},\"operationName\":\"getCountry\"}";
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(query)
		.when()
			.post()
		.then()
			.assertThat()
			.statusCode(200)
			.body("data.country.name", equalTo(expectedName))
			.log().all();	
		
	}
}
