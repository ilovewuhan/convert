package com.sly.conver.util;

import com.alibaba.fastjson.JSON;
import com.sly.conver.constant.SsoConstant;
import com.sly.conver.model.*;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverUtil {

	private static String filePath = "D:\\mysql\\sqlali.sql";//绝对路径导出数据的文件

	private static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = DriverManager.getConnection("jdbc:mysql://" + ConverConfig.localhost + ":"
				+ ConverConfig.port + "/" + ConverConfig.dbname + "?" + ConverConfig.connectParams,
				ConverConfig.username, ConverConfig.password);

		return connection;
	}

	public static void getSQl(String sql) throws Exception {

		Connection connection = getConnection();
		PreparedStatement stat;
//		stat = connection.prepareStatement("SELECT MAX(pt_id) as num from personal_template_type");
//		ResultSet rs0 = stat.executeQuery();
//		rs0.next();
		// 获取模板类型表最大id
//		int maxId = rs0.getInt("num");
		int maxId = 40000;
		stat = connection.prepareStatement(sql);
		ResultSet rs = stat.executeQuery();
		ResultSet rs2;
		// 查询所有的
		List<PersonalTemplateType> result = CommonUtil.resultToList(rs, PersonalTemplateType.class);
		File file = new File(filePath);
		List<String> updateList = new ArrayList<>();
		StringBuilder sb = new StringBuilder("");
		// 增加模板类型时使用
		for (PersonalTemplateType ptt : result) {
			// 查询 同一模板类型 贴敷模板的医生id
			stat = connection.prepareStatement("SELECT DISTINCT doctor_id from template_sm where personal_type_id =  " + ptt.getPtId());
			rs2 = stat.executeQuery();
			// 用于统计同一模板类型下的医生个数
			int time = 1;
			int doctorId;

			while (rs2.next()) {
				doctorId = rs2.getInt("doctor_id");
				if (time == 1) {
					sb.append("update personal_template_type SET doctor_id = " + doctorId).append(" where pt_id = " + ptt.getPtId()).append(";\n");
					updateList.add(sb.toString());
					sb.setLength(0);
				} else if (time > 1) {
					// 同一模板下有多个医生 则需增加一条 插入 模板类型语句 以及 更新 模板表的数据
					maxId ++;
					sb.append("INSERT INTO cb_clinic.personal_template_type " +
							"(pt_id, pt_name, pt_status, clinic_id, doctor_id, `type`, ptp_type) " +
							"VALUES("+maxId+",'"+ptt.getPtName()+"',"+ptt.getPtStatus()+","+ptt.getClinicId()+","+doctorId+","+ptt.getType()+","+
							ptt.getPtpType()+");\n");
					sb.append("-- 插入模板类型表\n").
							append("UPDATE template_sm set personal_type_id = "+maxId+" where doctor_id = "+doctorId
							+" and personal_type_id = "+ptt.getPtId()+";\n");
					updateList.add(sb.toString());
					sb.setLength(0);
				}
				time ++;
			}
		}
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
			if (updateList.size() > 0) {
				for (int i = 0; i < updateList.size(); i++) {
					bw.append(updateList.get(i));
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

		stat.close();
		connection.close();
		int maxWm = getOtherSQl("select *  from personal_template_type where `type` = 2", maxId, "template_wm");
		int maxCM = getOtherSQl("select *  from personal_template_type where `type` = 3", maxWm, "template_cm");
		int maxPM = getOtherSQl("select *  from personal_template_type where `type` = 4", maxCM, "template_pm");
		getOtherSQl("select *  from personal_template_type where `type` = 5", maxPM, "template_pgm");

	}

	/**西药模板*/
	public static int getOtherSQl(String sql, int maxId, String tableName) throws Exception {
		Connection connection = getConnection();
		PreparedStatement stat;
		stat = connection.prepareStatement(sql);
		ResultSet rs = stat.executeQuery();
		ResultSet rs2;
		// 查询所有的
		List<PersonalTemplateType> result = CommonUtil.resultToList(rs, PersonalTemplateType.class);
		File file = new File(filePath);
		List<String> updateList = new ArrayList<>();
		StringBuilder sb = new StringBuilder("");
		// 增加模板类型时使用
		for (PersonalTemplateType ptt : result) {
			stat = connection.prepareStatement("SELECT DISTINCT doctor_id from "+tableName+" where personal_type_id =  " + ptt.getPtId());
			rs2 = stat.executeQuery();
			// 用于统计同一模板类型下的医生个数
			int time = 1;
			int doctorId;

			while (rs2.next()) {
				doctorId = rs2.getInt("doctor_id");
				if (time == 1) {
					sb.append("update personal_template_type SET doctor_id = " + doctorId).append(" where pt_id = " + ptt.getPtId()).append(";\n");
					updateList.add(sb.toString());
					sb.setLength(0);
				} else if (time > 1) {
					// 同一模板下有多个医生 则需增加一条 插入 模板类型语句 以及 更新 模板表的数据
					maxId ++;
					sb.append("INSERT INTO cb_clinic.personal_template_type " +
							"(pt_id, pt_name, pt_status, clinic_id, doctor_id, `type`, ptp_type) " +
							"VALUES("+maxId+",'"+ptt.getPtName()+"',"+ptt.getPtStatus()+","+ptt.getClinicId()+","+doctorId+","+ptt.getType()+","+
							ptt.getPtpType()+");\n");
					sb.append("-- 插入模板类型表\n").
							append("UPDATE "+tableName+" set personal_type_id = "+maxId+" where doctor_id = "+doctorId
									+" and personal_type_id = "+ptt.getPtId()+";\n");
					updateList.add(sb.toString());
					sb.setLength(0);
				}
				time ++;
			}
		}
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
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			if (updateList.size() > 0) {
				for (int i = 0; i < updateList.size(); i++) {
					bw.append(updateList.get(i));
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

		stat.close();
		connection.close();
		return maxId;
	}

	/**
	 *
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static List<Menu> getMenuData(String sql) throws Exception {
		List<Menu> result = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement stat = getConnection().prepareStatement(sql);

		ResultSet rs = stat.executeQuery();
		

		Map<String, String> menuRelation = ConverConfig.menuRelation;

		while (rs.next()) {
			Menu menu = new Menu();
			// oldId
			menu.setOldId(rs.getString(menuRelation.get("id")));
			// oldParentId
			menu.setOldParentId(rs.getString(menuRelation.get("parentId")));
			// name
			menu.setName(rs.getString(menuRelation.get("name")));
			// auth
			menu.setAuth(rs.getString(menuRelation.get("auth")));
			// type	这里的例子系统里0是菜单,1是按钮需要转换一下
			if("0".equals(rs.getString(menuRelation.get("type")))) {
				menu.setType(SsoConstant.MenuType.MENU);
			} else {
				menu.setType(SsoConstant.MenuType.BUTTON);
			}
			// icon
			menu.setIcon(rs.getString(menuRelation.get("icon")));
			// status 这里的例子系统里Y是删除N是有效,另一个字段isOpen是1激活 0关闭
			if("Y".equals(rs.getString(menuRelation.get("status")))) {
				menu.setStatus(SsoConstant.Status.DELETE);
			} else if("N".equals(rs.getString(menuRelation.get("status"))) && "1".equals(rs.getString("isOpen"))){
				menu.setStatus(SsoConstant.Status.ACTIVE);
			} else {
				menu.setStatus(SsoConstant.Status.FORBID);
			}
			// sort
			menu.setSort(rs.getInt(menuRelation.get("sort")));
			// remark
			menu.setRemark(rs.getString(menuRelation.get("remark")));
			// extend
			String extendStr = menuRelation.get("extend");
			String[] extendSplit = extendStr.split(",");

			Map<String, Object> extend = new HashMap<String, Object>();
			for (String string : extendSplit) {
				String[] split = string.split(":");
				extend.put(split[1], rs.getString(split[0]));
			}
			menu.setExtend(JSON.toJSONString(extend));

			result.add(menu);
		}

		stat.close();
		connection.close();
		return result;
	}

	/**
	 * 获取角色数据
	 * 
	 * @param sql
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static List<Role> getRoleData(String sql) throws Exception {
		List<Role> result = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement stat = getConnection().prepareStatement(sql);

		ResultSet rs = stat.executeQuery();
		

		Map<String, String> roleRelation = ConverConfig.roleRelation;

		while (rs.next()) {
			Role role = new Role();
			// oldId
			role.setOldId(rs.getString(roleRelation.get("id")));
			// name
			role.setName(rs.getString(roleRelation.get("name")));
			// status isOpen这里的例子系统里Y是删除N是有效,另一个字段isOpen是1激活 0关闭
			if("Y".equals(rs.getString(roleRelation.get("status")))) {
				role.setStatus(SsoConstant.Status.DELETE);
			} else if("N".equals(rs.getString(roleRelation.get("status"))) && "1".equals(rs.getString("isOpen"))) {
				role.setStatus(SsoConstant.Status.ACTIVE);
			} else {
				role.setStatus(SsoConstant.Status.FORBID);
			}
			// remark
			role.setRemark(rs.getString(roleRelation.get("remark")));
			// extend
			String extendStr = roleRelation.get("extend");
			String[] extendSplit = extendStr.split(",");

			Map<String, Object> extend = new HashMap<String, Object>();
			for (String string : extendSplit) {
				String[] split = string.split(":");
				extend.put(split[1], rs.getString(split[0]));
			}
			role.setExtend(JSON.toJSONString(extend));

			result.add(role);
		}

		stat.close();
		connection.close();
		return result;
	}

	/**
	 * 获取角色菜单关系数据
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static List<RoleMenu> getRoleMenuData(String sql) throws Exception {
		List<RoleMenu> result = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement stat = getConnection().prepareStatement(sql);

		ResultSet rs = stat.executeQuery();
		

		Map<String, String> roleMenuRelation = ConverConfig.roleMenuRelation;

		while (rs.next()) {
			RoleMenu roleMenu = new RoleMenu();
			// oldRoleId
			roleMenu.setOldRoleId(rs.getString(roleMenuRelation.get("roleId")));
			// oldMenuId
			roleMenu.setOldMenuId(rs.getString(roleMenuRelation.get("menuId")));

			result.add(roleMenu);
		}

		stat.close();
		connection.close();
		return result;
	}

	/**
	 * 获取用户角色关系数据
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static List<UserRole> getUserRoleData(String sql) throws Exception {
		List<UserRole> result = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement stat = getConnection().prepareStatement(sql);

		ResultSet rs = stat.executeQuery();
		

		Map<String, String> userRoleRelation = ConverConfig.userRoleRelation;

		while (rs.next()) {
			UserRole userRole = new UserRole();
			// userId
			userRole.setOldUserId(rs.getString(userRoleRelation.get("userId")));
			// roleId
			userRole.setOldRoleId(rs.getString(userRoleRelation.get("roleId")));

			result.add(userRole);
		}

		stat.close();
		connection.close();
		return result;
	}

	/**
	 * Excel导出菜单
	 * 
	 * @param menus
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static void exportMenuData(List<Menu> menus) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();

			Sheet sheet = workbook.createSheet("Sheet1");
			// 第一行为头
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < ConverConfig.menuTitle.length; i++) {
				Cell cell = titleRow.createCell(i);
				cell.setCellValue(ConverConfig.menuTitle[i]);
			}

			int rowIndex = 1;
			for (Menu menu : menus) {
				Row dataRow = sheet.createRow(rowIndex++);
				// id
				Cell cell0 = dataRow.createCell(0);
				cell0.setCellValue(menu.getId());
				// parentId
				Cell cell1 = dataRow.createCell(1);
				cell1.setCellValue(menu.getParentId());
				// name
				Cell cell2 = dataRow.createCell(2);
				cell2.setCellValue(menu.getName());
				// auth
				Cell cell3 = dataRow.createCell(3);
				cell3.setCellValue(menu.getAuth());
				// type
				Cell cell4 = dataRow.createCell(4);
				cell4.setCellValue(menu.getType());
				// icon
				Cell cell5 = dataRow.createCell(5);
				cell5.setCellValue(menu.getIcon());
				// status
				Cell cell6 = dataRow.createCell(6);
				cell6.setCellValue(menu.getStatus());
				// sort
				Cell cell7 = dataRow.createCell(7);
				cell7.setCellValue(menu.getSort());
				// extend
				Cell cell8 = dataRow.createCell(8);
				cell8.setCellValue(menu.getExtend());
				// remark
				Cell cell9 = dataRow.createCell(9);
				cell9.setCellValue(menu.getRemark());
			}

			// 导出
			OutputStream fileOut = new FileOutputStream(ConverConfig.exportLocation + "/菜单.xls");
			workbook.write(fileOut);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Excel导出菜单
	 * 
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static void exportRoleData(List<Role> roles) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();

			Sheet sheet = workbook.createSheet("Sheet1");
			// 第一行为头
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < ConverConfig.roleTitle.length; i++) {
				Cell cell = titleRow.createCell(i);
				cell.setCellValue(ConverConfig.roleTitle[i]);
			}

			int rowIndex = 1;
			for (Role role : roles) {
				Row dataRow = sheet.createRow(rowIndex++);
				// id
				Cell cell0 = dataRow.createCell(0);
				cell0.setCellValue(role.getId());
				// name
				Cell cell1 = dataRow.createCell(1);
				cell1.setCellValue(role.getName());
				// status
				Cell cell2 = dataRow.createCell(2);
				cell2.setCellValue(role.getStatus());
				// extend
				Cell cell3 = dataRow.createCell(3);
				cell3.setCellValue(role.getExtend());
				// remark
				Cell cell4 = dataRow.createCell(4);
				cell4.setCellValue(role.getRemark());
			}

			// 导出
			OutputStream fileOut = new FileOutputStream(ConverConfig.exportLocation + "/角色.xls");
			workbook.write(fileOut);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Excel导出角色菜单关系
	 * 
	 * @param roleMenus
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static void exportRoleMenuData(List<RoleMenu> roleMenus) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();

			Sheet sheet = workbook.createSheet("Sheet1");
			// 第一行为头
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < ConverConfig.roleMenuTitle.length; i++) {
				Cell cell = titleRow.createCell(i);
				cell.setCellValue(ConverConfig.roleMenuTitle[i]);
			}
			
			int rowIndex = 1;
			for (RoleMenu roleMenu : roleMenus) {
				Row dataRow = sheet.createRow(rowIndex++);
				// roleId
				Cell cell0 = dataRow.createCell(0);
				cell0.setCellValue(roleMenu.getRoleId());
				// menuId
				Cell cell1 = dataRow.createCell(1);
				cell1.setCellValue(roleMenu.getMenuId());	
			}
			
			// 导出
			OutputStream fileOut = new FileOutputStream(ConverConfig.exportLocation + "/角色菜单关系.xls");
			workbook.write(fileOut);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Excel导出用户角色关系
	 * 
	 * @param userRoles
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static void exportUserRoleData(List<UserRole> userRoles) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();

			Sheet sheet = workbook.createSheet("Sheet1");
			// 第一行为头
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < ConverConfig.userRoleTitle.length; i++) {
				Cell cell = titleRow.createCell(i);
				cell.setCellValue(ConverConfig.userRoleTitle[i]);
			}
			
			int rowIndex = 1;
			for (UserRole userRole : userRoles) {
				Row dataRow = sheet.createRow(rowIndex++);
				// userId
				Cell cell0 = dataRow.createCell(0);
				cell0.setCellValue(userRole.getUserId());
				// roleId
				Cell cell1 = dataRow.createCell(1);
				cell1.setCellValue(userRole.getRoleId());	
			}
			
			// 导出
			OutputStream fileOut = new FileOutputStream(ConverConfig.exportLocation + "/用户角色关系.xls");
			workbook.write(fileOut);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
