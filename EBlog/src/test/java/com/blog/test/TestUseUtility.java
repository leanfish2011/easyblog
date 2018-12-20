package com.blog.test;

import java.util.List;

import com.blog.po.SysUser;
import com.blog.po.SysUser;
import com.blog.utils.HibernateUtils;

public class TestUseUtility
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

//		SysUser user = new SysUser();
//		user.setId("test"); 
//		user.setUserName("Kobi"); 
//		HibernateUtils.add(user);//添加
		 
//		 user.setId(4); user.setName("Jamsww");
//		 HibernateUtils.update(user);//修改
		 

		/*
		 * user.setId(0); HibernateUtils.delete(user);//删除
		 */

		/*
		 * user.setUserCode("Jack"); SysUsers user1 = (SysUsers)
		 * HibernateUtils.findById(SysUsers.class, user.getUserCode());//查找
		 * System.out.println(user1.getId());
		 */

//		String sqlString = "select * from sys_users where id=?";
//		List<SysUsers> user1 = HibernateUtils.queryListParam(SysUsers.class, sqlString);
//		System.out.println(user1.get(0).getUserName());

	}

}
