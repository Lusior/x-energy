package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.CompanyDao;
import com.xhkj.server.energy.dao.mybatis.vo.Company;
import com.xhkj.server.energy.service.CompanyService;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyDao companyDao;

    @Override
    public Company getCompany() {
        return companyDao.get(ShiroUtil.getCompanyId());
    }
}
