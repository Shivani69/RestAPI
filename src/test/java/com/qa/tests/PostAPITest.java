package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase {
	TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

	
	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException{
		testBase = new TestBase();
	    serviceUrl = prop.getProperty("URL");
	     apiUrl= prop.getProperty("serviceURL");
		
	    url = serviceUrl + apiUrl;
		
	}
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
	    restClient = new RestClient();
	    HashMap<String,String>headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
	
		//jackson API
		ObjectMapper mapper = new ObjectMapper();
	    Users users = new Users("morpheus","leader");
	    
	    //object to json file conversion
	    mapper.writeValue(new File("C:/tools/projects/workspace/JavaTraining/restapi/src/main/java/com/qa/data/users.json"),users);
	    //object to json in String:
	    String usersJsonString=mapper.writeValueAsString(users);
	    System.out.println(usersJsonString);
	    closeableHttpResponse= restClient.post(url, usersJsonString, headerMap);
	    
	    //1. status Code
	    int statusCode =closeableHttpResponse.getStatusLine().getStatusCode();
	    Assert.assertEquals(statusCode, testBase.Response_Status_CODE_201);
	    
	    //2. JsonString
	    String responseString= EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
	     JSONObject responseJson = new JSONObject(responseString);
	     System.out.println("The response from API is:" +responseJson);
	     
	     //Json to java object:
	     Users usersResObj= mapper.readValue(responseString, Users.class);
	     System.out.println(usersResObj);
	     
	     Assert.assertTrue(users.getName().equals(usersResObj.getName()));
	     
	     Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
	     System.out.println(usersResObj.getId());
	     System.out.println(usersResObj.getCreatedAt());
	}	
	
	
}
