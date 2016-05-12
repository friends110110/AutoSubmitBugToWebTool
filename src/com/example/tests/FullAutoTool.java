package com.example.tests;

import java.util.regex.Pattern;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;

public class FullAutoTool {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp(String firefoxPath) throws Exception {
	  
	 File pathBinary = new File(firefoxPath);
	 FirefoxBinary Binary = new FirefoxBinary(pathBinary);
	 FirefoxProfile firefoxPro = new FirefoxProfile();       
	 driver = new FirefoxDriver(Binary,firefoxPro);  
	  
    //driver = new FirefoxDriver();
    baseUrl = "http://se.hundsun.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  public void callProgram(ArrayList<String> list) throws Exception{
	  if(list.size()<0){
		  throw new Exception("数据数太少 明显不对");
	  }
	  callProgram(list.get(0),list.get(1),list.get(2));
  }
  //solve one problem 
  public void callProgram(String problemId,String solution,String comment){
	    driver.get(baseUrl + "/dm/browse/HSTZYJF-"+problemId);
	    driver.findElement(By.id("action_id_721")).click();
	    driver.findElement(By.id("分配任务")).click();
	    driver.findElement(By.id("action_id_751")).click();
	    new Select(driver.findElement(By.id("resolution"))).selectByVisibleText("解决(修复成功)");
	    new Select(driver.findElement(By.id("customfield_10330"))).selectByVisibleText("其他");
	    driver.findElement(By.id("customfield_10007")).clear();
	    driver.findElement(By.id("customfield_10007")).sendKeys(""+solution);
	    new Select(driver.findElement(By.id("customfield_10392"))).selectByVisibleText("否");
	    driver.findElement(By.id("comment")).clear();
	    driver.findElement(By.id("comment")).sendKeys(""+comment);
	    driver.findElement(By.id("解决缺陷")).click();
  }
  @Test
  public void testFullAuto() throws Exception {
	  
	  callProgram("20854","放大了图标","添加备注");
	  
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
