package com.sly.conver.util;

import java.util.HashMap;
import java.util.Map;

/**
 * data转换配置
 * 
 * @author sly
 * @time 2020年4月23日
 */
public class ConverConfig {
	/** 数据库ip */
	public static String localhost = "rm-2ze74z6q3960a078nco.mysql.rds.aliyuncs.com";
	/** 数据库端口 */
	public static String port = "3306";
	/** 数据库名称 */
	public static String dbname = "cb_clinic";
	/** 用户名 */
	public static String username = "master_chunbo_01";
	/** 密码 */
	public static String password = "Adminsimba123456++";
	/** 转换后数据Excel导出位置 */
	public static String exportLocation = "D:\\Test\\sso";

	/** 连接参数 */
	public static String connectParams = "useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC";
	/** 菜单表头 */
	public static String[] menuTitle = { "id", "parentId", "name", "auth", "type", "icon", "status", "sort", "extend", "remark" };
	/** 角色表头 */
	public static String[] roleTitle = { "id", "name", "status", "extend", "remark" };
	/** 角色菜单关系表头 */
	public static String[] roleMenuTitle = { "roleId", "menuId" };
	/** 用户角色关系表头 */
	public static String[] userRoleTitle = { "userId", "roleId" };

	/** 菜单和数据库字段对应关系 */
	public static Map<String, String> menuRelation = new HashMap<String, String>() {
		private static final long serialVersionUID = 5373622840244888288L;
		{
			put("id", "funcId");
			put("parentId", "parentId");
			put("name", "funcName");
			put("auth", "funcUrl");
			put("type", "funcTag");
			put("icon", "funcIcon");
			put("status", "logicDel");
			put("sort", "funcSort");
			put("extend", "funcTag:funcTag,isOpen:isOpen,funcType:funcType");
			put("remark", "remark");
		}
	};

	/** 角色和数据库字段对应关系 */
	public static Map<String, String> roleRelation = new HashMap<String, String>() {
		private static final long serialVersionUID = 5373622840244888288L;
		{
			put("id", "roleId");
			put("name", "roleName");
			put("status", "logicDel");
			put("extend", "isOpen:isOpen");
			put("remark", "remark");
		}
	};

	/** 角色菜单关系和数据库字段对应关系 */
	public static Map<String, String> roleMenuRelation = new HashMap<String, String>() {
		private static final long serialVersionUID = 5373622840244888288L;
		{
			put("roleId", "roleId");
			put("menuId", "funcId");
		}
	};

	/** 角色菜单关系和数据库字段对应关系 */
	public static Map<String, String> userRoleRelation = new HashMap<String, String>() {
		private static final long serialVersionUID = 5373622840244888288L;
		{
			put("userId", "userId");
			put("roleId", "roleId");
		}
	};

}
