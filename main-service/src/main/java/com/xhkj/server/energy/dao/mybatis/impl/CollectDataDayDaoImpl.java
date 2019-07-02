package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.CollectDataDayDao;
import com.xhkj.server.energy.dao.mybatis.mapper.CollectDataDayMapper;
import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDayExample;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CollectDataDayDaoImpl implements CollectDataDayDao {

    @Resource
    private CollectDataDayMapper collectDataDayMapper;

    @Override
    public void save(CollectDataDay collectDataDay) {
        collectDataDayMapper.insert(collectDataDay);
    }

    @Override
    public CollectDataDay get(Integer id) {
        return collectDataDayMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(CollectDataDay collectDataDay) {
        collectDataDayMapper.updateByPrimaryKey(collectDataDay);
    }

    @Override
    public void delete(Integer id) {
        collectDataDayMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<CollectDataDay> getByUniqueIndex(int type, Date dt, String standId, int companyId) {
        CollectDataDayExample example = new CollectDataDayExample();
        example.createCriteria().andTypeEqualTo(type).andDtEqualTo(dt).andStandIdEqualTo(standId).andCompanyIdEqualTo(companyId);
        return collectDataDayMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getAnalysisWater(Page page) {
        return collectDataDayMapper.getAnalysisWater(page);
    }

    @Override
    public List<Map<String, Object>> getAnalysisElectricity(Page page) {
        return collectDataDayMapper.getAnalysisElectricity(page);
    }

    @Override
    public List<Map<String, Object>> getAnalysisHeat(Page page) {
        return collectDataDayMapper.getAnalysisHeat(page);
    }

    @Override
    public float getTotalHeat(Date yesterdayDate) {
        return collectDataDayMapper.getTotalHeat(yesterdayDate);
    }

    @Override
    public List<CollectDataDay> getByDateAndType(int type, Date dt, List<String> stationIds) {
        CollectDataDayExample example = new CollectDataDayExample();
        CollectDataDayExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type).andDtEqualTo(dt).andCompanyIdEqualTo(ShiroUtil.getCompanyId());
        if (stationIds != null && stationIds.size() > 0) {
            criteria.andStationIdIn(stationIds);
        }
        return collectDataDayMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getDataEconByDateTime(Page page) {
        page.getParams().put("company_id", ShiroUtil.getCompanyId());
        return collectDataDayMapper.getDataEconByDateTime(page);
    }

    //**********自定义结束*****////

}
