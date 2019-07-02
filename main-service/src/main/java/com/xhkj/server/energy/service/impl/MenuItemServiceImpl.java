package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.MenuItemDao;
import com.xhkj.server.energy.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    MenuItemDao menuItemDao;

    @Override
    public Set<String> getPermissions(Set<String> roleIds) {
        String[] ids = new String[roleIds.size()];
        int i = 0;
        for (String id : roleIds) {
            ids[i++] = id;
        }
        return menuItemDao.getPermissionsByRoleIds(ids);
    }
}
