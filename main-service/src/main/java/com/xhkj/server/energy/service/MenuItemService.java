package com.xhkj.server.energy.service;

import java.util.Set;

public interface MenuItemService {
    Set<String> getPermissions(Set<String> roles);
}
