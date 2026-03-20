package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallStorageMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallStorage;
import com.lyqf.qianfanmall.db.domain.QianfanmallStorageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallStorageService {
    @Autowired
    private QianfanmallStorageMapper storageMapper;

    public void deleteByKey(String key) {
        QianfanmallStorageExample example = new QianfanmallStorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(QianfanmallStorage storageInfo) {
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setUpdateTime(LocalDateTime.now());
        storageMapper.insertSelective(storageInfo);
    }

    public QianfanmallStorage findByKey(String key) {
        QianfanmallStorageExample example = new QianfanmallStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(QianfanmallStorage storageInfo) {
        storageInfo.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public QianfanmallStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<QianfanmallStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        QianfanmallStorageExample example = new QianfanmallStorageExample();
        QianfanmallStorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
