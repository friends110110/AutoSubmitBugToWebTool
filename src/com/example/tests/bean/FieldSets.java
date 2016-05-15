package com.example.tests.bean;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldSets {

	//标题党
	public ArrayList<String> titleList =null;
	//内容
	public ArrayList<ArrayList<String>> contentList=new ArrayList<ArrayList<String>>();
	public FieldSets(){
		String []titles={"problem_id(改数列没数据整行作废说)","description","result(未解决(尚未处理)、未解决(延后解决)、未解决(无法解决)、未解决(不需解决)、未解决(无法重现)、解决(修复成功)、解决(不是缺陷)、解决(重复缺陷))","solution","comment"};
		titleList=new ArrayList(Arrays.asList(titles));

	}
}
