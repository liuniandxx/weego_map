package me.weego.service.impl;

import me.weego.dao.AreaDao;
import me.weego.model.AreaModel;
import me.weego.service.AreaService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author ln
 */
@Service
public class AreaServiceImpl implements AreaService{

    @Resource
    private AreaDao areaDao;

    @Override
    public List<AreaModel> queryByName(String areaName) {
        checkArgument(StringUtils.isNotBlank(areaName), "param areaName should not blank");
        LoggerUtil.logBiz("Area query by name", areaName);
        return  areaDao.queryByName(areaName);
    }
}
