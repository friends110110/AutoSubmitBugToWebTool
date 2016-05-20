package com.example.tests.tool;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.example.tests.configuration.ConfigParams;

public class WebDriverUtils {

	private static WebDriver driver;
	/**
	 * 
	 * @return the webDriver object is used for deal with something about web operation
	 * @throws Exception 
	 */
	public static WebDriver getWebDriver() {
		ConfigParams param=ConfigParams.getInstance();
		if(null==driver){
			 File pathBinary = new File(param.getValue(ConstantValue.KEY_FIREFOX_PATH));
			 FirefoxBinary Binary = new FirefoxBinary(pathBinary);
			 FirefoxProfile firefoxPro = new FirefoxProfile();   
			 driver = new FirefoxDriver(Binary,firefoxPro);  
		     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 
		}
		return driver;
	}
	
	  public static void closeWebDriver() throws Exception {
	    driver.quit();
//	    String verificationErrorString = verificationErrors.toString();
//	    if (!"".equals(verificationErrorString)) {
//	      fail(verificationErrorString);
//	    }
	  }
}
