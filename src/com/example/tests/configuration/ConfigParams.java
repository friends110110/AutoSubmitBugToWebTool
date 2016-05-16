package com.example.tests.configuration;

import java.util.HashMap;

import com.example.tests.tool.ConstantValue;

public class ConfigParams {

	HashMap<String,String> paramsMap=new HashMap<String,String>();
	static ConfigParams instance =new ConfigParams();
	private ConfigParams(){
		paramsMap.put(ConstantValue.KEY_WEBSITE, ConstantValue.DEFAULT_CREATE_EXCEL_URL);
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
}
