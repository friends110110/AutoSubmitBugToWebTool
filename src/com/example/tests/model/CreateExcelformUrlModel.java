package com.example.tests.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
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
import org.apache.poi.ss.usermodel.BorderStyle;
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
import com.example.tests.configuration.ConfigParams;
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
     XSSFRow row =null;
	 ConfigParams paramsMap=ConfigParams.getInstance();

	
	 /**
	  * 根据url地址生成 xlsx表格，
	  * problemId	description result	solution	comment
			xx			xx			xx		xx			xx
	  * @param url
	  */
	 public FieldSets getDataFromUrl(String url){
		 FieldSets fieldSets=new FieldSets();
		 WebDriver webDriver=WebDriverUtils.getWebDriver();
		 System.out.println("please login in the foxfire,30 seconds ,and wait for a moment");
	     webDriver.get(url);
	     WebElement webElement;
	     boolean isLogin=false;
	     while(!isLogin){
		     try{
		    	 webElement=webDriver.findElement(By.xpath("//td[@class='nav summary']"));
		    	 isLogin=true;
		     }catch(Exception e){
		    	 System.out.println("please login ,do not waste of time");
		     }
	     }
	     List<WebElement> webElementLists = webDriver.findElements(By.xpath(("//td[@class='nav summary']/a")));  
	     final ArrayList<ArrayList<String>> contentList=new ArrayList<ArrayList<String>>();
		 System.out.println("fetch data from website:  "+ url);
	     Observable.from(webElementLists).subscribe(new Action1<WebElement>(){
			@Override
			public void call(WebElement element) {
			     ArrayList<String> list=new ArrayList<String>();
				 String probleId=element.getAttribute("href");
		    	 String description=element.getText();
		    	 list.add(probleId);
		    	 list.add(description);
		    	 System.out.print(probleId+"\t"+description);
		    	 System.out.println();
		    	 contentList.add(list);
			}
	     });
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
		final ArrayList<ArrayList<String>> dataList =fieldSets.contentList;
		final ArrayList<String> titleList=fieldSets.titleList;
		// TODO Auto-generated method stub
		// HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象  
		try{
	        XSSFWorkbook workBook = new XSSFWorkbook();  
	        final XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象  
	        sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为  
	        sheet.setColumnWidth(3, titleList.get(3).getBytes().length*2*256);
	        sheet.setColumnWidth(4, titleList.get(4).getBytes().length*2*256);
	        sheet.setColumnWidth(6, titleList.get(6).getBytes().length*2*256);

	        final XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象  
	        // 设置字体  
	        XSSFFont font = workBook.createFont();// 创建字体对象  
	        font.setFontHeightInPoints((short) 11);// 设置字体大小  
	        font.setBoldweight(HSSFFont.DEFAULT_CHARSET);// 设置粗体  
	        font.setFontName("宋体");// 设置为黑体字  
	        style.setFont(font);// 将字体加入到样式对象  
	        // 设置对齐方式  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中  
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中  
	        //自动换行
	        style.setWrapText(true);
	        
	        //调整列宽以使用内容长度  
	        sheet.autoSizeColumn(2,true);  
	        sheet.autoSizeColumn((short)3,true);  
	        // 设置边框  
	        style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线  
	        style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色  
	        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线  
	        style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框  
	        style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框  
	        // 格式化日期  
	        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));  
	        Observable.create(new OnSubscribe< ArrayList<String>>(){
				@Override
				public void call(final Subscriber<? super ArrayList<String>> rowSubscribe) {
					//row count is equal to titleList plus dataList
					Observable.range(0, 1+dataList.size()).subscribe(new Action1<Integer>() {
						@Override
						public void call(Integer rowIndex) {
					    	row = sheet.createRow(rowIndex);
					    	//row.setHeightInPoints(23);
					    	
					    	
					    	
					    	//增加单元格的高度 以能够容纳2行字   
					    	//row.setHeightInPoints(2*sheet.getDefaultRowHeightInPoints());  
							if(0==rowIndex){
								rowSubscribe.onNext(titleList);
							}else{
								rowSubscribe.onNext(dataList.get(rowIndex-1));
							}
						}
					});
				}
	        }).subscribe(new Action1<ArrayList<String>>() {
				@Override
				public void call(final ArrayList<String> list) {
					Observable.range(0,list.size()).subscribe(new Action1<Integer>() {
						@Override
						public void call(Integer index) {
							XSSFCell cell = row.createCell(index);// 创建单元格  
			    	        cell.setCellValue(list.get(index));// 写入当前日期  
			    	        cell.setCellStyle(style);
			    	        if(index==1){
			    	        	float height=getExcelCellAutoHeight(list.get(1),ConstantValue.CHARNUMSSECONDSCell);
			    	        	row.setHeightInPoints(height);
			    	        }
						}
					});
				}
			});
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
	 public boolean deleteExcelFile(){
		 File file=new File(ConstantValue.BACKUP_FILE_PATH);
		 if(file.exists()){
			 if(false==file.delete()){
				 System.out.println("please close file at  " +ConstantValue.BACKUP_FILE_PATH);
				 return false;
			 }
			 System.out.println("success to delete this file ,and will restore this file");
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
				public void call(final Subscriber<? super Row> subscriber) {
					Observable.range(0, sheet.getPhysicalNumberOfRows())
							  .subscribe(new Action1<Integer>(){
									@Override
									public void call(Integer rowIndex) {
										Row row=sheet.getRow(rowIndex);
										//ensure the first cell which attach to the specified row is not empty
										if(row.getCell(0)!=null){
											subscriber.onNext(row);
											System.out.println();
										}
									}
								});
				}}).observeOn(Schedulers.immediate())
            	   .subscribe(new Subscriber<Row>() {
					@Override
					public void onCompleted() {
						System.out.println("everything seems to be ok");
					}
					@Override
					public void onError(Throwable e) {
						System.out.println("something seems to be wrong");
						e.printStackTrace();
					}
					@Override
					public void onNext(final Row row) {
						final ArrayList<String> list=new ArrayList<String>();
						int cellNumber=fieldSet.titleList.size();
						cellNumber=cellNumber<row.getPhysicalNumberOfCells()?row.getPhysicalNumberOfCells():cellNumber;
						
						Observable.range(0, cellNumber)
						.map(new Func1<Integer, String>() {
							@Override
							public String call(Integer cellIndex) {
								Cell cell=row.getCell(cellIndex);
								if(cell==null){
				            		System.out.print("nullvalue\t");
									return "";
								}else{
				            		System.out.print(cell.toString().trim()+"\t");
									return cell.toString().trim();
								}
							}
						}).subscribe(new Action1<String>() {
							@Override
							public void call(String value) {
								list.add(value);
							}
						});
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
	 
	public void removeCells( final FieldSets fieldSets,Integer... cells){
		ArrayList<String> titleList=fieldSets.titleList;
		Observable.from(cells).subscribe(new Action1<Integer>() {
			@Override
			public void call(final Integer cell) {
				fieldSets.titleList.remove(cell.intValue());
				ArrayList<ArrayList<String>> dataLists=fieldSets.contentList;
				Observable.from(dataLists).subscribe(new Action1<ArrayList<String>>() {
					@Override
					public void call(ArrayList<String> list) {
						list.remove(cell.intValue());
					}
				});
			}
		});
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
	
	    private float getExcelCellAutoHeight(String str, float fontCountInline) {
	        float defaultRowHeight = 15.00f;//每一行的高度指定
	        float defaultCount = 0.00f;
	        for (int i = 0; i < str.length(); i++) {
	            float ff = getregex(str.substring(i, i + 1));
	            defaultCount = defaultCount + ff;
	        }
	        return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;//计算
	    }

	    private static float getregex(String charStr) {
	        
	        if(charStr==" ")
	        {
	            return 0.5f;
	        }
	        // 判断是否为字母或字符
	        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
	            return 0.5f;
	        }
	        // 判断是否为全角

	        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
	            return 1.00f;
	        }
	        //全角符号 及中文
	        if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
	            return 1.00f;
	        }
	        return 0.5f;

	    }

}
