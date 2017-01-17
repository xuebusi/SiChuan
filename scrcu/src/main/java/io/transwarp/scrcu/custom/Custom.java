package io.transwarp.scrcu.custom;

import com.jfinal.plugin.activerecord.Page;

import io.transwarp.scrcu.base.util.BaseModel;

public class Custom extends BaseModel<Custom> {

	private static final long serialVersionUID = 2920278340134539131L;

	public static final Custom dao = new Custom();

	public Page<Custom> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from custom order by id asc");
	}

}
