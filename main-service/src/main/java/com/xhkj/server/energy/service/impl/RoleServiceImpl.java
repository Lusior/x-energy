package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.RoleDao;
import com.xhkj.server.energy.dao.mybatis.mapper.RoleMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    //TODO 这个是临时写的，因为dao的save没有返回值
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getById(String roleId) {
        return roleDao.getById(roleId);
    }

    /**
     *  保存角色与权限
     * @return
     */
    @Override
    public Boolean saveRoleAndItem(Role role,String[] items){
        if(save(role)){
            return saveRel(items ,String.valueOf(role.getRoleId()));
        }
        return false;
    }

    /**
     * 删除角色
     * //1.删除角色 2.删除角色与操作员的关系  3.删除角色与权限的关系
     *
     * @Title: deleteRole
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param roleId
     * @param @return    设定文件
     * @return Boolean    返回类型
     * @throws
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public Boolean deleteRole(String roleId){
        //1.删除角色
        if(roleDao.deleteByRoleId(roleId) > 0 ){
            //2删除角色与操作员的关系
            roleDao.deleteRoleOprRelByRoleId(roleId);
            //3.删除角色与权限的关系
            roleDao.deleteRoleItemRelByRoleId(roleId);
            return true;
        }
        return false;
    }

    /**
     *  修改角色与权限
     * @return
     */
    @Override
    public Boolean modifyRoleAndItem(Role role,String[] items){
        if(roleMapper.updateByPrimaryKeySelective(role) > 0){
            return saveRel(items ,String.valueOf(role.getRoleId()));
        }
        return false;
    }

    @Override
    public List<Role> getRoleByOprId(String oprId){
        return roleDao.getRoleByOprId(oprId);
    }

    @Override
    public List<Role> getAll(BootstrapTableParams params) {
        return roleDao.getAll(params);
    }

    /**
     * 保存角色与item的关系
     *
     * @Title: saveRel
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return 设定文件
     * @return Boolean 返回类型
     * @throws
     */
    public Boolean saveRel(String[] items, String roleId) {
        // 删除 改角色的关系
        roleDao.deleteRoleItemRelByRoleId(roleId);
        // /添加信息
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleId", roleId);
            map.put("itemId", items[i]);
            list.add(map);
        }
        return roleDao.insertRoleItemRel(list) > 0;

    }

    /**
     * 保存
     *
     * @Title: save
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param role
     * @param @return 设定文件
     * @return Boolean 返回类型
     * @throws
     */
    public Boolean save(Role role) {
        int ret =  roleMapper.insert(role) ;
        return ret > 0;
    }
}
