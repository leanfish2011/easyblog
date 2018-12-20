package com.blog.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;

/**
 * hibernate方式操作数据库。配置文件在hibernate.cfg.xml中
 * 
 * @author tim
 *
 */
public class HibernateUtils {

	private static SessionFactory sessionFactory;// 1、创建SessionFactory

	static {
		Configuration cfg = new Configuration().configure();// 2、读取配置文件
		sessionFactory = cfg.buildSessionFactory();// 3、初始化sessionfactory工厂
	}

	public static Session getSession() {
		return sessionFactory.openSession();// 4、创建并打开session
	}

	/**
	 * 新增
	 * 
	 * @param obj 实体
	 * @return 是否新增成功
	 */
	public static boolean add(Object obj) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();// 回滚事务
			}
			throw e;// 必须抛出异常
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param obj 实体
	 * @return 是否更新成功
	 */
	public static boolean update(Object obj) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
			result = true;
		} catch (Exception e) {
			if (tx != null) {
				// 事物回滚
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param obj 实体
	 * @return 是否删除成功
	 */
	public static boolean delete(Object obj) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			result = true;
		} catch (Exception e) {
			if (tx != null) {
				// 事物回滚
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 根据主键，查找数据
	 * 
	 * @param clazz 实体名称
	 * @param id 实体id，为hibernate文件中标注的为id的字段
	 * @return 实体
	 */
	public static Object findById(Class clazz, Serializable id) {
		Session session = null;
		Object object = null;
		try {
			session = getSession();
			object = session.get(clazz, id);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return object;
	}

	/**
	 * 根据查询某个实体集合，实体对应PO，针对返回单表结果
	 * 
	 * @param clazz 实体
	 * @param sql sql语句
	 * @param param 不定参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> queryListParam(Class clazz, String sql, Object... param) {
		List<T> list = new ArrayList<T>();
		Session session = null;
		try {
			session = getSession();
			Query query = session.createSQLQuery(sql).addEntity(clazz);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setParameter(i, param[i]);
				}
			}
			list = query.list();// 返回list
		} catch (Exception ex) {
			System.out.println(ex.toString());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * 执行sql，得到某个实体集合，实体对应自定义VO，针对多表联合查询返回结果
	 * @param clazz 实体
	 * @param sql sql语句
	 * @param param sql语句参数
	 * @return 实体的集合
	 */
	public static <T> List<T> queryListParamBean(Class clazz, String sql, Object... param) {
		List<T> list = new ArrayList<T>();
		Session session = null;
		try {
			session = getSession();
			Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz));
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					query.setParameter(i, param[i]);
				}
			}
			list = query.list();// 返回list
		} catch (Exception ex) {
			System.out.println(ex.toString());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	/**
	 * 返回数据个数
	 * 
	 * @param sql sql语句
	 * @param pras
	 * @return
	 */
	public static int queryOne(String sql) {
		int count = 0;
		Session session = null;
		try {
			session = getSession();
			Query query = session.createSQLQuery(sql);

			count = ((Number) query.uniqueResult()).intValue();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	/**
	 * 执行update、delete类型sql语句
	 * @param sql
	 * @return
	 */
	public static boolean executeSql(String sql) {
		boolean result = false;
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		int ret = query.executeUpdate();
		if (ret > 0) {
			result = true;
		}
		tx.commit();

		return result;
	}

}
