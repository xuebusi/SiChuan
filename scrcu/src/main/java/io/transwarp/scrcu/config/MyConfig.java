package io.transwarp.scrcu.config;

import java.util.Map;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.Configuration;
import io.transwarp.scrcu.app.*;
import io.transwarp.scrcu.authority.shiro.ResourceInterceptor;
import io.transwarp.scrcu.authority.shiro.ShiroInterceptor;
import io.transwarp.scrcu.authority.shiro.ShiroPlugin;
import io.transwarp.scrcu.authority.tags.ShiroTags;
import io.transwarp.scrcu.base.controller.IndexController;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.interceptor.CommonInterceptor;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.custom.Custom;
import io.transwarp.scrcu.custom.CustomController;
import io.transwarp.scrcu.portal.EventAnalysisController;
import io.transwarp.scrcu.portal.SiteAnalysisController;
import io.transwarp.scrcu.portal.UserAnalysisController;
import io.transwarp.scrcu.portal.VisitSourceController;
import io.transwarp.scrcu.portrait.PortraitController;
import io.transwarp.scrcu.sqlinxml.SqlInXmlPlugin;
import io.transwarp.scrcu.system.nav.NavController;
import io.transwarp.scrcu.system.nav.SysNav;
import io.transwarp.scrcu.system.resource.ResourceController;
import io.transwarp.scrcu.system.resource.SysRes;
import io.transwarp.scrcu.system.role.RoleController;
import io.transwarp.scrcu.system.role.SysRole;
import io.transwarp.scrcu.system.role.SysRoleRes;
import io.transwarp.scrcu.system.users.UserRoles;
import io.transwarp.scrcu.system.users.Users;
import io.transwarp.scrcu.system.users.UsersController;

public class MyConfig extends JFinalConfig {

	private Routes routes;

	public static final String configFile = "scrcu.properties";

	@Override
	public void configConstant(Constants me) {

		me.setDevMode(true);
		loadPropertyFile(configFile);
		Configuration cfg = FreeMarkerRender.getConfiguration();
		cfg.setDefaultEncoding("utf-8");
		cfg.setSharedVariable("shiro", new ShiroTags());
		
		me.setError404View("/common/404.html");

		InceptorUtil.devMode = PropKit.getBoolean("inceptor.devMode");
		InceptorUtil.encache = PropKit.getBoolean("inceptor.encache");

	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		Map<Integer, Map<Integer, SysNav>> navTree = SysNav.dao.tree();
		BaseUtils.navTree = navTree;

		Map<String, Integer> nav = SysNav.dao.nav();
		BaseUtils.nav = nav;

	}

	@Override
	public void configRoute(Routes me) {

		this.routes = me;
		me.add("/", IndexController.class, "index");
		me.add("/portrait", PortraitController.class);
		me.add("/system/users", UsersController.class, "system/users");
		me.add("/system/role", RoleController.class, "system/role");
		me.add("/system/Resource", ResourceController.class, "system/Resource");
		me.add("/system/nav", NavController.class, "system/nav");
		// 门户
		me.add("/portal/userAnalysis", UserAnalysisController.class, "portal/userAnalysis");
		me.add("/portal/visitSource", VisitSourceController.class, "portal/visitSource");
		me.add("/portal/siteAnalysis", SiteAnalysisController.class, "portal/siteAnalysis");
		me.add("/portal/eventAnalysis", EventAnalysisController.class, "portal/eventAnalysis");

		// 移动
		me.add("/app/userAnalysis", AppUserController.class, "app/userAnalysis");
		me.add("/app/behavior", AppBehaviorController.class, "app/behavior");
		me.add("/app/appAnalysis", AppController.class, "app/appAnalysis");
		me.add("/app/channel", AppChannelController.class, "app/channel");
		me.add("/app/memberAnalysis", AppMemberController.class, "app/memberAnalysis");
		me.add("/app/event", AppEventController.class, "app/event");

		me.add("/custom", CustomController.class, "custom");

	}

	@Override
	public void configPlugin(Plugins me) {

		//获取数据库的配置
		String jdbcUrl = getProperty("jdbcUrl");
		String driver = getProperty("driverClass");
		String username = getProperty("username");
		String password = getProperty("password");

		// Druid
		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password, driver);
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType(getProperty("dbType"));
		druidPlugin.addFilter(wallFilter);
//		druidPlugin.setValidationQuery("select 1 from dual");
		me.add(druidPlugin);

		// ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin("druid", druidPlugin);
//		arp.setDialect(new OracleDialect());
		arp.setDialect(new MysqlDialect());
		arp.addMapping("sys_users", Users.class);
		arp.addMapping("sys_user_roles", UserRoles.class);
		arp.addMapping("custom", Custom.class);
		arp.addMapping("sys_res", SysRes.class);
		arp.addMapping("sys_role", SysRole.class);
		arp.addMapping("sys_nav", SysNav.class);
		arp.addMapping("sys_role_res", SysRoleRes.class);
		me.add(arp);

		// 加载Shiro插件
		me.add(new ShiroPlugin(routes));

		// 缓存插件
		me.add(new EhCachePlugin());

		// 配置文件插件
		me.add(new SqlInXmlPlugin());

		// 添加缓存插件
		me.add(new EhCachePlugin());

		// 初始化hive数据源
//		InceptorUtil.initDataSource();

	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new ShiroInterceptor());
		me.add(new CommonInterceptor());
		me.add(new ResourceInterceptor());

	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler());
	}

	static Logger logger = Logger.getLogger(InceptorUtil.class);

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);
	}

}
