package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.PredictDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.PredictData;
import com.xhkj.server.energy.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictServiceImpl implements PredictService {

    @Autowired
    private PredictDataDao predictDataDao;

    public PredictData getThePredict() {
        return predictDataDao.getThePredict();
    }

    /**
     * 更新供热基础信息
     *
     * @param predictData 参数中的Tn、q是在前台修改后传过来的
     * @return 更新结果，true成功
     */
    public Boolean updateHaErBinSelective(PredictData predictData) {
        PredictData thePredict = predictDataDao.getThePredict();
        //全年日平均耗热量, Q1=L*F*q*（Tn- Tpj1）/（Tn- Tw1） L=0.864
        Float Q1 = (thePredict.getL() * thePredict.getF() * predictData.getQ() *
                (predictData.getTn() - thePredict.getTpj1()) / (predictData.getTn() - thePredict.getTw1()));

        thePredict.setTn(predictData.getTn());
        thePredict.setQ(predictData.getQ());
        thePredict.setQ1(Q1);

        return predictDataDao.updateHaErBinSelective(thePredict) > 0;
    }
}
