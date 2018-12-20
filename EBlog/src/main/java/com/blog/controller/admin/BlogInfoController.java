package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.BllArticle;
import com.blog.service.BlogService;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;
import com.blog.vo.ArticleAddRequest;
import com.blog.vo.ArticleSearchParams;
import com.blog.vo.ArticleUpdateRequest;

@Controller
@RequestMapping("/admin/blogInfo")
public class BlogInfoController extends BaseController {

	private static Logger logger = Logger.getLogger(BlogInfoController.class);

	// spring注解，表示需要自动装配，根据在spingmvc-confog.xml中配置的包，根据类型找对应的bean，bean需要用@注解
	@Autowired
	private BlogService blogService;

	@RequestMapping("/index")
	public String article() {
		return "/admin/blog/blog";
	}

	@RequestMapping("/add")
	public String articleInfo() {
		return "/admin/blog/bloginfo";
	}

	@RequestMapping("/searchBlog")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public Map<String, Object> searchArticle(ArticleSearchParams articleSearchParams) {
		articleSearchParams.setUserId(SessionHelper.getCurrentUserId(request));
		List<BllArticle> blogs = blogService.searchArticle(articleSearchParams);

		return JsonHelper.getModelMapforGrid(blogs);
	}

	// 通过文章id，读取文章信息
	@RequestMapping("/getDetailById")
	public String getDetailById(Model model, @RequestParam(value = "blogid", required = true) String blogid) {
		// 读取文章详细内容
		BllArticle article = blogService.getArticleById(blogid);
		model.addAttribute("articleDTO", article);

		return "admin/blog/bloginfo";
	}

	@RequestMapping("/saveBlog")
	@ResponseBody
	public Map<String, String> saveBlog(ArticleAddRequest articleAdd) {
		BllArticle article = new BllArticle();

		article.setId(UUID.randomUUID().toString());
		article.setTitle(articleAdd.getTitle());
		article.setTypeId(articleAdd.getBlogTypeId());
		article.setTypeName(articleAdd.getBlogTypeName());
		article.setContent(articleAdd.getContent());
		article.setCreator(SessionHelper.getCurrentUserId(request));

		boolean result = blogService.addArticle(article);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/updateBlog")
	@ResponseBody
	public Map<String, String> updateBlog(ArticleUpdateRequest articleUpdate) {
		BllArticle article = blogService.getArticleById(articleUpdate.getBlogid());

		article.setTitle(articleUpdate.getTitle());
		article.setTypeId(articleUpdate.getBlogTypeId());
		article.setTypeName(articleUpdate.getBlogTypeName());
		article.setContent(articleUpdate.getContent());
		article.setModifier(SessionHelper.getCurrentUserId(request));

		boolean result = blogService.updateArticle(article);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/deleteBlog")
	@ResponseBody
	public Map<String, String> deleteBlog(String blogid) {
		boolean result = blogService.deleteArticleById(blogid);

		logger.info("删除文章。" + blogid);

		return JsonHelper.getSucessResult(result);
	}

}
