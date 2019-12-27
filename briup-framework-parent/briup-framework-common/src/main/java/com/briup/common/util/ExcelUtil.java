package com.briup.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.briup.common.exception.CommonException;

@SuppressWarnings("all")
public class ExcelUtil {


    /**
     * 导出excel，普通通用的格式
     * @param title     标题
     * @param header    表头信息
     * @param list      数据
     * @param response  响应对象
     */
    public static void writeCommonExcelToResponse(String title, String[] header, List<List<String>> list, HttpServletResponse response){
        Asserts.notEmpty(title);
        Asserts.notEmpty(header);
        Asserts.notEmpty(list);
        if(header.length == 0){
            throw new CommonException("表头设置不能为空");
        }

        //创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建单元格样式
        HSSFCellStyle styleMain = workbook.createCellStyle();
        //水平居中
        styleMain.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        styleMain.setVerticalAlignment(VerticalAlignment.CENTER);

        //创建sheet页
        HSSFSheet sheet = workbook.createSheet(title);

        //设置表头
        setCommonHeader(sheet,header,styleMain);


        //处理表中的数据
        HSSFRow row;
        HSSFCell cell;

        //excel中一行数据的总单元格的数量
        for(int i=0;i<list.size();i++){

            List<String> subList = list.get(i);
            row = sheet.createRow(i+1);

            for(int j=0;j<subList.size();j++){
                cell = row.createCell(j);
                cell.setCellStyle(styleMain);
                cell.setCellValue(new HSSFRichTextString(subList.get(j)));
//                cell.setCellValue(subList.get(j));
            }
        }


        try {
            OutputStream os;
            if(response == null){
                os = new FileOutputStream("d:/"+title+".xls");
            }else{
                response.setContentType("application/x-download");
//                response.setHeader("Content-Disposition", "attachment;filename="
//                        + java.net.URLEncoder.encode(title, "utf-8") + ".xls");
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(title.getBytes("utf-8"),"ISO-8859-1") + ".xls");
//                response.setHeader("Content-Disposition", "attachment; filename="
//                        + title + ".xls");
//                response.setHeader("Content-disposition", "attachment;filename="
//                        + "export-data" + ".xls");

                os = response.getOutputStream();
            }

            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void setCommonHeader(HSSFSheet sheet, String[] header, HSSFCellStyle styleMain) {
        //设置第一行表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        for(int i=0;i<header.length;i++){
            cell = row.createCell(i);
            if("采集时间".equals(header[i])){
//                单位是1/256个字符宽度，100个字符宽度就是100*256
                sheet.setColumnWidth(i,30 * 256);
//                sheet.autoSizeColumn(i);
            }
            cell.setCellValue(new HSSFRichTextString(header[i]));
            cell.setCellStyle(styleMain);
        }
    }
}
