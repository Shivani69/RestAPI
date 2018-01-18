package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
      
	public int Response_Status_CODE_200 =200;
	public int Response_Status_CODE_500 =500;
	public int Response_Status_CODE_400 =400;
	public int Response_Status_CODE_401 =401;
	public int Response_Status_CODE_201 =201;
	public int Response_Status_CODE_404 =404;
	
	public Properties prop;
  
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			prop.load(ip);
		}catch (FileNotFoundException e) {
			e.printStackTrace();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
