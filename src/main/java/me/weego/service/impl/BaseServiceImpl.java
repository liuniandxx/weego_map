package me.weego.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liuniandxx on 16-4-23.
 */

@Service
public class BaseServiceImpl implements BaseService{
    @Resource
    MessageSource messageSource;

    public String getKey(String type) {
        checkNotNull(type, "param should with type");
        SpiderKeyEnum spiderKeyEnum = SpiderKeyEnum.getSpiderKeyEnumByType(type);
        checkNotNull(spiderKeyEnum, "error type : " + type);
        String value = messageSource.getMessage(spiderKeyEnum.getKey(), null, null);
        checkArgument(!StringUtils.equals(value, spiderKeyEnum.getKey()), "no result in properties");
        List<String> keys = Lists.newArrayList(Splitter.on(',').split(value));
        int random = new Random().nextInt(keys.size());
        return keys.get(random);
    }
}
