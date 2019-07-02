package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.ConstantsBase;
import com.xhkj.server.energy.dao.OprInfoDao;
import com.xhkj.server.energy.dao.mybatis.mapper.OprInfoMapper;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.OprInfoService;
import com.xhkj.server.energy.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OprInfoServiceImpl implements OprInfoService {
    @Autowired
    OprInfoDao oprInfoDao;
    @Autowired
    OprInfoMapper oprInfoMapper;

    @Override
    public Boolean save(OprInfo oprInfo) {
        return oprInfoMapper.insert(oprInfo) > 0;
    }

    @Override
    public OprInfo getById(String oprId) {
        return oprInfoDao.getById(Integer.parseInt(oprId));
    }

    /**
     * 更新
     *
     * @Title: modify
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param oprInfo
     * @param @param rolesIds
     * @param @param authRolesIds
     * @param @return 设定文件
     * @return Boolean 返回类型
     * @throws
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean modify(OprInfo oprInfo) {
        // 更新操作员信息
        if (oprInfo == null || oprInfoMapper.updateByPrimaryKeySelective(oprInfo) < 1) {
            // 更新失败
            return false;
        }
        // 密码加密
        if (StringUtils.isNotEmpty(oprInfo.getLoginPwd())) {
            oprInfo.setLoginPwd(new Md5Hash(oprInfo.getLoginPwd(), null).toString());
        }
        // 1更新角色信息 1.删除使用角色  3.添加使用角色
        Map<String, Object> delRoleMap = new HashMap<String, Object>();
        delRoleMap.put("oprId", oprInfo.getOprId());
        delRoleMap.put("relType", ConstantsBase.OPR_ROLE_REL_01);
        oprInfoDao.deleteOprRoleRel(delRoleMap);
        // 3 添加角色与操作员关系信息
        Set<String> roles = oprInfo.getRoles();
        if (roles != null && roles.size() > 0) {
            List<Map<String, Object>> roleList = setAddRoleToList(oprInfo.getOprId(), roles, ConstantsBase.OPR_ROLE_REL_01);
            oprInfoDao.insertOprRoleRel(roleList);
        }
        // 返回结果
        return true;
    }

    @Override
    public Boolean del(OprInfo oprInfo) {
        oprInfo.setOprSts(ConstantsBase.OPR_STS_00);
        return oprInfoMapper.updateByPrimaryKeySelective(oprInfo) > 0;
    }

    @Override
    public Boolean isExistOprInfo(String loginId) {
        return oprInfoDao.getCountByLoginId(loginId) < 1;
    }

    @Override
    public Boolean modifyPwd(OprInfo oprInfo) {
        // 密码加密
        if (oprInfo != null && StringUtils.isNotEmpty(oprInfo.getLoginPwd())) {
            oprInfo.setLoginPwd(oprInfo.encodePwd(oprInfo.getLoginPwd()));
            return oprInfoDao.updatePwdByOprId(oprInfo) > 0;
        }
        return false;
    }

    @Override
    public Boolean modifyPassword(String oldPassword, String newPassword) {
        return modifyPassword(oldPassword, newPassword, null);
    }

    @Override
    public OprInfo getByLoginId(String loginId) {
        try {
            return oprInfoDao.getByLoginId(loginId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OprInfo> getAll(BootstrapTableParams params) {
        return oprInfoDao.getAll(params);
    }

    public boolean modifyPassword(String oldPassword, String newPassword, OprInfo oprInfo) {
        if (oprInfo == null) {
            oprInfo =  (OprInfo) SecurityUtils.getSubject().getPrincipal();
        }
        if(!oprInfo.checkPwd(oldPassword)){
            return false;
        }
        // 修改密码
        String npassword = oprInfo.encodePwd(newPassword);
        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("password", npassword);
        paramMap.put("loginId", oprInfo.getLoginId());
        boolean b =  oprInfoDao.modifyPassword(paramMap) > 0;
        if(b){
            oprInfo.setLoginPwd(npassword);
        }
        return b;
    }

    private List<Map<String, Object>> setAddRoleToList(Integer oprId, Set<String> roles, String relType) {
        List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>(roles.size());
        for (String role : roles) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("oprId", oprId);
            map.put("relType", relType);
            map.put("roleId", role);
            roleList.add(map);
        }
        return roleList;
    }
}
