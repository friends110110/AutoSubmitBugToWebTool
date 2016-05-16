package com.example.tests.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import com.example.tests.tool.ConstantValue;

public class ConfigParams {

	HashMap<String,String> paramsMap=new HashMap<String,String>();
	static ConfigParams instance =new ConfigParams();
	private ConfigParams(){
		paramsMap.put(ConstantValue.KEY_WEBSITE, ConstantValue.DEFAULT_CREATE_EXCEL_URL);
		paramsMap.put(ConstantValue.KEY_FIREFOX_PATH, ConstantValue.DEFAULT_FIREFOX_PATH);

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
	public void initParams() throws IOException {
		File file=new File(ConstantValue.CONFIGURATION_FILE_PATH);
		if(!file.exists()){
			System.out.println("本应用需要安装 JDK、firefox浏览器");
			System.out.println("please type in the firefox path: like D:/Program Files (x86)/Mozilla Firefox/firefox.exe");
			Scanner scanner =new Scanner(System.in);
			String reg="(^//.|^/|^[a-zA-Z])?:?/.+(/$)?";
			String firefoxPath = null;
			while(scanner.hasNext()){
				firefoxPath=scanner.nextLine().trim();
				if(firefoxPath.matches(reg)){
					System.out.println("已经保存路径，下次无需再输了");
					break;
				}else{
					System.out.println("输入路径有误重新输入");
				}
			}
			if(firefoxPath!=null){
				paramsMap.put(ConstantValue.KEY_FIREFOX_PATH,firefoxPath);
				file.createNewFile();
				FileOutputStream writeFile = new FileOutputStream(file);
				Properties properties=new  Properties();
				properties.setProperty(ConstantValue.KEY_FIREFOX_PATH,firefoxPath);
				properties.store(writeFile, "create configuration");
				writeFile.close();
			}
		}else{
			FileInputStream in = new FileInputStream(ConstantValue.CONFIGURATION_FILE_PATH);
			Properties properties=new  Properties();
			properties.load(in);
			paramsMap.put(ConstantValue.KEY_FIREFOX_PATH,properties.getProperty(ConstantValue.KEY_FIREFOX_PATH, ConstantValue.DEFAULT_FIREFOX_PATH));
			in.close();
		}
	}
}
