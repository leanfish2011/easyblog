package com.blog.dao;

import java.util.List;

import com.blog.po.BllCommont;
import com.blog.vo.CommentRequest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:26:09
 * @description：TODO
 */
public interface CommentDAO {

	/**
	 * 获取用户的评论
	 * @param userId 用户id
	 * @return
	 */
	List<BllCommont> getCommentListByUser(String userId);

	/**
	 * 删除评论
	 * @param toDeleteIds 评论id集合
	 * @return
	 */
	boolean deleteComment(String toDeleteIds);

	/**
	 * 新增评论
	 * @param commont
	 * @return
	 */
	boolean addComment(BllCommont commont);

	/**
	 * 获取文章评论
	 * @param articleID
	 * @return
	 */
	List<CommentRequest> getCommentRequestById(String articleID);

}
