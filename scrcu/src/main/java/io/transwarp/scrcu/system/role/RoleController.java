package io.transwarp.scrcu.system.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.kit.JsonKit;

import io.transwarp.scrcu.base.controller.BaseController;

@RequiresAuthentication
public class RoleController extends BaseController {
	public void index() {
		setAttr("page", SysRole.dao.paginate(getParaToInt(0, 1), 10));
	}

	public void create() {
		SysRole sysRole = SysRole.dao.findFirst("select max(id)+1 as id from sys_role").set("cname","");
		if (isPost()) {
			if (getModel(SysRole.class, "sysgroup").save())
				redirect(getControllerKey());
			return;
		}
		setAttr("data", sysRole);
		render("form.html");
	}

	public void update() {
		if (isPost()) {
			if (getModel(SysRole.class, "sysgroup").set("id", getParaToInt(0)).update())
				redirect(getControllerKey());
			return;
		}
		SysRole model = SysRole.dao.findById(getParaToInt(0));
		setAttr("data", model);
		setAttr("res", JsonKit.toJson(model.getResidList()));
		render("form.html");
	}

	public void delete() {
		if (SysRole.dao.findById(getParaToInt(0)).delete())
			redirect(getControllerKey());
		else
			alertAndGoback("删除失败");
	}

	public void deleteAll() {
		Integer[] ids = getParaValuesToInt("id");
		for (Integer id : ids) {
			SysRole.dao.findById(id).delete();
		}
		redirect(getControllerKey());
	}

	public void set_res() {
		int role_id = getParaToInt(0,0);
		int res_id = getParaToInt(1,0);
		boolean flg = getParaToBoolean("checked");
		if (flg) {
			SysRoleRes rr = new SysRoleRes().set("role_id", role_id).set("res_id", res_id);
			if (rr.findByModel().size() == 0)
				rr.save();
		} else {
			// 删除
			SysRoleRes rr = new SysRoleRes().set("role_id", role_id).set("res_id", res_id).findFirstByModel();
			if (rr != null)
				rr.delete();
		}
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("success", true);
		renderJavascript(JsonKit.toJson(r));
		return;

	}

	public void set_res_all() {
		int role_id = getParaToInt(0);
		boolean flg = getParaToBoolean("checked");
		SysRole.dao.checkAll(role_id, flg);

		SysRole model = SysRole.dao.findById(role_id);
		renderJavascript(JsonKit.toJson(model.getResidList()));
	}
}
