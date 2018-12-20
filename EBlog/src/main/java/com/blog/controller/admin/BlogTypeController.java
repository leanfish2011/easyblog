package com.blog.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.blog.utils.SessionHelper;
import com.blog.po.BllArticletype;
import com.blog.service.BlogTypeService;
import com.blog.utils.JsonHelper;
import com.blog.vo.ArticleTypeAddRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 博客类别管理
 * @author tim
 * @date 2018年1月13日-下午6:19:38
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(BlogTypeController.class);

	@Autowired
	private BlogTypeService blogTypeService;

	/**
	 * 进入首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/admin/blog/blogtype";
	}

	/**
	 * 进入新增页
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "admin/blog/blogTypeEdit";
	}

	/**
	 * 获取用户下的博客分类
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getBlogTypeByUser")
	@ResponseBody
	public void getBlogTypeByUser(HttpServletResponse response) throws IOException {
		List<BllArticletype> typeList = blogTypeService.getTypeListByUser(SessionHelper.getCurrentUserId(request));

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();

		strOut.append("[");

		for (BllArticletype blogtype : typeList) {
			strOut.append("{");
			strOut.append("\"id\":\"" + blogtype.getId() + "\",");
			strOut.append("\"text\":\"" + blogtype.getTypeName().toString() + "\"");
			strOut.append("},");
		}

		String strJsonString = strOut.substring(0, strOut.length() - 1);
		strJsonString += "]";

		out.println(strJsonString);
		out.close();
	}

	@RequestMapping("/getBlogTypeListByUser")
	@ResponseBody
	public Map<String, Object> getBlogTypeListByUser() {
		// 获取当前登录的用户
		List<BllArticletype> typeList = blogTypeService.getTypeListByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(typeList);
	}

	@RequestMapping("/addBlogType")
	@ResponseBody
	public Map<String, String> addBlogType(ArticleTypeAddRequest articleTypeAdd) {
		BllArticletype articletype = new BllArticletype();

		articletype.setId(UUID.randomUUID().toString());
		articletype.setTypeName(articleTypeAdd.getTypeName());
		articletype.setDescription(articleTypeAdd.getDescription());
		articletype.setCreator(SessionHelper.getCurrentUserId(request));

		blogTypeService.addBlogType(articletype);

		return JsonHelper.getSucessResult(true, "新增成功！");
	}

	@RequestMapping("/editBlogType")
	public String editBlogType(Model model, @RequestParam(value = "blogTypeId", required = true) String blogTypeId) {
		BllArticletype articletype = blogTypeService.getBlogTypeById(blogTypeId);
		model.addAttribute("blogTypeDTO", articletype);

		return "admin/blog/blogTypeEdit";
	}

	@RequestMapping("/updateBlogType")
	@ResponseBody
	public Map<String, String> updateBlogType(String id, String description) {
		BllArticletype articletype = blogTypeService.getBlogTypeById(id);

		articletype.setDescription(description);
		articletype.setModifier(SessionHelper.getCurrentUserId(request));

		blogTypeService.updateBlogType(articletype);

		return JsonHelper.getSucessResult(true, "修改成功！");
	}

	@RequestMapping("/deleteBlogType")
	@ResponseBody
	public Map<String, String> deleteBlogType(String blogTypeIds) {
		String[] blogTypeIdArray = blogTypeIds.split(",");
		for (int i = 0; i < blogTypeIdArray.length; i++) {
			if (blogTypeService.getBlogCountByType(blogTypeIdArray[i]) > 0) {
				BllArticletype articletype = blogTypeService.getBlogTypeById(blogTypeIdArray[i]);
				return JsonHelper.getSucessResult(false, articletype.getTypeName() + "下存在博客，不能删除！");
			}
		}

		boolean result = blogTypeService.deleteBlogType(blogTypeIds);
		logger.info("删除文章类别。" + blogTypeIds);

		return JsonHelper.getSucessResult(result, "删除成功！");
	}

}
