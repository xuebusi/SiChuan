package io.transwarp.scrcu.system.nav;

import java.util.List;

public class NavService {
	public static List<SysNav> getTreeMap(int pid) {
		List<SysNav> rows = new SysNav().set("pid", pid).findByModel();
		if (!rows.isEmpty()) {
			for (int i = 0; i < rows.size(); i++) {
				List<SysNav> sub = getChildren(new Integer(rows.get(i).get("id").toString()));
				if (!sub.isEmpty()) {
					rows.get(i).put("hasChild", true);
					rows.get(i).put("children", sub);
				}
				;
			}
		}
		return rows;
	}

	public static List<SysNav> getChildren(int pid) {
		List<SysNav> rows = new SysNav().set("pid", pid).findByModel("orderid asc");
		if (!rows.isEmpty()) {
			for (int i = 0; i < rows.size(); i++) {
				List<SysNav> sub = getChildren(new Integer(rows.get(i).get("id").toString()));
				if (!sub.isEmpty()) {
					rows.get(i).put("hasChild", true);
					rows.get(i).put("children", sub);
				}
			}
		}
		return rows;
	}
}
