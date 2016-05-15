package com.example.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import com.example.tests.bean.FieldBean;
import com.example.tests.bean.FieldSets;
import com.example.tests.model.CreateExcelformUrlModel;
import com.example.tests.service.FieldServiceImpl;
import com.example.tests.model.CommitDataSetToUrlModel;
import com.example.tests.tool.ConstantValue;

public class Main {

	@Test
	public void testsCreateExcel(){
		CreateExcelformUrlModel fileTool=new CreateExcelformUrlModel(); 
		ArrayList<ArrayList<String>> data =new ArrayList<ArrayList<String>> ();
		for(int i=0;i<5;i++){
			ArrayList<String> str=new ArrayList<String>();
			for(int j=0;j<3;j++){
				str.add(i+"  "+String.valueOf(j));
			}
			data.add(str);
		}
		FieldSets fieldSet=new FieldSets();
		String []titles={"problem_id","description","solution","comment"};
		ArrayList<String> titleList=new ArrayList(Arrays.asList(titles));
		fieldSet.titleList=titleList;
		fieldSet.contentList=data;
		fileTool.createExcel("e:\\44.xlsx",fieldSet);
		
	}
	
	
	public static void main(String[] args) throws Exception{
		FieldServiceImpl fieldServiceImpl=new FieldServiceImpl();
		fieldServiceImpl.createExcelFromUrl(ConstantValue.createExcelURL);
		Scanner scanner=new Scanner(System.in);
		while(scanner.hasNext()){
			String isContinue=scanner.nextLine();
			if("y".equals(isContinue)){
				break;
			}
		}
		FieldSets fieldSets=fieldServiceImpl.parseExcelToDataSet();
		fieldServiceImpl.removeCells(fieldSets, ConstantValue.deleteCellsNumber);
		fieldServiceImpl.commitDataSetToUrl(fieldSets);
		
	}
	
	@Test
	public void testData(){
		FieldServiceImpl fieldServiceImpl=new FieldServiceImpl();
		FieldSets fieldSets=fieldServiceImpl.parseExcelToDataSet();
		fieldServiceImpl.removeCells(fieldSets, ConstantValue.deleteCellsNumber);

	}

}
