package com.example.tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.junit.Test;

import com.example.tests.bean.FieldBean;
import com.example.tests.bean.FieldSets;
import com.example.tests.configuration.ConfigParams;
import com.example.tests.model.CreateExcelformUrlModel;
import com.example.tests.service.FieldServiceImpl;
import com.example.tests.model.CommitDataSetToUrlModel;
import com.example.tests.tool.ConstantValue;

import rx.Observable;
import rx.functions.Action1;

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
	@Test
	public void testWriteFileAgain(){
		CreateExcelformUrlModel fileTool=new CreateExcelformUrlModel(); 
		final ArrayList<ArrayList<String>> data =new ArrayList<ArrayList<String>> ();
		for(int i=0;i<5;i++){
			ArrayList<String> str=new ArrayList<String>();
			for(int j=0;j<3;j++){
				str.add(i+"  "+String.valueOf(j));
			}
			data.add(str);
		}
		Observable.from(data).subscribe(new Action1<ArrayList<String>>(){

			@Override
			public void call(ArrayList<String> list) {
				// TODO Auto-generated method stub
				data.remove(list);
			}
			
		});
		FieldSets fieldSet=new FieldSets();
		String []titles={"problem_id","description","solution","comment"};
		ArrayList<String> titleList=new ArrayList(Arrays.asList(titles));
		fieldSet.titleList=titleList;
		fieldSet.contentList=data;
		fileTool.createExcel(ConstantValue.BACKUP_FILE_PATH, fieldSet);
	}
	
	public static void main(String[] args) throws Exception{
		Scanner scanner=new Scanner(System.in);
		ConfigParams paramsMap=ConfigParams.getInstance();
		FieldServiceImpl fieldServiceImpl=new FieldServiceImpl();
		fieldServiceImpl.initParam();
		System.out.println("type in the website, n for the historical lastest record");
		while(scanner.hasNext()){
			String websiteUrl=scanner.nextLine().trim();
			if("".equals(websiteUrl)){
				continue;
			}
			if(true==fieldServiceImpl.initWebSiteConfiguration(websiteUrl)){
				System.out.println("please login in the foxfire ");
				break;
			}
		}
		
//		fieldServiceImpl.createExcelFromUrl(paramsMap.getValue(ConstantValue.KEY_WEBSITE));

		while(true){
			FieldSets fieldSets=fieldServiceImpl.parseExcelToDataSet();
			//fieldServiceImpl.removeCells(fieldSets, ConstantValue.DELETE_CELLS_NUMBER);
			fieldServiceImpl.commitDataSetToUrl(fieldSets);
			if(fieldSets.contentList.size()==0){
				break;
			}
		}
	}
	
	@Test
	public void testData(){
//		FieldServiceImpl fieldServiceImpl=new FieldServiceImpl();
//		FieldSets fieldSets=fieldServiceImpl.parseExcelToDataSet();
//		fieldServiceImpl.removeCells(fieldSets, ConstantValue.deleteCellsNumber);
		FieldServiceImpl fieldServiceImpl=new FieldServiceImpl();
//		fieldServiceImpl.createExcelFromUrl(ConstantValue.createExcelURL);
//		Scanner scanner=new Scanner(System.in);
//		while(scanner.hasNext()){
//			String isContinue=scanner.nextLine();
//			if("y".equals(isContinue)){
//				break;
//			}
//		}
		
		
		FieldSets fieldSets=fieldServiceImpl.parseExcelToDataSet();
		fieldServiceImpl.removeCells(fieldSets, ConstantValue.DELETE_CELLS_NUMBER);
		System.out.println("sb");
	}

}
