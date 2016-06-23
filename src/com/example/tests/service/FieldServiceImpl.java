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
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

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
	public boolean restoreExcelFile(FieldSets fieldSet){
		boolean isDel=createExcelformUrlModel.deleteExcelFile();
		if(false==isDel){
			return isDel;
		}
		System.out.println("it will restore this file");
		createExcelformUrlModel.createExcel(ConstantValue.BACKUP_FILE_PATH, fieldSet);
		return true;
	}
	public void deleteExcelFile(){
		boolean isDel=false;
		while(false==isDel){
			isDel=createExcelformUrlModel.deleteExcelFile();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public boolean commitDataSetToUrl(final FieldSets fieldSet) throws Exception{
		  final ArrayList<ArrayList<String>> contentLists=fieldSet.contentList;
		  if(contentLists.size()<=0){
			  return true;
		  }
		  try{
			  commitExcelToUrlModel.callProgramToSubmit(contentLists);
		  }catch(Exception e){
				System.out.println("the left data has some errors,please correct it.(ps: assign to somebody who is not exist)");
				return restoreExcelFile(fieldSet);
		  }
		  return true;
	}
	public void removeCells(FieldSets fieldSets,Integer... cells){
		createExcelformUrlModel.removeCells(fieldSets, cells);
	}

	
	
	
}
