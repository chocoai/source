///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.jeesite.modules.asset.userroleconfig.service;
//
//import com.jeesite.common.entity.Page;
//import com.jeesite.common.service.CrudService;
//import com.jeesite.modules.asset.userroleconfig.dao.InterfaceFieldDao;
//import com.jeesite.modules.asset.userroleconfig.dao.InterfaceQueryDao;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceField;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceInfo;
//import com.jeesite.modules.asset.userroleconfig.entity.InterfaceQuery;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 接口查询条件Service
// *
// * @author dwh
// * @version 2018-07-18
// */
//@Service
//@Transactional(readOnly = true)
//public class InterfaceQueryService extends CrudService<InterfaceQueryDao, InterfaceQuery> {
//
//    @Autowired
//    private InterfaceFieldDao interfaceFieldDao;
//
//    /**
//     * 获取单条数据
//     *
//     * @param interfaceQuery
//     * @return
//     */
//    @Override
//    public InterfaceQuery get(InterfaceQuery interfaceQuery) {
//        return super.get(interfaceQuery);
//    }
//
//    /**
//     * 查询分页数据
//     *
//     * @param page           分页对象
//     * @param interfaceQuery
//     * @return
//     */
//    @Override
//    public Page<InterfaceQuery> findPage(Page<InterfaceQuery> page, InterfaceQuery interfaceQuery) {
//        return super.findPage(page, interfaceQuery);
//    }
//
//    /**
//     * 保存数据（插入或更新）
//     *
//     * @param interfaceQuery
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public void save(InterfaceQuery interfaceQuery) {
//        super.save(interfaceQuery);
//    }
//
//    /**
//     * 更新状态
//     *
//     * @param interfaceQuery
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public void updateStatus(InterfaceQuery interfaceQuery) {
//        super.updateStatus(interfaceQuery);
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param interfaceQuery
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public void delete(InterfaceQuery interfaceQuery) {
//        super.delete(interfaceQuery);
//    }
//
//    public List<InterfaceQuery> getInterfaceQueryList(String interfaceCode, String userCode, String fileCode) {
//
//        InterfaceQuery interfaceQuery = new InterfaceQuery();
//        interfaceQuery.setUserCode(userCode);
//        interfaceQuery.setInterfaceCode(interfaceCode);
//        List<InterfaceQuery> list = super.findList(interfaceQuery);
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                InterfaceQuery query = list.get(i);
//                InterfaceField field = new InterfaceField();
//                field.setFieldCode(query.getFieldCode());
//
//                field = interfaceFieldDao.get(field);
//                if (field!=null) {
//                    String sql = field.getFieldName() + " " + list.get(i).getSymbol() + " " + list.get(i).getFieldValue();
//                    list.get(i).setQueryListName(sql);
//                }
//
//            }
//        }
//
//        return list;
//    }
//
////	public static void main(String[] args) {
////		String stitching="1 and 2 ";
////		List<InterfaceQuery>interfaceQuerys=new ArrayList<>();
////		InterfaceQuery interfaceQuery=new InterfaceQuery();
////		interfaceQuery.setQueryCode("2");
////		interfaceQuery.setQueryName("dasd");
////		interfaceQuery.setFieldCode("1019783088362573824");
////		interfaceQuery.setFieldType("文本");
////		interfaceQuery.setFieldValue("大正物流");
////		interfaceQuery.setSymbol("=");
////		interfaceQuery.setInterfaceCode("1019783088345796608");
////		interfaceQuery.setUserCode("lenTest");
////		interfaceQuery.setQueryListName("用户ID = 大正物流");
////		interfaceQuery.setSortNo("1");
////		interfaceQuerys.add(interfaceQuery);
////		InterfaceQuery interfaceQuery2=new InterfaceQuery();
////		interfaceQuery2.setQueryCode("3");
////		interfaceQuery2.setQueryName("小丁");
////		interfaceQuery2.setFieldCode("1019783088362573824");
////		interfaceQuery2.setFieldType("文本");
////		interfaceQuery2.setFieldValue("user");
////		interfaceQuery2.setSymbol("=");
////		interfaceQuery2.setInterfaceCode("1019783088345796608");
////		interfaceQuery2.setUserCode("lenTest");
////		interfaceQuery2.setQueryListName("用户ID = user");
////		interfaceQuery2.setSortNo("2");
////		interfaceQuerys.add(interfaceQuery2);
////		String interfaceSQL="";
////		for (int i=0;i<interfaceQuerys.size();i++){
////
////			String param1="useRCode";
////			String param2="useRCode";
////			if(stitching==null){                                   //默认用and拼接
////				interfaceSQL+=param1+" "+interfaceQuerys.get(i).getSymbol()+" "+interfaceQuerys.get(i).getFieldValue() ;
////				if (interfaceQuerys.size()>1&&i<interfaceQuerys.size()-1){  //多条件并且不是最后一条用and拼接
////					interfaceSQL+=" and";
////				}
////			}else {
////				//查询这个序号的sql
////				String sortNo=interfaceQuerys.get(i).getSortNo();    //单个序号
////				String  danInterfaceSQL=param2+" "+interfaceQuerys.get(i).getSymbol()+" "+interfaceQuerys.get(i).getFieldValue() ; //单个序号对应的条件
////				String patt = "\\b"+sortNo+"+\\b";
////
//////                    // 用于测试的输入字符串
//////                    String input = "1 and 2";
////				System.out.println("Input:" + stitching);
////
////				// 从正则表达式实例中运行方法并查看其如何运行
////				Pattern r = Pattern.compile(patt);
////				Matcher m = r.matcher(stitching);
////				stitching= m.replaceAll(danInterfaceSQL);     //循环替换正则的数字，把patt替换成sql
////				interfaceSQL=stitching;
//////                    System.out.println("ReplaceAll:" + m.replaceAll("user = 'sadsa'"));
////				System.out.println(interfaceSQL);
////			}
////	}
//////		// 创建一个正则表达式模式，用以匹配一个单词（\b=单词边界）
////		String a="1";
////		String patt = "\\b"+a+"+\\b";
////
////		// 用于测试的输入字符串
////		String input = "1 and 2";
////		System.out.println("Input:" + input);
////
////		// 从正则表达式实例中运行方法并查看其如何运行
////		Pattern r = Pattern.compile(patt);
////		Matcher m = r.matcher(input);
////		System.out.println("ReplaceAll:" + m.replaceAll("user = 'sadsa'"));
////
////	}
////}
//
//
//    public InterfaceQuery getQueryByCode(InterfaceQuery interfaceQuery) {
//        return super.get(interfaceQuery);
//    }
//}