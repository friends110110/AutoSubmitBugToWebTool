package com.example.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception{
		ArrayList<FieldBean> beanList;
		//FileUtils fileUtils=new FileUtils();
		//ArrayList<FieldBean> beanList=fileUtils.parseFileToMap("e:\\11.xls");
		FileUtil2 fileUtils2=new FileUtil2(); 
		beanList=fileUtils2.parseFileToMap("e:\\22.xlsx");
		
		
		
		FullAutoTool tool=new FullAutoTool();
		String firefoxPath="D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
		tool.setUp(firefoxPath);
		
	}

}
