package com.xhkj.server.energy.shiro;

import com.xhkj.server.energy.service.OprInfoLoginLogService;
import com.xhkj.server.energy.utils.IpUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author fu_wei
 * @ClassName: RetryLimitHashedCredentialsMatcher
 * @Description: TODO(凭证匹配 � ?) 密码重试次数限制 如在1 个小时内密码�?多重�?5 次，如果尝试次数超过5 次就锁定1 小时�?1
 * 小时后可�? 次重试，如果还是重试失败，可以锁定如1 天，以此类推，防止密码被暴力破解。我们�??
 * 过继承HashedCredentialsMatcher，且使用Ehcache记录重试次数和超时时间�??
 * @date
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Autowired
    OprInfoLoginLogService oprInfoLoginLogService;

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    private Cache<String, AtomicInteger> passwordRetryCache;

    protected Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    @Transactional(noRollbackFor = LockedAccountException.class) //不进行回滚
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginId = (String) token.getPrincipal();
        //错误次数   retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(loginId);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(loginId, retryCount);
        }
        //获取记录次数+1
        if (retryCount.incrementAndGet() >= 5) {
            // 5次密码错
            //FIXME 可数据库进行密码锁定
            throw new LockedAccountException();
        }
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest request = (HttpServletRequest) ((WebSubject) subject).getServletRequest();
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            Serializable serializable = subject.getSession().getId();
            if (request != null && serializable != null) {
                String ip = IpUtils.getIpAddr(request);
                oprInfoLoginLogService.writeloginLog(loginId, serializable.toString(), ip);
            } else {
                throw new ConcurrentAccessException();
            }
            // 登录成功移除登录次数缓存
            passwordRetryCache.remove(loginId);
        } else {
            request.setAttribute("loginId", loginId);
        }
        return matches;
    }

}
