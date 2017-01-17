package io.transwarp.scrcu.system.users;

import java.util.Map;

import com.jfinal.plugin.activerecord.Db;

import io.transwarp.scrcu.base.util.BaseModel;

public class UserRoles extends BaseModel<UserRoles> {

    private static final long serialVersionUID = 2920278340134539131L;

    public static Map<Integer, String> status;

    public static final UserRoles dao = new UserRoles();

    /**
     * 根据userId删除用户权限
     *
     * @param userId 用户userId
     */
    public void deleteByUserId(int userId) {

        Db.update("delete from sys_user_roles where user_id = ?", userId);

    }

    /**
     * 根据username查找用户权限
     *
     * @param username 用户名
     * @return UserRoles
     */
    public UserRoles findByUserId(String username) {
        return this.findFirst(
                "select sur.* from sys_user_roles sur join sys_users su on sur.user_id =su.id where su.username = ?",
                username);
    }

}
