package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.BllSuggest;
import com.blog.service.SuggestService;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:46:41
 * @description：推荐控制器
 */
@Controller
@RequestMapping("/admin/suggest")
public class SuggestController extends BaseController {

	@Autowired
	private SuggestService suggestService;

	@RequestMapping("/index")
	public String mysuggests() {
		return "/admin/favorite/mysuggests";
	}

	@RequestMapping("/getSuggestListByUser")
	@ResponseBody
	public Map<String, Object> getSuggestListByUser() {
		List<BllSuggest> list = suggestService.getSuggestListByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteSuggest")
	@ResponseBody
	public Map<String, String> deleteSuggest(String suggestIds) {
		boolean result = suggestService.deleteSuggest(suggestIds);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addSuggest")
	@ResponseBody
	public Map<String, String> addSuggest(String articleId, String articleTitle) {
		if (suggestService.isExistSuggest(articleId, SessionHelper.getCurrentUserId(request))) {
			return JsonHelper.getSucessResult(false, "你已经推荐了该文章，不能重复推荐！");
		}

		BllSuggest suggest = new BllSuggest();

		suggest.setId(UUID.randomUUID().toString());
		suggest.setCreator(SessionHelper.getCurrentUserId(request));
		suggest.setArticleId(articleId);
		suggest.setArticleTitle(articleTitle);

		boolean result = suggestService.addSuggest(suggest);

		return JsonHelper.getSucessResult(result);
	}
}
