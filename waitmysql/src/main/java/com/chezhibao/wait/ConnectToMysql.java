/*
 * FileName: ConnectToMysql.java
 * Author:   Arshle
 * Date:     2018年02月27日
 * Description: 连接mysql服务
 */
package com.chezhibao.wait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 〈连接mysql服务〉<br>
 * 〈用于测试mysql服务是否可用〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class ConnectToMysql {
    /**
     * mysql连接
     */
    private Connection connection;
    /**
     * 驱动类名称
     */
    private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    /**
     * 连接地址
     */
    private static String url = "jdbc:mysql://${mysqlServiceName}:3306/cc?useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull";
    /**
     * mysql用户名
     */
    private static String userName = "root";
    /**
     * mysql密码
     */
    private static String password = "mychebao";
    /**
     * 建立mysql连接
     * @param url 连接地址
     * @param userName 用户名
     * @param password 密码
     */
    private void connect(String url,String userName,String password) throws Exception {
        System.out.println("start connected to Mysql...");
        Class.forName(DRIVER_CLASS_NAME);
        connection = DriverManager.getConnection(url,userName,password);
        if(!connection.isClosed()){
            System.out.println("success connected to Mysql.");
        }
    }
    /**
     * 进行校验查询
     * @return 查询结果
     */
    private int doQuery() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select 1 as success");
        int result = 0;
        while (resultSet.next()){
            result = resultSet.getInt("success");
        }
        if(connection != null){
            statement.close();
            connection.close();
        }
        return result;
    }

    public static void main(String[] args) {
        ConnectToMysql tool = new ConnectToMysql();
        try {
            if(args == null || args.length == 0){
                throw new Exception("unknown mysql serviceName.");
            }
            if(args.length >= 3){
                userName = args[1];
                password = args[2];
            }
            tool.connect(url.replace("${mysqlServiceName}",args[0]),userName,password);
            int result = tool.doQuery();
            if(result != 1){
                throw new Exception("error query result.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
