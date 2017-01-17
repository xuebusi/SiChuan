package io.transwarp.scrcu.system.nav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;

import io.transwarp.scrcu.base.util.BaseModel;
import io.transwarp.scrcu.system.resource.SysRes;
import io.transwarp.scrcu.system.role.SysRole;

@SuppressWarnings("serial")
public class SysNav extends BaseModel<SysNav> {
	public static final SysNav dao = new SysNav();

	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<SysNav> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from sys_nav order by id asc");
	}

	private SysRes res = null;

	private List<SysNav> childNav;

	public List<SysNav> getChildNav() {
		return childNav;
	}

	public void setChildNav(List<SysNav> childNav) {
		this.childNav = childNav;
	}

	public List<SysNav> findByRoleId(int roleId) {
		return find(
				"select sn.*,sr.ak from sys_nav sn left join sys_role_res srr on sn.res_id=srr.res_id left join sys_res sr on sr.id=sn.res_id where sn.res_id=0 or srr.role_id=? order by sn.id asc",
				roleId);
	}

	public List<SysNav> findNav() {
		return find(
				"select sn.*,sr.ak from sys_nav sn left join sys_role_res srr on sn.res_id=srr.res_id left join sys_res sr on sr.id=sn.res_id where sn.pid=0 order by sn.id asc");
	}

	public Map<String, Integer> nav() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<SysNav> list = findNav();
		for (SysNav nav : list) {
			result.put(nav.getStr("ak"), nav.getInt("id"));
		}
		return result;
	}

	/**
	 * 得到树形权限
	 * 
	 * @return
	 */
	public Map<Integer, Map<Integer, SysNav>> tree() {
		List<SysRole> roleList = SysRole.dao.findAll();
		// 角色、类型、list
		Map<Integer, Map<Integer, SysNav>> result = new HashMap<Integer, Map<Integer, SysNav>>();

		for (SysRole role : roleList) {
			List<SysNav> list = findByRoleId(role.getInt("id"));
			Map<Integer, List<SysNav>> map = new HashMap<Integer, List<SysNav>>();
			List<SysNav> navList = new ArrayList<SysNav>();
			Map<Integer, SysNav> m = new LinkedHashMap<Integer, SysNav>();
			for (SysNav p : list) {
				int parentId = p.getInt("pid");
				List<SysNav> l = map.get(parentId);
				if (l == null) {
					l = new ArrayList<SysNav>();
					map.put(parentId, l);
				}
				l.add(p);
				if (parentId == 0) {
					navList.add(p);
					m.put(p.getInt("id"), p);
				}
			}
			for (SysNav p : navList) {
				tree(p, map);
			}
			result.put(role.getInt("id"), m);
		}
		return result;
	}

	/**
	 * 递归构建tree
	 * 
	 * @param p
	 * @param map
	 */
	public void tree(SysNav p, Map<Integer, List<SysNav>> map) {
		int id = p.getInt("id");
		List<SysNav> list = map.get(id);
		if (list != null) {
			for (SysNav subPremissions : list) {
				tree(subPremissions, map);
			}
			p.setChildNav(list);
		}
	}

	public SysRes getRes() {
		if (this.res == null) {
			Integer res_id = getInt("res_id");
			SysRes res = SysRes.dao.findById(res_id);
			if (res == null)
				this.res = new SysRes();
			else
				this.res = res;
		}
		return this.res;
	}

	public boolean hasChild() {
		return exists("pid=?", (Object) get("id"));
	}

	public Map<String, Object> toNodeData() {
		Map<String, Object> node = new HashMap<String, Object>();
		node.put("id", get("id").toString());
		node.put("isParent", hasChild());
		node.put("cname", get("title").toString());
		String name = "<i class='" + getStr("icon") + "'></i>&nbsp;<font color=\"blue\">" + get("title").toString() + "</font>";
		if (get("ak") != null)
			name += " [" + (get("ak") == null ? "" : get("ak")) + "] ";
		node.put("name", name);
		node.put("pid", get("pid").toString());
		// node.put("iconSkin", getStr("icon"));
		return node;
	}
}