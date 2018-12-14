/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeesite.modules.asset.util.JdbcTest;
import com.jeesite.modules.file.service.FileEntityService;
import com.jeesite.modules.file.service.FileUploadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.Consumables;
import com.jeesite.modules.asset.consumables.dao.ConsumablesDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 耗材档案Service
 *
 * @author dwh
 * @version 2018-04-23
 */
@Service
@Transactional(readOnly = true)
public class ConsumablesService extends CrudService<ConsumablesDao, Consumables> {

    @Autowired
    private ConsumablesDao dao;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileEntityService fileEntityService;

    /**
     * 获取单条数据
     *
     * @param consumables
     * @return
     */
    @Override
    public Consumables get(Consumables consumables) {
        return super.get(consumables);
    }

    /**
     * 查询分页数据
     *
     * @param page        分页对象
     * @param consumables
     * @return
     */
    @Override
    public Page<Consumables> findPage(Page<Consumables> page, Consumables consumables) {
        return super.findPage(page, consumables);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param consumables
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Consumables consumables) {
        this.updateStatus(consumables);
        super.save(consumables);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(consumables.getId(), "consumables_image");
    }

    /**
     * 更新状态
     *
     * @param consumables
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(Consumables consumables) {
        super.updateStatus(consumables);
    }

    /**
     * 删除数据
     *
     * @param consumables
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Consumables consumables) {
//        super.delete(consumables);
        dao.deleteOne(consumables.getConsumablesCode());
    }

    @Override
    public void addDataScopeFilter(Consumables consumables, String ctrlPermi) {
        consumables.getSqlMap().getDataScope().addFilter("dsf", "Consumables", "a.consumablesCode", ctrlPermi);
    }

    //读取excel表格插入数据库
    public static void main(String[] args)throws Exception {
        //获取文件名字
            File file = new File("D:/Personal/Desktop/耗材物品20180606042136.xlsx");
            String fileName = file.getName();
            //获取文件类型
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(" **** fileType:" + fileType);
            //获取输入流
            InputStream stream = new FileInputStream(file);
            //获取工作薄
            Workbook xssfWorkbook = null;
            if (fileType.equals("xls")) {
                xssfWorkbook = new HSSFWorkbook(stream);
            } else if (fileType.equals("xlsx")) {
                xssfWorkbook = new XSSFWorkbook(stream);
            } else {
                System.out.println("您输入的excel格式不正确");
            }
            // Read the Sheet
            Sheet Sheet = xssfWorkbook.getSheetAt(0);
            // Read the Row 从0开始
        List<Consumables> list=new ArrayList<>();
            for (int rowNum = 1; rowNum <= Sheet.getLastRowNum(); rowNum++) {
                Row Row = Sheet.getRow(rowNum);
                if (Row != null) {
                    //判断这行记录是否存在
                    if (Row.getLastCellNum() < 1 || "".equals(getValue(Row.getCell(1)))) {
                        continue;
                    }
                    System.out.println(getValue(Row.getCell(0)));
                    System.out.println(getValue(Row.getCell(1)));
                    System.out.println(getValue(Row.getCell(2)));
                    Consumables consumables=new Consumables();
                    consumables.setConsumablesCode(getValue(Row.getCell(2))); //耗材编号
                    consumables.setConsumablesName(getValue(Row.getCell(3))); //耗材名字
                    consumables.setBarCode(getValue(Row.getCell(4)));//商品条码
                    consumables.setCategoryCode(getValue(Row.getCell(0))); //分类编码
                    consumables.setSpecifications(getValue(Row.getCell(6)));//规格信号
                    if(getValue(Row.getCell(7)).equals("1")||getValue(Row.getCell(7)).equals("个")){
                        consumables.setMeasureUnit("1");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("2")||getValue(Row.getCell(7)).equals("台")){
                        consumables.setMeasureUnit("2");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("3")||getValue(Row.getCell(7)).equals("本")){
                        consumables.setMeasureUnit("3");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("4")||getValue(Row.getCell(7)).equals("件")){
                        consumables.setMeasureUnit("4");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("5")||getValue(Row.getCell(7)).equals("套")){
                        consumables.setMeasureUnit("5");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("6")||getValue(Row.getCell(7)).equals("条")){
                        consumables.setMeasureUnit("6");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("7")||getValue(Row.getCell(7)).equals("包")){
                        consumables.setMeasureUnit("7");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("8")||getValue(Row.getCell(7)).equals("盒")){
                        consumables.setMeasureUnit("8");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("9")||getValue(Row.getCell(7)).equals("双")){
                        consumables.setMeasureUnit("9");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("10")||getValue(Row.getCell(7)).equals("沓")){
                        consumables.setMeasureUnit("10");//计量单位
                    } else if (getValue(Row.getCell(7)).equals("11")||getValue(Row.getCell(7)).equals("箱")){
                        consumables.setMeasureUnit("11");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("12")||getValue(Row.getCell(7)).equals("瓶")){
                        consumables.setMeasureUnit("12");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("13")||getValue(Row.getCell(7)).equals("把")){
                        consumables.setMeasureUnit("13");//计量单位
                    }else if (getValue(Row.getCell(7)).equals("14")||getValue(Row.getCell(7)).equals("张")){
                        consumables.setMeasureUnit("14");//计量单位
                    }
                    else if (getValue(Row.getCell(7)).equals("15")||getValue(Row.getCell(7)).equals("支")){
                        consumables.setMeasureUnit("15");//计量单位
                    } else if (getValue(Row.getCell(7)).equals("16")||getValue(Row.getCell(7)).equals("幅")){
                        consumables.setMeasureUnit("16");//计量单位
                    }
                    else {
                        consumables.setMeasureUnit("0");//计量单位
                    }
                    consumables.setMaxStock((long) 0); //最大库存
                    consumables.setMinStock((long)0); //库存下限、
                    consumables.setStatus(getValue(Row.getCell(5)));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Cell cell=Row.getCell(10);
                    System.out.println("----------"+cell.getDateCellValue());
                    consumables.setCreateDate(cell.getDateCellValue());
                    consumables.setRemarks(getValue(Row.getCell(11)));
                    JdbcTest jdbc=new JdbcTest();
                    jdbc.save(consumables.getConsumablesCode(),consumables.getConsumablesName(),consumables.getBarCode(),consumables.getCategoryCode(),consumables.getBrandTrademark(),
                            consumables.getSpecifications(),consumables.getMeasureUnit(),consumables.getMaxStock(),consumables.getMinStock() ,consumables.getCreateDate(), consumables.getRemarks());

                    //获取每一行
//                    a = new A();
//                    a.setId(getValue(Row.getCell(0)));
//                    a.setName(getValue(Row.getCell(1)));
//                    aList.add(a);
                }
            }
//            return aList;
        }
        private static String getValue(Cell cell){
            int type = CellFormat.ultimateType(cell);
            if (type == Cell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (type == Cell.CELL_TYPE_NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else if (type == Cell.CELL_TYPE_BLANK) {
                return "";
            } else {
                return cell.getStringCellValue().trim();
            }

    }
}