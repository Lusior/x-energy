package com.xhkj.server.energy.dao.mybatis.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.xhkj.server.energy.dao.mybatis.vo.ValveExample;
import com.xhkj.server.energy.utils.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import com.xhkj.server.energy.dao.ValveDao;
import com.xhkj.server.energy.dao.mybatis.mapper.ValveMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Valve;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ValveDaoImpl implements ValveDao {

    @Resource
    private ValveMapper valveMapper;

    @Override
    public void save(Valve valve) {
        valveMapper.insert(valve);
    }

    @Override
    public Valve get(Integer id) {
        return valveMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Valve valve) {
        valveMapper.updateByPrimaryKey(valve);
    }

    @Override
    public void delete(Integer id) {
        valveMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public int getMaxBatchNumber(String projectId) {
        ValveExample example = new ValveExample();
        if (PageHelper.getLocalPage().getPageSize() == 0) {
            example.setOrderByClause("id desc limit 1");
        } else {
            example.setOrderByClause("id desc ");
        }
        List<Valve> valves = valveMapper.selectByExample(example);
        if (valves != null && valves.size() > 0) {
            return valves.get(0).getBatchNumber();
        }
        return 0;
    }

    @Override
    public List<Valve> getAllLastBatchNumber(String projectId) {
        ValveExample example = new ValveExample();
        ValveExample.Criteria criteria = example.createCriteria().andBatchNumberEqualTo(getMaxBatchNumber(projectId));
        if (Strings.isNotBlank(projectId)) {
            criteria.andProjectIdEqualTo(projectId);
        }
        return valveMapper.selectByExample(example);
    }

    @Override
    public List<Valve> getAllLastBatchNumberForTime(String projectId, String searchTime) {

        ValveExample example = new ValveExample();
        ValveExample.Criteria criteria = example.createCriteria().andBatchNumberEqualTo(Integer.parseInt(searchTime));
        //ValveExample.Criteria criteria = example.createCriteria().andBatchNumberEqualTo(getMaxBatchNumber(projectId));
        if (Strings.isNotBlank(projectId)) {
            criteria.andProjectIdEqualTo(projectId);
        }
        return valveMapper.selectByExample(example);
    }


    //**********自定义结束*****////

}
