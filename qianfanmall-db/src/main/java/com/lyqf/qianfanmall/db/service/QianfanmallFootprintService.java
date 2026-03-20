package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallFootprintMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallFootprint;
import com.lyqf.qianfanmall.db.domain.QianfanmallFootprintExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallFootprintService {
    @Resource
    private QianfanmallFootprintMapper footprintMapper;

    public List<QianfanmallFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        QianfanmallFootprintExample example = new QianfanmallFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(QianfanmallFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public QianfanmallFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public QianfanmallFootprint findById(Integer userId, Integer id) {
        QianfanmallFootprintExample example = new QianfanmallFootprintExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return footprintMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        footprintMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        footprintMapper.insertSelective(footprint);
    }

    public List<QianfanmallFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        QianfanmallFootprintExample example = new QianfanmallFootprintExample();
        QianfanmallFootprintExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }
}
