package com.example.tests.service;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.NoSuchElementException;

import com.example.tests.bean.FieldSets;
import com.example.tests.configuration.ConfigParams;
import com.example.tests.model.CommitDataSetToUrlModel;
import com.example.tests.model.CreateExcelformUrlModel;
import com.example.tests.tool.ConstantValue;

import rx.Observable;
import rx.functions.Action1;

public class FieldServiceImpl {
	CreateExcelformUrlModel createExcelformUrlModel;
	CommitDataSetToUrlModel commitExcelToUrlModel;
	public FieldServiceImpl(){
		createExcelformUrlModel=new CreateExcelformUrlModel();
		commitExcelToUrlModel=new CommitDataSetToUrlModel();
	}
	
//    public boolean initWebSiteConfiguration(String params)throws Exception{
//    	return createExcelformUrlModel.initWebSiteConfiguration(params);
//    }
	/**
	 * 
	 * @param url
	 * @return
	 */
	public boolean createExcelFromUrl(String url){
		 FieldSets fieldSets=createExcelformUrlModel.getDataFromUrl(url);
		 return createExcelformUrlModel.createExcel(ConstantValue.BACKUP_FILE_PATH, fieldSets);
	}
	
	public FieldSets parseExcelToDataSet(){
		return createExcelformUrlModel.parseExcelToDataSets(ConstantValue.BACKUP_FILE_PATH);
	}
	
	public void commitDataSetToUrl(final FieldSets fieldSet) throws Exception{
		  final ArrayList<ArrayList<String>> contentLists=fieldSet.contentList;
		  if(contentLists.size()<=0){
			  return ;
		  }
		  final ArrayList<ArrayList<String>> delContentLists=new ArrayList<ArrayList<String>>();
		  Observable.from(contentLists).subscribe(new Action1<ArrayList<String>>() {
				@Override
				public void call(ArrayList<String> list) {
						try {
							commitExcelToUrlModel.callProgram(list);
						} catch (NoSuchElementException e) {
							System.out.println("the left data has some errors,please correct it.");
							//the left data has not submit to the url
							contentLists.removeAll(delContentLists);
							createExcelformUrlModel.deleteExcelFile();
							createExcelformUrlModel.createExcel(ConstantValue.BACKUP_FILE_PATH, fieldSet);
//							e.printStackTrace();
							return;
						}
						System.out.println(list.toString() +"  has successfully been submited ");
						delContentLists.add(list);
				}
			});
			contentLists.removeAll(delContentLists);
		
	}
	public void removeCells(FieldSets fieldSets,Integer... cells){
		createExcelformUrlModel.removeCells(fieldSets, cells);
	}

	
	
	
}
