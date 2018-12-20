package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogTypeDAO;
import com.blog.po.BllArticletype;
import com.blog.vo.TypeCountResponse;

/**
 * @author：Tim
 * @date：2017年7月9日 下午9:36:49
 * @description：博客类别服务类
 */
@Service
public class BlogTypeService {

	@Autowired
	private BlogTypeDAO blogTypeDAO;

	/**
	 * 新增
	 * @param articletype
	 * @return
	 */
	public boolean addBlogType(BllArticletype articletype) {
		return blogTypeDAO.addBlogType(articletype);
	}

	public boolean updateBlogType(BllArticletype articletype) {
		return blogTypeDAO.updateBlogType(articletype);
	}

	/**
	 * 根据博客类别id，获取博客类别对象
	 * @param blogTypeId 博客类别id
	 * @return
	 */
	public BllArticletype getBlogTypeById(String blogTypeId) {
		return blogTypeDAO.getBlogTypeById(blogTypeId);
	}

	public List<BllArticletype> getTypeListByUser(String userId) {
		return blogTypeDAO.getTypeListByUser(userId);
	}

	/**
	 * 删除博客类别
	 * @param blogTypeIds 博客类别id集合
	 * @return
	 */
	public boolean deleteBlogType(String blogTypeIds) {
		return blogTypeDAO.deleteBlogType(blogTypeIds);
	}

	/**
	 * 获取博客类别下博客数量
	 * @param blogTypeId 博客类别id
	 * @return
	 */
	public int getBlogCountByType(String blogTypeId) {
		return blogTypeDAO.getBlogCountByType(blogTypeId);
	}

	public List<TypeCountResponse> getTypeCount() {
		return blogTypeDAO.getTypeCount();
	}

	public List<TypeCountResponse> getTypeCount(String userId) {
		return blogTypeDAO.getTypeCount(userId);
	}
}
