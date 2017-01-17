package io.transwarp.scrcu.custom;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import io.transwarp.scrcu.base.controller.BaseController;
@RequiresAuthentication
public class CustomController extends BaseController {

	@RequiresPermissions("/custom")
	public void index() {
		setNav();
		setAttr("page", Custom.dao.paginate(getParaToInt(0, 1), 10));
	}

	@RequiresPermissions("/custom/save")
	public void add() {
	}

	@RequiresPermissions("/custom/update")
	public void edit() {
		setAttr("custom", Custom.dao.findById(getParaToInt()));
	}

	@RequiresPermissions("/custom/view")
	public void view() {
		Custom custom = Custom.dao.findById(getParaToInt());
		setAttr("custom", custom);
	}
	@RequiresPermissions("/custom/save")
	public void save() {
		Custom custom = getModel(Custom.class);
		custom.save();
		redirect("/custom");
	}
	@RequiresPermissions("/custom/update")
	public void update() {
		Custom custom = getModel(Custom.class);
		custom.update();
		redirect("/custom");
	}
	@RequiresPermissions("/custom/delete")
	public void delete() {
		Custom custom = Custom.dao.findById(getParaToInt());
		custom.delete();
		redirect("/custom");
	}

}
