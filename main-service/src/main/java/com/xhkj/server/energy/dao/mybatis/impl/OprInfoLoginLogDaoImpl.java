package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.OprInfoLoginLogDao;
import com.xhkj.server.energy.dao.mybatis.mapper.OprInfoLoginLogMapper;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfoLoginLog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class OprInfoLoginLogDaoImpl implements OprInfoLoginLogDao {

    @Resource
    private OprInfoLoginLogMapper oprInfoLoginLogMapper;

    @Override
    public void save(OprInfoLoginLog oprInfoLoginLog) {
        oprInfoLoginLogMapper.insert(oprInfoLoginLog);
    }

    @Override
    public OprInfoLoginLog get(Long id) {
        return oprInfoLoginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(OprInfoLoginLog oprInfoLoginLog) {
        oprInfoLoginLogMapper.updateByPrimaryKey(oprInfoLoginLog);
    }

    @Override
    public void delete(Long id) {
        oprInfoLoginLogMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//
    @Override
    public Map<String, Object> getLoginLastDt(String loginId) {
        return oprInfoLoginLogMapper.getLoginLastDt(loginId);
    }
    //**********自定义结束*****////

}
