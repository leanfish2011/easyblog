package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllFavarticle;
import com.blog.service.MyFavoriteArticleService;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/admin/favoriteArticle")
public class MyFavoriteArticleController extends BaseController {

	@Autowired
	private MyFavoriteArticleService myFavoriteArticleService;

	@RequestMapping("/index")
	public String myfavorite() {
		return "/admin/favorite/myfavorite";
	}

	@RequestMapping("/getMyFavoriteArticle")
	@ResponseBody
	public Map<String, Object> getMyFavoriteArticle() {
		List<BllFavarticle> list = myFavoriteArticleService
				.getMyFavoriteArticle(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> addMyFavoriteArticle(String articleTitle, String articleUrl, String describle) {
		BllFavarticle favArticle = new BllFavarticle();

		favArticle.setId(UUID.randomUUID().toString());
		favArticle.setCreator(SessionHelper.getCurrentUserId(request));
		favArticle.setArticleTitle(articleTitle);
		favArticle.setArticleUrl(articleUrl);
		favArticle.setDescrible(describle);

		myFavoriteArticleService.addMyFavoriteArticle(favArticle);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/updateMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> updateMyFavoriteArticle(String id, String articleTitle, String articleUrl,
			String describle) {
		BllFavarticle favArticle = myFavoriteArticleService.getMyFavoriteArticleById(id);

		favArticle.setArticleTitle(articleTitle);
		favArticle.setArticleUrl(articleUrl);
		favArticle.setDescrible(describle);
		favArticle.setModifier(SessionHelper.getCurrentUserId(request));

		myFavoriteArticleService.updateMyFavoriteArticle(favArticle);

		return JsonHelper.getSucessResult(true, "修改成功！");
	}

	@RequestMapping("/deleteMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> deleteMyFavoriteArticle(String favArticleIdIds) {
		boolean result = myFavoriteArticleService.deleteMyFavoriteArticle(favArticleIdIds);
		return JsonHelper.getSucessResult(result);
	}
}
