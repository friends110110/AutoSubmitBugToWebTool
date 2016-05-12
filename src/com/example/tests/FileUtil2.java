package com.example.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
/**
 * excel 2003  2007  is compatible
 * @author Administrator
 *
 */
public class FileUtil2 {
	 private static final String EXTENSION_XLS = "xls";
	 private static final String EXTENSION_XLSX = "xlsx";
	 
	    /**
	     * 读取excel文件内容
	     * @param filePath
	     * @throws FileNotFoundException
	     * @throws FileFormatException
	     */
	    public FieldSets parseFileToMap(String filePath)  {
			final FieldSets fieldSet=new FieldSets();
	        // 获取workbook对象
	        Workbook workbook = null;
			try{
	    	// 检查
	        this.preReadCheck(filePath);
	       
            workbook = this.getWorkbook(filePath);
            // 读文件 一个sheet一个sheet地读取
            final Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return null;
            }
            System.out.println("=======================" + sheet.getSheetName() + "=========================");

            for(int i=sheet.getFirstRowNum();i<sheet.getPhysicalNumberOfRows();i++){
            	Row row=sheet.getRow(i);
            	String cellValue;
            	ArrayList<String> list=new ArrayList<String>();
            	for(int j= row.getFirstCellNum();j<row.getPhysicalNumberOfCells();j++){
            		cellValue=row.getCell(j).toString();
            		list.add(cellValue);
            		System.out.print(cellValue+"\t");
            	}
            	if(0==i){
            		fieldSet.titleList=list;
            	}else{
            		fieldSet.contentList.add(list);
            	}
        		System.out.println();
            }
	                
	                
	                Observable.from(sheet).map(new Func1<Row,Void>(){
						@Override
						public Void call(Row row) {
							final ArrayList<String> list=new ArrayList<String>();
					    	Observable.from(row).map(new Func1<Cell,Void>(){
								@Override
								public Void call(Cell cell) {
									list.add(cell.getStringCellValue());
									System.out.println(cell.getStringCellValue()+"\t");
									return null;
								}
							});
							System.out.println();
							if(row==sheet.getRow(0)){
								fieldSet.titleList=list;
							}else {
								fieldSet.contentList.add(list);
							}
							return null;
						}
	                });
	         System.out.println("==============================================================================");
	          workbook.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return fieldSet;
	    }
	 

	  /***
	     * <pre>
	     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
	     *   xls:HSSFWorkbook
	     *   xlsx：XSSFWorkbook
	     * @param filePath
	     * @return
	     * @throws IOException
	     * </pre>
	     */
	    private Workbook getWorkbook(String filePath)   {
	        Workbook workbook = null;
	        try{
		        InputStream is = new FileInputStream(filePath);
		        if (filePath.endsWith(EXTENSION_XLS)) {
		            workbook = new HSSFWorkbook(is);
		        } else if (filePath.endsWith(EXTENSION_XLSX)) {
		            workbook = new XSSFWorkbook(is);
		        }
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        return workbook;
	    }

	    /**
	     * 文件检查
	     * @param filePath
	     * @throws FileNotFoundException
	     * @throws FileFormatException
	     */
	    private void preReadCheck(String filePath) throws FileNotFoundException, FileFormatException {
	        // 常规检查
	        File file = new File(filePath);
	        if (!file.exists()) {
	            throw new FileNotFoundException("传入的文件不存在：" + filePath);
	        }

	        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
	            throw new FileFormatException("传入的文件不是excel");
	        }
	    }

}
