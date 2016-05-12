package com.example.tests;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
/**
 *  2003 solution
 * @author Administrator
 *
 */
public class FileUtils {
	  jxl.Workbook readwb = null;   
	  jxl.write.WritableWorkbook wwb=null;
	  public ArrayList<FieldBean> parseFileToMap(String excelPath){
		  ArrayList<FieldBean> arrayList=new ArrayList<FieldBean>();
	        try  {   
	            //构建Workbook对象, 只读Workbook对象   
	            //直接从本地文件创建Workbook   
	            InputStream instream = new FileInputStream(excelPath);   
	            readwb = Workbook.getWorkbook(instream);   
	            //Sheet的下标是从0开始   
	            //获取第一张Sheet表   
	            Sheet readsheet = readwb.getSheet(0);   
	            //获取Sheet表中所包含的总列数   
	            int rsColumns = readsheet.getColumns();   
	            //获取Sheet表中所包含的总行数   
	            int rsRows = readsheet.getRows();   
	            //获取指定单元格的对象引用   
            	ArrayList<String> columnName=new ArrayList<String>();
	            for (int i = 0; i < rsRows; i++)   {   
	            	HashMap<String,String> beanMap=new HashMap<String,String>();
	                for (int j = 0; j < rsColumns; j++){   
	                    Cell cell = readsheet.getCell(j, i);   
	                    System.out.print(cell.getContents() + " ");
	                    if(i==0){
	                    	columnName.add(cell.getContents());
	                    }
	                    beanMap.put(columnName.get(j), cell.getContents());
	                }   
	                FieldBean fieldBean=(FieldBean) convertMap(FieldBean.class,beanMap);
	                if(0!=i){
	                	arrayList.add(fieldBean);
	                }
	                System.out.println();   
	            }   
	            
	        }catch(Exception e){
	        	e.printStackTrace();
	        } finally {   
	            readwb.close();   
	        }   
		  return arrayList;
	  }
	  public void createWriteFile() throws Exception{
		  //利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄   
		  try{
		  wwb = Workbook.createWorkbook(new File(   
                  "F:/红楼人物1.xls"), readwb);   
          //读取第一张工作表   
          jxl.write.WritableSheet ws = wwb.getSheet(0);   
          //获得第一个单元格对象   
          jxl.write.WritableCell wc = ws.getWritableCell(0, 0);   

          //判断单元格的类型, 做出相应的转化   

          if (wc.getType() == CellType.LABEL){   
              Label l = (Label) wc;   
              l.setString("新姓名");   
          }   
          //写入Excel对象   
          wwb.write();   
          wwb.close();   
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  
	  }
	  /**
	   *  map -> javabean
	   * @param type
	   * @param map
	   * @return
	   * @throws IntrospectionException
	   * @throws IllegalAccessException
	   * @throws InstantiationException
	   * @throws InvocationTargetException
	   */
	  @SuppressWarnings("rawtypes")  
	    private static Object convertMap(Class type, Map map)  
	            throws IntrospectionException, IllegalAccessException,  
	            InstantiationException, InvocationTargetException {  
	        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性  
	        Object obj = type.newInstance(); // 创建 JavaBean 对象  
	  
	        // 给 JavaBean 对象的属性赋值  
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();  
	        for (int i = 0; i< propertyDescriptors.length; i++) {  
	            PropertyDescriptor descriptor = propertyDescriptors[i];  
	            String propertyName = descriptor.getName();  
	  
	            if (map.containsKey(propertyName)) {  
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。  
	                Object value = map.get(propertyName);  
	  
	                Object[] args = new Object[1];  
	                args[0] = value;  
	  
	                descriptor.getWriteMethod().invoke(obj, args);  
	            }  
	        }  
	        return obj;  
	    }  
}
