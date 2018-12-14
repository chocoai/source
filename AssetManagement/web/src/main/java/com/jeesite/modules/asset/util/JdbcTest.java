package com.jeesite.modules.asset.util;

import java.sql.*;
import java.util.Date;
import java.util.Queue;

public class JdbcTest {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://x.wd.uvanart.com:10100/jeesite";
    static final String DB_URL = "jdbc:mysql://localhost/jeesite";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123456";

    public  void save(String consumablesCode,	// 耗材编号
                            String consumablesName,		// 耗材名称
                            String barCode,		// 商品条码
                            String categoryCode,		// 分类编号
                            String brandTrademark,		// 品牌商标
                            String specifications,		// 规格型号
                            String measureUnit,	// 计量单位
                            Long maxStock,		// 最大库存
                            Long minStock,		// 库存下限
                            Date createDate,
                            String remark

                             ) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String a = "12345";
            Date b=new Date();
            String sql;
             sql = "insert into js_am_consumables values (?,?,?,?,'',?,?,0,0,'0','system',?,'system',?,?)";
//            ResultSet rs = stmt.executeQuery(sql);
            int i=0;
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1, consumablesCode);
                pstmt.setString(2, consumablesName);
                pstmt.setString(3, barCode);
                pstmt.setString(4, categoryCode);
                pstmt.setString(5, specifications);

                pstmt.setString(6, measureUnit);
                pstmt.setDate(7,new java.sql.Date(createDate.getTime()));
                pstmt.setDate(8,new java.sql.Date(b.getTime()));
                pstmt.setString(9,remark);
                i = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("There are so thing wrong!");
}}
