package com.example.tests.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.example.tests.bean.FieldSets;
import com.example.tests.tool.ConstantValue;
import com.example.tests.tool.WebDriverUtils;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * excel 2003  2007  is compatible
 * @author Administrator
 *
 */
public class CreateExcelformUrlModel {
	 private static final String EXTENSION_XLS = "xls";
	 private static final String EXTENSION_XLSX = "xlsx";
	 

	 /**
	  * 根据url地址生成 xlsx表格，
	  * problemId	description result	solution	comment
			xx			xx			xx		xx			xx
	  * @param url
	  */
	 public FieldSets getDataFromUrl(String url){
		 FieldSets fieldSets=new FieldSets();
		 WebDriver webDriver=WebDriverUtils.getWebDriver();
	     webDriver.get(ConstantValue.createExcelURL);
	     WebElement webElement=webDriver.findElement(By.xpath("//td[@class='nav summary']"));
	     List<WebElement> webElementList = webDriver.findElements(By.xpath(("//td[@class='nav summary']/a")));  
	     ArrayList<ArrayList<String>> contentList=new ArrayList<ArrayList<String>>();
	     ArrayList<String> list;
	     for(int i=0;i<webElementList.size();i++){
	    	 list=new ArrayList<String>();
	    	 webElement=webElementList.get(i);
	    	 String probleId=webElement.getAttribute("href");
	    	 String description=webElement.getText();
	    	 list.add(probleId);
	    	 list.add(description);
	    	 System.out.print(probleId+"\t"+description);
	    	 System.out.println();
	    	 contentList.add(list);
	     }
	     fieldSets.contentList=contentList;
	     return fieldSets;
	     
	 }
	 /**
	  * 
	  * @param createPath
	  * @param fieldSets
	  * @return	true for success,false for failure
	  */
	 public boolean createExcel(String createPath,FieldSets fieldSets) {
		ArrayList<ArrayList<String>> dataList =fieldSets.contentList;
		ArrayList<String> titleList=fieldSets.titleList;
		// TODO Auto-generated method stub
		// HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象  
		try{
	        XSSFWorkbook workBook = new XSSFWorkbook();  
	        XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象  
	        sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为  
	        XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象  
	        // 设置字体  
	        XSSFFont font = workBook.createFont();// 创建字体对象  
	        font.setFontHeightInPoints((short) 15);// 设置字体大小  
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体  
	        font.setFontName("黑体");// 设置为黑体字  
	        style.setFont(font);// 将字体加入到样式对象  
	        // 设置对齐方式  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中  
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中  
	        // 设置边框  
	        style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线  
	        style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色  
	        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线  
	        style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框  
	        style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框  
	        // 格式化日期  
	        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));  
	//		Workbook workBook=this.getWorkbook(createPath);
		       for(int i=0;i<dataList.size();i++) {
		    	   ArrayList<String> rowList;
		    	   if(i==0){	//title
		    		   rowList=titleList;
		    	   }else{	//data from 0
		    		   rowList=dataList.get(i-1);
		    	   }
		    	   XSSFRow row = sheet.createRow(i);
		    	   row.setHeightInPoints(23);// 设置行高23像素  
		    	   for(int j=0;j<rowList.size();j++){
		    	        XSSFCell cell = row.createCell(j);// 创建单元格  
		    	        cell.setCellValue(rowList.get(j));// 写入当前日期  
		    	        cell.setCellStyle(style);
	//		           XSSFCell cell = row2.createCell(i);
	//		           xssfValue = new XSSFRichTextString(entry.get(i));
	//		           cell.setCellValue(xssfValue);
		           }
		       }
	//        XSSFRow row = sheet.createRow(1);// 创建一个行对象  
	//        row.setHeightInPoints(23);// 设置行高23像素  
	//        XSSFCell cell = row.createCell(1);// 创建单元格  
	//        cell.setCellValue(new Date());// 写入当前日期  
	//        cell.setCellStyle(style);// 应用样式对象  
	        // 文件输出流  
	        FileOutputStream os = new FileOutputStream(createPath);  
	        workBook.write(os);// 将文档对象写入文件输出流  
		       os.close();// 关闭文件输出流  
		       System.out.println("创建成功 office 2007 excel");  
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	    /**
	     * 读取excel文件内容
	     * @param filePath
	     * @throws FileNotFoundException
	     * @throws FileFormatException
	     */
	public FieldSets parseExcelToDataSets(String filePath)  {
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
            
            Observable.create(new OnSubscribe<Row>(){
				@Override
				public void call(Subscriber<? super Row> subscriber) {
					// TODO Auto-generated method stub
					for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
						subscriber.onNext(sheet.getRow(i));
						System.out.println();
					}
				}}).observeOn(Schedulers.immediate()).subscribe(new Subscriber<Row>() {
					@Override
					public void onCompleted() {
						// TODO Auto-generated method stub
						System.out.println("everything seems to be ok");
					}
					@Override
					public void onError(Throwable e) {
						// TODO Auto-generated method stub
						System.out.println("something seems to be wrong");
						e.printStackTrace();
					}
					@Override
					public void onNext(Row row) {
						// TODO Auto-generated method stub
						ArrayList<String> list=new ArrayList<String>();
						int cellNumber=fieldSet.titleList.size();
						cellNumber=cellNumber<row.getPhysicalNumberOfCells()?row.getPhysicalNumberOfCells():cellNumber;
						for(int j=0;j<cellNumber;j++){
							Cell cell=row.getCell(j);
							if(null==cell){
								//第一列为null的整行都是无效
								if(j==0){
									return;
								}
								list.add("");
			            		System.out.print("nullvalue"+"\t");
			            		continue;
							}
							String cellValue=cell.toString();
		            		list.add(cellValue.trim());
		            		System.out.print(cellValue+"\t");
						}
		            	if(row==sheet.getRow(0)){
		            		fieldSet.titleList=list;
		            	}else{
		            		fieldSet.contentList.add(list);
		            	}
					}
				});
	         System.out.println("==============================================================================");
	          workbook.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return fieldSet;
	    }
	 
	public void removeCells(final FieldSets fieldSets,int... cells){
		ArrayList<String> titleList=fieldSets.titleList;
		for(int j=0;j<cells.length;j++){
			titleList.remove(cells[j]);
		}

		ArrayList<ArrayList<String>> fieldLists=fieldSets.contentList;
		for(int i=0;i<fieldLists.size();i++){
			ArrayList<String> list=fieldLists.get(i);
			for(int j=0;j<cells.length;j++){
				list.remove(cells[j]);
			}
		}
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
