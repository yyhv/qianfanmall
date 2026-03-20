package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallCategoryMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallCategory;
import com.lyqf.qianfanmall.db.domain.QianfanmallCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallCategoryService {
    @Resource
    private QianfanmallCategoryMapper categoryMapper;
    private QianfanmallCategory.Column[] CHANNEL = {QianfanmallCategory.Column.id, QianfanmallCategory.Column.name, QianfanmallCategory.Column.iconUrl};

    public List<QianfanmallCategory> queryL1WithoutRecommend(int offset, int limit) {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<QianfanmallCategory> queryL1(int offset, int limit) {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<QianfanmallCategory> queryL1() {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<QianfanmallCategory> queryByPid(Integer pid) {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<QianfanmallCategory> queryL2ByIds(List<Integer> ids) {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public QianfanmallCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<QianfanmallCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        QianfanmallCategoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return categoryMapper.selectByExample(example);
    }

    public int updateById(QianfanmallCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallCategory category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insertSelective(category);
    }

    public List<QianfanmallCategory> queryChannel() {
        QianfanmallCategoryExample example = new QianfanmallCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
