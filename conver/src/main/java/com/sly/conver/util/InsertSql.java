package com.sly.conver.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zenglinghao
 * @date 2020-12-10 17:28:58
 **/
public class InsertSql {

    private static Connection conn = null;
    private static Statement sm = null;
    private static String schema = "cb_clinic";//模式名
    private static String select = "SELECT * FROM";//查询sql
    private static String insert = "INSERT INTO";//插入sql
    private static String values = "VALUES";//values关键字
    private static String[] table = {"personal_template_type"};//table数组
    private static List<String> insertList = new ArrayList<String>();//全局存放insertsql文件的数据
    private static String filePath = "D:\\mysql\\sql.sql";//绝对路径导出数据的文件

    /**
     * 导出数据库表*@paramargs *@throwsSQLException
     */
    public static void main(String[] args) throws SQLException {
        List<String> listSQL = new ArrayList<String>();
        connectSQL("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.0.127:3306/motooling2?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=true", "root", "123456");//连接数据库
        listSQL.add("SELECT * from template_sm where personal_type_id != 0");
        executeSQL(conn, sm, listSQL);//执行sql并拼装
        createFile();//创建文件
    }

    /**
     * 创建insertsql.txt并导出数据
     */
    private static void createFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("创建文件名失败！！");
                e.printStackTrace();
            }
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            if (insertList.size() > 0) {
                for (int i = 0; i < insertList.size(); i++) {
                    bw.append(insertList.get(i));
                    bw.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拼装查询语句
     *
     * @return返回 select集合
     */
    private static List<String> createSQL() {
        List<String> listSQL = new ArrayList<String>();
        for (int i = 0; i < table.length; i++) {
            StringBuffer sb = new StringBuffer();
            sb.append(select).append(" ").append(schema).append(".").append(table[i]);
            listSQL.add(sb.toString());
        }
        return listSQL;
    }

    /**
     * 连接数据库创建statement对象
     * *@paramdriver
     * *@paramurl
     * *@paramUserName
     * *@paramPassword
     */
    public static void connectSQL(String driver, String url, String UserName, String Password) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, UserName, Password);
            sm = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行sql并返回插入sql
     *
     * @paramconn
     * @paramsm
     * @paramlistSQL *
     * @throwsSQLException
     */
    public static void executeSQL(Connection conn, Statement sm, List listSQL) throws SQLException {
        List<String> insertSQL = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = getColumnNameAndColumeValue(sm, listSQL, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs.close();
            sm.close();
            conn.close();
        }
    }

    /**
     * 获取列名和列值
     *
     * @return
     * @paramsm
     * @paramlistSQL
     * @paramrs
     * @throwsSQLException
     */
    private static ResultSet getColumnNameAndColumeValue(Statement sm, List listSQL, ResultSet rs) throws SQLException {
        if (listSQL.size() > 0) {
            for (int j = 0; j < listSQL.size(); j++) {
                String sql = String.valueOf(listSQL.get(j));
                rs = sm.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    StringBuffer ColumnName = new StringBuffer();
                    StringBuffer ColumnValue = new StringBuffer();
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rs.getString(i).trim();
                        if ("".equals(value)) {
                            value = "";
                        }
                        if (i == 1 || i == columnCount) {
                            if(i==columnCount){
                                ColumnName.append(",");
                            }
                            ColumnName.append(rsmd.getColumnName(i));
                            if( i== 1){
                                if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                    ColumnValue.append("'").append(value).append("',");
                                } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)|| Types.TINYINT == rsmd.getColumnType(i)) {
                                    ColumnValue.append(value).append(",");
                                } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                    ColumnValue.append("timestamp'").append(value).append("',");
                                } else {
                                    ColumnValue.append(value).append(",");

                                }
                            }else{
                                if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                    ColumnValue.append("'").append(value);
                                } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)|| Types.TINYINT == rsmd.getColumnType(i)) {
                                    ColumnValue.append(value);
                                } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                    ColumnValue.append("timestamp'").append(value);
                                } else {
                                    ColumnValue.append(value);

                                }
                            }

                        } else {
                            ColumnName.append("," + rsmd.getColumnName(i));
                            if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                ColumnValue.append("'").append(value).append("'").append(",");
                            } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)|| Types.TINYINT == rsmd.getColumnType(i)) {
                                ColumnValue.append(value).append(",");
                            } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                ColumnValue.append("timestamp'").append(value).append("',");
                            } else {
                                ColumnValue.append(value).append(",");
                            }
                        }
                    }
                    System.out.println(ColumnName.toString());
                    System.out.println(ColumnValue.toString());
                    insertSQL(ColumnName, ColumnValue,table[j]);
                }
            }
        }
        return rs;
    }

    /**
     * 拼装insertsql放到全局list里面
     * @paramColumnName
     * @paramColumnValue
     */
    private static void insertSQL(StringBuffer ColumnName, StringBuffer ColumnValue, String tableName) {
        StringBuffer insertSQL = new StringBuffer();
        insertSQL.append(insert).append(" ").append(schema).append(".")
                .append(tableName).append("(").append(ColumnName.toString()).append(")").append(values).append("(").append(ColumnValue.toString()).append(");");
        insertList.add(insertSQL.toString());
        System.out.println(insertSQL.toString());

    }

}
