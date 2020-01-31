package com.louis.mango.common.utils.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建excel文件内容
 */
public class CreateExcelFileUtils {

    /**
     * 单sheet页导出
     * @param titleStr 表头
     * @param dataList 导出数据
     * @param fileName 表名
     * @return
     */
    public static File createExcelFile(String[] titleStr, List<List<String>> dataList, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(fileName); // sheet页名称
        Row row0 = sheet.createRow(0); // 创建标题栏

        CellStyle cellStyle0 = workbook.createCellStyle(); // 设置单元格格式

        // 给标题栏设置样式
        Font font0 = workbook.createFont();
        font0.setFontHeightInPoints((short) 13);
        font0.setColor(IndexedColors.BLACK.getIndex());

        cellStyle0.setFont(font0);

        for (int i=0; i<titleStr.length; i++) { // 创建标题栏标题
            Cell cell = row0.createCell(i);
            cell.setCellValue(titleStr[i]);
            cell.setCellStyle(cellStyle0);
        }

        // 填入数据
        for (int i=0; i<dataList.size(); i++) {
            Row row = sheet.createRow(i+1); // 创建内容栏
            for (int j=0; j<titleStr.length; j++) {
                // row.createCell(j); // 创建内容列
                row.createCell(j).setCellValue(dataList.get(i).get(j));
            }
        }

        return PoiUtils.createExcelFile(workbook, fileName);
    }

    /**
     * 多sheet页导出
     * @param titleStr 表头
     * @param sheetName sheet页名称
     * @param dataList 导出数据
     * @param fileName 表名
     * @return
     */
    public static File createExcelFileMoreSheet(String[] titleStr, String[] sheetName, List<List<List<String>>> dataList, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = null;
        for (int i=0; i<dataList.size(); i++) {
            List<List<String>> datas = dataList.get(i); // 一个sheet页数据
            sheet = workbook.createSheet(sheetName[i]); // sheet页名称
            Row row0 = sheet.createRow(0); // 创建标题栏

            CellStyle cellStyle0 = workbook.createCellStyle(); // 设置单元格格式

            // 给标题栏设置样式
            Font font0 = workbook.createFont();
            font0.setFontHeightInPoints((short) 13);
            font0.setColor(IndexedColors.BLACK.getIndex());

            cellStyle0.setFont(font0);

            for (int k=0; k<titleStr.length; k++) { // 创建标题栏标题
                Cell cell = row0.createCell(k);
                cell.setCellValue(titleStr[k]);
                cell.setCellStyle(cellStyle0);
            }

            // 填入数据
            for (int k=0; k<datas.size(); k++) {
                Row row = sheet.createRow(k+1); // 创建内容栏
                for (int j=0; j<titleStr.length; j++) {
                    // row.createCell(j); // 创建内容列
                    row.createCell(j).setCellValue(datas.get(k).get(j));
                }
            }
        }

        return PoiUtils.createExcelFile(workbook, fileName);
    }
}
