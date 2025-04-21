package api;

import java.util.Map;
import org.apache.logging.log4j.*;
import org.json.simple.JSONObject;
import org.junit.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testbase {

	protected static RequestSpecification httprequest;
	protected static Response response;
	protected static String filepath;
	protected static ExtentReports report;
	protected static ExtentTest test;
	protected static JSONObject js;
	private static Logger logger = LogManager.getLogger(Reports.class);
	final String url = "";
//-------------------------------------------------------------------------------------------------------------------//	

	protected String bearerToken;

	public Testbase() {
		httprequest = RestAssured.given();
	}

	// Method to set the bearer token
	protected void setBearerToken(String token) {
		bearerToken = token;
		httprequest.header("Authorization", "Bearer " + bearerToken);
	}

	protected String login(String baseURI, Map<String, String> requestBody) {
		String fullURI = url + baseURI;
	    httprequest.baseUri(fullURI);
	    httprequest.header("Content-Type", "application/json");
	    httprequest.body(requestBody);

	    // Send the login request
	    response = httprequest.post();

	    // Check if login was successful
	    if (response.getStatusCode() == 200) {
	        bearerToken = response.jsonPath().getString("data.token");
	        setBearerToken(bearerToken);
	        logger.info("Login successful. Bearer token set.");
	    } else {
	        logger.error("Login failed. Status code: " + response.getStatusCode());
	        logger.error("Login response: " + response.getBody().asString());
	    }

	    return bearerToken;
	}

	// Method for GET request with a specific URI
	protected void sendGetRequest(String baseURI) { 
		String fullURI = url + baseURI;
		httprequest.header("Content-Type", "application/json");
		httprequest.baseUri(fullURI);
		response = httprequest.get();
	}

	// Method for POST request with a specific URI
	protected void sendPostRequest(String baseURI, Map<String, String> requestBody) {
		String fullURI = url + baseURI;
		httprequest.baseUri(fullURI);
		httprequest.header("Content-Type", "application/json");
		httprequest.body(requestBody);
		response = httprequest.post();
	}

	// Method for PUT request with a specific URI
	protected void sendPutRequest(String baseURI) {
		String fullURI = url + baseURI;
		httprequest.baseUri(fullURI);
		response = httprequest.put();
	}

	// Method for DELETE request with a specific URI
	protected void sendDeleteRequest(String baseURI) {
		String fullURI = url + baseURI;
		httprequest.baseUri(fullURI);
		response = httprequest.delete();
	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method is to check the response body
	protected void Getbody() {
		logger.info("check response body");
		String responsebody = response.body().asString();
		logger.info("response body ----- " + responsebody);
		Assert.assertTrue(responsebody != null);
	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method is to check the Status code
	protected void Statuscode(int acceptedstatuscode) {

		logger.info("checkresponse code");
		int actualcode = response.getStatusCode();
		logger.info("statuscode is-----" + actualcode);
		Assert.assertEquals(actualcode, acceptedstatuscode);
	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method is to check the time taken for response
	protected void Checkresponsetime(double accetedresponsetime) {

		logger.info("checking response time");
		long responsetime = response.getTime();
		logger.info("response time is-----" + responsetime);
		if (responsetime > accetedresponsetime)
			Assert.assertTrue(responsetime < accetedresponsetime);

	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method is to check statusline
	protected void Statusline(String expectedline) {

		logger.info("checking status line");
		String actualstatusline = response.getStatusLine();
		logger.info("statusline is----" + actualstatusline);
		Assert.assertEquals(expectedline, actualstatusline);

	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method is to check the content type in headers in response
	protected void Contenttype(String expectedcontenttype, String headername) {

		logger.info("checking content type");
		String actualcontecttype = response.header(headername);
		logger.info("content type is----" + actualcontecttype);
		Assert.assertTrue(expectedcontenttype, true);
	}

//-------------------------------------------------------------------------------------------------------------------//

	// This methods give all the headers present
	protected void Getallheaders() {
		logger.info("Getting all headers");
		Headers allheaders = response.getHeaders();
		logger.info("All headers are----");
		for (Header header : allheaders) {
			System.out.println(header);
		}

	}

//-------------------------------------------------------------------------------------------------------------------//

	// This method check cookies in body
	protected void Cookies() {

		Map<String, String> actualcookies = response.cookies();
		// String actualcookies = response.cookie(expectedcookie);
		System.out.println(actualcookies);
		// Assert.assertEquals(expectedcookie, actualcookies);

	}

//-------------------------------------------------------------------------------------------------------------------//

	protected void Getbody1(String g) {

		String responsebody = response.body().asString();
		JsonPath j = new JsonPath(responsebody);
		System.out.println(j.getString(g));

	}

//-------------------------------------------------------------------------------------------------------------------//	

	// This method is for validation of data from response
	protected void Datavalidation(String g, String b) {

		String responsebody = response.body().asString();
		JsonPath j = new JsonPath(responsebody);
		Object a = j.get(g);
		String d = String.valueOf(a);
		String c = b;

		if (d.equals(c)) {
			System.out.println(c);
		} else {
			System.out.println("not equals");
		}

	}

//-------------------------------------------------------------------------------------------------------------------//

	protected void Setsessionid(String a) {

		logger.info("setting session id");

		// logger.info("session id set done" + a);

	}

//-------------------------------------------------------------------------------------------------------------------//	

	protected void Getsessionid() {

		logger.info("Checking session id");
		String currentsessionid = response.getSessionId();
		logger.info("session id is----" + currentsessionid);

	}

//-------------------------------------------------------------------------------------------------------------------//

	protected void Hashcode() {
		logger.info("checking hashcode");
		int hash = response.hashCode();
		logger.info("Hashcode is----" + hash);

	}

	protected void Getbody2(Object a) {
		String responsebody = response.body().asString();
		JsonPath j = new JsonPath(responsebody);

	}

}
