package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallRegionMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallRegion;
import com.lyqf.qianfanmall.db.domain.QianfanmallRegionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QianfanmallRegionService {

    @Resource
    private QianfanmallRegionMapper regionMapper;

    public List<QianfanmallRegion> getAll(){
        QianfanmallRegionExample example = new QianfanmallRegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }

    public List<QianfanmallRegion> queryByPid(Integer parentId) {
        QianfanmallRegionExample example = new QianfanmallRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public QianfanmallRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<QianfanmallRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        QianfanmallRegionExample example = new QianfanmallRegionExample();
        QianfanmallRegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

}
