package com.example.tests.service;

import java.io.IOException;

import com.example.tests.bean.FieldSets;
import com.example.tests.configuration.ConfigParams;
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
	
    public boolean initWebSiteConfiguration(String params)throws Exception{
    	return createExcelformUrlModel.initWebSiteConfiguration(params);
    }
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
	
	public void commitDataSetToUrl(FieldSets fieldSet) throws Exception{
		commitExcelToUrlModel.callProgram(fieldSet);
		
	}
	public void removeCells(FieldSets fieldSets,Integer... cells){
		createExcelformUrlModel.removeCells(fieldSets, cells);
	}

	public void initParam() throws IOException {
		ConfigParams.getInstance().initParams();
	}
	
	
	
}
