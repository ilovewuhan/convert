package com.sly.conver.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: zenglinghao
 * @date 2020-12-10 16:47:27
 **/
public class ResultSetToBean {
    /**
     * 根据一个 ResultSet 结果集(当前行)生一个成相应的数据实体
     * @param resultSet 结果集
     * @param objectClass 数据实体类型,一个 JavaBean 如:  User
     * @param <T>
     * @return 封装好的 Bean 对象
     * @throws SQLException
     */
    public static<T> Object copy(ResultSet resultSet, Class<T> objectClass)  {
        //通过反射创建 objectClass 对象
        Object obj = null;
        try {
            obj = objectClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //objectClass 中所有属性
        Field[] fields = getAllFieldName(objectClass);
        try {
            for (Field f : fields) {
                //取出属性名称
                String fieldName = f.toString().substring(f.toString().lastIndexOf(".")+1);
                //判断int类型
                if (f.toString().indexOf("int") == -1) {
                    invokeSet(obj, fieldName, resultSet.getString(humpToLine2(fieldName)));
                } else {
                    invokeSet(obj, fieldName, resultSet.getInt(humpToLine2(fieldName)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 根据属性名获取get方法
     * @param objectClass
     * @param fieldName 属性名
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有属性
     * @param objectClass
     * @return Field[]
     */
    private static Field[] getAllFieldName(Class objectClass) {
        Field[] fields = objectClass.getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
        }
        return fields;
    }

    /**
     * 根据属性名执行相应set方法
     * @param o 执行对象
     * @param fieldName 属性名
     * @param value 属性值
     */
    private static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description      大写的字段变成下划线跟小写的形式
     * @author           yexinfa
     * @date             2019/12/27 15:27
     */
    public static String humpToLine2(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
