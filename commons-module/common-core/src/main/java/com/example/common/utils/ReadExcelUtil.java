package com.example.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZH
 */
public class ReadExcelUtil {

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";
    private static final String EXTENSION_XLSM = "xlsm";
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    private static final String E = "E";
    private static final String VAL = ".0";
    private static final String REGEX_RULE = "[0-9]*";

    public ReadExcelUtil() {
    }
    public ReadExcelUtil(InputStream inputStream, String fileName) throws IOException {
        if (fileName.endsWith(EXTENSION_XLS)) {
            wb = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(EXTENSION_XLSX)) {
            wb = new XSSFWorkbook(inputStream);
        } else if (fileName.endsWith(EXTENSION_XLSM)){
            wb = new XSSFWorkbook(inputStream);
        }
    }


    /**
     * 读取Excel数据内容
     *
     * @param
     * @return Map 包含单元格数据内容的Map对象
     * @author zengwendong
     */
    public List<Map<String,Object>> readExcelContent(int sheetIndex, int rowIndex) throws Exception {
            if(wb==null){
                throw new Exception("Workbook对象为空！");
            }
            List<Map<String,Object>> contents = new ArrayList<Map<String,Object>>();

            sheet = wb.getSheetAt(sheetIndex);
            // 得到总行数
            int rowNum = sheet.getLastRowNum();
            row = sheet.getRow(rowIndex);
            if(row == null){
                return null;
            }
            int colNum = row.getPhysicalNumberOfCells();
            //没有数据
            if(rowNum < 1){
                return null;
            }
            for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                int j = 0;
                Map<String,Object> cellValue = new HashMap<String, Object>(16);
                //判断某行是否有数据
                if(row.getCell(j) == null || row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK){
                    continue;
                }
                while (j < colNum) {
                    if(row != null){
                        Object obj = getCellFormatValue(row.getCell(j));
                        cellValue.put(String.valueOf(j), obj);
                    }
                    j++;
                }
                contents.add(cellValue);
            }
            return contents;
    }

    /**
     *
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     * @author zengwendong
     */
    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                    double doubleVal = cell.getNumericCellValue();
                    long longVal = Math.round(cell.getNumericCellValue());
                    if(Double.parseDouble(longVal + VAL) == doubleVal) {
                        cellvalue = longVal;
                    } else {
                        cellvalue = doubleVal;
                    }
                    break;
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    }else if(isNumber(cell)){
                        // 如果是纯数字
                        NumberFormat f=new DecimalFormat("############");
                        f.setMaximumFractionDigits(0);
                        cellvalue = f.format(cell.getNumericCellValue());
                    }
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                    // 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    public static boolean isNumber(Cell cell){
        Object o = cell.getNumericCellValue();
        boolean flag = func(String.valueOf(o));
        //判断是否是科学计数法
        String str = o.toString();
        if(str.indexOf(E) > 0){
            return true;
        }
        return flag;
    }

    public static final Pattern PATTERN = Pattern.compile(REGEX_RULE);
    public static Boolean func(String content) {
        Matcher m = PATTERN.matcher(content);
        return m.matches();
    }



}
