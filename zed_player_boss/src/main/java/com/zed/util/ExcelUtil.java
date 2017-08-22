package com.zed.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/**
	 * 判断Cell中的数据类型并获取Cell中的数据
	 * @param XSSFCell
	 * @return String
	 */
	public static Object getCellContent(XSSFCell cell){
		int type=3;
		if(null != cell){
			type = cell.getCellType();
		}
		switch (type) {
		case XSSFCell.CELL_TYPE_STRING:  	// 字符串
			return cell.getRichStringCellValue().getString();
		case XSSFCell.CELL_TYPE_NUMERIC:   // 数字
			if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
				return cell.getDateCellValue();
			}else{
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_BOOLEAN:   // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_FORMULA:   // 公式
			return String.valueOf(cell.getCellFormula());
		case XSSFCell.CELL_TYPE_ERROR: // 故障
			return String.valueOf(cell.getErrorCellValue());
		case XSSFCell.CELL_TYPE_BLANK: // 空值
			return "";
		default:
			return "未知类型数据";
		}
	}
	
	/**
	 * 获取一行的所有单元格
	 * @param XSSFRow
	 * @return List&lt;XSSFCell&gt;
	 */
	
	public static List<XSSFCell> getCells(XSSFRow row){
		if (null != row) {
			List<XSSFCell> cells = new ArrayList<XSSFCell>();
			if (null != row) {
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);
					cells.add(cell);
				}
			}
			return cells;
		}
		return null;
	}
	
	/**
	 * 获取sheet中的rows
	 * @param XSSFSheet
	 * @return List&lt;XSSFRow&gt;
	 */
	public static List<XSSFRow> getRows(XSSFSheet sheet){
		if (null != sheet && sheet.getLastRowNum() > 0) {
			List<XSSFRow> rows = new ArrayList<XSSFRow>();
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (null != row) {
						rows.add(row);
				}
			}
			return rows;
		}
		return null;
	}
	
	/**
	 * 获取Excel中的sheet
	 * @param XSSFWorkbook
	 * @return XSSFSheet
	 */
	public static XSSFSheet getSheet(XSSFWorkbook wbs){
		XSSFSheet sheet = null;
		if (null != wbs) {
			sheet = wbs.getSheetAt(0);
		}
		return sheet;
	}
	
	/**
	 * 获取文件中的Workbook
	 * @param File
	 * @return XSSFWorkbook
	 */
	public static XSSFWorkbook getWorkbook(String url){
		if (null != url) {
			try {
				FileInputStream fis = new FileInputStream(url);
				return new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
