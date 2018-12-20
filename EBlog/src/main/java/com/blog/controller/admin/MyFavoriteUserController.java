package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllFavuser;
import com.blog.service.MyFavoriteUserService;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/admin/favoriteUser")
public class MyFavoriteUserController extends BaseController {

	@Autowired
	private MyFavoriteUserService myFavoriteUserService;

	@RequestMapping("/getMyFavoriteUser")
	@ResponseBody
	public Map<String, Object> getMyFavoriteUser() {
		List<BllFavuser> list = myFavoriteUserService.getMyFavoriteUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addMyFavoriteUser")
	@ResponseBody
	public Map<String, String> addMyFavoriteUser(String favUser, String favUserDescrible) {
		BllFavuser myFavoriteUser = new BllFavuser();

		myFavoriteUser.setId(UUID.randomUUID().toString());
		myFavoriteUser.setCreator(SessionHelper.getCurrentUserId(request));
		myFavoriteUser.setFavUser(favUser);
		myFavoriteUser.setDescrible(favUserDescrible);

		myFavoriteUserService.addMyFavoriteUser(myFavoriteUser);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/updateMyFavoriteUser")
	@ResponseBody
	public Map<String, String> updateMyFavoriteUser(String favuserId, String favUser, String favUserDescrible) {
		BllFavuser myFavoriteUser = myFavoriteUserService.getMyFavuserById(favuserId);

		myFavoriteUser.setFavUser(favUser);
		myFavoriteUser.setDescrible(favUserDescrible);
		myFavoriteUser.setModifier(SessionHelper.getCurrentUserId(request));

		myFavoriteUserService.updateMyFavoriteUser(myFavoriteUser);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/deleteMyFavoriteUser")
	@ResponseBody
	public Map<String, String> deleteMyFavoriteUser(String favUserIds) {
		boolean result = myFavoriteUserService.deleteMyFavoriteUser(favUserIds);
		return JsonHelper.getSucessResult(result);
	}
}
