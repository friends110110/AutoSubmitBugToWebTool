package com.example.tests.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Properties;
import java.util.Scanner;

import com.example.tests.tool.ConstantValue;
import com.example.tests.tool.WebDriverUtils;

public class ConfigParams {

	HashMap<String,String> paramsMap=new HashMap<String,String>();
	static ConfigParams instance =new ConfigParams();
	private ConfigParams(){
//		paramsMap.put(ConstantValue.KEY_WEBSITE, ConstantValue.DEFAULT_CREATE_EXCEL_URL);
//		paramsMap.put(ConstantValue.KEY_FIREFOX_PATH, ConstantValue.DEFAULT_FIREFOX_PATH);

	}
	public static ConfigParams getInstance(){
		return instance;
	}
	public String getValue(String key){
		return paramsMap.get(key);
	}
	
	public String setValue(String key,String value){
		return paramsMap.put(key,value);
	}
	public boolean isExistConf(){
		File file=new File(ConstantValue.CONFIGURATION_FILE_PATH);
		boolean isFileExist= file.exists();
		if(false == isFileExist){
			System.out.println("本应用需要安装 firefox浏览器");
			System.out.println("please type in the firefox path: like D:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		}
		return isFileExist;
	}
	public boolean createConfFile(String params){
		String reg="(^//.|^/|^[a-zA-Z])?:?/.+(/$)?";
		params=params.trim().replace("\\", "/");
		if(params.matches(reg)){
			try{
				paramsMap.put(ConstantValue.KEY_FIREFOX_PATH, params);
				WebDriverUtils.getWebDriver();
			}catch(Exception e){
				System.out.println("Specified firefox binary location does not exist or is not a real file: "+params);
				return false;
			}
			System.out.println("已经保存路径，下次无需再输了");
			return true;
		}else{
			System.out.println("输入路径有误重新输入");
			return false;
		}
	}
	public boolean readConfFile() throws Exception {
		try{
			FileInputStream in = new FileInputStream(ConstantValue.CONFIGURATION_FILE_PATH);
			Properties properties=new  Properties();
			properties.load(in);
			Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();  
	        while (it.hasNext()) {  
	            Entry<Object, Object> entry = it.next();  
	            String key = (String) entry.getKey();  
	            String value = (String) entry.getValue();  
				paramsMap.put(key,value);
	        }  
			System.out.println("It had read the firefoxPath:  "+paramsMap.get(ConstantValue.KEY_FIREFOX_PATH));
			in.close();
		}catch(Exception e){
			System.out.println("fail to read configuration ,at "+ConstantValue.BACKUP_FILE_PATH);
			throw e;
		}
		return true;
	}

	public boolean setWebsiteUrl(String websiteUrl){
		//the latest website is useful
		try{
			if(true==typeInWebSiteConfiguration(websiteUrl)){
				if(!"n".equals(websiteUrl)){
					File file=new File(ConstantValue.CONFIGURATION_FILE_PATH);
					FileOutputStream writeFile = new FileOutputStream(file);
					Properties properties=new  Properties();
					for(Map.Entry<String, String> entry:paramsMap.entrySet()){
						properties.setProperty(entry.getKey(),entry.getValue());
					}
					properties.store(writeFile, "create configuration");
					writeFile.close();
				}
				return true;
			}
		}catch(Exception e){
			System.out.println("fail to save website ");
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param param  the value is "n" or a legal website
	 * @return true for success, false for failure
	 */
     private boolean typeInWebSiteConfiguration(String param) {
    	 try{
	    	 boolean isUrl=param.matches("(http|ftp|https):\\/\\/([\\w.]+\\/?)\\S*");
	    	 if("n".equals(param)){
	    		 String websiteUrl=paramsMap.get(ConstantValue.KEY_WEBSITE_URL);
	    		 if(websiteUrl==null||!websiteUrl.matches("(http|ftp|https):\\/\\/([\\w.]+\\/?)\\S*")){
	    			 System.out.println("the latest website url has mistaken");
	    			 return false;
	    		 }
			 }else if(true==isUrl){
	    		 paramsMap.put(ConstantValue.KEY_WEBSITE_URL, param);
			 }else{
	    		 System.out.println("the website you typed in has mistakes,type in again");
	    		 return false;
	    	 }
    	 }catch(Exception e){
				System.out.println("file is non-existence or the website key is non-existence,"
						+ "	please type in the website again");
    		 return false;
    	 }
		return true;
     }
}
