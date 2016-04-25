package me.weego.service.impl;

import me.weego.dao.AttractionDao;
import me.weego.model.AttractionModel;
import me.weego.service.AttractionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ln
 */
@Service
public class AttractionServiceImpl implements AttractionService{
    @Resource
    private AttractionDao attractionDao;

    @Override
    public List<AttractionModel> queryByName(String attractions) {
        return attractionDao.queryByName(attractions);
    }
}
