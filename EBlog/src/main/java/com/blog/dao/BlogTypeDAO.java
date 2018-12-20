package com.blog.dao;

import java.util.List;

import com.blog.po.BllArticletype;
import com.blog.vo.TypeCountResponse;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:01:04
 * @description：博客类别
 */
public interface BlogTypeDAO {

	/**
	 * 根据博客类别id，获取博客类别对象
	 * @param blogTypeId 博客类别id
	 * @return
	 */
	BllArticletype getBlogTypeById(String blogTypeId);

	/**
	 * 查询某用户的所有的博客类别
	 * @param userId 用户id
	 * @return
	 */
	List<BllArticletype> getTypeListByUser(String userId);

	/**
	 * 获取文章分类，以及每个分类下的文章总数
	 * @return
	 */
	List<TypeCountResponse> getTypeCount();

	/**
	 * 获取指定用户下的文章分类，以及每个分类下的文章总数
	 * @param userId 用户ID
	 * @return
	 */
	List<TypeCountResponse> getTypeCount(String userId);

	/**
	 * 删除博客类别
	 * @param blogTypeIds 博客类别id集合
	 * @return
	 */
	boolean deleteBlogType(String blogTypeIds);

	/**
	 * 获取博客类别下博客数量
	 * @param blogTypeId 博客id
	 * @return
	 */
	int getBlogCountByType(String blogTypeId);

	/**
	 * 新增博客类别
	 * @param articletype 博客类别对象
	 * @return
	 */
	boolean addBlogType(BllArticletype articletype);

	/**
	 * 修改博客类别
	 * @param articletype 博客类别
	 * @return
	 */
	boolean updateBlogType(BllArticletype articletype);

}
