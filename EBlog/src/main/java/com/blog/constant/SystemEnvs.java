package com.blog.constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @author：Tim
 * @date：2018年1月20日 上午9:58:21
 * @description：系统参数
 */
public class SystemEnvs {

	private static Properties properties = new Properties();// 读取配置文件
	static {
		try {
			properties.load(SystemEnvs.class.getResourceAsStream("/systemEnv.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过key读取配置文件中值
	 * @param key
	 * @return
	 */
	public static String getSystemEnvsValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取一个配置列表，按逗号分割进行划分
	 * 
	 * @param name
	 * @return 如果未配置或配置为空，则返回一个空的list
	 */
	public static List<String> getAsList(String name) {
		List<String> set = new ArrayList<String>();

		String value = properties.getProperty(name);
		if (StringUtils.isNotBlank(value)) {
			String[] vs = value.split(",");

			for (String v : vs) {
				if (StringUtils.isNotBlank(v.trim()))
					set.add(v.trim());
			}
		}

		return set;
	}

	/**
	 * 每页数量。用于首页分页
	 * @return
	 */
	public static int getPageSize() {
		return Integer.valueOf(getSystemEnvsValue("pagesize"));
	}

	/**
	 * 相似度阈值。用于头像登录
	 * @return
	 */
	public static double getSimilarty() {
		return Double.valueOf(getSystemEnvsValue("similarty"));
	}

	/**
	 * 是否启用ElasticSearch搜索
	 * @return
	 */
	public static boolean getEnableES() {
		return Boolean.valueOf(getSystemEnvsValue("enableElasticSearch"));
	}

}
