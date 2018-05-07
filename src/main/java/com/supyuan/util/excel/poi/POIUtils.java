package com.supyuan.util.excel.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * poi操作office文件
 * @author liangxp
 *
 * Date:2017年11月9日上午9:30:29 
 * 
 * @email liangxp@anfawuliu.com
 */
public class POIUtils {
	
	/**
	 * 将对象集合输出为xls文件
	 * @author liangxp
	 * Date:2017年11月9日上午9:29:31 
	 *
	 * @param data
	 * @param title
	 * @param fileOut
	 * @return
	 */
	public static <E> void generateXlsExcelStream(List<E> data, String title, OutputStream fileOut){
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(title);
		for (int i=0; i< data.size();i++) {
			E obj = data.get(i);
			Row row = null;
			if(i==0){//第一次填写头信息
				row = sheet.createRow((short)0);
				Class<? extends Object> cls = obj.getClass();
				Field[] declaredFields = cls.getDeclaredFields();
				for (int j=0; j< declaredFields.length;j++) {
					Field field = declaredFields[j];
					FieldComment annotation = field.getAnnotation(FieldComment.class);
					if(annotation != null){
						Cell cell = row.createCell(j);
						cell.setCellValue(annotation.name());
					}
				}
			}
			row = sheet.createRow((short)(i+1));
			setCellValues(row, obj);
		}
		try {
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 将对象集合输出为xlsx文件
	 * @author liangxp
	 * Date:2017年11月9日上午9:30:06 
	 *
	 * @param data
	 * @param title
	 * @param fileOut
	 * @return
	 */
	public static <E> void generateXlsxExcelStream(List<E> data, String title, OutputStream fileOut){
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(title);
		for (int i=0; i< data.size();i++) {
			E obj = data.get(i);
			Row row = null;
			if(i==0){//第一次填写头信息
				row = sheet.createRow((short)0);
				Class<? extends Object> cls = obj.getClass();
				Field[] declaredFields = cls.getDeclaredFields();
				for (int j=0; j< declaredFields.length;j++) {
					Field field = declaredFields[j];
					FieldComment annotation = field.getAnnotation(FieldComment.class);
					if(annotation != null){
						Cell cell = row.createCell(j);
						cell.setCellValue(annotation.name());
					}
				}
			}
			row = sheet.createRow((short)(i+1));
			setCellValues(row, obj);
		}
		try {
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static <E> void setCellValues(Row row, E obj){
		Class<? extends Object> cls = obj.getClass();
		Field[] declaredFields = cls.getDeclaredFields();
		for (int i=0; i< declaredFields.length;i++) {
			Field fieldComment = declaredFields[i];
			try {
				FieldComment annotation = fieldComment.getAnnotation(FieldComment.class);
				if(annotation != null){
					String name = fieldComment.getName();
					Method method = cls.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), null);
					Object returns = method.invoke(obj, null);
					Cell cell = row.createCell(i);
					cell.setCellValue(returns+"");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
