package com.xhkj.server.energy.shiro;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.service.MenuItemService;
import com.xhkj.server.energy.service.OprInfoLoginLogService;
import com.xhkj.server.energy.service.OprInfoService;
import com.xhkj.server.energy.utils.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private OprInfoService oprInfoService;
    @Autowired
    private MenuItemService menuItemService;
    @Autowired
    OprInfoLoginLogService oprInfoLoginLogService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        OprInfo oprInfo = (OprInfo) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //do nothing  获取角色列表(权限控制中将不适用角色)
        // 获取权限列表
        Set<String> roles = oprInfo.getRoles();
        authorizationInfo.setStringPermissions(menuItemService.getPermissions(roles));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginId = (String) token.getPrincipal();

        // 获取操作员信息
        OprInfo oprInfo = oprInfoService.getByLoginId(loginId);

        //1.账号是否存在 00:注销,01:正常 02:锁定
        if (oprInfo == null || StringUtils.isEmpty(oprInfo.getOprSts())) {
            throw new UnknownAccountException();// 1.没找到帐号
        } else if (oprInfo.getOprSts().equals("02")) {//是否被锁
            throw new LockedAccountException();
        } else if (oprInfo.getOprSts().equals("01")) {
            //操作员账户正常  do nothing
        } else {
            throw new UnknownAccountException(); //包含注销操作
        }

        Map<String, Object> dttm = oprInfoLoginLogService.getLoginLastDt(loginId);
        if (null != dttm) {
            oprInfo.setLastDt((String) dttm.get("CRT_DT"));
            oprInfo.setLastTm((String) dttm.get("CRT_TM"));
        }
        //密码16位为
        String loginPwd = oprInfo.getLoginPwd();
        String salt16 = loginPwd.substring(0, 16);
        // 赋予验证 取出16位以后的值作为密
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(oprInfo, loginPwd.substring(16), getName());
        ByteSource byteSource = new SimpleByteSource(Hex.decode(salt16));
        authenticationInfo.setCredentialsSalt(byteSource);
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
