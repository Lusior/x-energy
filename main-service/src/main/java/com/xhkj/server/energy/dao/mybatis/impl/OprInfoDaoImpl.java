package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.OprInfoDao;
import com.xhkj.server.energy.dao.mybatis.mapper.OprInfoMapper;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class OprInfoDaoImpl implements OprInfoDao {

    @Resource
    private OprInfoMapper oprInfoMapper;

    @Override
    public void save(OprInfo oprInfo) {
        oprInfoMapper.insert(oprInfo);
    }

    @Override
    public OprInfo get(Integer oprId) {
        return oprInfoMapper.selectByPrimaryKey(oprId);
    }

    @Override
    public void update(OprInfo oprInfo) {
        oprInfoMapper.updateByPrimaryKey(oprInfo);
    }

    @Override
    public void delete(Integer oprId) {
        oprInfoMapper.deleteByPrimaryKey(oprId);
    }

    ////*******自定义开始********//
    @Override
    public void deleteOprRoleRel(Map<String, Object> delRoleMap) {
        oprInfoMapper.deleteOprRoleRel(delRoleMap);
    }

    @Override
    public void insertOprRoleRel(List<Map<String, Object>> roleList) {
        oprInfoMapper.insertOprRoleRel(roleList);
    }

    @Override
    public int getCountByLoginId(String loginId) {
        return oprInfoMapper.getCountByLoginId(loginId);
    }

    @Override
    public int updatePwdByOprId(OprInfo oprInfo) {
        return oprInfoMapper.updateByPrimaryKeySelective(oprInfo);
    }

    @Override
    public int modifyPassword(Map<String, Object> paramMap) {
        return oprInfoMapper.modifyPassword(paramMap);
    }

    @Override
    public OprInfo getByLoginId(String loginId) {
        return oprInfoMapper.getByLoginId(loginId);
    }

    @Override
    public List<OprInfo> getAll(BootstrapTableParams params) {
        return oprInfoMapper.getAll(params);
    }

    @Override
    public OprInfo getById(int parseInt) {
        return oprInfoMapper.getById(parseInt);
    }
    //**********自定义结束*****////

}
