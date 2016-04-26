package me.weego.service.impl;

import me.weego.dao.ShoppingDao;
import me.weego.model.ShoppingModel;
import me.weego.service.ShoppingService;
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
public class ShoppingServiceImpl implements ShoppingService {
    @Resource
    private ShoppingDao shoppingDao;

    @Override
    public List<ShoppingModel> queryByName(String name) {
        checkArgument(StringUtils.isNotBlank(name), "param name should not blank");

        LoggerUtil.logBiz("Shopping query by name", name);
        return shoppingDao.queryByName(name);
    }
}
