package com.socketio.server.socket;

import com.socketio.server.utils.DBHelper;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SocketIOServerMain {

    private static SocketIOServer socketio;

    public static void main(String[] args) throws InterruptedException{

        String sql = "select * from video_info";
        DBHelper dbHelper = new DBHelper(sql);

        String jsonObjectStr = "";
        try {
            ResultSet resultSet = dbHelper.getStatement().executeQuery();
            //获取列表
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();

                //遍历ResultSetMetaData数据每一列
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = resultSet.getString(columnName);
                    jsonObject.put(columnName, value);
                }
                jsonObjectStr = jsonObject.toString();
            }
            dbHelper.close();
        } catch (SQLException e) {
            dbHelper.close();
            e.printStackTrace();
        }

        socketio = new SocketIOServer();
        //这里发送的消息内容可以结合具体场景自定义对象
        socketio.startServer("borcast", jsonObjectStr);
        System.out.println(jsonObjectStr);

    }
}
