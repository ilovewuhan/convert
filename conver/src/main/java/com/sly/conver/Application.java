package com.sly.conver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sly.conver.model.*;
import com.sly.conver.util.CommonUtil;
import com.sly.conver.util.ConverUtil;

/**
 * data转换类型
 * 
 * @author sly
 * @time 2020年4月22日
 */
public class Application {

	/**
	 * data转换
	 * 
	 * @param args
	 * @throws Exception
	 * @author sly
	 * @time 2020年4月23日
	 */
	public static void main(String[] args) throws Exception {

		// 贴敷模板类型数据更新
		ConverUtil.getSQl("select *  from personal_template_type where `type` = 1");
	}

}
