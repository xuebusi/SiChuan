package io.transwarp.scrcu.system.users;

import com.jfinal.plugin.activerecord.Page;

import io.transwarp.scrcu.base.util.BaseModel;

public class Users extends BaseModel<Users> {

    private static final long serialVersionUID = 2920278340134539131L;

    public static final Users dao = new Users();

    /**
     * 根据pageNumber,pageSize查找用户信息
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     * @return 用户分页信息
     */
    public Page<Users> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "select u.*", "from sys_users u order by u.id asc");
    }

    /**
     * 根据userId查找用户信息
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    public Users findByUserIdWithRoleId(int userId) {
        return findFirst("select u.*,ur.role_id from sys_users u left join sys_user_roles ur on u.id = ur.user_id where u.id = " + userId);
    }

    /**
     * 根据用户名查找用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    public Users findByUserName(String name) {
        return findFirst("select u.* from sys_users u where u.username = '" + name + "'");
    }
}
