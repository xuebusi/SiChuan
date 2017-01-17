package io.transwarp.scrcu.system.resource;

import java.util.HashMap;
import java.util.Map;

import io.transwarp.scrcu.base.util.BaseModel;

@SuppressWarnings("serial")
public class SysRes extends BaseModel<SysRes> {
	public static final SysRes dao = new SysRes();

	public boolean hasChild() {
		Object id = get("id");
		return exists("pid=?", (Object) get("id"));
	}

	public boolean hasParent() {
		return exists("id=?", (Object) get("pid"));
	}

	public Map<String, Object> toNodeData() {
		Map<String, Object> node = new HashMap<String, Object>();
		node.put("id", get("id").toString());
		node.put("isParent", hasChild());
		node.put("cname", get("cname").toString());
		String name = "<font color=\"blue\">" + get("cname").toString() + "</font>";
		if (get("ak") != null)
			name += " [" + (get("ak") == null ? "" : get("ak")) + "] ";
		node.put("name", name);
		node.put("pid", get("pid").toString());
		return node;
	}
}
