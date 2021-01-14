package com.sly.conver.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	private static Pattern linePattern = Pattern.compile("_(\\w)");
	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/**
	 * 驼峰转下划线,最后转为大写
	 * @param str
	 * @return
	 */
	public static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString().toUpperCase();
	}

	/**
	 * 下划线转驼峰,正常输出
	 * @param str
	 * @return
	 */
	public static String lineToHump(String str) {
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
        /**
         * UUID 大写
         *
         * @return
         * @author sly
         * @time 2020年4月23日
         */
	public static String genUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	
	public static void main(String[] args) {
		System.out.println(genUUID());
	}

	public static <T> List<T> resultToList(ResultSet resultSet, Class<T> clazz) {
		//创建一个 T 类型的数组
		List<T> list = new ArrayList<>();
		try {

			//获取resultSet 的列的信息
			ResultSetMetaData metaData = resultSet.getMetaData();
			//遍历resultSet
			while (resultSet.next()) {
				//通过反射获取对象的实例
				T t = clazz.getConstructor().newInstance();
				//遍历每一列
				for (int i = 0; i < metaData.getColumnCount(); i++) {

					//获取列的名字
					String fName = metaData.getColumnLabel(i + 1);
					//因为列的名字和我们EMP中的属性名是一样的，所以通过列的名字获得其EMP中属性
					Field field = clazz.getDeclaredField(lineToHump(fName));
					//因为属性是私有的，所有获得其对应的set 方法。set+属性名首字母大写+其他小写
					String setName = "set" + lineToHump(fName).toUpperCase().substring(0, 1) + lineToHump(fName).substring(1);
					//因为属性的类型和set方法的参数类型一致，所以可以获得set方法
					Method setMethod = clazz.getMethod(setName, field.getType());
					//执行set方法，把resultSet中的值传入emp中，  再继续循环传值
					setMethod.invoke(t, resultSet.getObject(fName));
				}
				//把赋值后的对象 加入到list集合中
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回list
		return list;
	}

}
