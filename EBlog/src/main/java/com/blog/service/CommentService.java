package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CommentDAO;
import com.blog.po.BllCommont;
import com.blog.vo.CommentRequest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:23:59
 * @description：TODO
 */
@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public List<BllCommont> getCommentListByUser(String userId) {
		return commentDAO.getCommentListByUser(userId);
	}

	// 获取文章评论
	public List<CommentRequest> getCommentRequestById(String articleID) {
		return commentDAO.getCommentRequestById(articleID);
	}

	// 增加评论，该文章的评论次数加1，数据库使用触发器实现
	public boolean addComment(BllCommont commont) {
		return commentDAO.addComment(commont);
	}

	// 删除评论，评论对应的文章评论数量减1，数据库触发器实现
	public boolean deleteComment(String toDeleteIds) {
		return commentDAO.deleteComment(toDeleteIds);
	}

}
