package com.example.tests.service;

import com.example.tests.bean.FieldSets;
import com.example.tests.model.CommitDataSetToUrlModel;
import com.example.tests.model.CreateExcelformUrlModel;
import com.example.tests.tool.ConstantValue;

public class FieldServiceImpl {
	CreateExcelformUrlModel createExcelformUrlModel;
	CommitDataSetToUrlModel commitExcelToUrlModel;
	public FieldServiceImpl(){
		createExcelformUrlModel=new CreateExcelformUrlModel();
		commitExcelToUrlModel=new CommitDataSetToUrlModel();
	}
	/**
	 * 
	 * @param url
	 * @return
	 */
	public boolean createExcelFromUrl(String url){
		 FieldSets fieldSets=createExcelformUrlModel.getDataFromUrl(url);
		 return createExcelformUrlModel.createExcel(ConstantValue.backupFilePath, fieldSets);
	}
	
	public FieldSets parseExcelToDataSet(){
		return createExcelformUrlModel.parseExcelToDataSets(ConstantValue.backupFilePath);
	}
	
	public void commitDataSetToUrl(FieldSets fieldSet) throws Exception{
		commitExcelToUrlModel.callProgram(fieldSet);
		
	}
	public void removeCells(FieldSets fieldSets,int... cells){
		createExcelformUrlModel.removeCells(fieldSets, cells);
	}
	
	
	
}
