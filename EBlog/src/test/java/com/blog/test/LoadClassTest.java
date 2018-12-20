package com.blog.test;

import java.net.URL;

import javax.servlet.Filter;

import org.junit.Test;

/**
 * @author：Tim
 * @date：2018年4月1日 下午8:07:48
 * @description：TODO
 */
public class LoadClassTest {

	@Test
	public void get() {
		URL url = Filter.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println("path:" + url.getPath() + "  name:" + url.getFile());
	}
}
