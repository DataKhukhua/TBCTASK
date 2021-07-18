import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class Test1 {
	// FIRST SCENARIO
	@Test (dataProvider = "user-data", dataProviderClass=DP.class)
	public void testUserPost(String username, String password, String checkCode, String checkMessage) {

		baseURI  = "https://bookstore.toolsqa.com/Account/v1";
		RequestSpecification request = given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("userName", username);
		requestParams.put("password", password);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post("/user");

		String code = response.jsonPath().get("code");
		String message = response.jsonPath().get("message");

		Assert.assertEquals(code, checkCode);
		Assert.assertEquals(message, checkMessage);
	}

	// SECOND SCENARIO
	@Test (dataProvider = "authorize-data", dataProviderClass=DP.class)
	public void testAuthorizePost(
			String username, 
			String password , 
			String authorizeStatus,
			String generateStatus,
			String generateResult,
			String authorizeStatus2
			)
	{

		baseURI = "https://bookstore.toolsqa.com/Account/v1";
		RequestSpecification request = given();

		JSONObject requestParams = new  JSONObject();

		requestParams.put("userName", username);
		requestParams.put("password", password);
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post("/user");

		// CHECK BOOKS IS EMPTY AND USERNAME
		String responseUsername = response.jsonPath().get("username");
		ArrayList<String> books = response.jsonPath().get("books");

		assertEquals(username, responseUsername);
		assertTrue(books.isEmpty());


		// CHECK NEW USER AUTHORIZE STATUS
		Response authorizeResponse = request.post("/Authorized");
		assertEquals(authorizeStatus, authorizeResponse.asString());

		// GENERATE TOKEN CALL
		Response generateTokenResponse = request.post("/GenerateToken");
		String statusResponse = generateTokenResponse.jsonPath().get("status");
		String resultResponse = generateTokenResponse.jsonPath().get("result");

		assertEquals(generateStatus,statusResponse);
		assertEquals(generateResult, resultResponse);

		// CHECK USER AUTHORIZE STATUS AGAIN
		Response authorizeResponse2 = request.post("/Authorized");
		assertEquals(authorizeStatus2, authorizeResponse2.asString());



	}






}
