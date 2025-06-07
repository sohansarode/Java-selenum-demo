package apitest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import api.Testbase;


public class Get_Profile extends Testbase {

	@Test(description = "", priority = 0)
    public void edituserapitest() {
        Map<String, String> s = new HashMap<String, String>();
        s.put("email", "");
        s.put("password", "");
        
        Map<String, String> v = new HashMap<String, String>();
        s.put("", "");
        

        String bearerToken = login("v1/login", s);

        if (bearerToken != null) {
            // Set the bearer token after login
            setBearerToken(bearerToken);

            // Use the full URI for the GET request
            sendGetRequest("v1/get-user-profile");
            System.out.println("logout success");
        } else {
            System.out.println("Token is null. Login failed.");
        }
        
        Getbody();
        Statuscode(200);

		Statusline("HTTP/1.1 200 OK");

		Checkresponsetime(5000);

		Contenttype("text/html;charset=UTF-8", "Content-Type");
    }

}
