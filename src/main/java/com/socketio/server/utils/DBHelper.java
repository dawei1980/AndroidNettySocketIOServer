package com.socketio.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * 数据库帮助类
 */
public class DBHelper {
//    private static String url = "jdbc:postgresql://172.18.5.248:5432/aicameraplatform?characterEncoding=utf8&useSSL=false&autoReconnect=true";
//    private static String driver = "org.postgresql.Driver";
//    private static String user = "postgres";
//    private static String password = "aicamera";

    /**驱动程序名*/
    String driver = "com.mysql.cj.jdbc.Driver";
    /**URL指向要访问的数据库名mydata*/
    String url = "jdbc:mysql://localhost:3306/video_info?characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=UTC";
    /**MySQL配置时的用户名*/
    String user = "root";
    /**MySQL配置时的密码*/
    String password = "801230";

    //声明Connection对象
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    private PreparedStatement statement = null;

    public DBHelper(String sql) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.connection.close();
            this.statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
