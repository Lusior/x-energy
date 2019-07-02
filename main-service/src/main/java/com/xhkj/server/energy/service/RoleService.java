package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;

public interface RoleService {
    Role getById(String roleId);

    Boolean saveRoleAndItem(Role role, String[] items);

    Boolean deleteRole(String roleId);

    Boolean modifyRoleAndItem(Role role, String[] items);

    List<Role> getRoleByOprId(String oprId);

    List<Role> getAll(BootstrapTableParams params);
}
