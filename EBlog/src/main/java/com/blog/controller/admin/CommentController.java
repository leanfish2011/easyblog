package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllCommont;
import com.blog.service.CommentService;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:20:35
 * @description：评论控制器
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentController extends BaseController {

	private static Logger logger = Logger.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	@RequestMapping("/index")
	public String mycomment() {
		return "/admin/favorite/mycomment";
	}

	@RequestMapping("/getCommentListByUser")
	@ResponseBody
	public Map<String, Object> getCommentListByUser() {
		List<BllCommont> list = commentService.getCommentListByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteComment")
	@ResponseBody
	public Map<String, String> deleteComment(String commentIds) {
		boolean result = commentService.deleteComment(commentIds);

		logger.info("删除评论：" + commentIds);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addComment")
	@ResponseBody
	public Map<String, String> addComment(String articleId, String articleTitle, String comContent) {
		BllCommont commont = new BllCommont();

		commont.setId(UUID.randomUUID().toString());
		commont.setArticleId(articleId);
		commont.setArticleTitle(articleTitle);
		commont.setComContent(comContent);
		commont.setCreator(SessionHelper.getCurrentUserId(request));

		boolean result = commentService.addComment(commont);

		return JsonHelper.getSucessResult(result);
	}
}
