package io.transwarp.scrcu.base.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;

public class CommonInterceptor implements Interceptor {

	public void intercept(Invocation ai) {
//		System.out.println(ai.getController().getRequest().getSession().getId());
		Controller ctrl = ai.getController();
		String cp = JFinal.me().getContextPath();
		ctrl.setAttr("root", ("".equals(cp) || "/".equals(cp)) ? "" : cp);
		ctrl.setAttr("action", ai.getActionKey());
		ctrl.setAttr("ControllerKey", ai.getControllerKey());
		ctrl.setAttr("ActionKey", ai.getActionKey());
		ai.getController().keepPara("start_dt");
		ai.getController().keepPara("end_dt");
		ai.invoke();

	}
}
