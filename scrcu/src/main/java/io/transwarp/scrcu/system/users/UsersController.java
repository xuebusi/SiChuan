package io.transwarp.scrcu.system.users;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import io.transwarp.scrcu.base.interceptor.CommonInterceptor;
import io.transwarp.scrcu.system.role.SysRole;

@Before(CommonInterceptor.class)
@RequiresAuthentication
public class UsersController extends Controller {
	@RequiresPermissions("/system/users")
	public void index() {
		setAttr("message", "hello word");
		setAttr("userPage", Users.dao.paginate(getParaToInt(0, 1), 10));
	}

	@RequiresPermissions("/system/users/add")
	public void add() {
		List<SysRole> roleList = SysRole.dao.list();
		setAttr("roleList", roleList);
		Map<String, Integer> levels = new HashMap<>();
		levels.put("省", 10);
		levels.put("省联社", 11);
		levels.put("省联社部门", 12);
		levels.put("省联社二级部门", 13);
		levels.put("市", 20);
		levels.put("市办事处(市级联社)", 21);
		levels.put("市办事处(市级联社)部门", 22);
		levels.put("市办事处二级部门", 23);
		levels.put("县", 30);
		levels.put("县联社", 31);
		levels.put("县联社部门", 32);
		levels.put("县联社二级部门", 33);
		levels.put("中心社", 41);
		levels.put("营业网点 ", 51);
		levels.put("营业网点部门 ", 52);
		setAttr("levels", levels);
	}

	@RequiresPermissions("/system/users/update")
	public void edit() {
		setAttr("users", Users.dao.findByUserIdWithRoleId(getParaToInt()));
		List<SysRole> roleList = SysRole.dao.list();
		setAttr("roleList", roleList);
		Map<String, Integer> levels = new HashMap<>();
		levels.put("省", 10);
		levels.put("省联社", 11);
		levels.put("省联社部门", 12);
		levels.put("省联社二级部门", 13);
		levels.put("市", 20);
		levels.put("市办事处(市级联社)", 21);
		levels.put("市办事处(市级联社)部门", 22);
		levels.put("市办事处二级部门", 23);
		levels.put("县", 30);
		levels.put("县联社", 31);
		levels.put("县联社部门", 32);
		levels.put("县联社二级部门", 33);
		levels.put("中心社", 41);
		levels.put("营业网点 ", 51);
		levels.put("营业网点部门 ", 52);
		setAttr("levels", levels);

	}

	@Before(UsersValidator.class)
	@RequiresPermissions("/system/users/save")
	public void save() {
		Users users = getModel(Users.class);
		users.save();
		UserRoles.dao.deleteByUserId(users.getInt("id"));
		int roleid = getParaToInt("role_id");
		UserRoles ur = new UserRoles();
		ur.set("user_id", users.get("id"));
		ur.set("role_id", roleid);
		ur.set("create_time", new Date());
		ur.save();
		redirect("/system/users");
	}

	@Before(UsersValidator.class)
	@RequiresPermissions("/system/users/update")
	public void update() {
		Users users = getModel(Users.class);
		UserRoles.dao.deleteByUserId(users.getInt("id"));
		int roleid = getParaToInt("role_id");
		UserRoles ur = new UserRoles();
		ur.set("user_id", users.get("id"));
		ur.set("role_id", roleid);
		ur.set("create_time", new Date());
		ur.save();
		users.update();
		redirect("/system/users");
	}

	@RequiresPermissions("/system/users/delete")
	public void delete() {
		Users users = Users.dao.findById(getParaToInt());
		users.delete();
		redirect("/system/users");
	}

	@RequiresPermissions("/system/users/online")
	public void online() {

		redirect("/system/users");
	}

}
