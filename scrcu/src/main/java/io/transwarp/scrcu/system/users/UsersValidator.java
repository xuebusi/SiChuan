package io.transwarp.scrcu.system.users;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UsersValidator extends Validator {

	protected void validate(Controller controller) {
		validateRequiredString("users.username", "usernameMsg", "请输入用户名");
		validateRequiredString("users.password", "passwordMsg", "请输入用户密码!");
	}

	protected void handleError(Controller controller) {
		controller.keepModel(Users.class);
		String actionKey = getActionKey();
		if (actionKey.equals("/system/users/save"))
			controller.render("add.html");
		else if (actionKey.equals("/system/users/update"))
			controller.render("edit.html");
	}
}
