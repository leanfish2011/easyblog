package com.blog.controller.index;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.blog.constant.SystemEnvs;
import com.blog.controller.admin.BaseController;
import com.blog.po.BllArticle;
import com.blog.po.SysUser;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.CommentService;
import com.blog.service.UserService;
import com.blog.utils.ArticleUtils;
import com.blog.utils.JsonHelper;
import com.blog.vo.ArticleIndexResponse;
import com.blog.vo.CommentRequest;
import com.blog.vo.TypeCountResponse;

/**
 * @author：Tim
 * @date：2018年2月5日 下午3:18:42
 * @description：用于首页（即不需要登录）跳转
 */

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	int pCount = SystemEnvs.getPageSize();// 每页显示记录数目

	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogTypeService blogTypeService;

	@Autowired
	private UserService userService;

	/**
	 * 开始页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String register() {
		return "admin/register";
	}

	// 生成分页按钮
	@RequestMapping("/getArticlePage")
	@ResponseBody
	public Map<String, Object> getArticlePage(@RequestParam(value = "action", required = true) String action) {

		RequestContext requestContext = new RequestContext(request);// 读取多语资源

		String url = request.getContextPath() + action;
		boolean isType = url.contains("typeid=") ? true : false;// 判断是否是点击了分类，分类则url带有类别参数

		int toalCount = blogService.getArticlePage(url);// 总数
		int page = (toalCount % pCount == 0) ? (toalCount / pCount) : (toalCount / pCount + 1);// 总页数

		// 拼接分页html
		StringBuffer strBPageHtml = new StringBuffer();
		strBPageHtml.append("<p>");
		strBPageHtml.append(requestContext.getMessage("all"));
		strBPageHtml.append(page);
		if (toalCount > 0) {
			strBPageHtml.append(requestContext.getMessage("pages"));
		} else {
			strBPageHtml.append(requestContext.getMessage("page"));
		}

		strBPageHtml.append("&nbsp;");
		for (int i = 1; i < page + 1; i++) {
			if (i == 1) {
				strBPageHtml.append("<a class='aPageDisable' href='javascript:void(0);'");
			} else {
				strBPageHtml.append("<a href='javascript:void(0);'");
			}

			strBPageHtml.append(" onclick=\"getArticle('");
			strBPageHtml.append(url);
			if (isType) {
				strBPageHtml.append("&page=");
			} else {
				strBPageHtml.append("?page=");
			}

			strBPageHtml.append(i);
			strBPageHtml.append("')\">");
			strBPageHtml.append(i);
			strBPageHtml.append("</a>&nbsp;");
		}
		strBPageHtml.append("</p>");

		return JsonHelper.getModel(strBPageHtml.toString());
	}

	// 按照文章分类读取该分类下的文章
	@RequestMapping("/getArticleByType")
	@ResponseBody
	public Map<String, Object> getArticleByType(Model model,
			@RequestParam(value = "typeid", required = true) String typeid,
			@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByType(typeid, page));
		model.addAttribute("dto", newList);

		return JsonHelper.getModelMap(newList);
	}

	// 分页读取文章，按照时间先后顺序排列
	@RequestMapping("/getallArticle")
	@ResponseBody
	public Map<String, Object> getallArticleList(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(0, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照阅读量排列
	@RequestMapping("/getArticleRead")
	@ResponseBody
	public Map<String, Object> getArticleRead(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(1, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照评论量排列
	@RequestMapping("/getArticleCommit")
	@ResponseBody
	public Map<String, Object> getArticleCommit(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(2, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照推荐量排列
	@RequestMapping("/getArticleSuggest")
	@ResponseBody
	public Map<String, Object> getArticleSuggest(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(3, page));

		return JsonHelper.getModelMap(newList);
	}

	// 通过文章id，读取文章信息和评论
	@RequestMapping("/getDetailByIdView")
	public String getDetailByIdView(Model model, @RequestParam(value = "id", required = true) String id) {
		// 读取文件详细内容
		ArticleIndexResponse articleIndexResponse = blogService.getDetailByIdView(id);
		model.addAttribute("artdto", articleIndexResponse);

		// 读取该文章的评论
		List<CommentRequest> comList = commentService.getCommentRequestById(id);
		model.addAttribute("comList", comList);

		// 当前选择的博主用户信息
		SysUser selectedUser = userService.getUserById(articleIndexResponse.getCreator());
		model.addAttribute("selectedUser", selectedUser);

		// 该篇文章阅读数加1
		blogService.addReadCount(id);

		return "blog/article/articleView";// 跳转到该用户文章浏览页
	}

	// 读取该用户的所有博客
	@RequestMapping("/getArticleByCreateBy")
	public String getArticleByCreateBy(Model model, @RequestParam(value = "userId", required = true) String userId) {
		// 当前用户博客信息
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByCreator(userId));
		model.addAttribute("dto", newList);

		// 当前选择的博主用户信息
		SysUser selectedUser = userService.getUserById(userId);
		model.addAttribute("selectedUser", selectedUser);

		return "blog/article/articleViewlistuser";// 跳转到该用户页面，显示该用户所有文章
	}

	// 获取分类及每个分类下的文章数量
	@RequestMapping("/getCategory")
	@ResponseBody
	public Map<String, Object> getCategory() {
		StringBuffer strBCategory = new StringBuffer();
		strBCategory.append("<ul>");

		List<TypeCountResponse> lstTypeCount = blogTypeService.getTypeCount();
		for (TypeCountResponse typeCountResponse : lstTypeCount) {
			strBCategory.append("<li><a onClick='addTypeMenu(\"");
			strBCategory.append(typeCountResponse.getTypeName());
			strBCategory.append("\",\"");
			strBCategory.append(typeCountResponse.getTypeId());
			strBCategory.append("\")");
			strBCategory.append("' href=\"#\" >");
			strBCategory.append(typeCountResponse.getTypeName());
			strBCategory.append("(");
			strBCategory.append(typeCountResponse.getTypeCount());
			strBCategory.append(")");
			strBCategory.append("</a></li>");
		}

		strBCategory.append("</ul>");

		return JsonHelper.getModel(strBCategory.toString());
	}

	@RequestMapping("/getCategoryByUser")
	@ResponseBody
	public Map<String, Object> getCategoryByUser(String userId) {
		StringBuffer strBCategory = new StringBuffer();
		strBCategory.append("<ul>");

		List<TypeCountResponse> lstTypeCount = blogTypeService.getTypeCount(userId);
		for (TypeCountResponse typeCountResponse : lstTypeCount) {
			strBCategory.append("<li><a onClick='addTypeMenu(\"");
			strBCategory.append(typeCountResponse.getTypeName());
			strBCategory.append("\",\"");
			strBCategory.append(typeCountResponse.getTypeId());
			strBCategory.append("\")");
			strBCategory.append("' href=\"#\" >");
			strBCategory.append(typeCountResponse.getTypeName());
			strBCategory.append("(");
			strBCategory.append(typeCountResponse.getTypeCount());
			strBCategory.append(")");
			strBCategory.append("</a></li>");
		}

		strBCategory.append("</ul>");

		return JsonHelper.getModel(strBCategory.toString());
	}

}
