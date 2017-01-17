package io.transwarp.scrcu.system.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.JFinal;
import com.jfinal.kit.JsonKit;

import io.transwarp.scrcu.base.controller.BaseController;

@RequiresAuthentication
public class ResourceController extends BaseController {

//	@RequiresPermissions("/system/resource")
	public void index() {
		render("index.html");
	}

//	@RequiresPermissions("/system/resource/create")
	public void create() {
		Integer pid = getParaToInt(0, 0);
		if (isPost()) {
			SysRes model = getModel(SysRes.class, "sysres");
			if (model.save()) {
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("success", true);
				r.put("data", model.toNodeData());
				renderJavascript(JsonKit.toJson(r));
				return;
			}
		}
		setAttr("aks", JFinal.me().getAllActionKeys());
		setAttr("data", new SysRes().set("pid", pid));
		render("form.html");
	}
	
//	@RequiresPermissions("/system/resource/update")
	public void update() {
		if (isPost()) {
			Integer id = getParaToInt("sysres.id");
			SysRes model = getModel(SysRes.class, "sysres").set("id", id);
			if (model.update()) {
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("success", true);
				r.put("data", model.toNodeData());
				renderJavascript(JsonKit.toJson(r));
				return;
			}
		} else {
			Integer id = getParaToInt(0);
			setAttr("aks", JFinal.me().getAllActionKeys());
			setAttr("data", SysRes.dao.findById(id));
			render("form.html");
		}
	}
//	@RequiresPermissions("/system/resource/delete")
	public void delete() {
		int id = getParaToInt();
		if (SysRes.dao.findById(id).delete()) {
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("success", true);
			r.put("data", new HashMap<String, Object>().put("id", id));
			renderJavascript(JsonKit.toJson(r));
			return;
		} else {
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("success", false);
			r.put("msg", "删除失败");
			renderJavascript(JsonKit.toJson(r));
			return;
		}
	}
//	@RequiresPermissions("/system/resource/getlist")
	public void getlist() {
		Integer pid = getParaToInt("id", 0);
		List<SysRes> models = SysRes.dao.where("pid=? order by seq", pid);
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		Iterator<SysRes> it = models.iterator();
		while (it.hasNext()) {
			SysRes model = it.next();
			nodes.add(model.toNodeData());
		}
		renderJson(nodes);
	}
}
