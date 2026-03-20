package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallCollectMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallCollect;
import com.lyqf.qianfanmall.db.domain.QianfanmallCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallCollectService {
    @Resource
    private QianfanmallCollectMapper collectMapper;

    public int count(int uid, byte type, Integer gid) {
        QianfanmallCollectExample example = new QianfanmallCollectExample();
        example.or().andUserIdEqualTo(uid).andTypeEqualTo(type).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public List<QianfanmallCollect> queryByType(Integer userId, Byte type, Integer page, Integer limit, String sort, String order) {
        QianfanmallCollectExample example = new QianfanmallCollectExample();
        QianfanmallCollectExample.Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Byte type) {
        QianfanmallCollectExample example = new QianfanmallCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public QianfanmallCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {
        QianfanmallCollectExample example = new QianfanmallCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(QianfanmallCollect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return collectMapper.insertSelective(collect);
    }

    public List<QianfanmallCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        QianfanmallCollectExample example = new QianfanmallCollectExample();
        QianfanmallCollectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }
}
